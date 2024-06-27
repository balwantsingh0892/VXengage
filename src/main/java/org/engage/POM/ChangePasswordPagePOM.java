package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.getValueFromUIJSON;

public class ChangePasswordPagePOM {

    public static Properties changePasswordPageProperties;
    public static List<String> environment;

    public ChangePasswordPagePOM(List<String> environment) {
        ChangePasswordPagePOM.environment =environment;
        changePasswordPageProperties = BaseUtilities.readPropertyfile(FilePaths.CHANGE_PASSWORD_PAGE_PROPERTIES);
    }

    public void verifyPageTitleCard() {
        ExtentSparkReport.getExtentLogger().info("Validate Title card in change password page.");
        String actHeader = Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("changePasswordTitleCard"))).trim();
        assertThat(actHeader, getValueFromUIJSON(environment, "changePasswordPageData.header"), "header matched with expected", "header not matched with expected");
        System.out.println("header matched with expected");
        ExtentSparkReport.getExtentLogger().info("successfully validated the Title card.");
    }

    public void verifyTitleCards() {
        ExtentSparkReport.getExtentLogger().info("Validate cards headers in change password page");
        String cardTitle = Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("titleCardChangePassword"))).trim();
        assertThat(cardTitle, getValueFromUIJSON(environment, "changePasswordPageData.cardTitle"), "card title matched with expected", "card title not matched with expected");
        System.out.println("card title matched with expected");
        String cardTitle1 = Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("titleCardTwoFactorAuthentication"))).trim();
        assertThat(cardTitle1, getValueFromUIJSON(environment, "changePasswordPageData.cardTitle1"), "Change Password page card title matched with expected", "Change Password page card title not matched with expected");
        System.out.println("Change Password page card title matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully validated the card headers");
    }

    public void happyFlowChangePassword() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering valid password in change password page");
            sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"), decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
            sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"), getValueFromUIJSON(environment, "changePasswordPageData.changePassword"));
            sendKeysToElement(changePasswordPageProperties.getProperty("conformNewPasswordTextField"), getValueFromUIJSON(environment, "changePasswordPageData.changePassword"));
            clickOnElement(changePasswordPageProperties.getProperty("eyeToggle"), false);
            String value = getAttribute(changePasswordPageProperties.getProperty("eyeToggle"), "class");
            Assert.assertTrue(value.contains("show-entry-icon"), "password eye icon not working");
            ExtentSparkReport.getExtentLogger().info("Password Eye Icon Verified in change password page.");
            clickSaveBtn();
            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("successPasswordChange"))),getValueFromUIJSON(environment, "changePasswordPageData.successfulPasswordChange"),"Password has been updated in change password page","Password did not updated in change password page");
            System.out.println("Password changed in change password page ");
        } catch (Exception e) {
            Assert.fail("Failed to verify password eye icon. Error message: " + e.getMessage());
        }
    }

    public void verifyChangePasswordFields() {
        try {
            clickSaveBtn();
            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("errormessagePasswordFields"))),( getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage")), "Validated the All field required error message","Not validated the All field required error message");
            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("errormessageOldPass"))), (getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage")),"Required field error validated for Old password field","Required field error not validated for Old password field");
            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("errormessageNewPass"))), (getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage")),"Required field error validated for New password field","Required field error not validated for new password field");
            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("errormessageConformNewPass"))),( getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage")),"Required field error validated for confirm password field","Required field error not validated for Confirm password field");
            System.out.println("Assertion Validated Successfully.");
            ExtentSparkReport.getExtentLogger().info("Assertion Validated Successfully.");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Exception: " + e.getMessage());

        }

    }

    public void verifyPasswordMoreThan30Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 30 Characters in password Field.");
            sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"), getValueFromUIJSON(environment, "changePasswordPageData.passwordMoreThan30Char"));
            WebElement passwordValue = getElement(changePasswordPageProperties.getProperty("oldPasswordTextField"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            Objects.requireNonNull(passwordValue, "username field is null");
            String enteredValue = passwordValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            if (enteredValue.length() == 30) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 30");
            } else {
                Assert.fail("Validation failed: Entered more than 30 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if password has more than 30 characters. Error message: " + e.getMessage());
        }
    }

    public void clickSaveBtn(){
        performJSClickElement(changePasswordPageProperties.getProperty("saveButton"));
//        clickOnElement(changePasswordPageProperties.getProperty("saveButton"), false);
    }

    public void verifyRequiredFields(){
        ExtentSparkReport.getExtentLogger().info("Validate Change password page Error Message to Populate for Required Field.");
        clickSaveBtn();
        String oldPassError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageOldPass"));
        assert oldPassError != null;
        assertThat(oldPassError, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage"),"Required field error validated for Old password field","Required field error not validated for Old password field");
        String newPassError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert newPassError != null;
        assertThat(newPassError, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage"),"Required field error validated for New password field","Required field error not validated for New password field");
        String confPassError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageConfirmNewPass"));
        assert confPassError != null;
        assertThat(confPassError, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage"),"Required field error validated for Confirm password field","Required field error not validated for Confirm password field");
        String errorMessage = getTextOfElement(changePasswordPageProperties.getProperty("errorMessagePassword"));
        assert errorMessage != null;
        assertThat(errorMessage, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage"),"Validated the All field required error message","Not validated the All field required error message");
       System.out.println("Error messages Validated for Blank fields in Change password page");
       ExtentSparkReport.getExtentLogger().info("Successfully validated the error messages in change password page.");

    }

    public void verifyPasswordCriteriaErrorMessage(){


        ExtentSparkReport.getExtentLogger().info("Validate Change password page Field Error Messages.");
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "invalidCredentials[1].InvalidPassword"));
        clickSaveBtn();
        String passCriteria = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert passCriteria != null;
        assertThat(passCriteria, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage"),"Validated the password criteria error message for invalid password ","Not validated the password criteria error message for invalid password ");
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
        clickSaveBtn();
        passCriteria = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert passCriteria != null;
        assertThat(passCriteria, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage"),"Validated the password criteria error message for special characters ","Not validated the password criteria error message for special characters ");
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "changePasswordPageData.passwordUpperCase"));
        clickSaveBtn();
        passCriteria = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert passCriteria != null;
        assertThat(passCriteria, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage"),"Validated the password criteria error message for only uppercase","Not validated the password criteria error message for only uppercase ");
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "changePasswordPageData.passwordLowerCase"));
        clickSaveBtn();
        passCriteria = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert passCriteria != null;
        assertThat(passCriteria, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage"),"Validated the password criteria error message for lower cases","Not validated the password criteria error message for only lowercase");
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "changePasswordPageData.password6Chars"));
        clickSaveBtn();
        passCriteria = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageNewPass"));
        assert passCriteria != null;
        assertThat(passCriteria, getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage"),"Validated the password criteria error minimum 6 characters ","Not validated the password criteria error message for minimum 6 charactrers ");
        System.out.println("Errror messages of password criteria is validated");
        ExtentSparkReport.getExtentLogger().info("Successfully validated the error messages for password criteria.");
    }

    public void verifySameNewAndOldPassword(){
        ExtentSparkReport.getExtentLogger().info("Validate Change password page using same password.");
        clear(changePasswordPageProperties.getProperty("oldPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
        clear(changePasswordPageProperties.getProperty("conformNewPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("conformNewPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
        clickSaveBtn();
        String actError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessagePassword"));
        assert actError != null;
        assertThat(actError, getValueFromUIJSON(environment, "changePasswordPageData.samePasswordErrorMessage"),"Validated error message for entering same password","Not validated error message for entering same password");
        System.out.println("Validated error message for entering same password");
        ExtentSparkReport.getExtentLogger().info("Successfully Validated the error message for same password.");
    }

    public void verifyPasswordAndConfirmPasswordMismatchErrorMessage(){
        ExtentSparkReport.getExtentLogger().info("Validate change  password page with different password in new password and confirm password fields.");
        clear(changePasswordPageProperties.getProperty("oldPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                getValueFromUIJSON(environment, "changePasswordPageData.changePassword"));
        clear(changePasswordPageProperties.getProperty("conformNewPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("conformNewPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "invalidCredentials[1].InvalidPassword"))));
        clickSaveBtn();
        String actError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessageConfirmNewPass"));
        assert actError != null;
        assertThat(actError, getValueFromUIJSON(environment, "changePasswordPageData.passwordMisMatchError"),"Validated the change  password page with different password in new password and confirm password fields.","Not validate change  password page with different password in new password and confirm password fields.\"");
        System.out.println("Validate change  password page with different password in new password and confirm password fields.");
        ExtentSparkReport.getExtentLogger().info("Successfully validated the password fields of New and Confirm password.");
    }

    public void verifyInvalidOldPasswordError(){
        ExtentSparkReport.getExtentLogger().info("Validate Change password page using old password.");
        clear(changePasswordPageProperties.getProperty("oldPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"),
                decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "invalidCredentials[1].InvalidPassword"))));
        clear(changePasswordPageProperties.getProperty("newPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"),
                Objects.requireNonNull(getValueFromUIJSON(environment, "changePasswordPageData.changePassword")));
        clear(changePasswordPageProperties.getProperty("conformNewPasswordTextField"));
        sendKeysToElement(changePasswordPageProperties.getProperty("conformNewPasswordTextField"),
                Objects.requireNonNull(getValueFromUIJSON(environment, "changePasswordPageData.changePassword")));
        clickSaveBtn();
        String actError = getTextOfElement(changePasswordPageProperties.getProperty("errorMessagePassword"));
        assert actError != null;
        assertThat(actError, getValueFromUIJSON(environment, "changePasswordPageData.invalidOldPasswordErrorMessage"),"Validated the fields using Old password","Not validated the fields using old password");
        System.out.println("Error messages are validated using old password");
        ExtentSparkReport.getExtentLogger().info("Successfully validated the password fields using old password.");
    }
    public void changePasswordBackToExistingPassword() {
        try {
            ExtentSparkReport.getExtentLogger().info("Rechanging back to Old Password");
            sendKeysToElement(changePasswordPageProperties.getProperty("oldPasswordTextField"),getValueFromUIJSON(environment, "changePasswordPageData.changePassword"));
            sendKeysToElement(changePasswordPageProperties.getProperty("newPasswordTextField"), decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
            sendKeysToElement(changePasswordPageProperties.getProperty("conformNewPasswordTextField"),decryptPasswordNormal(Objects.requireNonNull(getValueFromUIJSON(environment, "validCredentials[0].password"))));
            clickSaveBtn();

            assertThat(Objects.requireNonNull(getTextOfElement(changePasswordPageProperties.getProperty("successPasswordChange"))),(getValueFromUIJSON(environment, "changePasswordPageData.successfulPasswordChange")),"Successful changed back to Old password","Not successful in changing back the password");
            System.out.println("Password Changed back to Previous Password");
        } catch (Exception e) {
            Assert.fail("Failed to verify password eye icon. Error message: " + e.getMessage());
        }
    }

    public void validatePasswordVerbiage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Password Verbiage to Show on UI.");
            String actualVerbiage = getTextOfElement(changePasswordPageProperties.getProperty("passwordVerbiage"));
            String expectedVerbiage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordHelpText");
            assert actualVerbiage != null;
            assertThat((actualVerbiage),(expectedVerbiage),"Verbiage matched for password requirements","Verbiage message did not matched");
            System.out.println("Verbiage Matched for password requirements");
            ExtentSparkReport.getExtentLogger().info("Successfully Validated the password requirement verbiage.");
        } catch (Exception e) {
            Assert.fail("Failed to validate password verbiage. Error message: " + e.getMessage());
        }
    }

}



