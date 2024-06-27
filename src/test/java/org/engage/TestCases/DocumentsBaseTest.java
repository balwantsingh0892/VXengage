package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Database.DatabaseConnection;
import org.engage.Database.EngageQueries;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

//Scenario_14

import static org.engage.Database.DatabaseOperations.executeInsertUpdateDelete;
import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

public class DocumentsBaseTest extends TestUtils {
    @Test(description = "To Verify the Verbiage, Title, and Required Fields Messages for Upload Documents", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyVerbiageTitleRequiredFieldMessages() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.goToUploadDocuments();
        documentsPagePOM.validateVerbiageTitleRequiredMessages();
        documentsPagePOM.validateCardTitleWithAccountNumber();
    }

    @Test(description = "To Validate the Negative Cases for Upload Documents", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void uploadDocumentNegativeCasesValidation() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.goToUploadDocuments();
        documentsPagePOM.validateDropDownItemsForCategory();
        documentsPagePOM.validateUnsupportedDocumentType();
    }

    @Test(description = "To Validate the Upload Document File Size More than 40 MB", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void validateFileSizeMoreThan40MB() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.goToUploadDocuments();
        documentsPagePOM.validateFileMoreThan40MB();
    }

    @Test(description = "To Validate the Upload Document File with valid file extension and Size - Happy Flow", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void validateValidFileUploadHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        executeInsertUpdateDelete(EngageQueries.UPDATE_CUSTOMER_VIEW_FOR_UPLOADED_FILES_CURRENT_DATE, DatabaseConnection.DatabaseType.MariaDB);
        documentsPagePOM.goToViewDocuments();
        documentsPagePOM.validFileExtensionWithAllowedFileSize();
        documentsPagePOM.validateTableHeadersText();
    }

    @Test(description = "To Validate the View Document Page Elements", priority = 5, alwaysRun = true, groups = "Phase1_Group2")
    public void validatePageElementViewDocument() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        documentsPagePOM.goToViewDocuments();
        documentsPagePOM.validateTitleAndOtherPageElements();
        documentsPagePOM.validatePagination();
        documentsPagePOM.validateSearchBarAndSorting();

    }

}
