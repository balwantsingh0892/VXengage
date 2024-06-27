package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

//Scenario_01 and Scenario_02

public class RegistrationBaseTest extends TestUtils {
    @Test(description = "Validate the Mandatory Fields Error Messages Profile Registration Page", priority = 1, alwaysRun = true, groups = "Phase1_Group1")
    public void validateMandatoryFieldsErrorMessagesResidential() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.clickOnSubmitButton();
        registrationPOM.validateErrorRegistrationMessage();
        registrationPOM.validateAccountNumberMandatoryFieldErrorMessage();
        registrationPOM.validateAccountNumberVerbiage();
        registrationPOM.validateUsernameRequiredFieldMessage();
        registrationPOM.validateUsernameVerbiage();
        registrationPOM.validateLastNameMandatoryFieldErrorMessage();
        registrationPOM.validateEmailAddressMandatoryFieldErrorMessage();
        registrationPOM.validateConfirmEmailAddressMandatoryFieldErrorMessage();
        registrationPOM.validatePasswordMandatoryFieldErrorMessage();
        registrationPOM.validatePasswordVerbiage();
        registrationPOM.validateConfirmPasswordMandatoryFieldErrorMessage();
        registrationPOM.validateTermAndConditionsMandatoryFieldErrorMessage();
        registrationPOM.validateTermAndConditionsLink();
        registrationPOM.validatePrivacyPolicyLink();
        registrationPOM.clickGoToAccount();
        registrationPOM.getURL();

    }

    @Test(description = "Validates the input for the account number field to ensure it meets the specified criteria", priority = 2,alwaysRun = true,groups = "Phase1_Group1")
    public void validateAccountNumberInput()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.accountFieldValidationGreaterThanSevenDigits();
        registrationPOM.accountFieldValidationSpecialCharacters();
    }

    @Test(description = "Validates the input for the Username field to ensure it meets the specified criteria", priority = 3,alwaysRun = true,groups = "Phase1_Group1")
    public void validateUsernameInput()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateUsernameNoSpecialCharsOutsideAllowedRange();
        registrationPOM.validateUsernameFieldErrorMessage();
        registrationPOM.testEnterMoreThan30CharactersInUsername();
        registrationPOM.validateUsernameFieldErrorMessage();
        registrationPOM.testEnterLessThan5CharactersInUsername();
        registrationPOM.validateUsernameFieldErrorMessage();

    }

    @Test(description = "Validates 'Residential' radio button is selected", priority = 4,alwaysRun = true, groups = "Phase1_Group1")
    public void validateResidentialRadioSelected() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateResidentialRadioSelected();
    }

    @Test(description = "Validate the Maximum Allowed Characters for First Name Field.", priority = 5,alwaysRun = true,groups = "Phase1_Group1" )
    public void verifyFirstNameCharacterLimit()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.verifyFirstNameMoreThan40Characters();
    }

    @Test(description = "Verify First Name field allows special characters", priority = 6,alwaysRun = true, groups = "Phase1_Group1")
    public void verifyFirstNameAllowsSpecialCharacters() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.verifyFirstNameAllowsSpecialCharacters();
    }

    @Test(description = "Validate the Maximum Allowed Characters for Last Name Field.", priority = 7,alwaysRun = true, groups = "Phase1_Group1")
    public void verifyLastNameCharacterLimit()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.verifyLastNameMoreThan40Characters();
    }

    @Test(description = "Verify Last Name field allows special characters", priority = 8,alwaysRun = true, groups = "Phase1_Group1")
    public void verifyLastNameAllowsSpecialCharacters() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.verifyLastNameAllowsSpecialCharacters();
    }
    @Test(description = "Validate Email & Confirm Email Address Fields Negative Cases", priority = 9,alwaysRun = true, groups = "Phase1_Group1")
    public void validateEmailAndConfirmEmailFields() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateEmailFieldForInvalidFormat();
        registrationPOM.validateEmailAndConfirmEmailMismatch();
    }

    @Test(description = "Validate Password & Confirm Password Fields Negative Cases", priority = 10,alwaysRun = true, groups = "Phase1_Group1")
    public void validatePasswordAndConfirmPasswordFields() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateInvalidPasswordCriteria();
        registrationPOM.validateInvalidPasswordAndConfirmPasswordCombination();
    }
    @Test(description = "Validate SSN/Driver's License/ House Number Negative Cases With Existing Username", priority = 11,alwaysRun = true, groups = "Phase1_Group1")
    public void validatePersonalInfoFieldsTopLevelErrorMessage()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validatePersonalInfoTopErrorMessagesOnEmptyFields();
        registrationPOM.validatePersonalInfoTopErrorMessagesWithInvalidData();
        registrationPOM.validateExistingUsername();
    }

    @Test(description = "Validates the input for the SSN field to ensure it meets the specified criteria", priority = 12,alwaysRun = true, groups = "Phase1_Group1")
    public void validateSSNFieldInput()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateMaxDigitLimitSSN();
        registrationPOM.fieldSSNValidationSpecialCharacters();
    }

    @Test(description = "Validates the input for the Driver's License field to ensure it meets the specified criteria", priority = 13,alwaysRun = true, groups = "Phase1_Group1")
    public void validateDriversLicenseFieldInput()  {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateMaxDigitLimitDriversLicense();
        registrationPOM.driverLicenseFieldValidationSpecialCharacters();
    }

    @Test(description = "Validates the input for the Service Address House Number field to ensure it meets the specified criteria", priority = 14,alwaysRun = true, groups = "Phase1_Group1")
    public void validateServiceAddressFieldInput() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Balwant Singh").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.validateMaxDigitLimitServiceAddress();
        registrationPOM.serviceAddressFieldValidationSpecialCharacters();
    }


    @Test(description = "Validates 'commercial' radio button is selected", priority = 15,alwaysRun = true, groups = "Phase1_Group1")
    public void validateCommercialRadioSelected() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
    }

    @Test(description = "Validates company Name characters", priority = 16,alwaysRun = true, groups = "Phase1_Group1")
    public void validateCompanyNameMaxChar() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.verifyCompanyNameMoreThan40Characters();
    }

    @Test(description = "Validates  company Name special characters", priority = 17,alwaysRun = true, groups = "Phase1_Group1")
    public void validateCompanyNameSpecialChar() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.verifyCompanyNameAllowsSpecialCharacters();
    }

    @Test(description = "Validates Federal Tax id Field limit", priority = 18,alwaysRun = true, groups = "Phase1_Group1")
    public void validateFederalTaxIdValueMoreThan9Characters() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.verifyTaxIDMoreThan9Characters();
    }

