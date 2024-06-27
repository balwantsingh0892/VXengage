package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Database.DatabaseConnection;
import org.engage.Database.EngageQueries;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

//Sceanrio_03

import static org.engage.Database.DatabaseOperations.executeInsertUpdateDelete;

public class LoginPageBaseTest extends TestUtils {

    @Test(description = "To Verify the Login Functionality with Invalid Credentials 1", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void loginInvalidUsernameValidPassword() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        executeInsertUpdateDelete(EngageQueries.UPDATE_IP_ADDRESS_COUNT_MORE_THAN_15, DatabaseConnection.DatabaseType.MariaDB);
        loginApplication("invalidCredentials[0].invalidUserName", "invalidCredentials[0].validPassword",testDataFilePaths);
        loginPagePOM.getInvalidUsernameErrorMessageAndValidate();
    }

    @Test(description = "To Verify the Login Functionality with Invalid Credentials 2", priority = 2, alwaysRun = true, groups = "Phase1_Group1" )
    public void loginValidUsernameInvalidPassword() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginApplication("invalidCredentials[1].ValidUsername", "invalidCredentials[1].InvalidPassword",testDataFilePaths);
        loginPagePOM.getInvalidPasswordErrorMessageAndValidate();
    }
    @Test(description = "To Verify the Login Functionality with Invalid Credentials 3", priority = 3, alwaysRun = true, groups = "Phase1_Group1")
    public void loginInvalidUsernameInvalidPassword() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginApplication("invalidCredentials[2].InvalidUsername", "invalidCredentials[2].InvalidPassword",testDataFilePaths);
        loginPagePOM.getInvalidUserAndPasswordErrorMessageAndValidate();
    }
    @Test(description = "To Verify Login Page with Empty Fields and error messages", priority = 4, alwaysRun = true, groups = "Phase1_Group1")
    public void loginPageErrorEmptyFields() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.loginEmptyCredentials();
    }
    @Test(description = "To Verify Login Page with Username Fields and error messages", priority = 5, alwaysRun = true, groups = "Phase1_Group1")
    public void loginPageEmptyUsernameField(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.loginEmptyUsername();
    }
    @Test(description = "To Verify Login Page with Empty Password Fields and error messages", priority = 6, alwaysRun = true, groups = "Phase1_Group1")
    public void loginPageEmptyPasswordField() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.loginEmptyPassword();
    }

    @Test(description = "To Verify Login Page with username greater than 30 charaters", priority = 7, alwaysRun = true, groups = "Phase1_Group1")
    public void loginUsernameGreaterThanThirty() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.validateUsernameGreaterThanThirtyChar();
    }
/*    @Test(description = "To Verify Login Page with special characters in password", priority = 11, alwaysRun = true, groups = "Phase1_Group1")
    public void loginPasswordSpecialChar() throws InterruptedException {
        loginPagePOM.passwordSpecialCharaters();
    }*/

    @Test(description = "To Verify Login Footer links", priority = 8, alwaysRun = true, groups = "Phase1_Group1")
    public void loginFooterLinksValidation() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnStopService();
        loginPagePOM.clickOnAgencyAssistPortal();
        loginPagePOM.clickOnContactUs();
    }

    @Test(description = "To Verify Login Right side links, texts and Language Button", priority = 9, alwaysRun = true, groups = "Phase1_Group1")
    public void loginPageLinksRightSide() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileAndGoToAccount();
        loginPagePOM.clickOnStartService();
        loginPagePOM.clickOnPayBillAsGuest();
        loginPagePOM.clickOnOnlineForms();
        loginPagePOM.validateLoginPageText();
        loginPagePOM.validateLanguageData();
    }
    @Test(description = "To Verify eye icon ", priority = 10, alwaysRun = true, groups = "Phase1_Group1")
    public void loginHappyFlowWithPasswordEyeIconValidation() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.loginHappyFlowWithPasswordEyeIcon();
    }

    @Test(description = "To Verify Login Page forgot links", priority = 11, alwaysRun = true, groups = "Phase1_Group1")
    public void loginLinkForgotPasswordUsernameValidation() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.verifyForgotUserNameLnk();
        loginPagePOM.verifyForgotPasswordLnk();
    }


}