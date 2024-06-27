package org.engage.Base;

import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.engage.CommonUtilities.JSONUtility;
import org.engage.CommonUtilities.CommonUtility;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Duration;
import java.util.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.engage.Constants.FilePaths.*;

public class BaseUtilities {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    static Logger log = Logger.getLogger(BaseUtilities.class);

    public static void initialization(String url) {
        System.out.println("Browser Initialization Started...");
        String browser = System.getProperty("browser", "chrome");
        Capabilities cap = null;
        CommonUtility.writePropertyFile("OSName", System.getProperty("os.name").toLowerCase(), FilePaths.GLOBAL_CONFIGS_PATH);
        if (browser.equalsIgnoreCase("chrome") || browser.isEmpty() || browser.equalsIgnoreCase("Google chrome")) {
            ChromeOptions co = getOptions();
            driver.set(WebDriverManager.chromedriver().capabilities(co).create());
        } else if (browser.equalsIgnoreCase("Internet Explorer") || browser.equalsIgnoreCase("IE")
                || browser.equalsIgnoreCase("InternetExplorer") || browser.equalsIgnoreCase("Explorer")) {
            WebDriverManager.iedriver().setup();
            driver.set(new InternetExplorerDriver());
        } else if (browser.equalsIgnoreCase("FireFox") || browser.equalsIgnoreCase("FF")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        } else if (browser.equalsIgnoreCase("Edge") || browser.equalsIgnoreCase("Microsoft Edge")) {
            WebDriverManager.edgedriver().setup();
            driver.set(new EdgeDriver());
        } else if (browser.equalsIgnoreCase("headless chrome")) {
            ChromeOptions co = getChromeOptions();
            driver.set(WebDriverManager.chromedriver().capabilities(co).create());
        } else {
            ChromeOptions co = getOptions();
            driver.set(WebDriverManager.chromedriver().capabilities(co).create());
        }
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        try {
            getDriver().get(url);
        } catch (Exception e) {
            System.err.println("Please enter the URL in TestData.json");
        }
        wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
        cap = ((RemoteWebDriver) getDriver()).getCapabilities();
        CommonUtility.writePropertyFile("BrowserVersion", cap.getBrowserVersion(), FilePaths.GLOBAL_CONFIGS_PATH);
    }

    @NotNull
    private static ChromeOptions getOptions() {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--ignore-ssl-errors=yes");
        co.addArguments("--ignore-certificate-errors");
        co.addArguments("--disable-notifications");
        co.addArguments("force-device-scale-factor=0.70");
        co.addArguments("--window-size=1920,1080");
        co.addArguments("--disable-infobars"); // Disables infobars
        co.addArguments("--start-maximized"); // Starts the browser in maximized mode
        co.addArguments("--disable-extensions"); // Disables extensions
        co.addArguments("--disable-popup-blocking"); // Disables popup blocking
        co.addArguments("--disable-dev-shm-usage"); // Overcomes limited resource problems in Docker
        return co;
    }

    @NotNull
    private static ChromeOptions getChromeOptions() {
        ChromeOptions co = new ChromeOptions();
        // Ignore SSL certificate errors
        co.addArguments("--ignore-ssl-errors=yes");
        co.addArguments("--ignore-certificate-errors");
        // Disable notifications
        co.addArguments("--disable-notifications");
        // Disables GPU hardware acceleration
        co.addArguments("--disable-gpu");
        // Disable extensions
        co.addArguments("--disable-extensions");
        // Bypasses OS security model, useful for Docker environments
        co.addArguments("--no-sandbox");
        // Overcomes limited resource problems in Docker
        co.addArguments("--disable-dev-shm-usage");
        // Set the window size
        co.addArguments("--window-size=1920,1080");
        // Set the zoom level to 70% by using force-device-scale-factor
        co.addArguments("--force-device-scale-factor=0.7");
        // Disable infobars
        co.addArguments("--disable-infobars");
        // Allow remote origins
        co.addArguments("--remote-allow-origins=*");
        // Set the browser to headless mode
        co.setHeadless(true);
        return co;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }


