package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

public class SentItemsPageTest extends TestUtils {
    @Test(description = " Verifiy All elements in Sent items page", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyAllElementsInSentItemsPage() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        sentItemsPagePOM.clickOnMessageMenu();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        sentItemsPagePOM.verifySentItemsTitleCard();
        sentItemsPagePOM.clickComposeButton();
        sentItemsPagePOM.verifyComposeMessageTitleCard();
        sentItemsPagePOM.selectSubMenu("Sent Items");
        sentItemsPagePOM.verifySentItemsTitleCard();
        sentItemsPagePOM.verifySentItemsColumns();
        sentItemsPagePOM.verifySortingOfColumns();
        sentItemsPagePOM.clickOnMessage();
        sentItemsPagePOM.verifyMessageTitleCard();

    }

}
