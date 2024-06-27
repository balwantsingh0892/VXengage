package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Database.DatabaseConnection;
import org.engage.Database.EngageQueries;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Database.DatabaseOperations.executeQuery;

public class ForgotUsernamePagePOM {

    public static Properties forgotUsernamePageproperties;
    public static List<String> environment;
    public ForgotUsernamePagePOM(List<String> environment)
    {
        ForgotUsernamePagePOM.environment = environment;
        forgotUsernamePageproperties = BaseUtilities.readPropertyfile(FilePaths.FORGOT_USERNAME_PAGE_PROPERTIES);
    }


    public void validateTitleAndVerbiageMessage()
    {
        String forgotTitle = getTextOfElement(forgotUsernamePageproperties.getProperty("forgotTitle"));
        Assert.assertEquals(forgotTitle,getValueFromUIJSON(environment,"forgotUsernameValidationData.forgotUsernameTitleMessage"));
        System.out.println("Forgot Username title message validated successfully.");
        String forgotVerbiage = getTextOfElement(forgotUsernamePageproperties.getProperty("forgotVerbiage"));
        Assert.assertEquals(forgotVerbiage,getValueFromUIJSON(environment,"forgotUsernameValidationData.forgotUsernameVerbiageMessage"));
        System.out.println("Forgot Username Verbiage message validated successfully.");
    }

    public void requiredFieldsValidationForgotUsername()
    {
        scrollDown(1000);
        clickOnElement(forgotUsernamePageproperties.getProperty("submitButton"),false);
        String errorMessage= getTextOfElement(forgotUsernamePageproperties.getProperty("errorMessageLocator"));
        Assert.assertEquals(errorMessage,getValueFromUIJSON(environment,"forgotUsernameValidationData.forgotUsernameErrorMessage"));
        System.out.println("Error Message Validated Successfully.");

        String emailRequiredMessage= getTextOfElement(forgotUsernamePageproperties.getProperty("emailMessageLocator"));
        Assert.assertEquals(emailRequiredMessage,getValueFromUIJSON(environment,"forgotUsernameValidationData.forgotUsernameRequiredFieldMessage"));
        System.out.println("Email Required Error Message Validated Successfully.");

        String captchaRequiredMessage= getTextOfElement(forgotUsernamePageproperties.getProperty("captchaRequiredMessageLocator"));
        Assert.assertEquals(captchaRequiredMessage,getValueFromUIJSON(environment,"forgotUsernameValidationData.forgotUsernameRequiredFieldMessage"));
        System.out.println("Captcha Required Error Message Validated Successfully.");
        scrollDown(1000);
        clickOnElement(forgotUsernamePageproperties.getProperty("goToAccount"),false);
        clickOnElement(forgotUsernamePageproperties.getProperty("returnButton"),false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));

    }

    public void validateEmailMoreThan50Letters()
    {
     sendKeysToElement(forgotUsernamePageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotUsernamePage.longEmail51Letters"));
        WebElement emailAddressElement = getElement(forgotUsernamePageproperties.getProperty("emailAddress"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Email Address field from UI.");
        Objects.requireNonNull(emailAddressElement, "Email Address field is null");
        String enteredValue = emailAddressElement.getAttribute("value");
        ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 50 Letters");
        if (enteredValue.length() == 50) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Letters are 50.");
            emailAddressElement.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 50 letters. Actual length: " + enteredValue.length());
        }
    }

    public void validateInvalidEmailAddressMessage()
    {
        sendKeysToElement(forgotUsernamePageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotUsernamePage.invalidEmailAddress"));
        scrollDown(1000);
        clickOnElement(forgotUsernamePageproperties.getProperty("submitButton"),false);
        String emailMessage = getTextOfElement(forgotUsernamePageproperties.getProperty("emailMessageLocator"));
        Assert.assertEquals(emailMessage,getValueFromUIJSON(environment,"forgotUsernameValidationData.invalidEmailErrorMessage"));
        System.out.println("Invalid Email Error Message Validated Successfully.");
    }

    public void validateCaptchaValueMoreThan6Characters()
    {
        sendKeysToElement(forgotUsernamePageproperties.getProperty("captchaField"),getValueFromUIJSON(environment,"testDataForgotUsernamePage.captchaValue8Characters"));
        WebElement captchaFieldElement = getElement(forgotUsernamePageproperties.getProperty("captchaField"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Captcha field from UI.");
        Objects.requireNonNull(captchaFieldElement, "Captcha field is null");
        String enteredValue = captchaFieldElement.getAttribute("value");
        ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 6 Characters");
        if (enteredValue.length() == 6) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Characters are 6.");
            captchaFieldElement.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 6 Characters. Actual length: " + enteredValue.length());
        }
    }

    public void validateCaptchaRefresh() {
       WebElement captchaImage= getElement(forgotUsernamePageproperties.getProperty("captchaImage"));
       System.out.println("Captcha Image: "+captchaImage);
        assert captchaImage != null;
        String initialCaptcha = captchaImage.getAttribute("src");
        System.out.println("Initial Captcha: "+initialCaptcha);
        WebElement refreshCaptchaButton= getElement(forgotUsernamePageproperties.getProperty("refreshCaptchaButton"));
        assert refreshCaptchaButton != null;
        refreshCaptchaButton.click();
        String newCaptcha = captchaImage.getAttribute("src");
        System.out.println("New Captcha: "+newCaptcha);
        if(initialCaptcha != newCaptcha)
        {
            Assert.assertTrue(true);
        }
        else
            Assert.fail();
    }

    public void validateValidEmailInvalidCaptchaMessage()
    {
        sendKeysToElement(forgotUsernamePageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotUsernamePage.validEmailAddress"));
        sendKeysToElement(forgotUsernamePageproperties.getProperty("captchaField"),getValueFromUIJSON(environment,"testDataForgotUsernamePage.invalidCaptchaValue"));
        scrollDown(500);
        clickOnElement(forgotUsernamePageproperties.getProperty("submitButton"),false);
        Assert.assertEquals(getTextOfElement(forgotUsernamePageproperties.getProperty("errorMessageLocator")),getValueFromUIJSON(environment,"forgotUsernameValidationData.invalidCaptchaCodeMessage"));
        System.out.println("Error Message for Invalid Captcha Validated Successfully.");
        String masterCaptcha = executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_CAPTCHA, DatabaseConnection.DatabaseType.MariaDB);
        sendKeysToElement(forgotUsernamePageproperties.getProperty("captchaField"),masterCaptcha);
        clickOnElement(forgotUsernamePageproperties.getProperty("submitButton"),false);
        Assert.assertEquals(getTextOfElement(forgotUsernamePageproperties.getProperty("errorMessageLocator")),getValueFromUIJSON(environment,"forgotUsernameValidationData.unregisteredEmailErrorMessage"));
    }

    public void validDataForForgotUsername()
    {
        String validEmail= executeQuery(EngageQueries.SELECT_ACTIVE_EMAIL_ADDRESS, DatabaseConnection.DatabaseType.MariaDB);
        sendKeysToElement(forgotUsernamePageproperties.getProperty("emailAddress"),validEmail);
        String masterCaptcha = executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_CAPTCHA, DatabaseConnection.DatabaseType.MariaDB);
        sendKeysToElement(forgotUsernamePageproperties.getProperty("captchaField"),masterCaptcha);
        clickOnElement(forgotUsernamePageproperties.getProperty("submitButton"),false);
        clickOnElement(forgotUsernamePageproperties.getProperty("closePopUp"),false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginUrl"));
    }
}
