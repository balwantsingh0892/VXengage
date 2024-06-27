package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;
import java.text.ParseException;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_09

public class BillingAndUsageHistoryPageTest extends TestUtils {

    @Test(description = "To verify all elements in Billing and history usage page", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyBillingAndUsageHistoryElements() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        billingAndUsageHistoryPagePOM.selectAccountForBillingAndHistoryUsage();
        billingAndUsageHistoryPagePOM.clickOnBillingMenu();
        billingAndUsageHistoryPagePOM.selectSubMenu("Billing & Usage History");
        billingAndUsageHistoryPagePOM.verifyPageHeader();
        billingAndUsageHistoryPagePOM.verifyCardHeader();
        billingAndUsageHistoryPagePOM.verifyTabs();
        billingAndUsageHistoryPagePOM.clickOnMeterDropDown();
        billingAndUsageHistoryPagePOM.verifyMeterDropDownOptions();
    }

    @Test(description = "To verify all elements under Billing meter", priority = 2, alwaysRun = true, groups = "Phase1_Group1")
    public void detailedValidationBillingMeter() throws InterruptedException, ParseException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        billingAndUsageHistoryPagePOM.selectAccountForBillingAndHistoryUsage();
        billingAndUsageHistoryPagePOM.clickOnBillingMenu();
        billingAndUsageHistoryPagePOM.selectSubMenu("Billing & Usage History");
        billingAndUsageHistoryPagePOM.verifyBillingElements();
        billingAndUsageHistoryPagePOM.verifyBillingDate();
        billingAndUsageHistoryPagePOM.verifyBillingSlider();
        billingAndUsageHistoryPagePOM.verifySelectDateBilling();
        billingAndUsageHistoryPagePOM.verifyBillingNextBtn();
        billingAndUsageHistoryPagePOM.verifyBillingPrevBtn();
        billingAndUsageHistoryPagePOM.verifyBillingColumns();
        billingAndUsageHistoryPagePOM.verifyBillingFileDownload();
    }

    @Test(description = "To verify all elements under Water meter", priority = 3, alwaysRun = true, groups = "Phase1_Group1")
    public void detailedValidationWaterMeter() throws InterruptedException, ParseException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        billingAndUsageHistoryPagePOM.selectAccountForBillingAndHistoryUsage();
        billingAndUsageHistoryPagePOM.clickOnBillingMenu();
        billingAndUsageHistoryPagePOM.selectSubMenu("Billing & Usage History");
        billingAndUsageHistoryPagePOM.selectMeter("waterMeter");
        billingAndUsageHistoryPagePOM.verifyWaterBillingElements();
        billingAndUsageHistoryPagePOM.verifyWaterBillingDate();
        billingAndUsageHistoryPagePOM.verifyWaterBillingSlider();
        billingAndUsageHistoryPagePOM.verifyWaterSelectDateBilling();
        billingAndUsageHistoryPagePOM.verifyWaterBillingNextBtn();
        billingAndUsageHistoryPagePOM.verifyWaterBillingPrevBtn();
        billingAndUsageHistoryPagePOM.verifyWaterBillingColumns();
        billingAndUsageHistoryPagePOM.verifyWaterBillingFileDownload();
    }

    @Test(description = "To verify all elements under Electric meter", priority = 4, alwaysRun = true, groups = "Phase1_Group1")
    public void detailedValidationElectricMeter() throws InterruptedException, ParseException {

        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        billingAndUsageHistoryPagePOM.selectAccountForBillingAndHistoryUsage();
        billingAndUsageHistoryPagePOM.clickOnBillingMenu();
        billingAndUsageHistoryPagePOM.selectSubMenu("Billing & Usage History");
        billingAndUsageHistoryPagePOM.selectMeter("electricMeter");
        billingAndUsageHistoryPagePOM.verifyElectricBillingElements();
        billingAndUsageHistoryPagePOM.verifyElectricBillingDate();
        billingAndUsageHistoryPagePOM.verifyElectricBillingSlider();
        billingAndUsageHistoryPagePOM.verifyElectricSelectDateBilling();
        billingAndUsageHistoryPagePOM.verifyElectricBillingNextBtn();
        billingAndUsageHistoryPagePOM.verifyElectricBillingPrevBtn();
        billingAndUsageHistoryPagePOM.verifyElectricBillingColumns();
        billingAndUsageHistoryPagePOM.verifyElectricBillingFileDownload();
    }




}
