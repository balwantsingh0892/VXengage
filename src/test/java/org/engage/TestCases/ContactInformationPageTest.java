package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_15

public class ContactInformationPageTest extends TestUtils {

    @Test(description = " Verify All elements in Contact Information page", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyAllElementsInContactInformationPage() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyContactInformationPageCardHeaders();
    }

    @Test(description = " Verifiy Mailing Address elements in Contact Information page", priority = 2, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMailingAddressElements() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyMailingAddressSection();//bug
    }

    @Test(description = " // Verify USA Physical Address fields in Contact Information page", priority = 3, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyUSAPhysicalAddress() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyArrowIconExpand("Mailing Address");
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();
        contactInformationPagePOM.verifyPhysicalAddressType();
        contactInformationPagePOM.verifyPhysicalAddressFields();
        contactInformationPagePOM.verifyRequiredFieldsForUsa();//check again DD default selected problem
        contactInformationPagePOM.verifyHouseNumber();
        contactInformationPagePOM.verifyPreCondDDOptions();
        contactInformationPagePOM.verifyFractionalHouseNumber();
        contactInformationPagePOM.verifyStreetName();
        contactInformationPagePOM.verifyUnit();
        contactInformationPagePOM.verifyCity();
        contactInformationPagePOM.verifyZipCode();
        contactInformationPagePOM.verifyPostCondDDOptions();
        contactInformationPagePOM.verifyUnitTypeDDOptions();//DB implementation required
        contactInformationPagePOM.verifyStateDDOptions();
        contactInformationPagePOM.verifyStreetTypeDDUSAPhysical();//DB implementation required
    }

    @Test(description = " Verify USA Physical Address USA Physical Happy flow in Contact Information page", priority = 4, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMailingAddressUSAPhysicalAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();
        contactInformationPagePOM.verifyPhysicalAddressType();
        contactInformationPagePOM.enterDataInPhysicalAddress();
        Thread.sleep(2000);
        contactInformationPagePOM.verifyUpdateMsgMailing();
    }

    @Test(description = " Verify Canada Physical Address in Contact Information page", priority = 5, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyCanadaPhysicalAddress() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDCanada();
        Thread.sleep(2000);
        contactInformationPagePOM.verifyPhysicalAddressType();
        contactInformationPagePOM.verifyPhysicalAddressFieldsForCanada();
        contactInformationPagePOM.verifyRequiredFieldsForCanada();//check again DD default selected problem
        contactInformationPagePOM.verifyHouseNumber();
        contactInformationPagePOM.verifyPreCondDDOptions();
        contactInformationPagePOM.verifyFractionalHouseNumber();
        contactInformationPagePOM.verifyStreetName();
        contactInformationPagePOM.verifyPostDirectionalDDOptions();
        contactInformationPagePOM.verifyUnit();
        contactInformationPagePOM.verifyCity();
        contactInformationPagePOM.verifyPostalCode();
        contactInformationPagePOM.verifyUnitTypeDDOptions();
        contactInformationPagePOM.verifyProvinceDDOptions();
        contactInformationPagePOM.verifyStreetTypeDDCanadaPhysical();// D/B implementation(202 names in UI)
    }

    @Test(description = " Verify Canada Postal Address happy flow in Contact Information page", priority = 6, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMailingAddressCanadaPostalAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDCanada();
        contactInformationPagePOM.verifyPhysicalAddressType();
        contactInformationPagePOM.enterDataInPhysicalAddressCanada();
        contactInformationPagePOM.verifyUpdateMsgMailing();
    }

    @Test(description = " Verify USA Postal Address in Contact Information page", priority = 7, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyUSAPOBoxAddress() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();
        Thread.sleep(2000);
        contactInformationPagePOM.verifyPOBoxAddressType();
        contactInformationPagePOM.verifyPOAddressFieldsForUSA();
        contactInformationPagePOM.verifyPORequiredFieldsForUSA();
        contactInformationPagePOM.verifyPOBox();
        contactInformationPagePOM.verifyCity();
        contactInformationPagePOM.verifyStateDDOptionsPO();
        contactInformationPagePOM.verifyZipCode();
    }

    @Test(description = " Verify USA Postal Address Happy flow in Contact Information page", priority = 8, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMailingAddressUSAPOAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();
        contactInformationPagePOM.verifyPOBoxAddressType();
        contactInformationPagePOM.enterDataInPOUSA();
        contactInformationPagePOM.verifyUpdateMsgMailing();// Bug
    }

