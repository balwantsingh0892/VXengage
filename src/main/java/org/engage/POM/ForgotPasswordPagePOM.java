package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Database.DatabaseConnection;
import org.engage.Database.EngageQueries;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Database.DatabaseOperations.*;

public class ForgotPasswordPagePOM {

   public static Properties forgotPasswordPageproperties;
   public static List<String> environment;

    public ForgotPasswordPagePOM(List<String> environment)
    {
        ForgotPasswordPagePOM.environment = environment;
        forgotPasswordPageproperties = BaseUtilities.readPropertyfile(FilePaths.FORGOT_PASSWORD_PAGE_PROPERTIES);

    }

    public void validateTitleAndVerbiageMessage() throws InterruptedException {
        String forgotTitle = getTextOfElement(forgotPasswordPageproperties.getProperty("forgotPasswordTitle"));
        Assert.assertEquals(forgotTitle,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordTitleMessage"));
        System.out.println("Forgot Password title message validated successfully.");
        String forgotVerbiage = getTextOfElement(forgotPasswordPageproperties.getProperty("forgotVerbiage"));
        Assert.assertEquals(forgotVerbiage,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordVerbiageMessage"));
        System.out.println("Forgot Password Verbiage message validated successfully.");
    }

    public void requiredFieldsValidationForgotPassword()
    {
        scrollDown(1000);
        clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
        String errorMessage= getTextOfElement(forgotPasswordPageproperties.getProperty("errorMessageLocator"));
        Assert.assertEquals(errorMessage,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordErrorMessage"));
        System.out.println("Error Message Validated Successfully.");

        String usernameMessage= getTextOfElement(forgotPasswordPageproperties.getProperty("usernameMessageLocator"));
        Assert.assertEquals(usernameMessage,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordRequiredFieldMessage"));
        System.out.println("Username Required Error Message Validated Successfully.");

        String emailRequiredMessage= getTextOfElement(forgotPasswordPageproperties.getProperty("emailMessageLocator"));
        Assert.assertEquals(emailRequiredMessage,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordRequiredFieldMessage"));
        System.out.println("Email Required Error Message Validated Successfully.");

        String captchaRequiredMessage= getTextOfElement(forgotPasswordPageproperties.getProperty("captchaRequiredMessageLocator"));
        Assert.assertEquals(captchaRequiredMessage,getValueFromUIJSON(environment,"forgotPasswordValidationData.forgotPasswordRequiredFieldMessage"));
        System.out.println("Captcha Required Error Message Validated Successfully.");

        scrollDown(1000);
        clickOnElement(forgotPasswordPageproperties.getProperty("goToAccount"),false);
        clickOnElement(forgotPasswordPageproperties.getProperty("returnButton"),false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
    }

        public void validateUsernameAndEmailMoreThan50Letters(SoftAssert softAssert)
        {
            sendKeysToElement(forgotPasswordPageproperties.getProperty("username"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.longUsername58Letters"));
            WebElement usernameElement = getElement(forgotPasswordPageproperties.getProperty("username"));
            Objects.requireNonNull(usernameElement, "Username field is null");
            sendKeysToElement(forgotPasswordPageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.longEmail51Letters"));
            WebElement emailAddressElement = getElement(forgotPasswordPageproperties.getProperty("emailAddress"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Username and Email Address fields from UI.");
            Objects.requireNonNull(emailAddressElement, "Email Address field is null");
            String enteredUsernameValue = usernameElement.getAttribute("value");
            String enteredEmailValue = emailAddressElement.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 50 Letters");
            softAssert.assertTrue(enteredUsernameValue.length() == 50 && enteredEmailValue.length() == 50, "Validation failed: Entered more than 50 letters. Actual length for Username Field: " + enteredUsernameValue.length() + ", And Actual length for Email Field: " + enteredEmailValue.length());
            if (enteredUsernameValue.length() == 50 && enteredEmailValue.length()==50) {
                ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Letters are 50.");
            }
                usernameElement.clear();
                emailAddressElement.clear();
        }

        public void validateInvalidEmailAddressMessage()
        {
            sendKeysToElement(forgotPasswordPageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.invalidEmailAddress"));
            scrollDown(1000);
            clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
            String emailMessage = getTextOfElement(forgotPasswordPageproperties.getProperty("emailMessageLocator"));
            Assert.assertEquals(emailMessage,getValueFromUIJSON(environment,"forgotPasswordValidationData.invalidEmailErrorMessage"));
            System.out.println("Invalid Email Error Message Validated Successfully.");
        }

        public void validateInvalidCaptchaWithValidUsernameEmail()
        {
           WebElement emailAddressElement = getElement(forgotPasswordPageproperties.getProperty("emailAddress"));
           assert emailAddressElement != null;
           emailAddressElement.clear();
           sendKeysToElement(forgotPasswordPageproperties.getProperty("username"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.validUsername"));
           sendKeysToElement(forgotPasswordPageproperties.getProperty("emailAddress"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.validEmailAddress"));
           scrollDown(1000);
           sendKeysToElement(forgotPasswordPageproperties.getProperty("captchaField"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.invalidCaptchaValue"));
           clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
           Assert.assertEquals(getTextOfElement(forgotPasswordPageproperties.getProperty("errorMessageLocator")),getValueFromUIJSON(environment,"forgotPasswordValidationData.invalidCaptchaCodeMessage"));
           System.out.println("Error Message Validated Successfully for Invalid Captcha.");

        }

        public void validateInvalidUsername()
        {
            String captchaValue=executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_CAPTCHA, DatabaseConnection.DatabaseType.MariaDB);
            sendKeysToElement(forgotPasswordPageproperties.getProperty("captchaField"),captchaValue);
            clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
            Assert.assertEquals(getTextOfElement(forgotPasswordPageproperties.getProperty("errorMessageLocator")),getValueFromUIJSON(environment,"forgotPasswordValidationData.invalidUsernameMessage"));
        }

        public void validateInvalidEmail()
        {
            Objects.requireNonNull(getElement(forgotPasswordPageproperties.getProperty("username"))).clear();
            List<Map<String,String>> resultList = executeQueryAndGetRows(EngageQueries.SELECT_ACTIVE_USERNAME_EMAIL_ADDRESS, DatabaseConnection.DatabaseType.MariaDB);
            if (!resultList.isEmpty()) {
                Map<String, String> result = resultList.get(0);
                String username = result.get("user_name");
                System.out.println("Username: " + username);
                sendKeysToElement(forgotPasswordPageproperties.getProperty("username"),username);
            } else {
                System.out.println("No data found.");
            }
            //String username = result.get("user_name");
            Objects.requireNonNull(getElement(forgotPasswordPageproperties.getProperty("emailAddress"))).clear();
            String randomEmail = generateRandomEmail(10);
            sendKeysToElement(forgotPasswordPageproperties.getProperty("emailAddress"),randomEmail);
            String captchaValue=executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_CAPTCHA, DatabaseConnection.DatabaseType.MariaDB);
            sendKeysToElement(forgotPasswordPageproperties.getProperty("captchaField"),captchaValue);
            clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
            Assert.assertEquals(getTextOfElement(forgotPasswordPageproperties.getProperty("errorMessageLocator")),getValueFromUIJSON(environment,"forgotPasswordValidationData.invalidEmailAddressMessage"));
        }

        public void validateCaptchaValueMoreThan6Characters()
        {
            sendKeysToElement(forgotPasswordPageproperties.getProperty("captchaField"),getValueFromUIJSON(environment,"testDataForgotPasswordPage.captchaValue8Characters"));
            WebElement captchaFieldElement = getElement(forgotPasswordPageproperties.getProperty("captchaField"));
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

        public void validateCaptchaRefresh()
        {
            WebElement captchaImage= getElement(forgotPasswordPageproperties.getProperty("captchaImage"));
            System.out.println("Captcha Image: "+captchaImage);
            assert captchaImage != null;
            String initialCaptcha = captchaImage.getAttribute("src");
            System.out.println("Initial Captcha: "+initialCaptcha);
            WebElement refreshCaptchaButton= getElement(forgotPasswordPageproperties.getProperty("refreshCaptchaButton"));
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

    public void enterValidDataForHappyFlow()
    {
        List<Map<String, String>> resultList = executeQueryAndGetRows(EngageQueries.SELECT_ACTIVE_USERNAME_EMAIL_ADDRESS, DatabaseConnection.DatabaseType.MariaDB);
        if (!resultList.isEmpty()) {
            Map<String, String> result = resultList.get(0);
            String username = result.get("user_name");
            String emailAddress = result.get("email_address");
            sendKeysToElement(forgotPasswordPageproperties.getProperty("username"),username);
            sendKeysToElement(forgotPasswordPageproperties.getProperty("emailAddress"),emailAddress);
        } else {
            System.out.println("No data found.");
        }
        String captchaValue=executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_CAPTCHA, DatabaseConnection.DatabaseType.MariaDB);
        sendKeysToElement(forgotPasswordPageproperties.getProperty("captchaField"),captchaValue);
        clickOnElement(forgotPasswordPageproperties.getProperty("submitButton"),false);
        clickOnElement(forgotPasswordPageproperties.getProperty("closePopUp"),false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginUrl"));
    }
}

