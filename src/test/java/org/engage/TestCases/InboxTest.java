package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.POM.InboxPOM;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_10

public class InboxTest extends TestUtils {

    @Test(description = " Verify All elements in Inbox page", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyElementsInInboxPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        inboxPOM.inboxHeadersValidation();
        inboxPOM.verifyColumns();
        inboxPOM.verifySorting();
        inboxPOM.clickOnMessage();
        inboxPOM.verifyMarkAsUnRead();
        inboxPOM.verifyMarkAsReadTooltip();
        inboxPOM.verifyMarkAsRead();
        inboxPOM.verifyArchiveIcon();
        inboxPOM.verifyArchivedMessage();
        inboxPOM.verifyHideArchivedMessage();
        inboxPOM.unArchiveMsg();
    }

}
