package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

//Scenario_4

public class ForgotUsernamePageBaseTest extends TestUtils {

    @Test(description = "To Verify the Forgot Username Required Fields, Verbiage and Title Message", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyForgotUsernameRequiredFieldsVerbiageAndTitleMessage() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotUsernameLink();
        forgotUsernamePagePOM.validateTitleAndVerbiageMessage();
        forgotUsernamePagePOM.requiredFieldsValidationForgotUsername();
    }
    @Test(description = "To Validate the Email Address Field Validation on Forgot Username Page", priority = 2, alwaysRun = true, groups = "Phase1_Group1")
    public void validationOfEmailAddressField() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotUsernameLink();
        forgotUsernamePagePOM.validateEmailMoreThan50Letters();
        forgotUsernamePagePOM.validateInvalidEmailAddressMessage();
    }

    @Test(description = "To Validate the Captcha Field Validation with Refresh Captcha on Forgot Username Page", priority = 3, alwaysRun = true, groups = "Phase1_Group1")
    public void validationOfCaptchaField() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotUsernameLink();
        forgotUsernamePagePOM.validateCaptchaValueMoreThan6Characters();
        forgotUsernamePagePOM.validateCaptchaRefresh();
        forgotUsernamePagePOM.validateValidEmailInvalidCaptchaMessage();
    }

    @Test(description = "Happy Flow for Forgot Username", priority = 4, alwaysRun = true, groups = "Phase1_Group1")
    public void happyFlowForgotUsername() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnForgotUsernameLink();
        forgotUsernamePagePOM.validDataForForgotUsername();

    }

}
