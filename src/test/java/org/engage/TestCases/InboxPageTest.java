package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_12

public class InboxPageTest extends TestUtils {

    @Test(description = " Verify All elements in Inbox page", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyElementsInInboxPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Inbox");
        inboxPagePOM.verifyInboxTitleCard();
        inboxPagePOM.clickOnMessage("inboxPageData.subjectTextInbox");
        inboxPagePOM.verifyMessageTitleCard();
        inboxPagePOM.verifyReply();
        inboxPagePOM.verifyMessageArea();
        inboxPagePOM.verifyBackToInboxBtn();
        inboxPagePOM.clickOnMessage("inboxPageData.subjectTextInbox");
        inboxPagePOM.VerifyArchiveBtn();

    }

    @Test(description = " Verify All elements and fields in Inbox page", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyDetailsValidationInInboxPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Inbox");
        inboxPagePOM.clickOnMessage("inboxPageData.categoryNameInbox");
        inboxPagePOM.VerifyArchiveBtn();
        inboxPagePOM.verifyInboxTitleCard();
        inboxPagePOM.unArchiveAMessage();
        inboxPagePOM.verifyMessageDetailsField("inboxPageData.subjectTextInbox", "formBodyElements", false);
    }

    @Test(description = " Verify All Show Replies in Inbox page", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyShowRepliesInInboxPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Inbox");
        inboxPagePOM.verifyMessageDetailsField("inboxPageData.subjectTextInbox", "formBodyElements", true);
    }

    @Test(description = " Verify All elements and field limits in Inbox page", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyAllElementsInInboxReplyPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Inbox");
        inboxPagePOM.clickOnMessage("inboxPageData.subjectTextInbox");
        inboxPagePOM.verifyReply();
        inboxPagePOM.verifyReplyTo();
        inboxPagePOM.clickDiscardBtn();
        inboxPagePOM.verifyReply();
        String msg = inboxPagePOM.messageAreaText(softAssert);
        inboxPagePOM.verifyAttachmentVerbiage(softAssert);
        inboxPagePOM.verifyUnsupportedDoc(softAssert);
        inboxPagePOM.sendReply();
        inboxPagePOM.alertClose();
        inboxPagePOM.verifyInboxTitleCard();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        inboxPagePOM.clickOnMessage("inboxPageData.subjectTextInbox");
        inboxPagePOM.verifyReply(msg);
        softAssert.assertAll();
    }

    @Test(description = " Verify All elements in sent page", priority = 5, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyElementsInSentItemsPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        sentItemsPagePOM.clickOnMessage();
        inboxPagePOM.verifyMessageTitleCard();
        inboxPagePOM.verifyReply();
        inboxPagePOM.verifyBackToInboxBtn();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        sentItemsPagePOM.clickOnMessage();
        inboxPagePOM.clickPreviousBtn();
    }

    @Test(description = " Verify elements and fields in sent page", priority = 6, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyElementsInSentItemsReplyPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        inboxPagePOM.verifyMessageDetailsField("inboxPageData.sentItemSubjectText", "sentBodyElements", false);
    }
    @Test(description = " Verify All Show Replies in Sent Items page", priority = 7, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyShowRepliesInSentItemsPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        inboxPagePOM.verifyMessageDetailsField("inboxPageData.sentItemSubjectText", "sentBodyElements", true);
    }

    @Test(description = " Verify All fields and field limit in sent page", priority = 8, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyAllElementsInSentItemsReplyPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        inboxPagePOM.clickOnMessage("inboxPageData.sentItemSubjectText");
        inboxPagePOM.verifyReply();
        inboxPagePOM.clickDiscardBtn();
        inboxPagePOM.verifyReply();
        inboxPagePOM.verifyReplyTo();
        String msg = inboxPagePOM.messageAreaText(softAssert);
        inboxPagePOM.verifyAttachmentVerbiage(softAssert);
        inboxPagePOM.verifyUnsupportedDoc(softAssert);
        inboxPagePOM.sendReply();
        inboxPagePOM.alertClose();
        inboxPagePOM.verifySentItemsTitleCard();
        inboxPagePOM.clickOnMessage("inboxPageData.sentItemSubjectText");
        inboxPagePOM.verifyReply(msg);
        softAssert.assertAll();
    }
}
