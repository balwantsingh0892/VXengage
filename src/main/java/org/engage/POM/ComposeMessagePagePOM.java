package org.engage.POM;

import com.aventstack.extentreports.Status;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.POM.HomePagePOM.homePageProperties;

public class ComposeMessagePagePOM {
    public static Properties composeMessagePageProperties;
    public static List<String> environment;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ComposeMessagePagePOM(List<String> environment) {
        ComposeMessagePagePOM.environment = environment;
        composeMessagePageProperties = BaseUtilities.readPropertyfile(FilePaths.COMPOSE_MESSAGE_PAGE_PROPERTIES);
    }

    public void goToComposeMessage() {
        try {
            logger.log(Level.INFO, "Navigating to Messages section.");
            ExtentSparkReport.getExtentLogger().info("Navigating to Messages section.");
            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Messages"));
            logger.log(Level.INFO, "Clicking on Compose Message.");
            ExtentSparkReport.getExtentLogger().info("Clicking on Compose Message.");
            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Compose Message"));

            String expectedURL = getValueFromUIJSON(environment, "assertURLs.composeMessageURL");
            logger.log(Level.INFO, "Asserting the current URL: {0}", expectedURL);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting the current URL " + expectedURL);
            assertURL(expectedURL);

            logger.log(Level.INFO, "Navigation to Compose Message page completed successfully.");
            ExtentSparkReport.getExtentLogger().info("Navigation to Compose Message page completed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Navigation to Compose Message page failed: {0}", e.getMessage());
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Navigation to Compose Message page failed " + e.getMessage());
            throw e;
        }
    }

    public void validateVerbiageTitleAndMessages() {

        try {
            logger.log(Level.INFO, "Starting validation of compose message page.");
            String composeMessageHeader = getTextOfElement(composeMessagePageProperties.getProperty("composeMessageHeader"));
            String expectedComposeMessageHeader = getValueFromUIJSON(environment, "composeMessagePageData.composeHeaderMessage");
            logger.log(Level.INFO, "Validating compose message header: {0} == {1}", new Object[]{composeMessageHeader, expectedComposeMessageHeader});
            Assert.assertEquals(composeMessageHeader, expectedComposeMessageHeader);

            String composeHeaderVerbiage = getTextOfElement(composeMessagePageProperties.getProperty("composeHeaderVerbiage"));
            String expectedComposeHeaderVerbiage = getValueFromUIJSON(environment, "composeMessagePageData.headerVerbiage");
            logger.log(Level.INFO, "Validating compose header verbiage: {0} == {1}", new Object[]{composeHeaderVerbiage, expectedComposeHeaderVerbiage});
            Assert.assertEquals(composeHeaderVerbiage, expectedComposeHeaderVerbiage);

            String categoryText = getTextOfElement(composeMessagePageProperties.getProperty("composeFieldsName").replace("***", "category"));
            String expectedCategoryText = getValueFromUIJSON(environment, "composeMessagePageData.categoryText");
            logger.log(Level.INFO, "Validating category text: {0} == {1}", new Object[]{categoryText, expectedCategoryText});
            Assert.assertEquals(categoryText, expectedCategoryText);

            String messageText = getTextOfElement(composeMessagePageProperties.getProperty("composeFieldsName").replace("***", "message"));
            String expectedMessageText = getValueFromUIJSON(environment, "composeMessagePageData.messageText");
            logger.log(Level.INFO, "Validating message text: {0} == {1}", new Object[]{messageText, expectedMessageText});
            Assert.assertEquals(messageText, expectedMessageText);

            String subjectText = getTextOfElement(composeMessagePageProperties.getProperty("composeFieldsName").replace("***", "subject"));
            String expectedSubjectText = getValueFromUIJSON(environment, "composeMessagePageData.subjectText");
            logger.log(Level.INFO, "Validating subject text: {0} == {1}", new Object[]{subjectText, expectedSubjectText});
            Assert.assertEquals(subjectText, expectedSubjectText);

            String attachmentText = getTextOfElement(composeMessagePageProperties.getProperty("attachmentLocator"));
            String expectedAttachmentText = getValueFromUIJSON(environment, "composeMessagePageData.attachmentText");
            logger.log(Level.INFO, "Validating attachment text: {0} == {1}", new Object[]{attachmentText, expectedAttachmentText});
            Assert.assertEquals(attachmentText, expectedAttachmentText);

            String messageHeader = getTextOfElement(composeMessagePageProperties.getProperty("messageHeader"));
            String expectedMessageHeader = getValueFromUIJSON(environment, "composeMessagePageData.messageText");
            logger.log(Level.INFO, "Validating message header: {0} == {1}", new Object[]{messageHeader, expectedMessageHeader});
            Assert.assertEquals(messageHeader, expectedMessageHeader);

            String attachmentVerbiage = getTextOfElement(composeMessagePageProperties.getProperty("attachmentVerbiage"));
            String expectedAttachmentVerbiage = Objects.requireNonNull(getValueFromUIJSON(environment, "composeMessagePageData.attachmentVerbiage")).trim();
            logger.log(Level.INFO, "Validating attachment verbiage: {0} == {1}", new Object[]{attachmentVerbiage, expectedAttachmentVerbiage});
            Assert.assertEquals(attachmentVerbiage, expectedAttachmentVerbiage);

            logger.log(Level.INFO, "Validation of compose message page completed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Validation failed: {0}", e.getMessage());
            throw e;
        }
    }

