package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.POM.HomePagePOM;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_7 and Scenario-20

public class HomePageBaseTest extends TestUtils {
    
    @Test(description = "To validate Header elements in Account Summary page", priority = 1, groups = "Phase1_Group1")
    public void headerElementsVerification() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.verifyCompanyLogoHeader();
        homePagePOM.verifyIconHeaders();

    }

    @Test(description = "To validate Header Icons in Account Summary page", priority = 2, groups = "Phase1_Group1")
    public void headerIconsVerification() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.verifyChangeLanguage();
        homePagePOM.verifySupportIcon();
        homePagePOM.verifyFAQ();
        homePagePOM.verifyMsgIcon();
        homePagePOM.verifyAccount();
        homePagePOM.verifyLinks();
    }

    @Test(description = "To validate Footer Icons in Account Summary page", priority = 3, groups = "Phase1_Group1")
    public void footerIconsVerification() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.FooterLogoAndAboutSectionVerification();
        homePagePOM.verifyAboutSectionLinks();//Bug
        homePagePOM.verifySupportSectionLinks();// BUG
        homePagePOM.verifySocialMediaSectionLink();

    }

    @Test(description = "To validate Left Navigation Menu in Account Summary page", priority = 4, groups = "Phase1_Group1")
    public void accountSummaryLeftNavigationMenuValidation() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        Thread.sleep(2000);
        homePagePOM.selectAccountLeftMenuNav();
        homePagePOM.verifyAccDetails();
        homePagePOM.verifyBillingSubMenu();
        homePagePOM.validateBillinngSubMenuOptions();
        homePagePOM.verifyUsageInsightSubMenu();
        homePagePOM.validateUsageInsightSubMenuOption();
        homePagePOM.verifyPaymentSubMenu();
        homePagePOM.validatePaymentSubMenuOptions();
        homePagePOM.verifyServiceRequestSubMenu();
        homePagePOM.validateServiceRequestSubMenuOptions();
        homePagePOM.verifyDocumentSubMenu();
        homePagePOM.validateDocumentSubMenuOptions();
        homePagePOM.validateOutagesOption();
        homePagePOM.verifyMessageSubMenu();
        homePagePOM.validateMessageSubMenuOptions();
        homePagePOM.validateOnlineFormsOption();
        homePagePOM.verifyProfileSubMenu();
        homePagePOM.validateProfileSubMenuOptions();

    }

    @Test(description = "To validate Account Summary page Cards", priority = 5, groups = "Phase1_Group1")
    public void verifyAccountSummaryPageVerification() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.selectAccountLeftMenuNav();
        homePagePOM.accountSummaryPageCardVerification();

    }

    @Test(description = "To validate Information Center Card in Account Summary page", priority = 6, groups = "Phase1_Group1")
    public void accountSummaryInformationCenterValidation() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.selectAccountLeftMenuNav();
        homePagePOM.verifyInformationCenterCard();//Vxsmart link bug

    }
    @Test(description = "To validate Logout Link in Account Summary page", priority = 7, groups = "Phase1_Group2")
    public void accountSummaryLogOutValidation() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        homePagePOM.verifyLogOutLink();
    }



    @Test(description = "To validate the Billing Card in Account Summary page", priority = 8, groups = "Phase1_Group1")
    public void validateBillingCard() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        documentsPagePOM.selectSpecificAccountForUpload();
        homePagePOM.validateBillingCardData();
    }



}

