package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_13

public class ComposeMessageBaseTest extends TestUtils {

    @Test(description = "To Verify the Verbiage, Title, and Fields Messages for Compose Message", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyVerbiageTitleFieldMessages() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        composeMessagePagePOM.goToComposeMessage();
        composeMessagePagePOM.validateVerbiageTitleAndMessages();
    }

    @Test(description = "To validate the Negative Cases for Compose Message with Required Field Messages", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyNegativeCasesAndRequiredMessages() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        composeMessagePagePOM.goToComposeMessage();
        composeMessagePagePOM.validateNegativeCasesWithRequiredFields(softAssert);
        composeMessagePagePOM.validateNegativeCasesForOtherFields();
        softAssert.assertAll();
    }

    @Test(description = "To validate the Negative Cases for File Upload, Unsupported and More than 40 MB File.", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void validateFileRelatedNegativeCases() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        composeMessagePagePOM.goToComposeMessage();
        composeMessagePagePOM.validateFileNegativeCases();
    }

    @Test(description = "To validate the Happy Flow without Attachment", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void happyFlowWithoutAttachment() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        composeMessagePagePOM.goToComposeMessage();
        composeMessagePagePOM.validateHappyFlowWithoutAttachment();
    }

    @Test(description = "To validate the Happy Flow with Attachment", priority = 5, alwaysRun = true, groups = "Phase1_Group2")
    public void happyFlowWithAttachment() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        composeMessagePagePOM.goToComposeMessage();
        composeMessagePagePOM.validateHappyFlowWithAttachment();
    }

}