    public static String getValueFromUIJSON(List<String> jsonFilePaths, String regex) {
        try {
            String value = Objects.requireNonNull(JSONUtility.getValueFromJSON(jsonFilePaths, "$." + regex)).toString();
            if (value != null) {
                return value;
            }
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to get value from UI JSON with regex '" + regex + "'. Error: " + e.getMessage());
            throw new RuntimeException("Failed to get value from UI JSON with regex '" + regex + "' from any provided environment files.", e);
        }
        throw new RuntimeException("Failed to get value from UI JSON with regex '" + regex + "' from any provided environment files.");
    }


    public static List<String> getTestDataFilePaths(String environment) {
        List<String> filePaths = new ArrayList<>();
        switch (environment.toLowerCase()) {
            case "sit":
                filePaths.add(ENGAGE_TEST_DATA_SIT_JSON);
                filePaths.add(ENGAGE_TEST_DATA_SIT_JSON_PHASE_2); // Add your Phase 2 SIT JSON path
                break;
            case "hope":
                filePaths.add(ENGAGE_TEST_DATA_HOPE_JSON);
                filePaths.add(ENGAGE_TEST_DATA_HOPE_JSON_PHASE_2); // Add your Phase 2 HOPE JSON path
                break;
            default:
                filePaths.add(ENGAGE_TEST_DATA_JSON);
                filePaths.add(ENGAGE_TEST_DATA_JSON_PHASE_2); // Add your Phase 2 default JSON path
                break;
        }
        return filePaths;
    }


    public static Properties readPropertyfile(String propertyFilePath) {
        Properties p = new Properties();
        try (FileReader reader = new FileReader(propertyFilePath)) {
            p.load(reader);
        } catch (IOException e) {
            log.error("Error reading property file: " + e.getMessage());
        }
        return p;
    }

    public static void tearDown() {
        WebDriver driverInstance = getDriver();
        if (driverInstance != null) {
            try {
                driverInstance.quit();
            } catch (Exception e) {
                System.err.println("Exception occurred while quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
                wait.remove();
                try {
                    Thread.sleep(300); // Ensure sleep is not interrupted
                } catch (InterruptedException ie) {
                    System.err.println("Thread sleep was interrupted: " + ie.getMessage());
                    // Optionally, re-interrupt the current thread if needed
                    Thread.currentThread().interrupt();
                }
            }
        }
    }





    public static void sendKeysToElement(String locator, String text) {
        try {
            Objects.requireNonNull(getElement(locator)).sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to element '" + locator + "'.", e);
        }
    }


    private static String getLocatorType(String locator) {
        return splitString(locator, "]: ", 0).substring(1).toUpperCase();
    }

    private static String getLocatorValue(String locator) {
        return splitString(locator, "]: ", 1);
    }

    private static String splitString(String locator, String regEx, int index) {
        return locator.split(regEx)[index];
    }

    public static void clear(String locator) {
        Objects.requireNonNull(getElement(locator)).clear();
    }

    public static WebElement getElement(String locator, boolean... failOnInvisible) {
        boolean fail = failOnInvisible.length <= 0 || failOnInvisible[0]; // Default value is true
        try {
            String locatorType = getLocatorType(locator);
            String locatorValue = getLocatorValue(locator);
            WebElement element = null;
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            switch (locatorType) {
                case "XPATH":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                    break;
                case "CSS":
                case "CSSSELECTOR":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                    break;
                case "NAME":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
                    break;
                case "CLASSNAME":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
                    break;
                case "LINKTEXT":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                    break;
                case "PARTIALLINKTEXT":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
                    break;
                case "TAGNAME":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
                    break;
                case "ID":
                    element = waitForElement(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid locator type: " + locatorType);
            }
            jse.executeScript("arguments[0].style.border='cyan solid 3px'", element);
            return element;
        } catch (NoSuchElementException | TimeoutException e) {
            if (fail) {
                return null;
            } else {
                ExtentSparkReport.getExtentLogger().warning("Element is not visible on the UI '" + locator + "'. Message: " + e.getMessage());
                System.out.println("Element is not visible on the UI '" + locator + "'. Message: " + e.getMessage());
            }
        } catch (Exception e) {
            Assert.fail("Failed to get element '" + locator + "'.", e);
        }
        return null;
    }


    private static WebElement waitForElement(Function<? super WebDriver, WebElement> condition) {
        return getWait().until(condition);
    }

    public static WebElement clickOnElement(String locator, boolean newTab) {
        WebElement ele = null;
        try {
            if (locator == null) {
                throw new IllegalArgumentException("Locator cannot be null.");
            }

            ele = getElement(locator);

            if (ele == null) {
                throw new NoSuchElementException("Element not found with locator: " + locator);
            }

            if (verifyElementIsClickable(ele)) {
                if (newTab) {
                    Actions actions = new Actions(getDriver());
                    actions.keyDown(Keys.CONTROL).click(ele).keyUp(Keys.CONTROL).perform();
                } else {
                    ele.click();
                }
                ExtentSparkReport.getExtentLogger().info("Clicked successfully on element with locator: " + locator);
            } else {
                Assert.fail("Element with locator: " + locator + " is not clickable.");
                ExtentSparkReport.getExtentLogger().fail("Element with locator: " + locator + " is not clickable.");
            }
        } catch (IllegalArgumentException e) {
            Assert.fail("Invalid argument: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Invalid argument: " + e.getMessage());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Element not found: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Failed to click on element with locator: " + locator + ". Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to click on element with locator: " + locator + ". Error message: " + e.getMessage());
        }
        return ele;
    }



    public static String switchToNewTab() {
        try {
            String parentTab = getDriver().getWindowHandle();
            Set<String> handles = getDriver().getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(parentTab)) {
                    getDriver().switchTo().window(handle);
                    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                    return parentTab + ";" + handle;
                }
            }
            return parentTab + ";";
        } catch (Exception e) {
            Assert.fail("Failed to switch to new tab. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to switch to new tab. Error message: " + e.getMessage());
            return "";
        }
    }

