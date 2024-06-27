package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

//Scenario_5

public class ForgotPasswordPageBaseTest extends TestUtils {

    @Test(description = "To Verify the Forgot Password Required Fields, Verbiage and Title Message", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyForgotPasswordRequiredFieldsVerbiageAndTitleMessage() throws InterruptedException {
       ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotPasswordLink();
        forgotPasswordPagePOM.validateTitleAndVerbiageMessage();
        forgotPasswordPagePOM.requiredFieldsValidationForgotPassword();
    }
    @Test(description = "To Validate the Username and Email Address Field Validations on Forgot Password Page", priority = 2, alwaysRun = true, groups = "Phase1_Group1")
    public void validationOfUsernameAndEmailAddressFields() {
       ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotPasswordLink();
        forgotPasswordPagePOM.validateUsernameAndEmailMoreThan50Letters(softAssert);
        forgotPasswordPagePOM.validateInvalidEmailAddressMessage();
        forgotPasswordPagePOM.validateInvalidCaptchaWithValidUsernameEmail();
        forgotPasswordPagePOM.validateInvalidUsername();
        forgotPasswordPagePOM.validateInvalidEmail();
        softAssert.assertAll();
    }

    @Test(description = "To Validate the Captcha Field Validation with Refresh Captcha on Forgot Password Page", priority = 3, alwaysRun = true, groups = "Phase1_Group1")
    public void validationOfCaptchaField() {
       ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotPasswordLink();
        forgotPasswordPagePOM.validateCaptchaValueMoreThan6Characters();
        forgotPasswordPagePOM.validateCaptchaRefresh();
    }

    @Test(description = "Happy Flow for Forgot Password", priority = 4, alwaysRun = true, groups = "Phase1_Group1")
    public void happyFlowForForgotPassword() {
       ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotPasswordLink();
        forgotPasswordPagePOM.enterValidDataForHappyFlow();
    }

}
