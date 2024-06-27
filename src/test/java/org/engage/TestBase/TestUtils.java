package org.engage.TestBase;

import com.aventstack.extentreports.ExtentTest;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Database.DatabaseConnection;
import org.engage.POM.*;
import org.engage.POM_Phase_2.StartServiceResidentialPagePOM;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import java.lang.reflect.Method;
import java.util.List;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Constants.FilePaths.ENGAGE_TEST_DATA_HOPE_JSON;
import static org.engage.POM.LoginPagePOM.enterUserName;

public class TestUtils extends ExtentSparkReport {

    public LoginPagePOM loginPagePOM;
    public RegistrationPOM registrationPOM;

    public HomePagePOM homePagePOM;
    public BillingDeliveryPreferencePagePOM billingRefPOM;
    public ChangePasswordPagePOM changePasswordPagePOM;
    public AccountSummaryDropDownPOM accountSummaryDropDownPOM;
    public AccountSettingPagePOM accountSettingPagePOM;
    public BillingAndUsageHistoryPagePOM billingAndUsageHistoryPagePOM;
    public SentItemsPagePOM sentItemsPagePOM;
    public InboxPagePOM inboxPagePOM;
    public InboxPOM inboxPOM;
    public ContactInformationPagePOM contactInformationPagePOM;
    protected StartServiceResidentialPagePOM startServiceResidentialPagePOM;

    protected static List<String> testDataFilePaths;
    public ForgotUsernamePagePOM forgotUsernamePagePOM;
    public ForgotPasswordPagePOM forgotPasswordPagePOM;
    protected SoftAssert softAssert;
    public DocumentsPagePOM documentsPagePOM;
    public ComposeMessagePagePOM composeMessagePagePOM;

    @BeforeSuite(alwaysRun = true)
    public void initialiseBeforeSuite() {
        ExtentSparkReport.initialise();
        DatabaseConnection.getConnection(DatabaseConnection.DatabaseType.MariaDB);
        DatabaseConnection.getConnection(DatabaseConnection.DatabaseType.CIS);
    }

    @BeforeMethod(alwaysRun = true)
    public void launchBrowser(Method method, ITestContext context) {
        String env = System.getProperty("environment", "TestData");
        testDataFilePaths = getTestDataFilePaths(env);
        initialization(getValueFromUIJSON(testDataFilePaths, "applicationURL"));
        loginPagePOM = new LoginPagePOM(testDataFilePaths);
        homePagePOM = new HomePagePOM(testDataFilePaths);
        registrationPOM = new RegistrationPOM(testDataFilePaths);
        billingRefPOM = new BillingDeliveryPreferencePagePOM(testDataFilePaths);
        changePasswordPagePOM = new ChangePasswordPagePOM(testDataFilePaths);
        forgotUsernamePagePOM = new ForgotUsernamePagePOM(testDataFilePaths);
        forgotPasswordPagePOM = new ForgotPasswordPagePOM(testDataFilePaths);
        softAssert = new SoftAssert();
        documentsPagePOM = new DocumentsPagePOM(testDataFilePaths);
        accountSummaryDropDownPOM = new AccountSummaryDropDownPOM(testDataFilePaths);
        accountSettingPagePOM = new AccountSettingPagePOM(testDataFilePaths);
        billingAndUsageHistoryPagePOM = new BillingAndUsageHistoryPagePOM(testDataFilePaths);
        composeMessagePagePOM = new ComposeMessagePagePOM(testDataFilePaths);
        sentItemsPagePOM = new SentItemsPagePOM(testDataFilePaths);
        inboxPagePOM = new InboxPagePOM(testDataFilePaths);
        inboxPOM = new InboxPOM(testDataFilePaths);
        contactInformationPagePOM = new ContactInformationPagePOM(testDataFilePaths);
        startServiceResidentialPagePOM = new StartServiceResidentialPagePOM(testDataFilePaths);

        Test test = method.getAnnotation(Test.class);
        setMethod(method, test);

    }

    public void loginApplication(String userName, String password, List<String> environments) {
        try {
            ExtentTest logger = ExtentSparkReport.getExtentLogger();
            logger.info("Entering Username field value: " + userName);
            System.out.println("Entering Username field value: " + userName);
            String userNameValue = getValueFromUIJSON(environments, userName);
            logger.info("Entering Password field value: " + password);
            System.out.println("Entering Password field value: " + password);
            String passwordValue = getValueFromUIJSON(environments, password);

            for (String environment : environments) {
                if (environment.equals(ENGAGE_TEST_DATA_HOPE_JSON)) {
                    scrollDown(500);
                }
            }

            enterUserName(userNameValue);
            loginPagePOM.enterPassword(passwordValue);

            logger.info("Clicking on Login Button");
            System.out.println("Clicking on Login Button");
            loginPagePOM.performLogin();
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Login An error occurred: " + e.getMessage());
            System.out.println("Login An error occurred: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void generateHtmlReport(ITestResult result)  {
        ExtentSparkReport.generateReport(result);
        ExtentSparkReport.clearThreadLocals();
        tearDown();

    }

    @AfterSuite(alwaysRun = true)
    public void closeDBConnection() {
        DatabaseConnection.closeConnection(DatabaseConnection.DatabaseType.MariaDB);
        DatabaseConnection.closeConnection(DatabaseConnection.DatabaseType.CIS);
        ExtentSparkReport.flushReports();
    }
}