    public static void switchToChildTabAndClose(String childTab) {
        try {
            getDriver().switchTo().window(childTab).close();
        } catch (Exception e) {
            Assert.fail("Failed to switch to child tab and close. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to switch to child tab and close. Error message: " + e.getMessage());
        }
    }

    public static void switchToParentTab(String parentTab) {
        try {
            getDriver().switchTo().window(parentTab);
        } catch (Exception e) {
            Assert.fail("Failed to switch to parent tab. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to switch to parent tab. Error message: " + e.getMessage());
        }
    }

    public static WebElement isElementVisible(String locator, boolean... failOnInvisible) {
        boolean fail = failOnInvisible.length <= 0 || failOnInvisible[0]; // Default value is true
        WebElement ele = null;
        try {
            if (locator == null) {
                throw new IllegalArgumentException("Locator cannot be null.");
            }

            ele = getElement(locator, fail); // Pass the fail parameter

            if (isElementDisplayed(ele)) {
                ExtentSparkReport.getExtentLogger().info("Element is visible with locator: " + locator);
            } else {
                if (fail) {
                    Assert.fail("Element with locator: " + locator + " is not visible.");
                } else {
                    ExtentSparkReport.getExtentLogger().warning("Element with locator: " + locator + " is not visible.");
                    System.out.println("Element with locator: " + locator + " is not visible.");
                }
            }
        } catch (IllegalArgumentException e) {
            ExtentSparkReport.getExtentLogger().fail("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Exception occurred while checking visibility of element with locator: " + locator + ". Error message: " + e.getMessage());
        }
        return ele;
    }



    public static String getTitle() {
        return getDriver().getTitle();
    }

    public static boolean verifyElementIsClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