    @Test(description = " Verify Canada Postal Address in Contact Information page", priority = 9, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyCanadaPOBoxAddress() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDCanada();
        contactInformationPagePOM.verifyPOBoxAddressType();
        contactInformationPagePOM.verifyPOAddressFieldsForCanada();
        contactInformationPagePOM.verifyPORequiredFieldsForCanada();//check again DD default selected problem
        contactInformationPagePOM.verifyPOBox();
        contactInformationPagePOM.verifyCity();
        contactInformationPagePOM.verifyProvinceDDOptionsPO();//verify field
        contactInformationPagePOM.verifyPostalCode();
    }

    @Test(description = " Verify Canada Postal Address Happy flow in Contact Information page", priority = 10, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMailingAddressCanadaPOAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDCanada();
        contactInformationPagePOM.verifyPOBoxAddressType();
        contactInformationPagePOM.enterDataInPOCanada();
        contactInformationPagePOM.verifyUpdateMsgMailing();//bug
    }

    @Test(description = " Verify Outside USA Address in Contact Information page", priority = 11, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyOutsideFields() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.switchOnAddressOutsideToggle();
        contactInformationPagePOM.verifyOutsideFields();
        contactInformationPagePOM.verifyOutsideRequiredFields();
        contactInformationPagePOM.verifyMailingAddress1();
        contactInformationPagePOM.verifyMailingAddress2();
        contactInformationPagePOM.verifyOutsideCity();
        contactInformationPagePOM.verifyOutsideCountryDDOptions();// country list DB implementation needed
        contactInformationPagePOM.switchOnAddressOutsideToggle();//toggle error
    }
    @Test(description = " Verify Outside USA Address Happy flow in Contact Information page", priority = 12, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyOutsideAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.switchOnAddressOutsideToggle();
        contactInformationPagePOM.enterDataInOutsideFields();
        contactInformationPagePOM.verifyUpdateMsgMailing();
        contactInformationPagePOM.clickOnMailingAddressEditButton();
        contactInformationPagePOM.switchOnAddressOutsideToggle();//toggle issue
    }
    @Test(description = " Verify Email Address in Contact Information page", priority = 13, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyEmailAddressFields() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyArrowIconExpand("Email Address");
        contactInformationPagePOM.verifyArrowIconClose("Email Address");
        contactInformationPagePOM.clickOnEmailEditButton();
        contactInformationPagePOM.verifyEmailVerbiage();
        contactInformationPagePOM.verifyCurrentEmailAddress();
        contactInformationPagePOM.verifyEmailRequiredFields();
        contactInformationPagePOM.verifyTopError();
        contactInformationPagePOM.verifyEmailMismatchError();
        contactInformationPagePOM.verifyInvalidEmailError();
        contactInformationPagePOM.verifyEmailExistingError();
    }
    @Test(description = " Verify Email Address Happy flow in Contact Information page", priority = 14, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyEmailAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.clickOnEmailEditButton();
        contactInformationPagePOM.updateEmail();
        contactInformationPagePOM.clickOnEmailEditButton();
        contactInformationPagePOM.reUpdateEmail();
    }

    @Test(description = " Verify Phone Number in Contact Information page", priority = 15, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyPhoneNumberFields() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyPhoneArrowIconExpand("Phone Numbers");
        contactInformationPagePOM.verifyPhoneNumberArrowIconClose("Phone Numbers");
        contactInformationPagePOM.clickOnPhoneEditButton();
        contactInformationPagePOM.verifyPhoneVerbiage();
        contactInformationPagePOM.verifyPhoneNumberFields();
    }
    @Test(description = " Verify military Mailing Address in Contact Information page", priority = 16, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMilitaryMailingAddress() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyArrowIconExpand("Mailing Address");
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();
        contactInformationPagePOM.verifyMilitaryAddressType();
        contactInformationPagePOM.verifyMilitaryAddressFields();
        contactInformationPagePOM.verifyRequiredFieldsMilitaryAddress();
        contactInformationPagePOM.verifyMilitaryStreetName();//Bug allows more char in field
        contactInformationPagePOM.verifyCityMilitaryDDOptions();
        contactInformationPagePOM.verifyStateMilitaryDDOptions();
        contactInformationPagePOM.verifyZipCodeMilitary();
    }
    @Test(description = " Verify military Mailing Address in Contact Information page", priority = 16, alwaysRun = true, groups = "Phase1_Group1")
    public void verifyMilitaryMailingAddressHappyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        contactInformationPagePOM.clickOnProfileMenu();
        contactInformationPagePOM.verifyContactInformationPageHeader();
        contactInformationPagePOM.verifyArrowIconExpand("Mailing Address");
        contactInformationPagePOM.verifyAddressOutsideToggle();
        contactInformationPagePOM.selectCountryDDUSA();//DD
        contactInformationPagePOM.verifyMilitaryAddressType();
        contactInformationPagePOM.validDataMilitary();
        contactInformationPagePOM.verifyUpdateMsgMilitaryMailing();//Need valid data

    }

}