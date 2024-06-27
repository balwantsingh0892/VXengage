package org.engage.CommonUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.engage.Constants.FilePaths;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.engage.Base.BaseUtilities.getDriver;

//import static org.engage.Base.BaseUtilities.driver;

public class ExtentSparkReport {
    public static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> extentLogger = new ThreadLocal<>();

    public static void initialise() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(FilePaths.EXTENT_HTML_REPORT_PATH + generateDynamicName() + ".html");
        setConfig();
    }

    private static void setConfig() {
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("VXengage_Automation");
        spark.config().setReportName("AutomationReport_" +
                CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("ProjectName") + "_" +
                CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("TeamName"));
        spark.config().setOfflineMode(true);
        extent.attachReporter(spark);
        setSystemInfo();
    }

    private static void setSystemInfo() {
        try {
            extent.setSystemInfo("SYSTEM NAME", InetAddress.getLocalHost().getHostName());
            extent.setSystemInfo("BROWSER NAME", CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("browser"));
            extent.setSystemInfo("OS Name", CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("OSName"));
            extent.setSystemInfo("BROWSER VERSION", CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("BrowserVersion"));
            extent.setSystemInfo("APPLICATION URL", CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("applicationURL"));
            extent.setSystemInfo("ENVIRONMENT", CommonUtility.readPropertyfile(FilePaths.EXTENT_REPORT_PROPERTIES).getProperty("Environment"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setMethod(Method m, Test result) {
        String testCaseName = result.description();
        String methodName = m.getName();
        String testName = getClass().getSimpleName();
        System.out.println("Setting up test: " + testName + " : " + methodName + "() - Test Case: " + testCaseName);
        ExtentTest extentTest = extent.createTest(testName + " : " + methodName + "()", "Test Case: " + testCaseName);
        test.set(extentTest);
        extentLogger.set(extentTest.createNode("Test Steps"));
    }

    public static void generateReport(ITestResult result) {
        ExtentTest logger = extentLogger.get();
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, "Test Case Failed: " + result.getThrowable());
            String screenshotPath = getScreenshot(result);
            logger.addScreenCaptureFromPath(screenshotPath, "Failed Screenshot");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, "Test Case Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, "Test Case Skipped");
        }
        String duration = getDuration(result);
        logger.info("Test Case Duration: " + duration);
        extent.flush();
    }

    private static String getDuration(ITestResult result) {
        long durationMillis = result.getEndMillis() - result.getStartMillis();
        long seconds = (durationMillis / 1000) % 60;
        long minutes = (durationMillis / (1000 * 60)) % 60;
        long hours = (durationMillis / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, durationMillis % 1000);
    }

    private static String generateDynamicName() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-ms");
        return "VXengage - " + dateFormat.format(new Date());
    }

    private static String getScreenshot(ITestResult result) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-h-m-s");
        TakesScreenshot screenshot = ((TakesScreenshot) getDriver());
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        String path = FilePaths.SCREENSHOTS_PATH + result.getName() + dateFormat.format(new Date()) + ".png";
        File dest = new File(path);
        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            extentLogger.get().fail("Could not capture screenshot: " + e.getMessage());
        }
        return path;
    }

    // Getter and setter for test
    public static ExtentTest getTest() {
        return test.get();
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    // Getter and setter for extentLogger
    public static ExtentTest getExtentLogger() {
        return extentLogger.get();
    }

    public static void setExtentLogger(ExtentTest logger) {
        extentLogger.set(logger);
    }

    public static void flushReports() {
        extent.flush();
    }

    // Clear ThreadLocal variables
    public static void clearThreadLocals() {
        test.remove();
        extentLogger.remove();
    }
}
