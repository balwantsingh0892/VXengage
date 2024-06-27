package org.engage.POM_Phase_2;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.*;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.getValueFromUIJSON;

public class StartServiceResidentialPagePOM {
    public static List<String> environment;
    public static Properties startServiceResidentialPageProperties;

    public StartServiceResidentialPagePOM(List<String> environment){
        StartServiceResidentialPagePOM.environment = environment;
        startServiceResidentialPageProperties = BaseUtilities.readPropertyfile(FilePaths.START_SERVICE_RESIDENTIAL_PAGE_PROPERTIES);
    }

    public void clickOnServiceRequestMenu() {
        scrollDown(200);
        performJSClickElement(startServiceResidentialPageProperties.getProperty("serviceRequestBtn"));
        ExtentSparkReport.getExtentLogger().info("Verified the click on Service Request Menu .");
    }
    public void selectSubMenu(String expSubMenu) {
        performJSClickElement(startServiceResidentialPageProperties.getProperty("menu").replace("^^^", expSubMenu));
        ExtentSparkReport.getExtentLogger().info("Verified the click on Service Request Sub menu Start Service.");
    }
    public void verifyPageTitleAndSubTitle(){
        String actHeader = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("headerTitle"))).trim();
        Assert.assertEquals(actHeader, getValueFromUIJSON(environment,"startServiceResidentialPage.header"), "header not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Cards headers in Start Service page.");
        String actCardTitle = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("subTitleHeader"))).trim();
        Assert.assertEquals(actCardTitle, getValueFromUIJSON(environment,"startServiceResidentialPage.subTitleHeader"), "Sub title not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Sub title in Start Service page.");
    }
    public void verifyBannerVerbiageAndStep_1Header(){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("bannerVerbiage"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.bannerVerbiage"), "Verbiage not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Verbiage in Start Service page.");
        String actHeader = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("step_1PageHeader"))).trim();
        Assert.assertEquals(actHeader, getValueFromUIJSON(environment,"startServiceResidentialPage.step_1PageHeader"), "Verbiage not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Verbiage in Start Service page.");
    }

    public void verifyProgressBar(String index){
        String value = getAttribute(startServiceResidentialPageProperties .getProperty("progressBarIcon").replace("index", index),"class");
        assertThatContains(value, "active", "Progress bar verified", "Progress bar not verified");
    }
    public void verifyErrorRequiredField(String field){
        String actValue = getTextOfElement(startServiceResidentialPageProperties.getProperty("errorField").replace("field", field));
        assert actValue != null;
        assertThat(actValue, getValueFromUIJSON(environment, "contactInformationPageData.requiredError"), "error field validated", "error field not validated");
    }

    public void verifyRequiredFieldsStep_1(){
        clear(startServiceResidentialPageProperties.getProperty("houseNumberInputField"));
        clear(startServiceResidentialPageProperties.getProperty("streetNameInputField"));
        clear(startServiceResidentialPageProperties.getProperty("cityInputField"));
        clear(startServiceResidentialPageProperties.getProperty("zipcodeInputField"));
        ExtentSparkReport.getExtentLogger().info("Verified the Mandatory fields are cleared in Step_1 .");
        clickOnElement(startServiceResidentialPageProperties.getProperty("verifyButtonStep_1"),false);
        ExtentSparkReport.getExtentLogger().info("Verified the click on verify button in Step_1.");
        verifyErrorRequiredField("StartService_AccountType");
        verifyErrorRequiredField("StartService_HouseNumber");
        verifyErrorRequiredField("StartService_StreetName");
        verifyErrorRequiredField("StartService_City");
        verifyErrorRequiredField("StartService_State");
        verifyErrorRequiredField("StartService_ZipPostalCode");
        ExtentSparkReport.getExtentLogger().info("Verified the error messages for Mandatory fields in Step_1 page .");
    }
    public void verifyStreetNameVerbiage(){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("streetNameVerbiage"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.streetNameVerbiage"), "Verbiage not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Verbiage in Start Service page.");
    }

    public void verifyDDOptions(String locator, String data){
        List<String> actOptions = getAllDropdownOption(startServiceResidentialPageProperties.getProperty(locator));
        System.out.println(actOptions);
        List<String> expOptions = Arrays.asList(getValueFromUIJSON(environment, data).split(","));
        assertThat(actOptions.equals(expOptions), "all options are displayed", "all options are not displayed");
    }
    public void verifyAccountTypeDropDown(){
        verifyDDOptions("accountTypesDropDown","startServiceResidentialPage.accountTypesDropDown");
        ExtentSparkReport.getExtentLogger().info("Verified the options present in dropdown in Step_1 page .");
    }

    public void verifyCharLimit(String locator , String data, int limit) {
        try {
            sendKeysToElement(startServiceResidentialPageProperties.getProperty(locator), getValueFromUIJSON(environment, data));
            WebElement charLimitValidation = getElement(startServiceResidentialPageProperties.getProperty(locator));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered value from UI.");
            Objects.requireNonNull(charLimitValidation, "field is null");
            String enteredValue = charLimitValidation.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to Required digits");
            if (enteredValue.length() == limit) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Digits matched with required digits.");
                charLimitValidation.clear();
            } else {
                Assert.fail("Validation failed: Entered more than " + limit + " digits. Actual length: " + enteredValue.length());
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation. Error message: " + e.getMessage());
        }
    }

    public void verifyField(String locator, String data1, String data2, int limit1, int limit2){
        verifyCharLimit(locator, data1, limit1);
        verifyCharLimit(locator, data2, limit2);
    }
    public void verifyStreetNameField(){
        verifyField("streetNameInputField", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 50);
        ExtentSparkReport.getExtentLogger().info("Verified the street name field.");
    }
    public void verifyApt_Lot_Unit_SiteField(){
        verifyField("apt_Lot_Unit_inputField", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 50);
        ExtentSparkReport.getExtentLogger().info("Verified the Apt-Lot-Unit-Site field.");
    }
    public void verifyCity(){
        verifyField("cityInputField", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 50);
        ExtentSparkReport.getExtentLogger().info("Verified the City field");
    }
    public void verifyStateDDOptions(){
        verifyDDOptions("stateDDStep_1", "contactInformationPageData.");
        ExtentSparkReport.getExtentLogger().info("Verified the state dropdown list or options.");
    }
    public void verifyZipCode(){
        verifyField( "zipcodeStep_1", "contactInformationPageData.poBoxPhysicalUsa", "contactInformationPageData.postalSpecChar", 10, 0);
        ExtentSparkReport.getExtentLogger().info("Verified the Zipcode field .");
    }
    public void verifyUsingInvalidData(){
        selectOption(startServiceResidentialPageProperties.getProperty("accountTypeSelector"), "text="+getValueFromUIJSON(environment, "validDataStartServiceResidentialStep_1.AccountType"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("houseNumberInputField"),getValueFromUIJSON(environment,"InvalidDataStartServiceResidentialPageStep_1.houseNo"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("streetNameInputField"),getValueFromUIJSON(environment,"InvalidDataStartServiceResidentialPageStep_1.streetName"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("cityInputField"),getValueFromUIJSON(environment,"InvalidDataStartServiceResidentialPageStep_1.city"));
        selectOption(startServiceResidentialPageProperties.getProperty("stateSelector"), "text="+getValueFromUIJSON(environment, "InvalidDataStartServiceResidentialPageStep_1.stateType"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("zipcodeInputField"),getValueFromUIJSON(environment,"InvalidDataStartServiceResidentialPageStep_1.zipCode"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("verifyButtonStep_1"),false);
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("invalidDataErrorMsg"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.invalidDataErrorMsg"), "Error message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Validated the fields using invalid data in Step_1 page.");
    }
    public void verifyUsingValidData(){
        clear(startServiceResidentialPageProperties.getProperty("houseNumberInputField"));
        clear(startServiceResidentialPageProperties.getProperty("streetNameInputField"));
        clear(startServiceResidentialPageProperties.getProperty("cityInputField"));
        clear(startServiceResidentialPageProperties.getProperty("zipcodeInputField"));
        selectOption(startServiceResidentialPageProperties.getProperty("accountTypeSelector"), "text="+getValueFromUIJSON(environment, "validDataStartServiceResidentialStep_1.AccountType"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("houseNumberInputField"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_1.houseNo"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("streetNameInputField"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_1.streetName"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("cityInputField"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_1.city"));
        selectOption(startServiceResidentialPageProperties.getProperty("stateSelector"), "text="+getValueFromUIJSON(environment, "validDataStartServiceResidentialStep_1.stateType"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("zipcodeInputField"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_1.zipCode"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("verifyButtonStep_1"),false);
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("verifyYourAddressHeader"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.verifyYourAddressHeader"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Validated the All the fields using Valid data and successfully moved to new section tab in Step_1 page.");
    }
    public void verifyNewSectionVerbiageStep_1(){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("verifyYourAddressVerbiage"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.verifyYourAddressVerbiage"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the verbiage in New section Tab.");
    }
    public void verifyFieldNewSectionStep_1(){
        clickOnNextBtnStep_1();
        String actMsg = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("newSectionStep_1ReqMsg"))).trim();
        Assert.assertEquals(actMsg, getValueFromUIJSON(environment,"startServiceResidentialPage.reqMessage"), "message not matched with expected");
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("verifyYourAddressVerbiage"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.verifyYourAddressVerbiage"), "message not matched with expected");
        String address = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("addressHeader"))).trim();
        Assert.assertEquals(address, getValueFromUIJSON(environment,"startServiceResidentialPage.addressVerifiaction"), "message not matched with expected");
        performJSClickElement(startServiceResidentialPageProperties.getProperty("serviceAddressRadioBtn"));
        ExtentSparkReport.getExtentLogger().info("Successfully validated all the error messages in New section tab.");
        clickOnNextBtnStep_1();
        String headerStep_2 = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("headerStep_2"))).trim();
        Assert.assertEquals(headerStep_2, getValueFromUIJSON(environment,"startServiceResidentialPage.step_2Header"), "Header message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully validated that New section tab navigates to Step_2 page.");
    }
    public void clickOnNextBtnStep_1(){
        performJSClickElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_1"));
//        clickOnElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_1"),false);
        ExtentSparkReport.getExtentLogger().info("Verified the click Next button in Step_1 page.");
    }
    public void verifyNavigationToStep_2(){
        verifyUsingValidData();
        verifyFieldNewSectionStep_1();
    }
    public void clickPreviousBtn(){
        performJSClickElement(startServiceResidentialPageProperties.getProperty("previousBtnStep_2"));
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("verifyYourAddressVerbiage"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.verifyYourAddressVerbiage"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully clicked on previous button and navigated back to Step_1 page.");
    }
    public void requiredFieldStep_2StartServicePage(){
        clear(startServiceResidentialPageProperties.getProperty("firstNameStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("lastNameStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("emailAddressStep_2"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_2"),false);
        verifyErrorRequiredField("StartService_PrimaryAccttHolderFirstName");
        verifyErrorRequiredField("StartService_PrimaryAccttHolderLastName");
        verifyErrorRequiredField("StartService_PrimaryAccttHolderSsn");
        verifyErrorRequiredField("StartService_PrimaryAccountHolderDriverLicence");
        verifyErrorRequiredField("StartService_PrimaryAccountHolderDriverLicenceState");
        verifyErrorRequiredField("StartService_PrimaryAccountHolderDOB");
        verifyErrorRequiredField("StartService_PrimaryAccttHolderTelephoneNumber");
        verifyErrorRequiredField("StartService_PrimaryAccountHolderEmail");
        verifyErrorRequiredField("primaryAccountHolderConfirmation");
        verifyErrorRequiredField("primaryAccountHolderConsent");
        ExtentSparkReport.getExtentLogger().info("Successfully validated all the error messages in Step_2 page.");
    }

    public void verifyFirstNameFieldStep_2(){
        verifyField("firstNameStep_2", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 40, 40);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the first name field .");
    }
    public void verifyLastNameFieldStep_2(){
        verifyField("lastNameStep_2", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 40, 40);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the last name field .");
    }
    public void verifySocialSecurityNoFieldStep_2(){
        verifyField("socialSecurityNoStep_2", "startServiceResidentialPage.phoneNumberStep_2", "contactInformationPageData.streetFieldSpecChar", 11, 0);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the social security number field .");
    }
    public void verifyEyeToggleStep_2(){
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("socialSecurityNoStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.socialSecurityNumberStep_2"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("socialSecurityNoEyeIconStep_2"), false);
        String value = getAttribute(startServiceResidentialPageProperties.getProperty("socialSecurityNoEyeIconStep_2"), "class");
        Assert.assertTrue(value.contains("show-entry-icon"), "Eye icon not working");
        System.out.println("Eye icon Verified Successfully");
        ExtentSparkReport.getExtentLogger().info("Eye Icon Verified.");
    }
    public void verifySocialSeVerbiageStep_2(){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("socialSecurityNoVerbiageStep_2"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.socialSecurityVerbiageStep_2"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the social security verbiage .");
    }
    public void verifyDriverLicenseStateDDOptions(){
        verifyDDOptions("dirverLicenseStateStep_2", "driverLicenseStateDDStep_2.driverLicenseStateDDStep_2");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the driver license state dropdown.");
    }
    public void verifyPrimaryPhoneNumberFieldStep_2(){
        verifyField("phoneNumberStep_2","driverLicenseStateDDStep_2.phoneNumberStep_2","driverLicenseStateDDStep_2.contactInformationPageData.streetFieldSpecChar",14,5);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the primary phone number field .");
    }
    public void verifySecondaryPhoneNumberFieldStep_2(){
        verifyField("phoneNumberStep_2","driverLicenseStateDDStep_2.phoneNumberStep_2","driverLicenseStateDDStep_2.contactInformationPageData.streetFieldSpecChar",14,5);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the secondary phone number field .");
    }
    public void verifyEmailAddressFieldStep_2(){
        verifyField("emailAddressStep_2","testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 0);
        ExtentSparkReport.getExtentLogger().info("Successfully verified the email address field .");
    }
    public void verifyInvalidEmailAddress(){
        clear(startServiceResidentialPageProperties.getProperty("emailAddressStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("emailAddressStep_2"),getValueFromUIJSON(environment,"startServiceResidentialPage.invalidEmail"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_2"),false);
        String actErrorMsg = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("invalidEmailAddressErrorMsg"))).trim();
        Assert.assertEquals(actErrorMsg, getValueFromUIJSON(environment,"startServiceResidentialPage.invalidEmailErrorMsg"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the invalid email address error message.");
    }
    public void verifyInvalidPrimaryAndSecondaryPhoneNumbersStep_2(){
        clear(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("secondaryPhoneNoStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"),getValueFromUIJSON(environment,"startServiceResidentialPage.invalidPhoneNo"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("secondaryPhoneNoStep_2"),getValueFromUIJSON(environment,"startServiceResidentialPage.invalidPhoneNo"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_2"),false);
        String actErrorMsg = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("invalidPhoneNoHeader"))).trim();
        Assert.assertEquals(actErrorMsg, getValueFromUIJSON(environment,"startServiceResidentialPage.invalidPhoneNumber"), "message not matched with expected");
        String actErrorMsg1 = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("invalidSecondaryPhoneNoStep_2"))).trim();
        Assert.assertEquals(actErrorMsg1, getValueFromUIJSON(environment,"startServiceResidentialPage.invalidPhoneNumber"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the invalid primary and secondary phone number error messages.");

    }
    public void verifyUsingValidDataStep_2() throws InterruptedException {
        clear(startServiceResidentialPageProperties.getProperty("firstNameStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("lastNameStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("socialSecurityNoStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("driverLicenseStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"));
        clear(startServiceResidentialPageProperties.getProperty("emailAddressStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("firstNameStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.firstNameStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("lastNameStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.lastNameStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("socialSecurityNoStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.socialSecurityNumberStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("driverLicenseStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.driverLicenseStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.phoneNumberStep_2"));
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("emailAddressStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.emailAddressStep_2"));
        verifyDateFromDatePicker();
        selectOption(startServiceResidentialPageProperties.getProperty("driverLicenseStateDDStep_2"), "text="+getValueFromUIJSON(environment, "validDataStartServiceResidentialStep_2.stateTypeStep_2"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("t&c_1Step_2"),false);
        clickOnElement(startServiceResidentialPageProperties.getProperty("t&c_2Step_2"),false);
        clear(startServiceResidentialPageProperties.getProperty("secondaryPhoneNoStep_2"));
        clickOnElement(startServiceResidentialPageProperties.getProperty("nextBtnStep_2"),false);
        Thread.sleep(3000);
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty("pageHeaderStep_3"))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage.pageHeaderStep_3"), "message not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Successfully validated all the fields using valid data in Step_2 page and navigated to Step_3 page.");
    }
    public void selectDateFromDatePicker(@NotNull String expDate){
        String[] dateArray = expDate.split("-");
        clickOnElement(startServiceResidentialPageProperties.getProperty("datePickerInput"), false);
        selectOption(startServiceResidentialPageProperties.getProperty("prevYearSelect"), "text=" + dateArray[2]);
        selectOption(startServiceResidentialPageProperties.getProperty("prevMonthSelect"), "text=" + dateArray[1]);
        clickOnElement(startServiceResidentialPageProperties.getProperty("prevDay").replaceAll("dayToSelect", dateArray[0]), false);
    }

    public void verifyDateFromDatePicker(){
        selectDateFromDatePicker(getValueFromUIJSON(environment, "startServiceResidentialPage.dateOfBirthStep_2"));
        ExtentSparkReport.getExtentLogger().info("Successfully verified the date .");
    }

    public void verifyBelow18DateFromDatePicker(@NotNull String expDate){
        String[] dateArray = expDate.split("-");
        clickOnElement(startServiceResidentialPageProperties.getProperty("datePickerInput"), false);
        selectOption(startServiceResidentialPageProperties.getProperty("prevYearSelect"), "text=" + dateArray[2]);
        selectOption(startServiceResidentialPageProperties.getProperty("prevMonthSelect"), "text=" + dateArray[1]);
        String xpath = startServiceResidentialPageProperties.getProperty("dayOff").replaceAll("dayToSelect", dateArray[0]);
        List<WebElement> elements = getElements(xpath);
        assert elements != null;
        String status = "";
        if (elements.size() > 1){
            status = getAttribute(elements.get(1), "class");
        }else {
            status = getAttribute(elements.get(0), "class");
        }
        assertThatContains(status, "disabled", "can't select below 18, fields are disabled", "can select below 18, fields are not disabled");
        ExtentSparkReport.getExtentLogger().info("Successfully verified that age below 18 are disabled.");
        clickOnElement(startServiceResidentialPageProperties.getProperty("lastNameStep_2"), false);
    }

    public void verifyDateWithBelow18(){
        LocalDate dateBelow18Local = getRandomDate(LocalDate.now(), "minus=month=216");
        String dateBelow18 = String.valueOf(getRandomDate(dateBelow18Local, "plus=day=1"));
        System.out.println(dateBelow18);
        String[] daArray =  String.valueOf(dateBelow18).split("-");
        String month = getMonNumToStringFormat(Integer.parseInt(daArray[1])).toLowerCase();
        month = month.replaceFirst(String.valueOf(month.charAt(0)), String.valueOf(month.charAt(0)).toUpperCase());
        dateBelow18 = daArray[2] + "-" + month + "-" + daArray[0];
        verifyBelow18DateFromDatePicker(dateBelow18);
    }

    public void verifyPhoneNumFormat(){
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.phoneNumberStep_2"));
        String actValue = Objects.requireNonNull(getElement(startServiceResidentialPageProperties.getProperty("primaryPhoneNoStep_2"))).getAttribute("value");;
        boolean status = actValue.matches("\\(\\d{3}\\) \\d{3}-\\d{4}");
        assertThat(status, "format matched", "format not matched");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the primary phone number format.");
        sendKeysToElement(startServiceResidentialPageProperties.getProperty("secondaryPhoneNoStep_2"),getValueFromUIJSON(environment,"validDataStartServiceResidentialStep_2.phoneNumberStep_2"));
        String actValue1 = Objects.requireNonNull(getElement(startServiceResidentialPageProperties.getProperty("secondaryPhoneNoStep_2"))).getAttribute("value");;
        boolean status1 = actValue1.matches("\\(\\d{3}\\) \\d{3}-\\d{4}");
        assertThat(status1, "format matched", "format not matched");
        ExtentSparkReport.getExtentLogger().info("Successfully verified the secondary phone number format.");
    }

    public void verifyVerbiageUnderToggleStep_3(){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty(""))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage."), "message not matched with expected");
    }
    public void verifyProceedStep_3ToStep_4ToggleOff(){
        //add a step to verify toggle turned off
        String actVerbiage = Objects.requireNonNull(getTextOfElement(startServiceResidentialPageProperties.getProperty(""))).trim();
        Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment,"startServiceResidentialPage."), "message not matched with expected");
    }

    public void selectCountryDD(String option){
        String actCountry = getSelectedOption(startServiceResidentialPageProperties.getProperty("countryDD"));
        String expCountry = getValueFromUIJSON(environment, option);
        assert actCountry != null;
        if(!actCountry.equals(expCountry)){
            selectOption(startServiceResidentialPageProperties.getProperty("countryDD"), "text="+expCountry);
            actCountry = getSelectedOption(startServiceResidentialPageProperties.getProperty("countryDD"));
        }
        assert actCountry != null;
        assertThat(actCountry, expCountry, "country dd verified", "country dd not verified");
    }



}
