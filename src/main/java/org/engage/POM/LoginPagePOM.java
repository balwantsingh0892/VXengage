package org.engage.POM;
import com.aventstack.extentreports.Status;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Base.BaseUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import static org.engage.Base.BaseUtilities.*;
import static org.engage.POM.HomePagePOM.environment;
import static org.engage.POM.HomePagePOM.homePageProperties;
import static org.engage.POM.RegistrationPOM.registrationPageProperties;

import org.testng.TestNGUtils;

public class LoginPagePOM {
    public static Properties loginPageProperties;
    public static List<String> environment;

    public LoginPagePOM(List<String> environment) {
        LoginPagePOM.environment=environment;
        loginPageProperties = BaseUtilities.readPropertyfile(FilePaths.LOGIN_PAGE_PROPERTIES);
    }

    public static void enterUserName(String username) {
        sendKeysToElement(loginPageProperties.getProperty("username"), username);
    }

    public static void enterPassword(String password) {
        String decryptedPassword = decryptPasswordNormal(password);
        sendKeysToElement(loginPageProperties.getProperty("password"), decryptedPassword);
    }

    public static void enterPasswordWithoutDecryption(String password) {
        sendKeysToElement(loginPageProperties.getProperty("password"),password);
    }

    public static void performLoginForValidCredentials() {
        clickOnElement(loginPageProperties.getProperty("loginButton"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.accountSummaryURL"));
        System.out.println("Clicked Successfully");
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully");
    }

    public static void performLogin() {
        clickOnElement(loginPageProperties.getProperty("loginButton"), false);
        System.out.println("Clicked Successfully");
        ExtentSparkReport.getExtentLogger().info("Login button clicked Successfully ");
    }

    public static void doLogout() {
        getWait().until(ExpectedConditions.elementToBeClickable(clickOnElement(homePageProperties.getProperty("iconAccount"), false)));
        clickOnElement(homePageProperties.getProperty("btnLogout"), false);
        System.out.println("Logged out Successfully");
        ExtentSparkReport.getExtentLogger().info("Logged Out Successfully");
    }

    public void getInvalidPasswordErrorMessageAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            System.out.println("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            Assert.assertEquals(getTextOfElement(loginPageProperties.getProperty("errorMessage")), getValueFromUIJSON(environment,"errorMessagesLoginPage.loginErrorMessage"));
            ExtentSparkReport.getExtentLogger().log(Status.PASS, "");
            ExtentSparkReport.getExtentLogger().info("Getting Login Page Status Message and Validating, Message is: " + getErrorMessage("passwordRequired"));
            System.out.println("Getting Login Page Status Message and Validating, Message is: " + getErrorMessage("passwordRequired"));
            Assert.assertEquals(getErrorMessage("passwordRequired"),getValueFromUIJSON(environment,"errorMessagesLoginPage.passwordCriteriaMessage"));
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void getInvalidUsernameErrorMessageAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            System.out.println("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            Assert.assertEquals(getTextOfElement(loginPageProperties.getProperty("errorMessage")), getValueFromUIJSON(environment,"errorMessagesLoginPage.loginErrorMessage"));
            ExtentSparkReport.getExtentLogger().info("Getting Login Page Status Message and Validating, Message is: " + getErrorMessage("usernameRequired"));
            System.out.println("Getting Login Page Status Message and Validating, Message is: " + getErrorMessage("usernameRequired"));
            Assert.assertEquals(getErrorMessage("usernameRequired"),getValueFromUIJSON(environment,"errorMessagesLoginPage.invalidUserNameError"));
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void getInvalidUserAndPasswordErrorMessageAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            System.out.println("Getting Login Page Status Message and Validating, Message is: " + getTextOfElement(loginPageProperties.getProperty("errorMessage")));
            Assert.assertEquals(getTextOfElement(loginPageProperties.getProperty("errorMessage")), getValueFromUIJSON(environment,"errorMessagesLoginPage.invalidCredentialsErrorMessage"));
        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("An error occurred: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public String getErrorMessage(String errorType) {
        return getTextOfElement(loginPageProperties.getProperty(errorType));
    }

    public void clickOnCreateProfileAndGoToAccount() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Create Profile Button.");
        clickOnElement(loginPageProperties.getProperty("createProfile"), false);
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Create Profile Button.");
        scrollDown(2000);
        clickOnElement(registrationPageProperties.getProperty("goToAccount"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
        ExtentSparkReport.getExtentLogger().info("Moved to account summary page successfully.");
        System.out.println("Clicked on create profile and moved to Account Summary Page Successfully");

    }

    public void clickOnCreateProfileButton() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Create Profile Button.");
        clickOnElement(loginPageProperties.getProperty("createProfile"), false);
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Create Profile Button.");
        System.out.println("Clicked successfully on Create profile button");
    }

    public void clickOnStartService() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Start Service Link.");
        clickOnElement(loginPageProperties.getProperty("linkStartService"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.startServiceURL"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Start service Link.");
        scrollDown(2000);
        clickOnElement(loginPageProperties.getProperty("returnToLogin"), false);
        clickOnElement(loginPageProperties.getProperty("returnAlert"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
        System.out.println("Validated Successfully on Start Service link");
    }
    public void clickOnPayBillAsGuest() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().info("Clicking on Pay bill as guest Link.");
        clickOnElement(loginPageProperties.getProperty("linkPayBillAsGuest"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.payBillAsGuestURL"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Pay bill as guest Link.");
        performJSClickElement(loginPageProperties.getProperty("returnToLogin"));
        Thread.sleep(2000);
        clickOnElement(loginPageProperties.getProperty("returnAlert"),false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
        System.out.println("Validated Successfully on Pay Bill As Guest link");
    }
    public void clickOnOnlineForms() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Online Forms Link.");
        clickOnElement(loginPageProperties.getProperty("linkOnlineForms"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.onlineFormsNonAuthURL"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Online Forms Link.");
        scrollDown(1500);
        clickOnElement(loginPageProperties.getProperty("returnToLogin"), false);
        clickOnElement(loginPageProperties.getProperty("returnAlert"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
        System.out.println("Validated Successfully on Online Forms link");
    }
    public void clickOnStopService() {
        scrollDown(1500);
        ExtentSparkReport.getExtentLogger().info("Clicking on Stop Service Link.");
        clickOnElement(loginPageProperties.getProperty("linkStopService"), false);
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Stop Service Link.");
        assertURL(getValueFromUIJSON(environment,"assertURLs.stopServiceURL"));
        scrollDown(1500);
        clickOnElement(loginPageProperties.getProperty("returnLoginStopService"), false);
        clickOnElement(loginPageProperties.getProperty("returnButtonStopService"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
        System.out.println("Validated Stop service link Successfully");
    }
    public void clickOnAgencyAssistPortal() throws InterruptedException {
        scrollDown(1500);
        ExtentSparkReport.getExtentLogger().info("Clicking on Agency Assist Portal Link.");
        clickOnElement(loginPageProperties.getProperty("linkAgencyAssistPortal"), false);
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Agency Assist Portal Link.");
        assertURL(getValueFromUIJSON(environment,"assertURLs.agencyAssistPortalURL"));
        performJSClickElement(loginPageProperties.getProperty("linkCustomerPortal"));
        Thread.sleep(2000);
        assertURL(getValueFromUIJSON(environment,"assertURLs.customerPortalURL"));
        System.out.println("Validated Online Forms link Successfully");
    }
    public void clickOnContactUs() {
        scrollDown(1500);
        performJSClickElement(loginPageProperties.getProperty("linkContactUs"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Contact Us Link.");
        assertURL(getValueFromUIJSON(environment,"assertURLs.contactUsURL"));
        System.out.println("Validated Click on contact us Successfully");
    }

    public void loginEmptyCredentials() {
        try {
            clickOnElement(loginPageProperties.getProperty("loginButton"), false);
            Assert.assertEquals(getErrorMessage("errorMessage"), getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage"), "Error message did not match");

            Assert.assertEquals(getErrorMessage("usernameRequired"), getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage"));
            Assert.assertEquals(getErrorMessage("passwordRequired"), getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage"));
            System.out.println("Error messages successfully validated for Empty credentials.");
            ExtentSparkReport.getExtentLogger().info("Error messages successfully validated for Empty credentials.");
        }
         catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Exception: " + e.getMessage());
        }
    }

    public void loginEmptyUsername() {
        try {
            String password = getValueFromUIJSON(environment, "validCredentials[0].password");
            String decryptPassword = decryptPasswordNormal(password);
            sendKeysToElement(loginPageProperties.getProperty("password"), decryptPassword);
            clickOnElement(loginPageProperties.getProperty("loginButton"), false);
            Assert.assertEquals(getErrorMessage("errorMessage"), getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage"), "Error message did not match");
            Assert.assertEquals(getErrorMessage("usernameRequired"), getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage"));
            System.out.println("Error messages successfully validated for Empty Username.");
            ExtentSparkReport.getExtentLogger().info("Error messages successfully validated for Empty Username.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Exception occurred: " + e.getMessage());
            throw e;
        }
    }


    public void loginEmptyPassword() {
        try {
            sendKeysToElement(loginPageProperties.getProperty("username"),
                    getValueFromUIJSON(environment, "validCredentials[0].username"));
            clickOnElement(loginPageProperties.getProperty("loginButton"), false);
            Assert.assertEquals(getErrorMessage("errorMessage"), getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage"), "Error message did not match");
            Assert.assertEquals(getErrorMessage("passwordRequired"), getValueFromUIJSON(environment, "errorMessagesLoginPage.errorMessage"));
            System.out.println("Error messages successfully validated for Empty Password .");
            ExtentSparkReport.getExtentLogger().info("Error messages successfully validated for Empty Password.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            ExtentSparkReport.getExtentLogger().fail("Exception occurred: " + e.getMessage());
            throw e;
        }
    }

    public void validateUsernameGreaterThanThirtyChar() {

        try {
            ExtentSparkReport.getExtentLogger().info("Entering 30 Characters in username Field.");
            sendKeysToElement(loginPageProperties.getProperty("username"),getValueFromUIJSON(environment,"validatingCredentialsInLoginPage.usernameMoreThan30"));
            WebElement usernameValue = getElement(loginPageProperties.getProperty("username"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            Objects.requireNonNull(usernameValue, "username field is null");
            String enteredValue = usernameValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            if (enteredValue.length() == 30) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 30");
                System.out.println("Verified Entered characters are less than or equal to 30");
            } else {
                Assert.fail("Validation failed: Entered more than 30 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if username has more than 30 characters. Error message: " + e.getMessage());
        }
    }

  /*  public void passwordSpecialCharaters() {
        try {
            sendKeysToElement(loginPageProperties.getProperty("password"),
                    getValueFromUIJSON(environment,"validatingCredentialsInLoginPage.specialCharacters"));
            WebElement accountSpecialCharField = getElement(loginPageProperties.getProperty("password"));
            String enteredSpecialCharValue = accountSpecialCharField.getAttribute("value");
            if (enteredSpecialCharValue.isEmpty()) {
                Assert.fail("Special Character not Entered.");
            } else {
                Assert.assertTrue(true, "Validation failed: Special Characters Allowed.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: "
                    + e.getMessage());
        }
    }*/

    public void validateLoginPageText() {
        try {
            List<WebElement> elements = getElements(loginPageProperties.getProperty("cardHeaders"));
            System.out.println(elements);
            List<String> expHeaders = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "loginPageValidation.cardHeaders")).split(","));
            assert elements != null;
            for(WebElement element : elements){
                Assert.assertTrue(expHeaders.contains(getTextOfElements(element)));

            }
        }catch (Exception e){
            Assert.fail("Unexpected Error. Error message: " + e.getMessage());
        }
    }

    public void loginHappyFlowWithPasswordEyeIcon(){
        try{
            ExtentSparkReport.getExtentLogger().info("Entering valid Username");
            enterUserName(getValueFromUIJSON(environment,"validCredentials[0].username"));
            ExtentSparkReport.getExtentLogger().info("Entering valid password");
            enterPassword(getValueFromUIJSON(environment,"validCredentials[0].password"));
            clickOnElement(loginPageProperties.getProperty("passwordEyeIcon"), false);
            String value = getAttribute(loginPageProperties.getProperty("passwordEyeIcon"), "class");
            Assert.assertTrue(value.contains("show-entry-icon"), "password eye icon not working");
            System.out.println("Login Page Verified Successfully");
            ExtentSparkReport.getExtentLogger().info("Password Eye Icon Verified.");
            Thread.sleep(2000);
            performLoginForValidCredentials();

        }
        catch (Exception e){
            Assert.fail("Failed to verify password eye icon. Error message: " + e.getMessage());
        }
    }

    public void verifyForgotUserNameLnk(){
        try{
            clickOnElement(loginPageProperties.getProperty("forgotUsername"), false);
            boolean status = isElementDisplayed(getElement(loginPageProperties.getProperty("forgotUserHeader")));
            Assert.assertTrue(status, "user not navigated to forgot username");
            scrollDown(1000);
            clickOnElement(loginPageProperties.getProperty("returnToLogin"), false);
            clickOnElement(loginPageProperties.getProperty("returnAlert"), false);
            assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
            System.out.println("Validated Forgot Username link Successfully");
            ExtentSparkReport.getExtentLogger().info("Validated Forgot Username link Successfully.");
        }catch (Exception e){
            Assert.fail("Failed to verify forgot username link. Error message: " + e.getMessage());
        }
    }

    public void verifyForgotPasswordLnk(){
        try{
            clickOnElement(loginPageProperties.getProperty("forgotPassword"), false);
            boolean status = isElementDisplayed(getElement(loginPageProperties.getProperty("forgotPassHeader")));
            Assert.assertTrue(status, "user not navigated to forgot password");
            scrollDown(1000);
            clickOnElement(loginPageProperties.getProperty("returnToLogin"), false);
            clickOnElement(loginPageProperties.getProperty("returnAlert"), false);
            assertURL(getValueFromUIJSON(environment,"assertURLs.loginURL"));
            System.out.println("Validated Forgot Password link Successfully");
            ExtentSparkReport.getExtentLogger().info("Validated Forgot Password link Successfully.");
        }catch (Exception e){
            Assert.fail("Failed to verify forgot password link. Error message: "
                    + e.getMessage());
        }
    }


    public void validateLanguageData() {
        try {
            clickOnElement(loginPageProperties.getProperty("language"), false);
            List<WebElement> languagesElements = getElements(loginPageProperties.getProperty("languages"));
            List<String> expectedLanguages = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "loginPageValidation.languages")).split(","));
            Assert.assertNotNull(languagesElements);
            Assert.assertEquals(languagesElements.size(), expectedLanguages.size(), "Number of languages elements and expected languages do not match.");

            for (int i = 0; i < languagesElements.size(); i++) {
                String actualLanguage = languagesElements.get(i).getText();
                System.out.println("Actual Language: "+actualLanguage);
                String expectedLanguage = expectedLanguages.get(i);
                System.out.println("Expected Language: "+expectedLanguage);
                Assert.assertEquals(actualLanguage, expectedLanguage, "Language at index " + i + " does not match.");
            }
            ExtentSparkReport.getExtentLogger().info("Verified all the languages in loginpage.");
        } catch (Exception e) {
            Assert.fail("Unexpected Error. Error message: " + e.getMessage());
        }
    }

    public void clickOnForgotUsernameLink() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Forgot Username Button.");
        clickOnElement(loginPageProperties.getProperty("forgotUsername"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.forgotUsernameURL"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Forgot Username Button.");
    }

    public void clickOnForgotPasswordLink() {
        ExtentSparkReport.getExtentLogger().info("Clicking on Forgot Password Button.");
        clickOnElement(loginPageProperties.getProperty("forgotPassword"), false);
        assertURL(getValueFromUIJSON(environment,"assertURLs.forgotPasswordURL"));
        ExtentSparkReport.getExtentLogger().info("Clicked Successfully on Forgot Password Button.");
    }

}