    public void validateNegativeCasesWithRequiredFields(SoftAssert softAssert) {
        try {
            logger.log(Level.INFO, "Clicking on send button.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on send button.");
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Validating required field message for category.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating required field message for category.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("requiredFieldLocator").replace("***", "category")),
                    getValueFromUIJSON(environment, "composeMessagePageData.requiredFieldMessage"));

            logger.log(Level.INFO, "Validating required field message for subject.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating required field message for subject.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("requiredFieldLocator").replace("***", "subject")),
                    getValueFromUIJSON(environment, "composeMessagePageData.requiredFieldMessage"));

            logger.log(Level.INFO, "Validating required field message for message.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating required field message for message.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("requiredFieldLocator").replace("***", "message")),
                    getValueFromUIJSON(environment, "composeMessagePageData.requiredFieldMessage"));

            logger.log(Level.INFO, "Validating error message.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating error message.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("errorMessageLocator")),
                    getValueFromUIJSON(environment, "composeMessagePageData.errorMessage"));

            logger.log(Level.INFO, "Clicking on category locator.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on category locator.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryLocator"), false);

            logger.log(Level.INFO, "Retrieving category list.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Retrieving category list.");
            List<WebElement> categoryList = getElements(composeMessagePageProperties.getProperty("categoryDropDown"));
            List<String> expectedCategory = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "composeMessagePageData.categoryList")).split(","));

            assert categoryList != null;
            logger.log(Level.INFO, "Validating category list size.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating category list size.");
            Assert.assertEquals(categoryList.size(), expectedCategory.size());

            for (int i = 0; i < Objects.requireNonNull(categoryList).size(); i++) {
                String categoryText = categoryList.get(i).getText();
                String expectedText = expectedCategory.get(i);
                logger.log(Level.INFO, "Validating category text: {0} == {1}", new Object[]{categoryText, expectedText});
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating category text: " + categoryText + " == " + expectedText);
                Assert.assertEquals(categoryText, expectedText);
            }

            logger.log(Level.INFO, "Clicking on the first category in the dropdown.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on the first category in the dropdown.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryFirstDropDown"), false);

            logger.log(Level.INFO, "Entering subject text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering subject text.");
            sendKeysToElement(composeMessagePageProperties.getProperty("subject"),
                    getValueFromUIJSON(environment, "composeMessageTestData.subject130Letters"));

            String enteredValue = Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("subject"))).getAttribute("value");
            assert enteredValue != null;

            if (enteredValue.length() < 125) {
                String errorMsg = "Max allowed letters are not 125, Max allowed letters are " + enteredValue.length();
                logger.log(Level.SEVERE, errorMsg);
                softAssert.fail(errorMsg);
            } else if (enteredValue.length() > 125) {
                String errorMsg = "Entered more than 125 letters: " + enteredValue.length();
                logger.log(Level.SEVERE, errorMsg);
                ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
                Assert.fail(errorMsg);
            } else {
                String successMsg = "Successfully validated. Max allowed letters are " + enteredValue.length();
                logger.log(Level.INFO, successMsg);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Validation failed: {0}", e.getMessage());
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Validation failed: " + e.getMessage());
            throw e;
        }
    }

    public void validateNegativeCasesForOtherFields() {
        try {
            logger.log(Level.INFO, "Clearing the subject field.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clearing the subject field.");
            Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("subject"))).clear();

            String specialChars = getValueFromUIJSON(environment, "composeMessageTestData.specialChars");
            logger.log(Level.INFO, "Entering special characters into the subject field: {0}", specialChars);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering special characters into the subject field: " + specialChars);
            sendKeysToElement(composeMessagePageProperties.getProperty("subject"), specialChars);

            String enteredValue = Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("subject"))).getAttribute("value");

            logger.log(Level.INFO, "Validating the entered value: {0} == {1}", new Object[]{enteredValue, specialChars});
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating the entered value: " + enteredValue + " == " + specialChars);
            Assert.assertEquals(enteredValue, specialChars);

            logger.log(Level.INFO, "Validation of negative cases for other fields completed successfully.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validation of negative cases for other fields completed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Validation failed: {0}", e.getMessage());
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Validation failed: " + e.getMessage());
            throw e;
        }

        try {
            String randomString = generateRandomString(300);
            logger.log(Level.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            switchToFrameWithIndex(0);
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), randomString);
            String extractedText = Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("messageBody"))).getText();
            int extractedTextCount = extractedText.length();
            switchToDefaultContent();
            String actualCount = getTextOfElement(composeMessagePageProperties.getProperty("textCount"));
            assert actualCount != null;
            int actualCountInt = Integer.parseInt(actualCount);

            if (extractedTextCount == actualCountInt && actualCountInt == randomString.length()) {
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validated Successfully Characters Count.");
                logger.log(Level.INFO, "Validated Successfully Characters Count.");
            }

            String randomString2005 = generateRandomString(2005);
            logger.log(Level.INFO, "Switching to frame with index 0 and entering random string of 2005 characters.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Switching to frame with index 0 and entering random string of 2005 characters.");
            switchToFrameWithIndex(0);
            Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("messageBody"))).clear();
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), randomString2005);
            switchToDefaultContent();
            logger.log(Level.INFO, "Clicking on send button.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on send button.");
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Asserting error message for exceeding character limit.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting error message for exceeding character limit.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("errorMessageLocator")),
                    getValueFromUIJSON(environment, "composeMessagePageData.subjectMoreThan2000CharMessage"));

            logger.log(Level.INFO, "Asserted Successfully. More than 2000 Characters are not allowed in Message Body.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserted Successfully. More than 2000 Characters are not allowed in Message Body.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Validation failed: {0}", e.getMessage());
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Validation failed: " + e.getMessage());
            throw e;
        }

        try {
            logger.log(Level.INFO, "Validating toolbar visibility.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating toolbar visibility.");
            Assert.assertTrue(Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("toolbar"))).isDisplayed());

            logger.log(Level.INFO, "Clearing message body and entering bold text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clearing message body and entering bold text.");
            switchToFrameWithIndex(0);
            Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("messageBody"))).clear();
            switchToDefaultContent();

            logger.log(Level.INFO, "Clicking on bold button.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on bold button.");
            clickOnElement(composeMessagePageProperties.getProperty("boldLocator"), false);
            clickOnElement(composeMessagePageProperties.getProperty("italicLocator"), false);
            logger.log(Level.INFO, "Entering bold text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering bold text.");
            switchToFrameWithIndex(0);
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), getValueFromUIJSON(environment, "composeMessagePageData.boldText"));
            logger.log(Level.INFO, "Validating bold text visibility.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating bold text visibility.");
            Assert.assertTrue(Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("textBoldLocator"))).isDisplayed());
            logger.log(Level.INFO, "Validating Italic text visibility.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validating Italic text visibility.");
            Assert.assertTrue(Objects.requireNonNull(getElement(composeMessagePageProperties.getProperty("textItalicLocation"))).isDisplayed());
            logger.log(Level.INFO, "Validation of bold and italic text completed successfully.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validation of bold and italic text completed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Validation failed: {0}", e.getMessage());
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Validation failed: " + e.getMessage());
            throw e;
        }
    }

    public void validateFileNegativeCases() {
        try {
            logger.log(Level.INFO, "Clicking on category locator.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on category locator.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryLocator"), false);

            logger.log(Level.INFO, "Clicking on the first category in the dropdown.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on the first category in the dropdown.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryFirstDropDown"), false);

            logger.log(Level.INFO, "Entering subject text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering subject text.");
            sendKeysToElement(composeMessagePageProperties.getProperty("subject"), generateRandomString(130));

            String randomString = generateRandomString(300);
            logger.log(Level.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            switchToFrameWithIndex(0);
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), randomString);
            switchToDefaultContent();

            logger.log(Level.INFO, "Uploading unsupported document type.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Uploading unsupported document type.");
            sendKeysToElement(composeMessagePageProperties.getProperty("fileUpload"), FilePaths.UNSUPPORTED_DOCUMENT_TYPE);
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Verifying unsupported document type error message.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Verifying unsupported document type error message.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("errorMessageLocator")), getValueFromUIJSON(environment, "composeMessagePageData.unsupportedErrorMessage"));

            WebElement fileInputElement = getElement(composeMessagePageProperties.getProperty("fileUpload"));
            resetFileInputElement(fileInputElement);

            logger.log(Level.INFO, "Uploading file larger than 40MB.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Uploading file larger than 40MB.");
            sendKeysToElement(composeMessagePageProperties.getProperty("fileUpload"), FilePaths.UPLOAD_FILE_SIZE_MORE_THAN_40MB);
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Verifying file size error message.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Verifying file size error message.");
            Assert.assertEquals(getTextOfElement(composeMessagePageProperties.getProperty("errorMessageLocator")), getValueFromUIJSON(environment, "composeMessagePageData.fileMoreThan40MBMessage"));

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An exception occurred: " + e.getMessage(), e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "An exception occurred: " + e.getMessage());
            throw e;
        }
    }


    public void validateHappyFlowWithoutAttachment() {
        try {
            logger.log(Level.INFO, "Clicking on category locator.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on category locator.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryLocator"), false);

            logger.log(Level.INFO, "Clicking on the first category in the dropdown.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on the first category in the dropdown.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryFirstDropDown"), false);

            logger.log(Level.INFO, "Entering subject text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering subject text.");
            String subjectRandomString = generateRandomString(20);
            sendKeysToElement(composeMessagePageProperties.getProperty("subject"), subjectRandomString);

            String randomString = generateRandomString(50);
            logger.log(Level.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            switchToFrameWithIndex(0);
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), randomString);
            switchToDefaultContent();

            logger.log(Level.INFO, "Clicking on send button.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on send button.");
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Asserting URL.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.sentItemsURL"));

            List<WebElement> element = getElements(composeMessagePageProperties.getProperty("firstRow"));
            List<String> expectedFirstRowData = Arrays.asList("Customer Service", "Government Document", subjectRandomString);

            logger.log(Level.INFO, "Asserting first row data.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting first row data.");
            assert element != null;
            for (int i = 0; i < element.size(); i++) {
                String actualElementText = element.get(i).getText();
                logger.log(Level.INFO, "Actual element text: " + actualElementText);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual element text: " + actualElementText);

                String expectedText = expectedFirstRowData.get(i);
                logger.log(Level.INFO, "Expected text: " + expectedText);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected text: " + expectedText);

                Assert.assertEquals(actualElementText, expectedText);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            ZonedDateTime cstDateTime = ZonedDateTime.now(ZoneId.of("America/Chicago"));
            String currentDateCST = cstDateTime.format(formatter);
            logger.log(Level.INFO, "Current Date in CST: " + currentDateCST);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Date in CST: " + currentDateCST);

            String actualElementText = getTextOfElement(composeMessagePageProperties.getProperty("firstRowDate"));
            logger.log(Level.INFO, "Actual date text: " + actualElementText);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual date text: " + actualElementText);

            logger.log(Level.INFO, "Asserting date.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting date.");
            Assert.assertEquals(actualElementText, currentDateCST);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred: ", e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Exception occurred: " + e.getMessage());
            throw e;
        }
    }

    public void validateHappyFlowWithAttachment()
    {
        try {
            logger.log(Level.INFO, "Clicking on category locator.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on category locator.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryLocator"), false);

            logger.log(Level.INFO, "Clicking on the first category in the dropdown.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on the first category in the dropdown.");
            clickOnElement(composeMessagePageProperties.getProperty("categoryFirstDropDown"), false);

            logger.log(Level.INFO, "Entering subject text.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Entering subject text.");
            String subjectRandomString = generateRandomString(20);
            sendKeysToElement(composeMessagePageProperties.getProperty("subject"), subjectRandomString);

            String randomString = generateRandomString(50);
            logger.log(Level.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Switching to frame with index 0 and entering random string of 300 characters.");
            switchToFrameWithIndex(0);
            sendKeysToElement(composeMessagePageProperties.getProperty("messageBody"), randomString);
            switchToDefaultContent();

            logger.log(Level.INFO, "Uploading valid file.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Uploading valid file.");
            sendKeysToElement(composeMessagePageProperties.getProperty("fileUpload"), FilePaths.VALID_FILE_EXTENSION_SIZE);

            logger.log(Level.INFO, "Clicking on send button.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on send button.");
            clickOnElement(composeMessagePageProperties.getProperty("sendButton"), false);

            logger.log(Level.INFO, "Asserting URL.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.sentItemsURL"));

            List<WebElement> element = getElements(composeMessagePageProperties.getProperty("firstRow"));
            List<String> expectedFirstRowData = Arrays.asList("Customer Service", "Government Document", subjectRandomString);

            logger.log(Level.INFO, "Asserting first row data.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting first row data.");
            assert element != null;
            for (int i = 0; i < element.size(); i++) {
                String actualElementText = element.get(i).getText();
                logger.log(Level.INFO, "Actual element text: " + actualElementText);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual element text: " + actualElementText);

                String expectedText = expectedFirstRowData.get(i);
                logger.log(Level.INFO, "Expected text: " + expectedText);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected text: " + expectedText);

                Assert.assertEquals(actualElementText, expectedText);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            ZonedDateTime cstDateTime = ZonedDateTime.now(ZoneId.of("America/Chicago"));
            String currentDateCST = cstDateTime.format(formatter);
            logger.log(Level.INFO, "Current Date in CST: " + currentDateCST);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Date in CST: " + currentDateCST);

            String actualElementText = getTextOfElement(composeMessagePageProperties.getProperty("firstRowDate"));
            logger.log(Level.INFO, "Actual date text: " + actualElementText);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual date text: " + actualElementText);

            logger.log(Level.INFO, "Asserting date.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting date.");
            Assert.assertEquals(actualElementText, currentDateCST);

            logger.log(Level.INFO, "Clicking on email icon.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicking on email icon.");
            clickOnElement(composeMessagePageProperties.getProperty("emailIcon"), false);

            logger.log(Level.INFO, "Asserting read message URL.");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserting read message URL.");
            assertURL(getValueFromUIJSON(environment, "assertURLs.readMessageURL"));

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred: ", e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Exception occurred: " + e.getMessage());
            throw e;
        }
    }
    }
