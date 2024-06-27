package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.POM.AccountSummaryDropDownPOM;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;
import org.engage.Base.BaseUtilities;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

// Scenario-8

public class AccountSummaryDropDownTest extends TestUtils {
    
    @Test(description = "To Verify page elements in AccountSummaryDropDown ", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyAllLinksInDropDown(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        accountSummaryDropDownPOM.verifyCityNewsLinks();
        accountSummaryDropDownPOM.changeMyPaymentOptions();
        accountSummaryDropDownPOM.viewMyDocuments();
        accountSummaryDropDownPOM.updateMyPreference();
        accountSummaryDropDownPOM.needHelpWithBill();
        accountSummaryDropDownPOM.reportMissedCollection();
        accountSummaryDropDownPOM.binReplacement();
        accountSummaryDropDownPOM.requestLeakAdjustment();
    }

}