        try {
            if (element == null) {
                log.error("Element cannot be null.");
                return false;
            }
            wait.until(ExpectedConditions.elementToBeClickable(element));
            log.info("Element is clickable");
            return true;
        } catch (TimeoutException e) {
            log.info("Element is not clickable within the specified timeout.");
            return false;
        } catch (Exception e) {
            log.error("An error occurred while verifying element click ability: " + e.getMessage());
            return false;
        }
    }



    public static boolean isElementDisplayed(WebElement element) {
        if (element == null){
            return false;
        }
        try {
            return element.isDisplayed();

        } catch (Exception e) {
            log.info("Element is not visible");
            return false;
        }

    }

      public static String getTextOfElement(String element) {
        try {
            WebElement webElement = getElement(element);
            if (webElement == null) {
                return null;
            }
            return webElement.getText();
        } catch (Exception e) {
            String errorMessage = "Failed to get text of element '" + element + "'. Error message: " + e.getMessage();
            Assert.fail(errorMessage);
            ExtentSparkReport.getExtentLogger().fail(errorMessage);
            return null;
        }
    }


    public static String getTextOfElements(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to get text of element. Error message: " + e.getMessage());
            return null;
        }
    }


    public static void assertElementText(String element, String expectedText) {
        try {
            WebElement ele = getElement(element);
            if (ele == null) {
                ExtentSparkReport.getExtentLogger().fail("Element '" + element + "' not found.");
                return;
            }
            String actualText = ele.getText();
            System.out.println(actualText);
            Assert.assertEquals(actualText, expectedText, "Expected text: '" + expectedText + "', but got: '" + actualText + "'");
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to assert text of element '" + element + "'. Error message: " + e.getMessage());
        }
    }


    public static void assertURL(String expectedURL) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe(expectedURL));
            String actualURL = getDriver().getCurrentUrl();
            Assert.assertEquals(actualURL, expectedURL, "Expected URL: '" + expectedURL + "', but got: '" + actualURL + "'");
        } catch (TimeoutException e) {
            String actualURL = getDriver().getCurrentUrl();
            Assert.fail("Timeout waiting for URL to be: '" + expectedURL + "'. Current URL: '" + actualURL + "'. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Timeout waiting for URL to be: '" + expectedURL + "'. Current URL: '" + actualURL + "'. Error message: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Failed to assert URL. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to assert URL. Error message: " + e.getMessage());
        }
    }



    public static void assertStringElementText(String actualText, String expectedText) {
        try {
            Assert.assertEquals(actualText, expectedText, "Expected text: '" + expectedText + "', but got: '" + actualText + "'");
            System.out.println("Actual: " + actualText + " Expected:" + expectedText);
        } catch (AssertionError e) {
            ExtentSparkReport.getExtentLogger().fail("Assertion error: " + e.getMessage());
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to assert text. Error message: " + e.getMessage());
        }
    }


    public static String decryptPasswordNormal(String encryptedPassword) {
        try {
            byte[] decodedBytes = Base64.decodeBase64(encryptedPassword.getBytes());
            return new String(decodedBytes);
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to decrypt password. Error message: " + e.getMessage());
            return null;
        }
    }


    public static void scrollDown(int pixels) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("window.scrollBy(0, " + pixels + ")");
            ExtentSparkReport.getExtentLogger().info("Scrolled down by " + pixels + " pixels");
            Thread.sleep(1000);
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to scroll down. Error message: " + e.getMessage());
        }
    }

    public static void performActionClickElement(String locator) {
        try {
            WebElement ele = getElement(locator);
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center', behavior: 'smooth'});", ele);
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(ele));
            assert ele != null;
            ele.click();
        } catch (Exception e) {
            System.out.println("Error clicking element: " + e.getMessage());
        }
    }

    public static boolean isElementSelected(String locator) {
        try {
            WebElement ele = getElement(locator);
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("arguments[0].scrollIntoView(true);", ele);
            assert ele != null;
            return ele.isSelected();
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to check if element is selected. Error message: " + e.getMessage());
            return false;
        }
    }

    public static void waitForElementToBeClickable(String locator) {
        try {
            WebElement element = getElement(locator);
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ExtentSparkReport.getExtentLogger().info("Element with locator '" + locator + "' is clickable");
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to wait for element with locator '" + locator + "' to be clickable. Error message: " + e.getMessage());
        }
    }

    public static void waitForElementToBeVisible(String locator) {
        try {
            WebElement element = getElement(locator);
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(element));
            ExtentSparkReport.getExtentLogger().info("Element with locator '" + locator + "' is Visible");
        } catch (Exception e) {
            Assert.fail("Failed to wait for element with locator '" + locator + "' to be clickable. Error message: " + e.getMessage());
        }
    }

    public static String generateRandomAlphanumericString() {
        try {
            String baseString = "Automation";
            int randomSuffix = (int) (Math.random() * 100000);
            return baseString + randomSuffix;
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to generate random alphanumeric string. Error message: " + e.getMessage());
            return null;
        }
    }

    public static boolean waitForPageToLoad() {
        try {
            // Initialize the WebDriverWait instance using ThreadLocal if not already done
            if (wait.get() == null) {
                wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
            }
            // Use the ThreadLocal WebDriverWait instance
            getWait().until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public static String getAttribute(String locator, String attrName) {
        String value = "";
        try {
            WebElement element = getElement(locator);
            wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
            wait.get().until(ExpectedConditions.elementToBeClickable(element));
            assert element != null;
            value = element.getAttribute(attrName);
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to wait for element with locator '" + locator + "' to be clickable. Error message: " + e.getMessage());
        }
        return value;

    }

    public static List<WebElement> getElements(String locator) {
        try {
            String locatorType = getLocatorType(locator);
            String locatorValue = getLocatorValue(locator);
            List<WebElement> elements = null;
            switch (locatorType) {
                case "XPATH":
                    elements = getDriver().findElements(By.xpath(locatorValue));
                    break;
                case "CSS":
                case "CSSSELECTOR":
                    elements = getDriver().findElements(By.cssSelector(locatorValue));
                    break;
                case "NAME":
                    elements = getDriver().findElements(By.name(locatorValue));
                    break;
                case "CLASSNAME":
                    elements = getDriver().findElements(By.className(locatorValue));
                    break;
                case "LINKTEXT":
                    elements = getDriver().findElements(By.linkText(locatorValue));
                    break;
                case "PARTIALLINKTEXT":
                    elements = getDriver().findElements(By.partialLinkText(locatorValue));
                    break;
                case "TAGNAME":
                    elements = getDriver().findElements(By.tagName(locatorValue));
                    break;
                case "ID":
                    elements = getDriver().findElements(By.id(locatorValue));
                    break;
            }
            return elements;
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to get element '" + locator + "'. Error message: " + e.getMessage());
            return null;
        }
    }

    public static void performJSClickElement(String locator) {
        try {
            WebElement ele = getElement(locator);
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("arguments[0].click()", ele);
        } catch (Exception e) {
            System.out.println("Error clicking element: " + e.getMessage());
        }
    }

    public static void assertThat(String actual, String expected, String passMsg, String failMsg) {
        if (actual.equals(expected)) {
            ExtentSparkReport.getExtentLogger().log(Status.PASS, passMsg);
            Assert.assertTrue(true);  // This line is optional since the assertion is always true here
        } else {
            //ExtentSparkReport.getExtentLogger().log(Status.FAIL, failMsg);
            Assert.fail(failMsg);  // Provide fail message for better traceability
        }
    }

    public static void assertThat(boolean status, String passMsg, String failMsg) {
        if (status) {
            Assert.assertTrue(true);
            System.out.println(passMsg);
            ExtentSparkReport.getExtentLogger().log(Status.PASS, passMsg);
        }else{
            System.out.println(failMsg);
            Assert.fail(failMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, failMsg);

        }
    }

    public static void assertThatContains(String actual, String expected, String passMsg, String failMsg) {
        if (actual.contains(expected)) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().log(Status.PASS, passMsg);
        }else{
            Assert.fail(failMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, failMsg);
        }
    }

    public static void assertThatNotContains(String actual, String expected, String passMsg, String failMsg) {
        if (!actual.contains(expected)) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().log(Status.PASS, passMsg);
        }else{
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, failMsg);
            Assert.fail();
        }
    }

    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public static void navigateToBack() {
        getDriver().navigate().back();
    }
    public static void navigateRefresh() {
        getDriver().navigate().refresh();
    }

    public static void selectOption(String locator, String option) {
        String[] optionArray = option.split("=");
        String type = optionArray[0];
        String opt = optionArray[1];
        Select select = new Select(Objects.requireNonNull(getElement(locator)));
        switch (type.toUpperCase()) {
            case "INDEX":
                select.selectByIndex(Integer.parseInt(opt));
                break;
            case "VALUE":
                select.selectByValue(opt);
                break;
            case "TEXT":
                select.selectByVisibleText(opt);
                break;
        }
    }

    public static List<String> getAllDropdownOption(String locator) {
        Select select = new Select(Objects.requireNonNull(getElement(locator)));
        List<WebElement> elements = select.getOptions();
        List<String> options = new ArrayList<>();
        for (WebElement element : elements) {
            String opt = element.getText();
            if(!opt.isEmpty()){
                options.add(opt);
            }
        }
        return options;
    }


    public static String extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new AssertionError("No number found in the text: " + text);
        }
    }

    public static int countRowsWithDate(List<WebElement> rows, String date) {
        int count = 0;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            for (WebElement cell : cells) {
                if (cell.getText().equals(date)) {
                    count++;
                }
            }
        }
        return count;
    }
    public static void selectValueFromDropdown(String locator, String valueToSelect) {
        Select select = new Select(Objects.requireNonNull(getElement(locator)));
        select.selectByVisibleText(valueToSelect);
    }

    public static void refreshPage()
    {
        getDriver().navigate().refresh();
    }

    public static void doubleClick(WebElement locator)
    {
        Actions actions = new Actions(getDriver());
        actions.doubleClick(locator).perform();
    }

    public static void dragAndDrop(WebElement locator, int xOffset)
    {
        Actions actions = new Actions(getDriver());
        actions.dragAndDropBy(locator, xOffset, 0).perform();
    }

    public static String convertDateToMonth(String dateFormat,String date) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Date parsedDate = inputFormat.parse(date);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        return outputFormat.format(parsedDate);
    }

    public static String convertDateToMonth(String inputDateFormat, String outputDateFormat, String date) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        Date parsedDate = inputFormat.parse(date);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        return outputFormat.format(parsedDate);
    }

    public static String generateRandomString(int length) {
        char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length);
            sb.append(characters[index]);
        }
        return sb.toString();
    }

    public static void switchToFrameWithIndex(int indexValue)
    {
        getDriver().switchTo().frame(indexValue);
    }
    public static void switchToFrameWithElement(WebElement frameElement) {
        getDriver().switchTo().frame(frameElement);
    }
    public static void switchToFrameWithNameOrId(String nameOrId) {
        getDriver().switchTo().frame(nameOrId);
    }
    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    public static void resetFileInputElement(WebElement locator)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].value = '';", locator);
    }

    public static String getElementValueJS(WebElement locator)
    {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return arguments[0].value;", locator);
    }
    public static String generateRandomEmail(int localPartLength) {
        char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(localPartLength);
        for (int i = 0; i < localPartLength; i++) {
            int index = random.nextInt(characters.length);
            sb.append(characters[index]);
        }
        String localPart = sb.toString();
        String[] domains = {"example.com", "test.com", "domain.com"};
        String domain = domains[random.nextInt(domains.length)];
        return localPart + "@" + domain;
    }

    public static void clickAndHold(WebElement element, WebElement element1) {

        try {
            Actions actions = new Actions(getDriver());
            actions.dragAndDrop(element, element1);
            ExtentSparkReport.getExtentLogger().info("Clicked successfully on element with locator: ");
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Failed to click on element with locator. Error message: " + e.getMessage());
        }


    }

    public static void clickElement(String xpath) {
        try {
            Objects.requireNonNull(getElement(xpath)).click();
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    public static Map<String, Integer> getTableHeaderIndex(String locator) {
        Map<String, Integer> headers = new LinkedHashMap<>();
        List<WebElement> elements = getElements(locator);
        for (int i = 0; i < Objects.requireNonNull(elements).size(); i++) {
            String header = elements.get(i).getText();
            headers.put(header, i + 1);
        }
        return headers;
    }

    public static List<String> getHeaders(String locator) {
        List<String> headers = new ArrayList<>();
        List<WebElement> elements = getElements(locator);
        for (int i = 0; i < Objects.requireNonNull(elements).size(); i++) {
            String header = elements.get(i).getText();
            headers.add(header);
        }
        return headers;
    }

    public static Map<Integer, Map<String, String>> getDateFromTable(String headerXpath, String rowsXpath, String dataXpath) {
        Map<Integer, Map<String, String>> data = new LinkedHashMap<>();
        Map<String, String> da;
        List<String> headers = getHeaders(headerXpath);
        int noOfRows = Objects.requireNonNull(getElements(rowsXpath)).size();
        for (int i = 0; i < noOfRows; i++) {
            da = new LinkedHashMap<>();
            dataXpath = dataXpath.replace("rowIndex", String.valueOf(i + 1));
            for (int j = 0; j < headers.size(); j++) {
                dataXpath = dataXpath.replace("colIndex", String.valueOf(j + 1));
                String data1 = getDriver().findElement(By.xpath(dataXpath)).getText();
                da.put(headers.get(j), data1);
            }
            dataXpath = dataXpath.replace(String.valueOf(headers.size() + 1), "colIndex");
            dataXpath = dataXpath.replaceFirst(String.valueOf(i + 1), "rowIndex");
            data.put(i + 1, da);
        }
        return data;
    }


    /*public static String generateRandomString(int count){
        return RandomStringUtils.random(count, true, true);
    }*/

    public static void switchToFrame(String name){
        WebElement element = getElement(name);
        getDriver().switchTo().frame(element);
    }

    public static void switchToDefault(){
        getDriver().switchTo().defaultContent();
    }


    public static int getMonthInNumFormat(String mon){
        Map<String, Integer> month = new LinkedHashMap<>();
        month.put("JAN", 1);
        month.put("FEB", 2);
        month.put("MAR", 3);
        month.put("APR", 4);
        month.put("MAY", 5);
        month.put("JUN", 6);
        month.put("JUL", 7);
        month.put("AUG", 8);
        month.put("SEP", 9);
        month.put("OCT", 10);
        month.put("NOV", 11);
        month.put("DEC", 12);
        return month.get(mon.toUpperCase());
    }

    public static String getMonNumToStringFormat(int num){
        Map<Integer, String> month = new LinkedHashMap<>();
        month.put(1, "JAN");
        month.put(2, "FEB");
        month.put(3, "MAR");
        month.put(4, "APR");
        month.put(5, "MAY");
        month.put(6, "JUN");
        month.put(7, "JUL");
        month.put(8, "AUG");
        month.put(9, "SEP");
        month.put(10, "OCT");
        month.put(11, "NOV");
        month.put(12, "DEC");
        return month.get(num);
    }

    public static void switchToParentTabAndCloseChildTab(String parent) {
        try {
            Set<String> windowIds = getDriver().getWindowHandles();
            for(String wind : windowIds){
                if(!wind.equals(parent)){
                    getDriver().switchTo().window(wind).close();
                    break;
                }
            }
            getDriver().switchTo().window(parent);
        } catch (Exception e) {
            Assert.fail("Failed to switch to child tab and close. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to switch to child tab and close. Error message: " + e.getMessage());
        }
    }

    public static String convertDateToMonth(String date) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date parsedDate = inputFormat.parse(date);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
        return outputFormat.format(parsedDate);
    }

    public static int getNextMonInNum(String mon){
        int nextMon = 0;
        int current = getMonthInNumFormat(mon)+1;
        if(current > 12){
            nextMon = 1;
        }else{
            nextMon = current;
        }
        return nextMon;
    }

    public static int getPrevMonInNum(String mon){
        int prevMon = 0;
        int current = getMonthInNumFormat(mon)-1;
        if(current < 1){
            prevMon = 12;
        }else{
            prevMon = current;
        }
        return prevMon;
    }

    public static void hoverElement(String locator){
        try {
            WebElement element = getElement(locator);
            Actions a = new Actions(getDriver());
            a.moveToElement(element).build().perform();
        }catch (Exception e){

        }
    }

    public static String getSelectedOption(String locator){
        Select select = new Select(Objects.requireNonNull(getElement(locator)));
        return select.getFirstSelectedOption().getText();
    }

    public static void waitForTextToBeVisible(String locator, String text, int timeoutInSeconds) {
        try {
            wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)));
            getWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locator), text));
            ExtentSparkReport.getExtentLogger().info("Text '" + text + "' is visible in the element with locator '" + locator + "'");
        } catch (Exception e) {
            Assert.fail("Failed to wait for text '" + text + "' in the element with locator '" + locator + "'. Error message: " + e.getMessage());
        }

    }

    public static LocalDate getRandomDate(LocalDate localDate, String expDate){
        String[] da = expDate.split("=");
        if(da[0].equals("minus")){
            if(da[1].contains("month") ){
                localDate = localDate.minusMonths(Integer.parseInt(da[2]));
            }else if(da[1].contains("day")){
                localDate = localDate.minusDays(Integer.parseInt(da[2]));
            }
        }else{
            if(da[1].contains("month") ){
                localDate = localDate.plusMonths(Integer.parseInt(da[2]));
            }else if(da[1].contains("day")){
                localDate = localDate.plusDays(Integer.parseInt(da[2]));
            }
        }
        return localDate;
    }
    public static String getAttribute(WebElement element, String attrName) {
        String value = "";
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            assert element != null;
            value = element.getAttribute(attrName);
        } catch (Exception e) {
            Assert.fail("Failed to wait for element with locator to be clickable. Error message: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Failed to wait for element with locator to be clickable. Error message: " + e.getMessage());
        }
        return value;

    }


}