//    @Test(description = "Validate MaxDigit Limit Service Address", priority = 19,alwaysRun = true, groups = "Smoke")
//    public void validateServiceAddressField() {
//        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Smoke").assignDevice("Google Chrome");
//        loginPagePOM.clickOnCreateProfileButton();
//        registrationPOM.selectCommercialCustomerTypeAndValidate();
//        registrationPOM.validateMaxDigitLimitServiceAddress();
//    }

    @Test (description = "Validate Commercial Mandatory Fields Error Messages Profile Registration Page", priority = 20, alwaysRun = true, groups = "Phase1_Group1")
    public void validateMandatoryFieldsErrorCommercial() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.clickOnSubmitButton();
        registrationPOM.validateAccountNumberMandatoryFieldErrorMessage();
        registrationPOM.validateAccountNumberVerbiage();
        registrationPOM.validateInvalidUsernameRequiredFieldMessage();
        registrationPOM.validateUsernameVerbiage();
        registrationPOM.validateCompanyNameMandatoryFieldErrorMessage();
        registrationPOM.validateEmailAddressMandatoryFieldErrorMessage();
        registrationPOM.validateConfirmEmailAddressMandatoryFieldErrorMessage();
        registrationPOM.validatePasswordMandatoryFieldErrorMessage();
        registrationPOM.validatePasswordVerbiage();
        registrationPOM.validateConfirmPasswordMandatoryFieldErrorMessage();
        registrationPOM.validateFederalTaxIdMandatoryFieldErrorMessage();
        registrationPOM.validateTermAndConditionsMandatoryFieldErrorMessage();
        registrationPOM.validateTermAndConditionsLink();
        registrationPOM.validatePrivacyPolicyLink();
        registrationPOM.clickGoToAccount();
        registrationPOM.getURL();
    }
    @Test (description = "Validate the existing user field Message for Commercial", priority = 21, alwaysRun = true, groups = "Phase1_Group1")
    public void validationExistingCustomerCommercial(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.validateValidDataCommercialWithExistingUsername();
    }

    @Test (description = "Validate the Happy Flow for residential", priority = 22, alwaysRun = true, groups = "Phase1_Group1")
    public void validationHappyFlowResidential() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.enterValidDataRegistrationResidential();
    }

    @Test (description = "Validate the Happy Flow for commercial", priority = 23, alwaysRun = true, groups = "Phase1_Group1")
    public void validationHappyFlowCommercial() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group1");
        loginPagePOM.clickOnCreateProfileButton();
        registrationPOM.selectCommercialCustomerTypeAndValidate();
        registrationPOM.enterValidDataRegistrationCommercial();
    }

}
