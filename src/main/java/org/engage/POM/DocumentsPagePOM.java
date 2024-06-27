package org.engage.POM;

import com.aventstack.extentreports.Status;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import static org.engage.Base.BaseUtilities.*;
import static org.engage.POM.HomePagePOM.homePageProperties;

public class DocumentsPagePOM {
    public static Properties documentsPageProperties;
    int initialCount;
    List<String> actualOptions;
    Logger logger = Logger.getLogger(this.getClass().getName());
    public static List<String> environment;

    public DocumentsPagePOM(List<String> environment)
    {
        DocumentsPagePOM.environment = environment;
        documentsPageProperties = BaseUtilities.readPropertyfile(FilePaths.DOCUMENTS_PAGE_PROPERTIES);
    }

    public void goToUploadDocuments() {
        try {
            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Documents"));
            String successMsg1 = "Clicked on 'Documents' in the sidebar menu";
            logger.log(Level.INFO, successMsg1);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg1);

            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Upload Documents"));
            String successMsg2 = "Clicked on 'Upload Documents' in the sidebar menu";
            logger.log(Level.INFO, successMsg2);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg2);

            assertURL(getValueFromUIJSON(environment, "assertURLs.uploadDocumentsURL"));
            String successMsg3 = "Asserted URL for Upload Documents page";
            logger.log(Level.INFO, successMsg3);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg3);
        } catch (Exception e) {
            String errorMsg = "Exception occurred: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void selectSpecificAccountForUpload() throws InterruptedException {
        try {
            String requiredAccount = "23956";
            Thread.sleep(2000);

            String currentAccount = getTextOfElement(documentsPageProperties.getProperty("accountLocator"));
            logger.log(Level.INFO, "Current Account: " + currentAccount);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Account: " + currentAccount);

            assert currentAccount != null;
            if (!currentAccount.matches(".*\\b" + requiredAccount + "\\b.*")) {
                clickOnElement(documentsPageProperties.getProperty("accountLocator"), false);
                List<WebElement> accountsList = getElements(documentsPageProperties.getProperty("accountsDropdown"));
                assert accountsList != null;

                logger.log(Level.INFO, "Accounts List: " + accountsList);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Accounts List: " + accountsList);

                for (WebElement element : accountsList) {
                    String accountText = getTextOfElements(element);
                    assert accountText != null;

                    logger.log(Level.INFO, "Current Account Text: " + accountText);
                    ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Account Text: " + accountText);

                    if (accountText.matches(".*\\b" + requiredAccount + "\\b.*")) {
                        element.click();
                        logger.log(Level.INFO, "Clicked Account: " + accountText);
                        ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked Account: " + accountText);
                        break;

                    }
                }
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            String errorMsg = "Exception occurred: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }


    public void goToViewDocuments() {
        try {
            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Documents"));
            String successMsg1 = "Clicked on 'Documents' in the sidebar menu";
            logger.log(Level.INFO, successMsg1);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg1);

            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "View Documents"));
            String successMsg2 = "Clicked on 'View Documents' in the sidebar menu";
            logger.log(Level.INFO, successMsg2);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg2);

            assertURL(getValueFromUIJSON(environment, "assertURLs.viewDocumentURL"));
            String successMsg3 = "Asserted URL for View Documents page";
            logger.log(Level.INFO, successMsg3);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, successMsg3);
        } catch (Exception e) {
            String errorMsg = "Exception occurred: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateVerbiageTitleRequiredMessages() {
        try {
            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("headerViewAndUploadDocument")), getValueFromUIJSON(environment, "documentsValidationData.uploadDocumentHeader"));
            logger.log(Level.INFO, "Validated headerViewAndUploadDocument");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("verbiageUploadDocument")), getValueFromUIJSON(environment, "documentsValidationData.verbiageUploadDocument"));
            logger.log(Level.INFO, "Validated verbiageUploadDocument");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("uploadHelpVerbiage")), getValueFromUIJSON(environment, "documentsValidationData.uploadHelpVerbiage"));
            logger.log(Level.INFO, "Validated uploadHelpVerbiage");

            performJSClickElement(documentsPageProperties.getProperty("addButton"));
            logger.log(Level.INFO, "Clicked on addButton");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("errorMessage")), getValueFromUIJSON(environment, "documentsValidationData.mainAlertErrorMessage"));
            logger.log(Level.INFO, "Validated errorMessage");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("categoryMessage")), getValueFromUIJSON(environment, "documentsValidationData.requiredMessage"));
            logger.log(Level.INFO, "Validated categoryMessage");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("fileUploadMessage")), getValueFromUIJSON(environment, "documentsValidationData.requiredMessage"));
            logger.log(Level.INFO, "Validated fileUploadMessage");

        } catch (Exception e) {
            String errorMsg = "Exception occurred: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateCardTitleWithAccountNumber() {
        try {
            String header2UploadText = getTextOfElement(documentsPageProperties.getProperty("header2UploadDocument"));
            String accountText = getTextOfElement(documentsPageProperties.getProperty("accountLocator"));
            if (header2UploadText == null || accountText == null) {
                throw new AssertionError("Text elements should not be null");
            }
            String extractedNumberHeader = extractNumber(header2UploadText);
            String extractedAccountNumber = extractNumber(accountText);

            logger.log(Level.INFO, "Extracted Number from Header: " + extractedNumberHeader);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Extracted Number from Header: " + extractedNumberHeader);

            logger.log(Level.INFO, "Extracted Number from Account: " + extractedAccountNumber);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Extracted Number from Account: " + extractedAccountNumber);

            Assert.assertEquals(extractedNumberHeader, extractedAccountNumber, "The numbers in the header and the account do not match!");

            String expectedHeaderMessage = getValueFromUIJSON(environment, "documentsValidationData.cardHeaderMessage") + extractedNumberHeader + ")";
            Assert.assertEquals(header2UploadText, expectedHeaderMessage);

            logger.log(Level.INFO, "Validated card header message");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validated card header message");
        } catch (Exception e) {
            String errorMsg = "Exception occurred: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateDropDownItemsForCategory() {
        try {
            clickOnElement(documentsPageProperties.getProperty("selectCategory"), false);
            logger.log(Level.INFO, "Clicked on selectCategory");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on selectCategory");

            List<WebElement> categoryElements = getElements(documentsPageProperties.getProperty("categoryList"));
            logger.log(Level.INFO, "Found " + categoryElements.size() + " category elements");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Found " + categoryElements.size() + " category elements");

            List<String> expectedCategoryElement = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "documentsValidationData.categoryElements")).split(","));
            Assert.assertNotNull(categoryElements);
            Assert.assertEquals(categoryElements.size(), expectedCategoryElement.size(), "Number of Category elements and expected categories do not match.");

            for (int i = 0; i < categoryElements.size(); i++) {
                String actualCategory = categoryElements.get(i).getText();
                logger.log(Level.INFO, "Actual Category: " + actualCategory);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual Category: " + actualCategory);

                String expectedCategory = expectedCategoryElement.get(i);
                logger.log(Level.INFO, "Expected Category: " + expectedCategory);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected Category: " + expectedCategory);

                Assert.assertEquals(actualCategory, expectedCategory, "Category at index " + i + " does not match.");
            }

            ExtentSparkReport.getExtentLogger().info("Verified all the Categories in Upload Documents.");
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateUnsupportedDocumentType() {
        try {
            clickOnElement(documentsPageProperties.getProperty("selectDocument").replace("***", "Credit Reference"), false);
            logger.log(Level.INFO, "Clicked on selectDocument");

            sendKeysToElement(documentsPageProperties.getProperty("selectFile"), FilePaths.UNSUPPORTED_DOCUMENT_TYPE);
            logger.log(Level.INFO, "Sent keys to selectFile");

            performJSClickElement(documentsPageProperties.getProperty("addButton"));
            logger.log(Level.INFO, "Clicked on addButton");

            String expectedErrorMessage = getValueFromUIJSON(environment, "documentsValidationData.invalidFileFormatErrorMessage");
            String actualErrorMessage = getTextOfElement(documentsPageProperties.getProperty("errorMessage"));
            logger.log(Level.INFO, "Expected Error Message: " + expectedErrorMessage);
            logger.log(Level.INFO, "Actual Error Message: " + actualErrorMessage);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected Error Message: " + expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual Error Message: " + actualErrorMessage);

            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }


    public void validateFileMoreThan40MB() {
        try {
            clickOnElement(documentsPageProperties.getProperty("selectCategory"), false);
            logger.log(Level.INFO, "Clicked on selectCategory");

            clickOnElement(documentsPageProperties.getProperty("selectDocument").replace("***", "Credit Reference"), false);
            logger.log(Level.INFO, "Clicked on selectDocument");

            sendKeysToElement(documentsPageProperties.getProperty("selectFile"), FilePaths.UPLOAD_FILE_SIZE_MORE_THAN_40MB);
            logger.log(Level.INFO, "Sent keys to selectFile");

            performJSClickElement(documentsPageProperties.getProperty("addButton"));
            logger.log(Level.INFO, "Clicked on addButton");

            String expectedErrorMessage = getValueFromUIJSON(environment, "documentsValidationData.fileSizeMoreThan40MBErrorMessage");
            String actualErrorMessage = getTextOfElement(documentsPageProperties.getProperty("errorMessage"));
            logger.log(Level.INFO, "Expected Error Message: " + expectedErrorMessage);
            logger.log(Level.INFO, "Actual Error Message: " + actualErrorMessage);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected Error Message: " + expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual Error Message: " + actualErrorMessage);

            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }


    public void validFileExtensionWithAllowedFileSize() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            ZonedDateTime cstDateTime = ZonedDateTime.now(ZoneId.of("America/Chicago"));
            String currentDateCST = cstDateTime.format(formatter);
            logger.log(Level.INFO, "Current Date in CST: " + currentDateCST);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Date in CST: " + currentDateCST);

            List<WebElement> tableRows = getElements(documentsPageProperties.getProperty("tableRows"));
            assert tableRows != null;
            initialCount = countRowsWithDate(tableRows, currentDateCST);
            logger.log(Level.INFO, "Initial count of rows with current date: " + initialCount);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Initial count of rows with current date: " + initialCount);

            performJSClickElement(homePageProperties.getProperty("sideBarMenu").replace("***", "Upload Documents"));
            logger.log(Level.INFO, "Clicked on 'Upload Documents' in the sidebar menu");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on 'Upload Documents' in the sidebar menu");

            clickOnElement(documentsPageProperties.getProperty("selectCategory"), false);
            logger.log(Level.INFO, "Clicked on selectCategory");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on selectCategory");

            clickOnElement(documentsPageProperties.getProperty("selectDocument").replace("***", "Credit Reference"), false);
            logger.log(Level.INFO, "Clicked on selectDocument");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on selectDocument");

            sendKeysToElement(documentsPageProperties.getProperty("selectFile"), FilePaths.VALID_FILE_EXTENSION_SIZE);
            logger.log(Level.INFO, "Sent keys to selectFile");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Sent keys to selectFile");

            performJSClickElement(documentsPageProperties.getProperty("addButton"));
            logger.log(Level.INFO, "Clicked on addButton");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on addButton");

            assertURL(getValueFromUIJSON(environment, "assertURLs.viewUploadDocumentURL"));
            logger.log(Level.INFO, "Asserted URL for View Upload Document page");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Asserted URL for View Upload Document page");

            Assert.assertEquals(getTextOfElement(documentsPageProperties.getProperty("viewDocumentMainMessageLocator")), getValueFromUIJSON(environment, "documentsValidationData.fileUploadSuccessMessage"));
            logger.log(Level.INFO, "Validated file upload success message");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validated file upload success message");
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateTableHeadersText() {
        try {
            List<WebElement> tableHeaderElements = getElements(documentsPageProperties.getProperty("tableHeaders"));
            List<String> expectedHeaders = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "documentsValidationData.tableHeadersViewDocument")).split(","));
            Assert.assertNotNull(tableHeaderElements);
            Assert.assertEquals(tableHeaderElements.size(), expectedHeaders.size(), "Number of Header elements and expected headers do not match.");

            for (int i = 0; i < expectedHeaders.size(); i++) {
                Assert.assertEquals(tableHeaderElements.get(i).getText(), expectedHeaders.get(i), "Header text mismatch at index " + i);
                logger.log(Level.INFO, "Actual Header: " + tableHeaderElements.get(i).getText() + ", Expected Header: " + expectedHeaders.get(i));
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual Header: " + tableHeaderElements.get(i).getText() + ", Expected Header: " + expectedHeaders.get(i));
            }
            ExtentSparkReport.getExtentLogger().info("Verified all the Headers in View Documents Table.");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            ZonedDateTime cstDateTime = ZonedDateTime.now(ZoneId.of("America/Chicago"));
            String currentDateCST = cstDateTime.format(formatter);
            logger.log(Level.INFO, "Current Date in CST: " + currentDateCST);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Date in CST: " + currentDateCST);

            List<WebElement> tableRows = getElements(documentsPageProperties.getProperty("tableRows"));
            assert tableRows != null;
            int newRowCount = countRowsWithDate(tableRows, currentDateCST);
            logger.log(Level.INFO, "New count of rows with current date: " + newRowCount);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "New count of rows with current date: " + newRowCount);
            Assert.assertEquals(initialCount + 1, newRowCount);

            List<WebElement> firstRowData = getElements(documentsPageProperties.getProperty("firstRowDataList"));
            assert firstRowData != null;
            List<String> expectedFirstRowData = Arrays.asList("SupportedFileTest", "txt", "Credit Reference", "17 B", currentDateCST);
            for (int i = 0; i < expectedFirstRowData.size(); i++) {
                String actualValue = firstRowData.get(i + 1).getText();
                logger.log(Level.INFO, "Actual Value : " + actualValue);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Actual Value : " + actualValue);
                String expectedValue = expectedFirstRowData.get(i);
                logger.log(Level.INFO, "Expected Value : " + expectedValue);
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Expected Value : " + expectedValue);
                Assert.assertEquals(actualValue, expectedValue, "Mismatch at index " + (i + 1));
            }
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }


    public void validateTitleAndOtherPageElements() {
        try {
            String viewDocumentHeader = getTextOfElement(documentsPageProperties.getProperty("headerViewAndUploadDocument"));
            Assert.assertEquals(viewDocumentHeader, getValueFromUIJSON(environment, "documentsValidationData.viewDocumentHeaderAndTitle"));
            logger.log(Level.INFO, "Validated view document header");
            ExtentSparkReport.getExtentLogger().info("Validated view document header");

            Assert.assertTrue(Objects.requireNonNull(getElement(documentsPageProperties.getProperty("searchBox"))).isDisplayed());
            logger.log(Level.INFO, "Search Bar is Present on the UI");
            ExtentSparkReport.getExtentLogger().info("Search Bar is Present on the UI");

            actualOptions = getAllDropdownOption(documentsPageProperties.getProperty("dropdownViewDocument"));
            List<String> expectedOptions = Arrays.asList("10", "20", "30", "40", "50", "100");

            if (actualOptions.size() != expectedOptions.size()) {
                logger.log(Level.WARNING, "Number of options mismatch: Expected " + expectedOptions.size() + ", but found " + actualOptions.size());
                ExtentSparkReport.getExtentLogger().log(Status.WARNING, "Number of options mismatch: Expected " + expectedOptions.size() + ", but found " + actualOptions.size());
            } else {
                for (int i = 0; i < actualOptions.size(); i++) {
                    String actualOptionText = actualOptions.get(i);
                    String expectedOptionText = expectedOptions.get(i);

                    if (!actualOptionText.equals(expectedOptionText)) {
                        logger.log(Level.WARNING, "Option text mismatch at index " + i + ": Expected " + expectedOptionText + ", but found " + actualOptionText);
                        ExtentSparkReport.getExtentLogger().log(Status.WARNING, "Option text mismatch at index " + i + ": Expected " + expectedOptionText + ", but found " + actualOptionText);
                    }
                }
            }

            Assert.assertEquals(getTitle(), getValueFromUIJSON(environment, "documentsValidationData.viewDocumentHeaderAndTitle"));
            logger.log(Level.INFO, "Title Verified.");
            ExtentSparkReport.getExtentLogger().info("Title Verified.");

            List<WebElement> elements = getElements(documentsPageProperties.getProperty("entriesTextPath"));
            for (int i = 0; i < Objects.requireNonNull(elements).size(); i++) {
                String elementText = elements.get(i).getText();
                if (elementText.contains("entries per page")) {
                    logger.log(Level.INFO, "Entries Per Page Text Validated Successfully.");
                    ExtentSparkReport.getExtentLogger().info("Entries Per Page Text Validated Successfully.");
                    break;
                }
            }
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validatePagination() {
        try {
            if (actualOptions.contains("100")) {
                selectValueFromDropdown(documentsPageProperties.getProperty("dropdownViewDocument"), "100");
                logger.log(Level.INFO, "Selected '100' from the dropdown");
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Selected '100' from the dropdown");
            } else {
                logger.log(Level.WARNING, "Value '100' not found in the dropdown options.");
                ExtentSparkReport.getExtentLogger().log(Status.WARNING, "Value '100' not found in the dropdown options.");
            }

            List<WebElement> tableRows = getElements(documentsPageProperties.getProperty("tableRows"));
            assert tableRows != null;
            if (tableRows.size() > 10) {
                WebElement element = isElementVisible(documentsPageProperties.getProperty("pagination"),false);
                if (element == null) {
                    Assert.assertTrue(true, "Pagination is not present in the DOM, hence not displayed.");
                    logger.log(Level.WARNING, "Pagination is not present in the DOM, hence not displayed.");
                    ExtentSparkReport.getExtentLogger().log(Status.WARNING, "Pagination is not present in the DOM, hence not displayed.");
                }

                selectValueFromDropdown(documentsPageProperties.getProperty("dropdownViewDocument"), "10");
                logger.log(Level.INFO, "Selected '10' from the dropdown");
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Selected '10' from the dropdown");

                boolean pagination = Objects.requireNonNull(getElement(documentsPageProperties.getProperty("pagination"))).isDisplayed();
                assertThat(pagination, "Pagination Verified Successfully", "Pagination Validation Failed.");
                logger.log(Level.INFO, "Pagination Verified Successfully");
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Pagination Verified Successfully");
            }
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }

    public void validateSearchBarAndSorting() throws InterruptedException {
        try {
            sendKeysToElement(documentsPageProperties.getProperty("searchBox"), "Search...");
            logger.log(Level.INFO, "Typed 'Search...' in the search box");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Typed 'Search...' in the search box");

            String noRecordDataTable = getTextOfElement(documentsPageProperties.getProperty("noRecordDataTable"));
            Assert.assertEquals(noRecordDataTable, getValueFromUIJSON(environment, "documentsValidationData.noRecordDataTableMessage"));
            logger.log(Level.INFO, "Validated no record message");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validated no record message");

            Objects.requireNonNull(getElement(documentsPageProperties.getProperty("searchBox"))).clear();
            sendKeysToElement(documentsPageProperties.getProperty("searchBox"), "Dollar");
            logger.log(Level.INFO, "Typed 'Dollar' in the search box");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Typed 'Dollar' in the search box");

            String tableRecord = getTextOfElement(documentsPageProperties.getProperty("categoryValue"));
            Assert.assertEquals(tableRecord, getValueFromUIJSON(environment, "documentsValidationData.categoryText"));
            logger.log(Level.INFO, "Validated category text");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Validated category text");

            refreshPage();
            logger.log(Level.INFO, "Page refreshed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Page refreshed");

            if (actualOptions.contains("100")) {
                selectValueFromDropdown(documentsPageProperties.getProperty("dropdownViewDocument"), "100");
                logger.log(Level.INFO, "Selected '100' from the dropdown");
                ExtentSparkReport.getExtentLogger().log(Status.INFO, "Selected '100' from the dropdown");
            } else {
                logger.log(Level.WARNING, "Value '100' not found in the dropdown options.");
                ExtentSparkReport.getExtentLogger().log(Status.WARNING, "Value '100' not found in the dropdown options.");
            }

            List<WebElement> tableRows = getElements(documentsPageProperties.getProperty("categoryTableRow"));
            List<String> originalList = new ArrayList<>();
            assert tableRows != null;
            for (WebElement element : tableRows) {
                originalList.add(element.getText());
            }

            List<String> reverseSortedList = new ArrayList<>(originalList);
            reverseSortedList.sort(Collections.reverseOrder());
            doubleClick(getElement(documentsPageProperties.getProperty("categoryLabel")));
            logger.log(Level.INFO, "Double clicked on category label");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Double clicked on category label");

            List<WebElement> categoryTableRowAfterClick = getElements(documentsPageProperties.getProperty("categoryTableRow"));
            List<String> afterClickList = new ArrayList<>();
            assert categoryTableRowAfterClick != null;
            for (WebElement element : categoryTableRowAfterClick) {
                afterClickList.add(element.getText());
            }

            Assert.assertEquals(reverseSortedList, afterClickList);
            logger.log(Level.INFO, "Verified reverse sorted list matches after click sorted list");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Verified reverse sorted list matches after click sorted list");
        } catch (Exception e) {
            String errorMsg = "Unexpected Error. Error message: " + e.getMessage();
            logger.log(Level.SEVERE, errorMsg);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, errorMsg);
            throw e;
        }
    }
}









