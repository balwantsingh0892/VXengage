package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.BillingDeliveryPreferencePagePOM.clickOnProfileMenu;
import static org.engage.POM.BillingDeliveryPreferencePagePOM.selectSubMenu;
import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//scenario 19

public class AccountSettingPageTest extends TestUtils {

    @Test(description = "To Verify Page Elements in Account Setting Page ", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void pageElementVerification(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.verifyPageTitleAndCardTitle();

    }

    @Test(description = "To Verify Page Elements in Account Setting Page ", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void  verifyDefaultAccountDetailsCard(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.verifyVerbiageDefaultAcc();
        accountSettingPagePOM.verifyLabel();
        accountSettingPagePOM.verifyPrepopulatedAcc();
        accountSettingPagePOM.verifyAccountsInDropdown();
        accountSettingPagePOM.verifySaveBtn();
        accountSettingPagePOM.verifyNewAccReLogin();
        accountSettingPagePOM.revertToOriginalAcc();

    }

    @Test(description = "To Verify Change Account Nick Name ", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void  verifyChangeAccNickNameCard(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.verifyNickNameLabel();
        accountSettingPagePOM.verifyVerbiageNewNickName();
        accountSettingPagePOM.verifyNickNameSaveBtn();
    }

    @Test(description = "To Verify Add an account in residential page ", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void  verifyAddAnAccountResidential() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.clickAddAccountBtn();
        accountSettingPagePOM.validateAccountNumberMandatoryFieldErrorMessage();
        accountSettingPagePOM.validateLastNameMandatoryFieldErrorMessage();
        accountSettingPagePOM.accountFieldValidationGreaterThanSevenDigits();
        accountSettingPagePOM.accountFieldValidationAlphaSpecialCharacters();
        accountSettingPagePOM.selectResidentialCustomerTypeAndValidate();
        accountSettingPagePOM.verifyLastNameMoreThan40Characters();
        accountSettingPagePOM.lastNameFieldValidationSpecialCharacters();
        accountSettingPagePOM.verifyAddAccountNickNameLabel();
        accountSettingPagePOM.verifyAddAccountVerbiageNewNickName();
        accountSettingPagePOM.verifyAddAccountErrorMessageWithInvalidData();
        accountSettingPagePOM.verifyAddAccountWithNickNameWithValidData();
    }

    @Test(description = "To Verify Add an account in commercial page ", priority = 5, alwaysRun = true, groups = "Phase1_Group2")
    public void  verifyAddAnAccountCommercial() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.selectCommercialCustomerTypeAndValidate();
        accountSettingPagePOM.clickAddAccountBtn();
        accountSettingPagePOM.validateAccountNumberMandatoryFieldErrorMessage();
        accountSettingPagePOM.validateCompanyNameMandatoryFieldErrorMessage();
        accountSettingPagePOM.validateTaxIDMandatoryFieldErrorMessage();
        accountSettingPagePOM.accountFieldValidationGreaterThanSevenDigits();
        accountSettingPagePOM.accountFieldValidationAlphaSpecialCharacters();
        accountSettingPagePOM.verifyCompanyNameMoreThan40Characters();
        accountSettingPagePOM.companyNameFieldValidationSpecialCharacters();
        accountSettingPagePOM.verifyTaxIDMoreThan9Characters();
        accountSettingPagePOM.taxIdFieldValidationAlphaSpecialCharacters();
        accountSettingPagePOM.verifyAddAccountVerbiageNewNickName();
        accountSettingPagePOM.verifyAddAccountErrorMessageWithInvalidDataCommercial();
        accountSettingPagePOM.verifyAddAccountWithNickNameCommercial();
    }

    @Test(description = "To Verify Remove Account in account setting page", priority = 6, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyRemoveAccount() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password", testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        accountSettingPagePOM.verifyRemoveAccountVerbiage();
        accountSettingPagePOM.verifyLinks();
        accountSettingPagePOM.verifyAndRemoveAccountIfPresent("validRequiredFieldDataForRegistration.accountNumber");
        accountSettingPagePOM.verifyAndRemoveAccountIfPresent("validRequiredFieldDataForRegistration.accountNumberCommercial");
    }


}
