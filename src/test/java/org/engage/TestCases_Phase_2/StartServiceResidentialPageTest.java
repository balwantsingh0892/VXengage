package org.engage.TestCases_Phase_2;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Phase-2_Scenario-5(Residential)

public class StartServiceResidentialPageTest extends TestUtils {

    @Test(description = " Verify All elements in Step-1 Start service page", priority = 1, alwaysRun = true, groups = "Phase2_Group1")
    public void verifyAllElementsStartServicePageStep_1() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase2_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        startServiceResidentialPagePOM.clickOnServiceRequestMenu();
        startServiceResidentialPagePOM.selectSubMenu("Start Service");
        startServiceResidentialPagePOM.verifyAccountTypeDropDown();
        startServiceResidentialPagePOM.verifyPageTitleAndSubTitle();
        startServiceResidentialPagePOM.verifyBannerVerbiageAndStep_1Header();
        startServiceResidentialPagePOM.verifyProgressBar("1");
        startServiceResidentialPagePOM.verifyRequiredFieldsStep_1();
        startServiceResidentialPagePOM.verifyStreetNameField();
        startServiceResidentialPagePOM.verifyStreetNameVerbiage();
        startServiceResidentialPagePOM.verifyApt_Lot_Unit_SiteField();
        startServiceResidentialPagePOM.verifyCity();
//        startServiceResidentialPagePOM.verifyStateDDOptions();//DB Implementation
        startServiceResidentialPagePOM.verifyZipCode();
        startServiceResidentialPagePOM.verifyUsingInvalidData();
        startServiceResidentialPagePOM.verifyUsingValidData();
   }
    @Test(description = " Verify All elements in New Section in Step-1 Start service page", priority = 2, alwaysRun = true, groups = "Phase2_Group1")
    public void verifyElementsNewSectionStartServicePageStep_1() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase2_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        startServiceResidentialPagePOM.clickOnServiceRequestMenu();
        startServiceResidentialPagePOM.selectSubMenu("Start Service");
        startServiceResidentialPagePOM.verifyUsingValidData();
        startServiceResidentialPagePOM.verifyNewSectionVerbiageStep_1();
        startServiceResidentialPagePOM.verifyFieldNewSectionStep_1();
    }
    @Test(description = " Verify All elements in New Section in Step-2 Start service page", priority = 3, alwaysRun = true, groups = "Phase2_Group1")
    public void verifyAllElementsStartServicePageStep_2() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase2_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        startServiceResidentialPagePOM.clickOnServiceRequestMenu();
        startServiceResidentialPagePOM.selectSubMenu("Start Service");
        startServiceResidentialPagePOM.verifyPageTitleAndSubTitle();
        startServiceResidentialPagePOM.verifyNavigationToStep_2();
        startServiceResidentialPagePOM.verifyProgressBar("2");
        startServiceResidentialPagePOM.clickPreviousBtn();
        startServiceResidentialPagePOM.clickOnNextBtnStep_1();
        startServiceResidentialPagePOM.requiredFieldStep_2StartServicePage();
//        startServiceResidentialPagePOM.verifyFirstNameFieldStep_2();//bug
//        startServiceResidentialPagePOM.verifyLastNameFieldStep_2();//bug
        startServiceResidentialPagePOM.verifySocialSecurityNoFieldStep_2();
        startServiceResidentialPagePOM.verifyEyeToggleStep_2();
//        startServiceResidentialPagePOM.verifyEmailAddressFieldStep_2()//Bug
        startServiceResidentialPagePOM.verifyInvalidEmailAddress();
        startServiceResidentialPagePOM.verifyInvalidPrimaryAndSecondaryPhoneNumbersStep_2();
        startServiceResidentialPagePOM.verifyPhoneNumFormat();
//        startServiceResidentialPagePOM.verifySocialSecurityNoFieldStep_2();//bug
        startServiceResidentialPagePOM.verifySocialSeVerbiageStep_2();
//        startServiceResidentialPagePOM.verifyDriverLicenseStateDDOptions();// DB Implementation //more states present in UI
        startServiceResidentialPagePOM.verifyDateWithBelow18();
        startServiceResidentialPagePOM.verifyDateFromDatePicker();
        startServiceResidentialPagePOM.verifyUsingValidDataStep_2();





    }


}
