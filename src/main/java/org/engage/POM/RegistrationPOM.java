package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Database.CISQueries;
import org.engage.Database.DatabaseConnection;
import org.engage.Database.EngageQueries;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Database.DatabaseOperations.executeQuery;
//import static org.engage.Database.DatabaseOperations.executeQueryMultipleColumns;
import static org.engage.Database.DatabaseOperations.executeQueryAndGetRows;
import static org.engage.POM.LoginPagePOM.*;

public class RegistrationPOM {
    public static Properties registrationPageProperties;
    public static List<String> environment;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public RegistrationPOM(List<String> environment) {
        RegistrationPOM.environment=environment;
        registrationPageProperties = BaseUtilities.readPropertyfile(FilePaths.REGISTRATION_PAGE_PROPERTIES);
    }

    public void clickOnSubmitButton() {
        ExtentSparkReport.getExtentLogger().info("Scrolling Down to Click Submit Button");
        scrollDown(2000);
        ExtentSparkReport.getExtentLogger().info("Waiting For Submit Button to be Clickable");
        waitForElementToBeClickable(registrationPageProperties.getProperty("submitButton"));
        ExtentSparkReport.getExtentLogger().info("Clicking on Submit Button");
        clickOnElement(registrationPageProperties.getProperty("submitButton"), false);
    }

    public void validateErrorRegistrationMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validating the Top Level Error Message on Registration Form Page.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorRegistration"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationFormErrorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to validate error message on registration form page. Error message: " + e.getMessage());
        }
    }

    public void validateAccountNumberMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Account Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorAccountNumber"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate account number mandatory field error message. Error message: " + e.getMessage());
        }
    }

    public void validateUsernameFieldErrorMessage() {
        try {
            Thread.sleep(2000);
            ExtentSparkReport.getExtentLogger().info("Validate Username Field Error Message to Populate for Invalid Username.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorUserName"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.invalidUserNameError");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate username field error message. Error message: " + e.getMessage());
        }
    }

    public void validateUsernameRequiredFieldMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Username Field Error Message to Populate for Required Username.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorUserName"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate username field error message. Error message: " + e.getMessage());
        }
    }

    public void validateLastNameMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Last Name Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorLastName"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate last name field error message. Error message: " + e.getMessage());
        }
    }

    public void validateEmailAddressMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Email Address Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorEmailAddress"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate email address field error message. Error message: " + e.getMessage());
        }
    }

    public void validateConfirmEmailAddressMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Confirm Email Address Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorEmailConfirmation"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate confirm email address field error message. Error message: " + e.getMessage());
        }
    }

    public void validatePasswordMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Password Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorPassword"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate password field error message. Error message: " + e.getMessage());
        }
    }

    public void validateConfirmPasswordMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Confirm Password Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorConfirmPassword"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate confirm password field error message. Error message: " + e.getMessage());
        }
    }

    public void validateTermAndConditionsMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Term & Conditions Error Message to Populate for Required Checkbox.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorTermsConditions"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate term & conditions field error message. Error message: " + e.getMessage());
        }
    }


    public void validateAccountNumberVerbiage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Account Number Verbiage to Show on UI.");
            String actualVerbiage = getTextOfElement(registrationPageProperties.getProperty("accountNumberVerbiage"));
            String expectedVerbiage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.accountNumberText");
            Assert.assertEquals(actualVerbiage, expectedVerbiage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate account number verbiage. Error message: " + e.getMessage());
        }
    }

    public void validateUsernameVerbiage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Username Verbiage to Show on UI.");
            String actualVerbiage = getTextOfElement(registrationPageProperties.getProperty("usernameVerbiage"));
            String expectedVerbiage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.usernameHelpText");
            Assert.assertEquals(actualVerbiage, expectedVerbiage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate username verbiage. Error message: " + e.getMessage());
        }
    }

    public void validatePasswordVerbiage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Password Verbiage to Show on UI.");
            String actualVerbiage = getTextOfElement(registrationPageProperties.getProperty("passwordVerbiage"));
            String expectedVerbiage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordHelpText");
            Assert.assertEquals(actualVerbiage, expectedVerbiage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate password verbiage. Error message: " + e.getMessage());
        }
    }


    public void accountFieldValidationGreaterThanSevenDigits() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering More than 7 digits in Account Number Field.");
            sendKeysToElement(registrationPageProperties.getProperty("accountNumber"), getValueFromUIJSON(environment, "testDataRegistrationPage.accountNumericValue"));
            WebElement accountNumberField = getElement(registrationPageProperties.getProperty("accountNumber"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Account Number field from UI.");
            Objects.requireNonNull(accountNumberField, "Account number field is null");
            String enteredValue = accountNumberField.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 7 digits");
            if (enteredValue.length() == 7) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Digits are 7.");
                accountNumberField.clear();
            } else {
                Assert.fail("Validation failed: Entered more than 7 digits. Actual length: " + enteredValue.length());
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation. Error message: " + e.getMessage());
        }
    }


    public void validateTermAndConditionsLink() {
        try {
            ExtentSparkReport.getExtentLogger().info("Scrolling Down the Page to click Term & Conditions.");
            scrollDown(1500);
            ExtentSparkReport.getExtentLogger().info("Clicking on Term & Conditions Link.");
            clickOnElement(registrationPageProperties.getProperty("termAndConditions"), true);
            ExtentSparkReport.getExtentLogger().info("Window/Tab Handling Started.");
            String tabsTC = switchToNewTab();
            String[] tabHandlesTC = tabsTC.split(";");
            String parentTabTC = tabHandlesTC[0];
            String childTabTC = tabHandlesTC.length > 1 ? tabHandlesTC[1] : null;
            ExtentSparkReport.getExtentLogger().info("Validating the Term & Condition URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.termAndConditionsURL"));
            ExtentSparkReport.getExtentLogger().info("Validated Successfully & Closing the Child Tab.");
            switchToChildTabAndClose(childTabTC);
            ExtentSparkReport.getExtentLogger().info("Successfully Closed the Child Tab and Switching to Parent Tab.");
            switchToParentTab(parentTabTC);
            ExtentSparkReport.getExtentLogger().info("Successfully Switched to Parent Tab.");
        } catch (Exception e) {
            Assert.fail("Failed to perform Term & Conditions link validation. Error message: " + e.getMessage());
        }
    }

    public void validatePrivacyPolicyLink() {
        try {
            ExtentSparkReport.getExtentLogger().info("Clicking on Privacy Policy Link.");
            clickOnElement(registrationPageProperties.getProperty("privacyPolicy"), true);
            ExtentSparkReport.getExtentLogger().info("Successfully Clicked and Window Handling Started.");
            String tabsPrivacy = switchToNewTab();
            String[] tabHandlesPrivacy = tabsPrivacy.split(";");
            String parentTabPrivacy = tabHandlesPrivacy[0];
            String childTabPrivacy = tabHandlesPrivacy.length > 1 ? tabHandlesPrivacy[1] : null;
            ExtentSparkReport.getExtentLogger().info("Validating the Privacy Policy URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.privacyPolicyURL"));
            ExtentSparkReport.getExtentLogger().info("Validated Successfully and Closing the Privacy Tab.");
            switchToChildTabAndClose(childTabPrivacy);
            ExtentSparkReport.getExtentLogger().info("Successfully Closed the Child Tab and Now Switching to Parent Tab.");
            switchToParentTab(parentTabPrivacy);
            ExtentSparkReport.getExtentLogger().info("Successfully Switched to Parent Tab.");
        } catch (Exception e) {
            Assert.fail("Failed to perform Privacy Policy link validation. Error message: " + e.getMessage());
        }
    }


    public void clickGoToAccount() {
        try {
            ExtentSparkReport.getExtentLogger().info("Clicking on Go to Account Link.");
            clickOnElement(registrationPageProperties.getProperty("goToAccount"), false);
            ExtentSparkReport.getExtentLogger().info("Successfully Clicked to Go To Account Link.");
        } catch (Exception e) {
            Assert.fail("Failed to click Go to Account link. Error message: " + e.getMessage());
        }
    }


    public void getURL() {
        try {
            boolean isPageLoaded = waitForPageToLoad();
            if (isPageLoaded) {
                ExtentSparkReport.getExtentLogger().info("Validating the Login URL.");
                assertURL(getValueFromUIJSON(environment, "assertURLs.loginURL"));
                ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
            } else {
                ExtentSparkReport.getExtentLogger().info("Page didn't load within 30 seconds.");
                System.out.println("Page didn't load within 30 seconds.");
            }

        } catch (Exception e) {
            Assert.fail("Failed to validate the Login URL. Error message: " + e.getMessage());
        }
    }

    public void accountFieldValidationSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("accountNumber"), getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
            WebElement accountSpecialCharField = getElement(registrationPageProperties.getProperty("accountNumber"));
            String enteredSpecialCharValue = accountSpecialCharField.getAttribute("value");
            if (enteredSpecialCharValue.isEmpty()) {
                Assert.assertTrue(true, "No Special Character Entered.");
            } else {
                Assert.fail("Validation failed: Special Characters Allowed.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }
    }


    public void testEnterMoreThan30CharactersInUsername() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("userName"), getValueFromUIJSON(environment, "testDataRegistrationPage.userNameValue35Char"));
            scrollDown(1500);
            clickOnElement(registrationPageProperties.getProperty("submitButton"), false);
        } catch (Exception e) {
            Assert.fail("Failed to test entering more than 30 characters in Username field. Error message: " + e.getMessage());
        }
    }


    public void testEnterLessThan5CharactersInUsername() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("userName"), getValueFromUIJSON(environment, "testDataRegistrationPage.userNameValue3Char"));
            scrollDown(1500);
            clickOnElement(registrationPageProperties.getProperty("submitButton"), false);
        } catch (Exception e) {
            Assert.fail("Failed to test entering less than 5 characters in Username field. Error message: " + e.getMessage());
        }
    }

    public void validateUsernameNoSpecialCharsOutsideAllowedRange() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("userName"), getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
            scrollDown(1500);
            clickOnElement(registrationPageProperties.getProperty("submitButton"), false);
        } catch (Exception e) {
            Assert.fail("Failed to validate Username with no special characters outside allowed range. Error message: " + e.getMessage());
        }
    }


    public void validateResidentialRadioSelected() {
        try {
            boolean residential = isElementSelected(registrationPageProperties.getProperty("residentialRadio"));
            if (residential) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Residential Radio button is selected.");
            } else {
                Assert.fail("Residential Radio is not selected.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to validate if Residential Radio is selected. Error message: " + e.getMessage());
        }
    }

    public void verifyFirstNameMoreThan40Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 52 Characters in First Name Field.");
            sendKeysToElement(registrationPageProperties.getProperty("firstName"), getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldValue52Chars"));
            WebElement firstNameValue = getElement(registrationPageProperties.getProperty("firstName"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            assert firstNameValue != null;
            String enteredValue = firstNameValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            if (enteredValue.length() == 40) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 40");
                firstNameValue.clear();
            } else {
                Assert.fail("Validation failed: Entered more than 40 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if First Name has more than 40 characters. Error message: " + e.getMessage());
        }
    }

    public void verifyFirstNameAllowsSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("firstName"), getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldSpecialChars"));
            WebElement firstNameValue = getElement(registrationPageProperties.getProperty("firstName"));
            Objects.requireNonNull(firstNameValue, "First Name field is null");
            String enteredValue = firstNameValue.getAttribute("value");
            Assert.assertEquals(enteredValue, getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldSpecialChars"));
            ExtentSparkReport.getExtentLogger().info("Special Characters Entered Successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to verify if First Name allows special characters. Error message: " + e.getMessage());
        }
    }

    public void verifyLastNameMoreThan40Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 52 Characters in Last Name Field.");
            sendKeysToElement(registrationPageProperties.getProperty("lastName"), getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldValue52Chars"));
            WebElement lastNameValue = getElement(registrationPageProperties.getProperty("lastName"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            Objects.requireNonNull(lastNameValue, "Last Name field is null");
            String enteredValue = lastNameValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            if (enteredValue.length() == 40) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 40");
                lastNameValue.clear();
            } else {
                Assert.fail("Validation failed: Entered more than 40 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if Last Name has more than 40 characters. Error message: " + e.getMessage());
        }
    }


    public void verifyLastNameAllowsSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("lastName"), getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldSpecialChars"));
            WebElement lastNameValue = getElement(registrationPageProperties.getProperty("lastName"));
            Objects.requireNonNull(lastNameValue, "Last Name field is null");
            String enteredValue = lastNameValue.getAttribute("value");
            Assert.assertEquals(enteredValue, getValueFromUIJSON(environment, "testDataRegistrationPage.nameFieldSpecialChars"));
            ExtentSparkReport.getExtentLogger().info("Special Characters Entered Successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to verify if Last Name allows special characters. Error message: " + e.getMessage());
        }
    }


    public void validateEmailFieldForInvalidFormat() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering Invalid Email Address in Email Field.");
            sendKeysToElement(registrationPageProperties.getProperty("emailAddress"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidEmail"));
            ExtentSparkReport.getExtentLogger().info("Entering Submit Button to Validate the Email Error Message.");
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Asserting the Actual and Expected Error Message.");
            String actualValue = getTextOfElement(registrationPageProperties.getProperty("errorEmailAddress"));
            String expectedValue = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.invalidEmailAddress");
            ExtentSparkReport.getExtentLogger().info("Actual Value- " + actualValue + " Expected Value- " + expectedValue);
            Assert.assertEquals(actualValue, expectedValue);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Actual & Expected Messages are same.");
        } catch (Exception e) {
            Assert.fail("Failed to validate email field for invalid format. Error message: " + e.getMessage());
        }
    }


    public void validateEmailAndConfirmEmailMismatch() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering the Different Email Value in Confirm Email Field.");
            sendKeysToElement(registrationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidConfirmEmail"));
            ExtentSparkReport.getExtentLogger().info("Clicking on Submit Button.");
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Asserting the Actual and Expected Error Message.");
            String actualValue = getTextOfElement(registrationPageProperties.getProperty("errorEmailConfirmation"));
            String expectedValue = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.invalidConfirmEmailCombination");
            ExtentSparkReport.getExtentLogger().info("Actual Value- " + actualValue + " Expected Value- " + expectedValue);
            Assert.assertEquals(actualValue, expectedValue);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Actual & Expected Messages are same.");
        } catch (Exception e) {
            Assert.fail("Failed to validate email and confirm email mismatch. Error message: " + e.getMessage());
        }
    }


    public void validateInvalidPasswordCriteria() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering an invalid password.");
            sendKeysToElement(registrationPageProperties.getProperty("password"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidPassword"));
            scrollDown(1500);
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Asserting the Actual and Expected Error Message for invalid password criteria.");
            String actualValue = getTextOfElement(registrationPageProperties.getProperty("errorPassword"));
            String expectedValue = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.passwordCriteriaMessage");
            Assert.assertEquals(actualValue, expectedValue);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Actual & Expected Messages are the same.");
        } catch (Exception e) {
            Assert.fail("Failed to validate invalid password criteria. Error message: " + e.getMessage());
        }
    }


    public void validateInvalidPasswordAndConfirmPasswordCombination() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering an invalid password in the Confirm Password field.");
            sendKeysToElement(registrationPageProperties.getProperty("confirmPassword"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidConfirmPassword"));
            scrollDown(1500);
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Asserting the Actual and Expected Error Message for invalid password and confirm password combination.");
            String actualValue = getTextOfElement(registrationPageProperties.getProperty("errorConfirmPassword"));
            String expectedValue = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.confirmPasswordCombinationMessage");
            Assert.assertEquals(actualValue, expectedValue);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Actual & Expected Messages are the same.");
        } catch (Exception e) {
            Assert.fail("Failed to validate invalid password and confirm password combination. Error message: " + e.getMessage());
        }
    }


    public void validatePersonalInfoTopErrorMessagesOnEmptyFields() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering required personal information fields.");
            sendKeysToElement(registrationPageProperties.getProperty("accountNumber"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.accountNumber"));
            String username = generateRandomAlphanumericString();
            sendKeysToElement(registrationPageProperties.getProperty("userName"), username);
            sendKeysToElement(registrationPageProperties.getProperty("firstName"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.firstName"));
            sendKeysToElement(registrationPageProperties.getProperty("lastName"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.lastName"));
            sendKeysToElement(registrationPageProperties.getProperty("emailAddress"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("password"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmPassword"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));
            scrollDown(1500);
            clickOnElement(registrationPageProperties.getProperty("checkboxTermCondition"), false);
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Successfully entered required personal information fields.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("errorRegistration")), getValueFromUIJSON(environment, "errorMessagesRegistrationPage.personalInfoMandatoryMessage"));
        } catch (Exception e) {
            Assert.fail("Failed to validate required personal information fields. Error message: " + e.getMessage());
        }
    }

    public void validatePersonalInfoTopErrorMessagesWithInvalidData() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("ssnLocator"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidSSN"));
            clickOnSubmitButton();
            waitForElementToBeVisible(registrationPageProperties.getProperty("cisDataMismatchLocator"));
            String actualMessage = getTextOfElement(registrationPageProperties.getProperty("errorRegistration"));
            String expectedMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.dataMismatchWithCISMessage");
            Assert.assertEquals(actualMessage, expectedMessage);
        } catch (Exception e) {
            Assert.fail("Failed to validate personal info top error messages with invalid data. Error message: " + e.getMessage());
        }
    }

    public void validateExistingUsername() {
        try {
            WebElement ssnField = getElement(registrationPageProperties.getProperty("ssnLocator"));
            assert ssnField != null;
            ssnField.clear();
            sendKeysToElement(registrationPageProperties.getProperty("ssnLocator"), getValueFromUIJSON(environment, "testDataRegistrationPage.validSSN"));
            WebElement usernameField = getElement(registrationPageProperties.getProperty("userName"));
            assert usernameField != null;
            usernameField.clear();
            List<Map<String,String>> resultList =executeQueryAndGetRows(EngageQueries.SELECT_ACTIVE_USERNAME_EMAIL_ADDRESS, DatabaseConnection.DatabaseType.MariaDB);
            if (!resultList.isEmpty()) {
                Map<String, String> result = resultList.get(0);
                String username = result.get("user_name");
                sendKeysToElement(registrationPageProperties.getProperty("userName"), username);
            } else {
                System.out.println("No data found.");
            }
            clickOnSubmitButton();
            Thread.sleep(4000);
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("errorRegistration")), getValueFromUIJSON(environment, "errorMessagesRegistrationPage.existingUsernameMessage"));
        } catch (Exception e) {
            Assert.fail("Failed to Validate" + e.getMessage());
        }
    }


    public void validateMaxDigitLimitSSN() {
        scrollDown(1000);
        sendKeysToElement(registrationPageProperties.getProperty("ssnLocator"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidSSN"));
        WebElement fieldSSN = getElement(registrationPageProperties.getProperty("ssnLocator"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered value for SSN field from UI.");
        Objects.requireNonNull(fieldSSN, "SSN field is null");
        String enteredValue = fieldSSN.getAttribute("value");
        ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 4 digits");
        if (enteredValue.length() == 4) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Digits are 4.");
            fieldSSN.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 4 digits. Actual length: " + enteredValue.length());
        }
    }

    public void fieldSSNValidationSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("ssnLocator"), getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
            WebElement ssnSpecialCharField = getElement(registrationPageProperties.getProperty("ssnLocator"));
            String enteredSpecialCharValue = ssnSpecialCharField.getAttribute("value");
            if (enteredSpecialCharValue.isEmpty()) {
                Assert.assertTrue(true, "No Special Character Entered.");
            } else {
                Assert.fail("Validation failed: Special Characters Allowed.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform SSN field validation for special characters. Error message: " + e.getMessage());
        }
    }


    public void validateMaxDigitLimitDriversLicense() {
        scrollDown(1000);
        sendKeysToElement(registrationPageProperties.getProperty("driversLicense"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidDriverLicense"));
        WebElement driversLicenseField = getElement(registrationPageProperties.getProperty("driversLicense"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Driver's License field from UI.");
        Objects.requireNonNull(driversLicenseField, "Driver's License field is null");
        String enteredValue = driversLicenseField.getAttribute("value");
        ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 16 Characters");
        if (enteredValue.length() == 16) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Values are of 16 Character String.");
            driversLicenseField.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 16 Characters. Actual length: " + enteredValue.length());
        }
    }

    public void driverLicenseFieldValidationSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("driversLicense"), getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
            WebElement driversLicenseSpecialCharField = getElement(registrationPageProperties.getProperty("driversLicense"));
            String enteredSpecialCharValue = driversLicenseSpecialCharField.getAttribute("value");
            if (enteredSpecialCharValue.isEmpty()) {
                Assert.assertTrue(true, "No Special Character Entered.");
            } else {
                Assert.fail("Validation failed: Special Characters Allowed.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }
    }

    public void validateMaxDigitLimitServiceAddress() {
        scrollDown(1000);
        sendKeysToElement(registrationPageProperties.getProperty("serviceAddressNumber"), getValueFromUIJSON(environment, "testDataRegistrationPage.invalidServiceAddress"));
        WebElement serviceAddressField = getElement(registrationPageProperties.getProperty("serviceAddressNumber"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Service Address field from UI.");
        Objects.requireNonNull(serviceAddressField, "Service Address field is null");
        String enteredValue = serviceAddressField.getAttribute("value");
        ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 10 Characters");
        if (enteredValue.length() == 10) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Values are of 10 Characters String.");
            serviceAddressField.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 10 Characters. Actual length: " + enteredValue.length());
        }
    }

    public void serviceAddressFieldValidationSpecialCharacters() {
        try {
            sendKeysToElement(registrationPageProperties.getProperty("serviceAddressNumber"), getValueFromUIJSON(environment, "testDataRegistrationPage.specialChar"));
            WebElement serviceAddressSpecialCharField = getElement(registrationPageProperties.getProperty("serviceAddressNumber"));
            Objects.requireNonNull(serviceAddressSpecialCharField, "Service Address field is null");
            String enteredSpecialCharValue = serviceAddressSpecialCharField.getAttribute("value");
            if (enteredSpecialCharValue.isEmpty()) {
                Assert.fail("Validation failed: Special Characters Not Allowed.");
            } else {
                Assert.assertTrue(true, "Special Characters Entered.");
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }

    }

    public void validateFederalTaxIdMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Federal Tax Id Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorFederalTaxId"));
            String expectedErrorMessage = getValueFromUIJSON(environment,"errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate Federal Tax Id number mandatory field error message. Error message: "
                    + e.getMessage());
        }
    }

    public void selectCommercialCustomerTypeAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Clicking on commercial.");
            clickOnElement(registrationPageProperties.getProperty("commercialRadio"), false);
            boolean commercialRadio = isElementSelected(registrationPageProperties.getProperty("commercialRadio"));
            if (commercialRadio) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Commercial Radio button is selected.");
            } else {
                Assert.fail("Commercial Radio is not selected.");
                ExtentSparkReport.getExtentLogger().info("Successfully Clicked to commercial");
            }
        }catch (Exception e) {
            Assert.fail("Failed to click on commercial. Error message: " + e.getMessage());
        }
    }

    public void validateCompanyNameMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate company name Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorCompanyName"));
            String expectedErrorMessage = getValueFromUIJSON(environment,"errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate company name mandatory field error message. Error message: "
                    + e.getMessage());
        }

    }

    public void validateInvalidUsernameRequiredFieldMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Invalid Username Field Error Message to Populate for Required Username.");
            String actualErrorMessage = getTextOfElement(registrationPageProperties.getProperty("errorUserName"));
            String expectedErrorMessage = getValueFromUIJSON(environment,"errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate username field error message. Error message: " + e.getMessage());
        }
    }


    public void validateValidDataCommercialWithExistingUsername() {
        List<Map<String,String>> resultList =executeQueryAndGetRows(CISQueries.SELECT_COMMERCIAL_CUSTOMER_DATA, DatabaseConnection.DatabaseType.CIS);
        Map<String, String> result = null;
        List<Map<String,String>> resultList1 =executeQueryAndGetRows(EngageQueries.SELECT_ACTIVE_USERNAME_EMAIL_ADDRESS, DatabaseConnection.DatabaseType.MariaDB);
        Map<String, String> result1 = null;
        try {
            ExtentSparkReport.getExtentLogger().info("Entering valid data.");
            if (!resultList.isEmpty()) {
                result = resultList.get(0);
                String accountNumber = result.get("UMACT");
                sendKeysToElement(registrationPageProperties.getProperty("accountNumber"),accountNumber);
            } else {
                System.out.println("No data found.");
            }
            if (!resultList1.isEmpty()) {
                result1 = resultList1.get(0);
                String username= result1.get("user_name");
                sendKeysToElement(registrationPageProperties.getProperty("userName"),username);
                result = resultList.get(0);
                String companyName = result.get("MSLNM");
                sendKeysToElement(registrationPageProperties.getProperty("companyName"),companyName);
            } else {
                System.out.println("No data found.");
            }
            sendKeysToElement(registrationPageProperties.getProperty("emailAddress"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmEmailAddress"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("password"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.password"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmPassword"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.password"));
//            sendKeysToElement(registrationPageProperties.getProperty("serviceAddressHouseNumber"),
//                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.serviceAddressHouseNumberCommercial"));
            result = resultList.get(0);
            String federalID = result.get("E_FEDERAL_TAX_ID");
            String actualFederalID= federalID.replace("F","");
            String part1 = actualFederalID.substring(0, 2);
            String part2 = actualFederalID.substring(2);
            String validFederalID = part1 + "-" + part2;
            sendKeysToElement(registrationPageProperties.getProperty("federalTaxId"),validFederalID);
            scrollDown(2000);
            clickOnElement(registrationPageProperties.getProperty("checkboxTermCondition"), false);
            clickOnSubmitButton();
            ExtentSparkReport.getExtentLogger().info("Successfully entered username verification page fields.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("errorRegistration")),
                    getValueFromUIJSON(environment,"errorMessagesRegistrationPage.existingUsernameMessage"));
        } catch (Exception e) {
            Assert.fail("Failed to validate with valid data. Error message: " + e.getMessage());
        }
    }

    public void verifyCompanyNameMoreThan40Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 40 Characters in Last NAme Field.");
            sendKeysToElement(registrationPageProperties.getProperty("companyName"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.nameFieldValue52Chars"));
            WebElement companyNameValue = getElement(registrationPageProperties.getProperty("companyName"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            assert companyNameValue != null;
            String enteredValue = companyNameValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            System.out.println(enteredValue.length());
            System.out.println(enteredValue);
            if (enteredValue.length() == 40) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 40");
                companyNameValue.clear();
            } else {
                System.out.println("Validation failed: Entered more than 40 Characters");
                Assert.fail("Validation failed: Entered more than 40 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if First Name has more than 40 characters. Error message: " + e.getMessage());
        }
    }

    public void verifyCompanyNameAllowsSpecialCharacters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate company name Field Allows special characters.");
            sendKeysToElement(registrationPageProperties.getProperty("companyName"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.nameFieldSpecialChars"));
            WebElement companyNameSpecialCharValue = getElement(registrationPageProperties.getProperty("companyName"));
            Objects.requireNonNull(companyNameSpecialCharValue, "Company Name field is null");
            String enteredValue = companyNameSpecialCharValue.getAttribute("value");
            Assert.assertEquals(enteredValue, getValueFromUIJSON(environment,"testDataRegistrationPage.nameFieldSpecialChars"));
            ExtentSparkReport.getExtentLogger().info("Special Characters Entered Successfully.");
        } catch (Exception e) {
            Assert.fail("Failed to verify if Company Name allows special characters. Error message: " + e.getMessage());
        }
    }

    public void verifyTaxIDMoreThan9Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 10 Digits in TaxID Field.");
            sendKeysToElement(registrationPageProperties.getProperty("federalTaxId"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.TaxIDValue10Chars"));
            WebElement federalTaxIdValue = getElement(registrationPageProperties.getProperty("federalTaxId"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Digits from UI");
            assert federalTaxIdValue != null;
            String enteredValue = federalTaxIdValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Digit Counts.");
            System.out.println("Matching the value with Allowed Digit Counts. Digits Fetched from UI are: "+enteredValue.length());
            if (enteredValue.length() == 9) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Digits are 9");
                federalTaxIdValue.clear();
            } else {
                System.out.println("Validation failed: Entered more than 9 Digits");
                Assert.fail("Validation failed: Entered more than 9 Digits");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if First Name has more than 9 Digits. Error message: " + e.getMessage());
        }
    }

    public void enterValidDataRegistrationCommercial() {
        try {
            List<Map<String,String>> resultList =executeQueryAndGetRows(CISQueries.SELECT_COMMERCIAL_CUSTOMER_DATA, DatabaseConnection.DatabaseType.CIS);
            Map<String, String> result = null;
            ExtentSparkReport.getExtentLogger().info("Entering valid data.");
            ExtentSparkReport.getExtentLogger().info("Entering required personal information fields.");
                result = resultList.get(0);
                    String accountNumber = result.get("UMACT");
                    sendKeysToElement(registrationPageProperties.getProperty("accountNumber"),accountNumber);
//            sendKeysToElement(registrationPageProperties.getProperty("accountNumber"),
//                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.accountNumberCommercial"));
            String username = generateRandomAlphanumericString();
            sendKeysToElement(registrationPageProperties.getProperty("userName"), username);
            String companyName = result.get("MSLNM");
            sendKeysToElement(registrationPageProperties.getProperty("companyName"),companyName);
//            sendKeysToElement(registrationPageProperties.getProperty("companyName"),
//                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.companyName"));
            sendKeysToElement(registrationPageProperties.getProperty("emailAddress"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmEmailAddress"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.email"));
            sendKeysToElement(registrationPageProperties.getProperty("password"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.password"));
            sendKeysToElement(registrationPageProperties.getProperty("confirmPassword"),
                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.password"));
//            sendKeysToElement(registrationPageProperties.getProperty("serviceAddressNumber"),
//                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.serviceAddressHouseNumberCommercial"));
//            sendKeysToElement(registrationPageProperties.getProperty("federalTaxId"),
//                    getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.federalTaxId"));
            result = resultList.get(0);
            String federalID = result.get("E_FEDERAL_TAX_ID");
            String actualFederalID= federalID.replace("F","");
            String part1 = actualFederalID.substring(0, 2);
            String part2 = actualFederalID.substring(2);
            String validFederalID = part1 + "-" + part2;
            sendKeysToElement(registrationPageProperties.getProperty("federalTaxId"),validFederalID);
            scrollDown(2000);
            clickOnElement(registrationPageProperties.getProperty("checkboxTermCondition"), false);
            clickOnSubmitButton();
            assertURL(getValueFromUIJSON(environment,"assertURLs.emailVerificationRegistration"));
            ExtentSparkReport.getExtentLogger().info("Successfully entered required personal information fields.");

            logger.log(Level.INFO, "Sending verification code.");
            ExtentSparkReport.getExtentLogger().info("Sending verification code.");
            String masterMFA = executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_MFA, DatabaseConnection.DatabaseType.MariaDB);
            sendKeysToElement(registrationPageProperties.getProperty("verificationCode"), masterMFA);

            logger.log(Level.INFO, "Clicking on verify code button.");
            ExtentSparkReport.getExtentLogger().info("Clicking on verify code button.");
            clickOnElement(registrationPageProperties.getProperty("buttonVerifyCode"), false);

            logger.log(Level.INFO, "Asserting registration complete header message.");
            ExtentSparkReport.getExtentLogger().info("Asserting registration complete header message.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("headerLocator")),
                    getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationCompleteHeaderMessage"));

            logger.log(Level.INFO, "Asserting email verified registration message.");
            ExtentSparkReport.getExtentLogger().info("Asserting email verified registration message.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("emailVerifiedLocator")),
                    getValueFromUIJSON(environment, "errorMessagesRegistrationPage.emailVerifiedRegistrationMessage"));

            logger.log(Level.INFO, "Clicking on go to account registration success button.");
            ExtentSparkReport.getExtentLogger().info("Clicking on go to account registration success button.");
            clickOnElement(registrationPageProperties.getProperty("goToAccountRegistrationSuccess"), false);

            logger.log(Level.INFO, "Asserting login URL.");
            ExtentSparkReport.getExtentLogger().info("Asserting login URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.loginURL"));

            logger.log(Level.INFO, "Entering username for login.");
            ExtentSparkReport.getExtentLogger().info("Entering username for login.");
            enterUserName(username);

            logger.log(Level.INFO, "Entering password for login.");
            ExtentSparkReport.getExtentLogger().info("Entering password for login.");
            enterPasswordWithoutDecryption(getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));

            logger.log(Level.INFO, "Performing login with valid credentials.");
            ExtentSparkReport.getExtentLogger().info("Performing login with valid credentials.");
            performLoginForValidCredentials();


        } catch (Exception e) {
            Assert.fail("Failed to validate required personal information fields. Error message: " + e.getMessage());
        }
    }

    public void enterValidDataRegistrationResidential() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering required personal information fields.");
            logger.log(Level.INFO, "Entering required personal information fields.");

            logger.log(Level.INFO, "Sending account number.");
            ExtentSparkReport.getExtentLogger().info("Sending account number.");
            sendKeysToElement(registrationPageProperties.getProperty("accountNumber"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.accountNumber"));

            logger.log(Level.INFO, "Generating random alphanumeric string for username.");
            ExtentSparkReport.getExtentLogger().info("Generating random alphanumeric string for username.");
            String username = generateRandomAlphanumericString();

            logger.log(Level.INFO, "Sending username.");
            ExtentSparkReport.getExtentLogger().info("Sending username.");
            sendKeysToElement(registrationPageProperties.getProperty("userName"), username);

            logger.log(Level.INFO, "Sending first name.");
            ExtentSparkReport.getExtentLogger().info("Sending first name.");
            sendKeysToElement(registrationPageProperties.getProperty("firstName"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.firstName"));

            logger.log(Level.INFO, "Sending last name.");
            ExtentSparkReport.getExtentLogger().info("Sending last name.");
            sendKeysToElement(registrationPageProperties.getProperty("lastName"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.lastName"));

            logger.log(Level.INFO, "Sending email address.");
            ExtentSparkReport.getExtentLogger().info("Sending email address.");
            sendKeysToElement(registrationPageProperties.getProperty("emailAddress"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.email"));

            logger.log(Level.INFO, "Sending confirmation email address.");
            ExtentSparkReport.getExtentLogger().info("Sending confirmation email address.");
            sendKeysToElement(registrationPageProperties.getProperty("confirmEmailAddress"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.email"));

            logger.log(Level.INFO, "Sending password.");
            ExtentSparkReport.getExtentLogger().info("Sending password.");
            sendKeysToElement(registrationPageProperties.getProperty("password"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));

            logger.log(Level.INFO, "Sending confirmation password.");
            ExtentSparkReport.getExtentLogger().info("Sending confirmation password.");
            sendKeysToElement(registrationPageProperties.getProperty("confirmPassword"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));

            logger.log(Level.INFO, "Sending SSN.");
            ExtentSparkReport.getExtentLogger().info("Sending SSN.");
            sendKeysToElement(registrationPageProperties.getProperty("ssnLocator"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.ssNo"));

            logger.log(Level.INFO, "Sending driver's license.");
            ExtentSparkReport.getExtentLogger().info("Sending driver's license.");
            sendKeysToElement(registrationPageProperties.getProperty("driversLicense"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.driverLicense"));

            logger.log(Level.INFO, "Sending service address number.");
            ExtentSparkReport.getExtentLogger().info("Sending service address number.");
            sendKeysToElement(registrationPageProperties.getProperty("serviceAddressNumber"),
                    getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.houseNumber"));

            logger.log(Level.INFO, "Scrolling down the page.");
            ExtentSparkReport.getExtentLogger().info("Scrolling down the page.");
            scrollDown(1500);

            logger.log(Level.INFO, "Clicking on terms and conditions checkbox.");
            ExtentSparkReport.getExtentLogger().info("Clicking on terms and conditions checkbox.");
            clickOnElement(registrationPageProperties.getProperty("checkboxTermCondition"), false);

            logger.log(Level.INFO, "Clicking on submit button.");
            ExtentSparkReport.getExtentLogger().info("Clicking on submit button.");
            clickOnSubmitButton();

            logger.log(Level.INFO, "Asserting URL for email verification registration.");
            ExtentSparkReport.getExtentLogger().info("Asserting URL for email verification registration.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.emailVerificationRegistration"));

            ExtentSparkReport.getExtentLogger().info("Successfully entered required personal information fields.");
            logger.log(Level.INFO, "Successfully entered required personal information fields.");

            logger.log(Level.INFO, "Sending verification code.");
            ExtentSparkReport.getExtentLogger().info("Sending verification code.");
            String masterMFA = executeQuery(EngageQueries.SELECT_MASTER_KEY_FOR_MFA, DatabaseConnection.DatabaseType.MariaDB);
            sendKeysToElement(registrationPageProperties.getProperty("verificationCode"), masterMFA);

            logger.log(Level.INFO, "Clicking on verify code button.");
            ExtentSparkReport.getExtentLogger().info("Clicking on verify code button.");
            clickOnElement(registrationPageProperties.getProperty("buttonVerifyCode"), false);

            logger.log(Level.INFO, "Asserting registration complete header message.");
            ExtentSparkReport.getExtentLogger().info("Asserting registration complete header message.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("headerLocator")),
                    getValueFromUIJSON(environment, "errorMessagesRegistrationPage.registrationCompleteHeaderMessage"));

            logger.log(Level.INFO, "Asserting email verified registration message.");
            ExtentSparkReport.getExtentLogger().info("Asserting email verified registration message.");
            Assert.assertEquals(getTextOfElement(registrationPageProperties.getProperty("emailVerifiedLocator")),
                    getValueFromUIJSON(environment, "errorMessagesRegistrationPage.emailVerifiedRegistrationMessage"));

            logger.log(Level.INFO, "Clicking on go to account registration success button.");
            ExtentSparkReport.getExtentLogger().info("Clicking on go to account registration success button.");
            clickOnElement(registrationPageProperties.getProperty("goToAccountRegistrationSuccess"), false);

            logger.log(Level.INFO, "Asserting login URL.");
            ExtentSparkReport.getExtentLogger().info("Asserting login URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.loginURL"));

            logger.log(Level.INFO, "Entering username for login.");
            ExtentSparkReport.getExtentLogger().info("Entering username for login.");
            enterUserName(username);

            logger.log(Level.INFO, "Entering password for login.");
            ExtentSparkReport.getExtentLogger().info("Entering password for login.");
            enterPasswordWithoutDecryption(getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.password"));

            logger.log(Level.INFO, "Performing login with valid credentials.");
            ExtentSparkReport.getExtentLogger().info("Performing login with valid credentials.");
            performLoginForValidCredentials();

        } catch (Exception e) {
            ExtentSparkReport.getExtentLogger().fail("Exception occurred: " + e.getMessage());
            logger.log(Level.SEVERE, "Exception occurred", e);
            throw new RuntimeException(e);
        }
    }

}

