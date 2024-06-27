package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.performJSClickElement;

public class ContactInformationPagePOM {

    public static Properties contactInformationPageProperties;
    public static List<String> environment;

    public ContactInformationPagePOM(List<String> environment) {
        ContactInformationPagePOM.environment = environment;
        contactInformationPageProperties = BaseUtilities.readPropertyfile(FilePaths.CONTACT_INFORMATION_PAGE_PROPERTIES);
    }

    public void clickOnProfileMenu()
    {
        scrollDown(2000);
        performJSClickElement(contactInformationPageProperties.getProperty("profileMenu"));
    }

    public void verifyContactInformationPageHeader(){
        clickOnElement(contactInformationPageProperties.getProperty("contactInformationLink"),false);
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of Contact Information  Page");
        String pageHeader = getTextOfElement(contactInformationPageProperties.getProperty("headerTitle"));
        Assert.assertEquals(pageHeader,getValueFromUIJSON(environment,"contactInformationPageData.pageHeader"));
    }

    public void verifyContactInformationPageCardHeaders(){
        boolean status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mailingAddressHeader")));
        System.out.println(status +" The mailing address card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mailingEditBtn")));
        System.out.println(status +" The mailing address card Edit Button is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("emailAddressHeader")));
        System.out.println(status +" The Emailing address card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("emailEditBtn")));
        System.out.println(status +" The mailing address card Edit Button is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("phoneNumberHeader")));
        System.out.println(status +" The Phone Number card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("phoneNoEditBtn")));
        System.out.println(status +" The Phone Number Edit button card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mobileNumberForNotificationHeader")));
        System.out.println(status +" The Mobile Number for notification card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mobileNoEditBtn")));
        System.out.println(status +" The Mobile Number card Edit Button is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("dailPhoneNumberHeader")));
        System.out.println(status +" The Dial Phone Number card is displayed");
        assertThat(status, "element displayed","element not displayed");
        status =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("dailPhoneEditBtn")));
        System.out.println(status +" The Dial Phone card Edit Button is displayed");
        assertThat(status, "element displayed","element not displayed");
    }

    public void verifyArrowIconExpand(String header){
        clickOnElement(contactInformationPageProperties.getProperty("arrowIcon").replace("arrow", header),false);
        String value = getAttribute(contactInformationPageProperties.getProperty("arrowHeader").replace("header", header),"aria-expanded");
        assertThat(value, "true", "arrow icon expanded", "arrow icon not expanded");
        String actName = getTextOfElement(contactInformationPageProperties.getProperty("btn").replace("headerName", header));
        assert actName != null;
        assertThat(actName, "Save", "btn changed to save", "btn not changed to save");
    }

    public void verifyPhoneArrowIconExpand(String header){
        clickOnElement(contactInformationPageProperties.getProperty("arrowIcon").replace("arrow", header),false);
        String value = getAttribute(contactInformationPageProperties.getProperty("arrowHeader").replace("header", header),"aria-expanded");
        assertThat(value, "true", "arrow icon expanded", "arrow icon not expanded");
        String actName = getTextOfElement(contactInformationPageProperties.getProperty("phoneNumSaveBtn"));
        assert actName != null;
        assertThat(actName, "Save", "btn changed to save", "btn not changed to save");
    }

    public void verifyArrowIconClose(String header){
        clickOnElement(contactInformationPageProperties.getProperty("arrowIcon").replace("arrow", header),false);
        String value = getAttribute(contactInformationPageProperties.getProperty("arrowHeader").replace("header", header),"aria-expanded");
        assertThat(value, "false", "arrow icon expanded", "arrow icon not expanded");
        String actName = getTextOfElement(contactInformationPageProperties.getProperty("editBtn").replace("headerName", header));
        assert actName != null;
        assertThat(actName, "Edit", "btn changed to edit", "btn not changed to edit");
    }
    public void verifyPhoneNumberArrowIconClose(String header){
        clickOnElement(contactInformationPageProperties.getProperty("arrowIcon").replace("arrow", header),false);
        String value = getAttribute(contactInformationPageProperties.getProperty("arrowHeader").replace("header", header),"aria-expanded");
        assertThat(value, "false", "arrow icon expanded", "arrow icon not expanded");
        String actName = Objects.requireNonNull(getTextOfElement(contactInformationPageProperties.getProperty("phoneNumEditBtn"))).trim();
        assertThat(actName, "Edit", "btn changed to edit", "btn not changed to edit");
    }

    public void verifyVerbiage(String headerName, String expData){
        String actVerbiage = Objects.requireNonNull(getTextOfElement(contactInformationPageProperties.getProperty("verbiage").replace("headerName", headerName))).trim();
        if (actVerbiage.contains("phone numbers")) {
            String accountID = getTextOfElement(contactInformationPageProperties.getProperty("accountLocator").trim());
            String expectedMessage = getValueFromUIJSON(environment, expData) + accountID + ".";
            Assert.assertEquals(actVerbiage, expectedMessage);
        }
        else if (actVerbiage.contains("mailing address"))
        {
            String accountID = getTextOfElement(contactInformationPageProperties.getProperty("accountLocator").trim());
            String expectedMessage = getValueFromUIJSON(environment, expData) + accountID + ".";
            Assert.assertEquals(actVerbiage, expectedMessage);
        }
        else
        {
            Assert.assertEquals(actVerbiage, getValueFromUIJSON(environment, expData));
        }
    }

    public void verifyEditBtn(String header){
        clickOnElement(contactInformationPageProperties.getProperty("editBtn").replace("headerName", header),false);
        String value = getAttribute(contactInformationPageProperties.getProperty("arrowHeader").replace("header", header),"aria-expanded");
        assertThat(value, "true", "arrow icon expanded", "arrow icon not expanded");
        String actName = getTextOfElement(contactInformationPageProperties.getProperty("saveBtn").replace("headerName", header));
        assert actName != null;
        assertThat(actName, "Save", "btn changed to save", "btn not changed to save");
    }

    public void verifyMailingAddressDisplayed(){
        boolean status = isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mailingAddress")));
        assertThat(status, "mailing address verified", "mailing address not verified");
    }

    public void verifyMailingAddressSection() throws InterruptedException {
        verifyArrowIconExpand("Mailing Address");
        verifyArrowIconClose("Mailing Address");
        verifyVerbiage("Mailing Address", "contactInformationPageData.mailingAddressVerbiage");
        verifyEditBtn("Mailing Address");
        verifyAddressOutsideToggle();
        verifyMailingAddressDisplayed();
        verifySaveWithSameAddress(); //Bug
    }

    public void verifySaveWithSameAddress(){
        performJSClickElement(contactInformationPageProperties.getProperty("mailingAddressSaveBtn"));
//        Assert.fail();
        assertThat(contactInformationPageProperties.getProperty("headerTitle"),getValueFromUIJSON(environment,"contactInformationPageData.sameAddressMessage"),"Same address error message verified","Same address error message not verified");
    }

    public void verifyMailingAddressArrow(){
        clickOnElement(contactInformationPageProperties.getProperty("arrowIcon").replace("arrow", "Mailing Address"),false);
        String mailingArrow = getAttribute(contactInformationPageProperties.getProperty("mailingHeaderArrow"),"aria-expanded");
        boolean element = isElementSelected(mailingArrow);
        boolean element1 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mailingAddressSaveBtn")));
        System.out.println(element1 +" The mailing address Save Button is displayed");

        clickOnElement(contactInformationPageProperties.getProperty("mailingAddressHeader"),false);
        String mailingArrow1 = getAttribute(contactInformationPageProperties.getProperty("mailingHeaderArrow"),"aria-expanded");
        boolean element2 = isElementSelected(mailingArrow);
        boolean element3 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("mailingEditBtn")));
        System.out.println(element2 +" The mailing address Save Button is displayed");

        ExtentSparkReport.getExtentLogger().info("Verifying Mailing Address Verbiage In Contact Information  Page");
        String cardHeader1 = getTextOfElement(contactInformationPageProperties.getProperty("mailingAddressVerbiage"));
        assert cardHeader1 != null;
        assertThat(cardHeader1, getValueFromUIJSON(environment, "contactInformationPageData.mailingAddressVerbiage"), "Verbiage in Mailing Address validated successfully", "Verbiage in Mailing Address not validated successfully");

        clickOnElement(contactInformationPageProperties.getProperty("mailingAddressSaveBtn"),false);
        String saveBtnMessage = getTextOfElement(contactInformationPageProperties.getProperty("mailingAddressVerbiage"));
        assert saveBtnMessage != null;
        assertThat(saveBtnMessage, getValueFromUIJSON(environment, "contactInformationPageData.mailingAddressVerbiage"), "Verbiage in Mailing Address validated successfully", "Verbiage in Mailing Address not validated successfully");
    }

    public void clickOnMailingAddressEditButton() {
        scrollDown(-1000);
        clickOnElement(contactInformationPageProperties.getProperty("mailingEditBtn"),false);
    }

    public void clickOnEmailEditButton(){
        clickOnElement(contactInformationPageProperties.getProperty("emailEditBtn"),false);
    }

    public void clickOnPhoneEditButton(){
        clickOnElement(contactInformationPageProperties.getProperty("phoneNoEditBtn"),false);
    }

    public void verifyUSPhysicalAddressFields(){
        boolean element =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("houseNumberTxt")));
        System.out.println(element +" The House Number field is displayed");
        boolean element1 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("preDirectionCodeTxt")));
        System.out.println(element1 +" The Pre direction code field is displayed");
        boolean element2=  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("fractionHouseNoTxt")));
        System.out.println(element2 +" The Fraction House Number field is displayed");
        boolean element3 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("streetNoTxt")));
        System.out.println(element3 +" The Street number field is displayed");
        boolean element4 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("streetTypeTxt")));
        System.out.println(element4 +" The Street Type field is displayed");
        boolean element5 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("postDirectionalCodeTxt")));
        System.out.println(element5 +" The Post Directional Code field is displayed");
        boolean element6 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("unitTypeTxt")));
        System.out.println(element6 +" The Unit Type Field is displayed");
        boolean element7 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("unitTxt")));
        System.out.println(element7 +" The Unit field is displayed");
        boolean element8 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("cityTxt")));
        System.out.println(element8 +" The City Field is displayed");
        boolean element9 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("stateTxt")));
        System.out.println(element9 +" The State Field is displayed");
        boolean element10 =  isElementDisplayed(getElement(contactInformationPageProperties.getProperty("postalCodeTxt")));
        System.out.println(element10 +" The Postal Code field is displayed");

    }

    public void verifyCharLimit(String locator , String data, int limit) {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering More than 5 digits in Account Number Field.");
            sendKeysToElement(contactInformationPageProperties.getProperty(locator), getValueFromUIJSON(environment, data));
            WebElement charLimitValidation = getElement(contactInformationPageProperties.getProperty(locator));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered value for House Number field from UI.");
            Objects.requireNonNull(charLimitValidation, "House number field is null");
            String enteredValue = charLimitValidation.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 5 digits");
            if (enteredValue.length() == limit) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Digits are 5.");
                charLimitValidation.clear();
            } else {
                Assert.fail("Validation failed: Entered more than " + limit + " digits. Actual length: " + enteredValue.length());
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation. Error message: " + e.getMessage());
        }
    }

    public void clickOnPOBoxButton(){
        clickOnElement(contactInformationPageProperties.getProperty("poBoxRadioBtn"),false);
    }

    public void validDataPOBoxMailing(){
        clickOnElement(contactInformationPageProperties.getProperty("houseNoTxtField"),false).clear();
        clickOnElement(contactInformationPageProperties.getProperty("streetNameTxtField"),false).clear();
        clickOnElement(contactInformationPageProperties.getProperty("cityTxtField"),false).clear();
        clickOnElement(contactInformationPageProperties.getProperty("postalCodeTxtField"),false).clear();
        clickOnElement(contactInformationPageProperties.getProperty("mailingSaveBtn"),false);

    }
    public void selectAccount(String acc) throws InterruptedException{
        clickOnElement(contactInformationPageProperties.getProperty("accDropDown"), false);
        Thread.sleep(2000);
        clickOnElement(contactInformationPageProperties.getProperty("accOptions").replaceAll("accNo", getValueFromUIJSON(environment, acc)), false);
    }

    public void selectStatePOBox() throws InterruptedException {
        selectAccount("billingAndHistoryUsagePageData.account");
    }

    public void verifyAddressOutsideToggle() throws InterruptedException {
        boolean status = Objects.requireNonNull(getElement(contactInformationPageProperties.getProperty("toggleIcon"))).isSelected();
        if(status){
            clickOnElement(contactInformationPageProperties.getProperty("toggleIcon"), false);
            Thread.sleep(2000);
            status = Objects.requireNonNull(getElement(contactInformationPageProperties.getProperty("toggleIcon"))).isSelected();
        }
        Assert.assertFalse(status);
        System.out.println(status);
    }

    public void selectCountryDD(String option){
        String actCountry = getSelectedOption(contactInformationPageProperties.getProperty("countryDD"));
//        String actCountry = getTextOfElement(contactInformationPageProperties.getProperty("countryDD"));
        String expCountry = getValueFromUIJSON(environment, option);
        assert actCountry != null;
        if(!actCountry.equals(expCountry)){
            selectOption(contactInformationPageProperties.getProperty("countryDD"), "text="+expCountry);
            actCountry = getSelectedOption(contactInformationPageProperties.getProperty("countryDD"));
        }
        assert actCountry != null;
        Assert.assertEquals(actCountry, expCountry);
    }

    public void selectCountryDDUSA() throws InterruptedException {
        Thread.sleep(2000);
        selectCountryDD("contactInformationPageData.countryOption1");
    }

    public void selectCountryDDCanada() throws InterruptedException {
        scrollDown(200);
        Thread.sleep(2000);
        selectCountryDD("contactInformationPageData.countryOption2");
    }

    public void selectAddressType(String type){
        clickOnElement(contactInformationPageProperties.getProperty("addressType").replaceAll("addressType", getValueFromUIJSON(environment, type)), false);
    }

    public void verifyPhysicalAddressType(){
        selectAddressType("contactInformationPageData.addressType1");
    }

    public void verifyPOBoxAddressType(){
        selectAddressType("contactInformationPageData.addressType2");
    }
    public void verifyMilitaryAddressType(){
        selectAddressType("contactInformationPageData.addressType3");
    }


    public void verifyDisplayed(String field){
        boolean status = isElementDisplayed(getElement(contactInformationPageProperties.getProperty("fields1").replace("fieldName", field)));
        Assert.assertTrue(status);
    }

    public void verifyDisplayed2(String locator){
        boolean status = isElementDisplayed(getElement(contactInformationPageProperties.getProperty(locator)));
        Assert.assertTrue(status);
    }

    public void verifyPhysicalAddressFields(){
        verifyDisplayed("House Number");
        verifyDisplayed("Pre Directional Code");
        verifyDisplayed("Fractional House Number");
        verifyDisplayed("Street Name");
        verifyDisplayed("Street Type");
        verifyDisplayed("Post Directional Code");
        verifyDisplayed("Unit Type");
        verifyDisplayed("Unit");
        verifyDisplayed2("cityUsaField");
        verifyDisplayed2("stateUsaField");
        verifyDisplayed("Zip Code");
    }

    public void verifyPhysicalAddressFieldsForCanada(){
        verifyDisplayed("House Number");
        verifyDisplayed("Pre Directional Code");
        verifyDisplayed("Fractional House Number");
        verifyDisplayed("Street Name");
        verifyDisplayed("Street Type");
        verifyDisplayed("Post Directional Code");
        verifyDisplayed("Unit Type");
        verifyDisplayed("Unit");
        verifyDisplayed2("cityTxt");
        verifyDisplayed2("provinceTxt");
        verifyDisplayed("Postal Code");
    }

    public void verifyPOAddressFieldsForUSA(){
        verifyDisplayed("PO Box");
        verifyDisplayed2("cityPoBoxUsa");
        verifyDisplayed("Zip Code");
        verifyDisplayed2("statePoBoxUsa");
    }

    public void verifyPOAddressFieldsForCanada(){
        verifyDisplayed("PO Box");
        verifyDisplayed2("cityUsaField");
        verifyDisplayed("Postal Code");
        verifyDisplayed("Province");
    }

    public void verifyErrorRequiredField(String field){
        String actValue = getTextOfElement(contactInformationPageProperties.getProperty("errorField").replace("field", field));
        Assert.assertEquals(actValue, getValueFromUIJSON(environment, "contactInformationPageData.requiredError"));
    }

    public void verifyField(String locator, String data1, String data2, int limit1, int limit2){
        verifyCharLimit(locator, data1, limit1);
        verifyCharLimit(locator, data2, limit2);
    }

    public void verifyHouseNumber(){
        verifyField("houseNumberInput", "contactInformationPageData.houseNo", "contactInformationPageData.alphaSpecialChars", 5, 5);
    }

    public void verifyDDOptions(String locator, String data){
        List<String> actOptions = getAllDropdownOption(contactInformationPageProperties.getProperty(locator));
        System.out.println(actOptions);
        List<String> expOptions = Arrays.asList(getValueFromUIJSON(environment, data).split(","));
        Assert.assertEquals(expOptions, actOptions);
    }

    public void verifyPreCondDDOptions(){
        verifyDDOptions("preCondDD", "contactInformationPageData.preCondDropDownList");
    }

    public void verifyPostCondDDOptions(){
        verifyDDOptions("postDirectionalCodeDD", "contactInformationPageData.preCondDropDownList");
    }

    public void verifyUnitTypeDDOptions(){
        verifyDDOptions("unitTypeDD", "contactInformationPageData.unitTypeDropDownList");
    }

    public void verifyStateDDOptions(){
        verifyDDOptions("stateDD", "contactInformationPageData.stateTypeDropDownList");
    }

    public void verifyStateDDOptionsPO(){
        verifyDDOptions("stateDDPO", "contactInformationPageData.stateTypeDropDownList");
    }

    public void verifyFractionalHouseNumber(){
        verifyField("fractionalHouseNumInput", "contactInformationPageData.houseNo", "contactInformationPageData.alphaSpecialChars", 5, 5);
    }

    public void verifyStreetName(){
        verifyField("streetNameInput", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 50);
    }

    public void verifyUnit(){
        verifyField("unitInput", "contactInformationPageData.char6", "contactInformationPageData.alphaSpecialChars", 5, 5);
    }

    public void verifyCity(){
        verifyField("cityInput", "testDataRegistrationPage.nameFieldValue52Chars", "testDataRegistrationPage.nameFieldValue52Chars", 50, 50);
    }

    public void verifyZipCode(){
        verifyField( "zipcodeInput", "contactInformationPageData.poBoxPhysicalUsa", "contactInformationPageData.postalSpecChar", 10, 0);
    }

    public void verifyPostalCode(){
        verifyField( "postalCodeInput", "contactInformationPageData.postalCodeCanada", "contactInformationPageData.postalSpecChar", 7, 0);
    }

    public void verifyPostDirectionalDDOptions(){
        verifyDDOptions("postDirectionalDD", "contactInformationPageData.preCondDropDownList");
    }

    public void verifyProvinceDDOptions(){
        verifyDDOptions("provinceDD", "contactInformationPageData.provinceDropDownList");
    }

    public void verifyProvinceDDOptionsPO(){
        verifyDDOptions("provinceDD", "contactInformationPageData.provinceDropDownList");
    }
    public void verifyStreetTypeDDCanadaPhysical(){
        verifyDDOptions("streetTypeCanadaPhysicalDD", "contactInformationPageData.provinceDropDownList");
    }
    public void verifyStreetTypeDDUSAPhysical(){
                 verifyDDOptions("streetTypeUsaPhysicalDD", "contactInformationPageData.");
    }

    public void verifyRequiredFieldsForUsa() throws InterruptedException {
        clear(contactInformationPageProperties.getProperty("houseNumberInput"));
        clear(contactInformationPageProperties.getProperty("streetNameInput"));
        clear(contactInformationPageProperties.getProperty("cityInput"));
        clear(contactInformationPageProperties.getProperty("zipcodeInput"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
        verifyErrorRequiredField("ContactInformation_MailingAddressHouseNumber");
        verifyErrorRequiredField("ContactInformation_MailingAddressStreetName");
        verifyErrorRequiredField("ContactInformation_MailingAddressCity");
        verifyErrorRequiredField("ContactInformation_MailingAddressZipPostalCode");
        verifyErrorRequiredField("ContactInformation_MailingAddressStreetType");
        verifyErrorRequiredField("ContactInformation_MailingAddressState");
    }

    public void verifyRequiredFieldsForCanada() throws InterruptedException {
        clear(contactInformationPageProperties.getProperty("houseNumberInput"));
        clear(contactInformationPageProperties.getProperty("streetNameInput"));
        clear(contactInformationPageProperties.getProperty("cityInput"));
        clear(contactInformationPageProperties.getProperty("postalCodeInput"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
        verifyErrorRequiredField("ContactInformation_MailingAddressHouseNumber");
        verifyErrorRequiredField("ContactInformation_MailingAddressStreetName");
        verifyErrorRequiredField("ContactInformation_MailingAddressCity");
        verifyErrorRequiredField("ContactInformation_MailingAddressZipPostalCode");
        Thread.sleep(1000);
        verifyErrorRequiredField("ContactInformation_MailingAddressStreetType");
        verifyErrorRequiredField("ContactInformation_MailingAddressProvince");
    }

    public void verifyPORequiredFieldsForUSA(){
        clear(contactInformationPageProperties.getProperty("poInput"));
        clear(contactInformationPageProperties.getProperty("cityPOInput"));
        clear(contactInformationPageProperties.getProperty("zipCodePOInput"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
        verifyErrorRequiredField("ContactInformation_MailingAddressPostOfficeBox");
        verifyErrorRequiredField("ContactInformation_MailingAddressCity");
        verifyErrorRequiredField("ContactInformation_MailingAddressZipPostalCode");
    }

    public void verifyPORequiredFieldsForCanada(){
        clear(contactInformationPageProperties.getProperty("poInput"));
        clear(contactInformationPageProperties.getProperty("cityPOInput"));
        clear(contactInformationPageProperties.getProperty("zipCodePOInput"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
        verifyErrorRequiredField("ContactInformation_MailingAddressPostOfficeBox");
        verifyErrorRequiredField("ContactInformation_MailingAddressCity");
        verifyErrorRequiredField("ContactInformation_MailingAddressZipPostalCode");
        verifyErrorRequiredField("ContactInformation_MailingAddressProvince");// default selected in UI
    }

    public void enterDataInPhysicalAddress() throws InterruptedException {
        clear(contactInformationPageProperties.getProperty("houseNumberInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("houseNumberInput"), getValueFromUIJSON(environment, "validDataUSAPhysical.houseNumber"));
        selectOption(contactInformationPageProperties.getProperty("preCondDD"), "text="+getValueFromUIJSON(environment, "validDataUSAPhysical.preDirectionCode"));
        clear(contactInformationPageProperties.getProperty("streetNameTxtField"));
        sendKeysToElement(contactInformationPageProperties.getProperty("streetNameTxtField"), getValueFromUIJSON(environment, "validDataUSAPhysical.streetName"));
        selectOption(contactInformationPageProperties.getProperty("streetTypeDD"), "text="+getValueFromUIJSON(environment, "validDataUSAPhysical.streeType"));
        selectOption(contactInformationPageProperties.getProperty("postDirectionalCodeDD"), "text="+getValueFromUIJSON(environment, "validDataUSAPhysical.postDirectionalCode"));
        clear(contactInformationPageProperties.getProperty("cityInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("cityInput"), getValueFromUIJSON(environment, "validDataUSAPhysical.city"));
        selectOption(contactInformationPageProperties.getProperty("stateDD"), "text="+getValueFromUIJSON(environment, "validDataUSAPhysical.state"));
        clear(contactInformationPageProperties.getProperty("zipcodeInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("zipcodeInput"), getValueFromUIJSON(environment, "validDataUSAPhysical.zipCode"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
    }

    public void enterDataInPhysicalAddressCanada(){
        sendKeysToElement(contactInformationPageProperties.getProperty("houseNumberInput"), getValueFromUIJSON(environment, "validDataCanadaPhysical.houseNumber"));
        selectOption(contactInformationPageProperties.getProperty("preCondDD"), "text="+getValueFromUIJSON(environment, "validDataCanadaPhysical.preDirectionCode"));
        sendKeysToElement(contactInformationPageProperties.getProperty("streetNameTxtField"), getValueFromUIJSON(environment, "validDataCanadaPhysical.streetName"));
        selectOption(contactInformationPageProperties.getProperty("streetTypeDD"), "text="+getValueFromUIJSON(environment, "validDataCanadaPhysical.streeType"));
        selectOption(contactInformationPageProperties.getProperty("postDirectionalCodeDD"), "text="+getValueFromUIJSON(environment, "validDataCanadaPhysical.postDirectionalCode"));
        sendKeysToElement(contactInformationPageProperties.getProperty("cityInput"), getValueFromUIJSON(environment, "validDataCanadaPhysical.city"));
        selectOption(contactInformationPageProperties.getProperty("provinceDD"), "text="+getValueFromUIJSON(environment, "validDataCanadaPhysical.Province"));
        clear(contactInformationPageProperties.getProperty("postalCodeInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("postalCodeInput"), getValueFromUIJSON(environment, "validDataCanadaPhysical.zipCode"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
    }

    public void enterDataInPOUSA(){
        sendKeysToElement(contactInformationPageProperties.getProperty("poInput"), getValueFromUIJSON(environment, "validDataUsaPOBox.poBox"));
        selectOption(contactInformationPageProperties.getProperty("stateDDPO"), "text="+getValueFromUIJSON(environment, "validDataUsaPOBox.state"));
        clear(contactInformationPageProperties.getProperty("cityPOInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("cityPOInput"), getValueFromUIJSON(environment, "validDataUsaPOBox.city"));
        clear(contactInformationPageProperties.getProperty("zipCodePOInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("zipCodePOInput"), getValueFromUIJSON(environment, "validDataUsaPOBox.zipCode"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
    }

    public void enterDataInPOCanada(){
        sendKeysToElement(contactInformationPageProperties.getProperty("poInput"), getValueFromUIJSON(environment, "validDataCanadaPOBox.poBox"));
        selectOption(contactInformationPageProperties.getProperty("provinceDD"), "text="+getValueFromUIJSON(environment, "validDataCanadaPOBox.province"));
        clear(contactInformationPageProperties.getProperty("cityPOInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("cityPOInput"), getValueFromUIJSON(environment, "validDataCanadaPOBox.city"));
        sendKeysToElement(contactInformationPageProperties.getProperty("postalCodeInput"), getValueFromUIJSON(environment, "validDataCanadaPOBox.postalCode"));
    }

    public void verifyUpdatedMsg(String locator , String data){
        scrollDown(-1000);
        ExtentSparkReport.getExtentLogger().info("Verifying Success Message of USA Physical Mailing Address in Contact Information Page");
        String pageHeader = getTextOfElement(contactInformationPageProperties.getProperty(locator));
        Assert.assertEquals(pageHeader,getValueFromUIJSON(environment,data));
    }
    public void verifyUpdateMsgMailing(){
        scrollDown(-1000);
        verifyUpdatedMsg("mailingUSPhysicalSuccessMsg","contactInformationPageData.mailingUsPhysiacalSuccessText");
    }
    public void verifyUpdateMsgEmailing(){
        verifyUpdatedMsg("emailUpadteSuccessHeader","contactInformationPageData.emailUpdatedMsg");
    }

    public void verifyPOBox(){
        verifyField("poInput", "contactInformationPageData.poBoxNo", "contactInformationPageData.alphaSpecialChars12", 12, 12);
    }

    public void switchOnAddressOutsideToggle() throws InterruptedException {
        boolean status = Objects.requireNonNull(getElement(contactInformationPageProperties.getProperty("toggleIcon"))).isSelected();
        if(!status){
            clickOnElement(contactInformationPageProperties.getProperty("toggleIcon"),false);
            Thread.sleep(2000);
            status = Objects.requireNonNull(getElement(contactInformationPageProperties.getProperty("toggleIcon"))).isSelected();
        }
        assertThat(status, "toggle selected", "toggle not selected");
    }

    public void verifyOutsideFields(){
        verifyDisplayed("Mailing Address Line 1");
        verifyDisplayed("Mailing Address Line 2");
        verifyDisplayed2("cityOutsideUsa");
        verifyDisplayed2("countryOutsideUsa");
    }

    public void verifyOutsideRequiredFields(){
        clear(contactInformationPageProperties.getProperty("mailingAdd1"));
        clear(contactInformationPageProperties.getProperty("osCityInput"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
//        clickOnElement(contactInformationPageProperties.getProperty("btn").replace("headerName", ""), false);
        verifyErrorRequiredField("MailingAddressLine1");
        verifyErrorRequiredField("InternationalCity");
//        verifyErrorRequiredField("InternationalCountry");
    }

    public void verifyMailingAddress1(){
        verifyField("mailingAdd1", "contactInformationPageData.Value62Chars", "contactInformationPageData.Value62AlphaSpecialChars", 60, 60);
    }

    public void verifyMailingAddress2(){
        verifyField("mailingAdd2", "contactInformationPageData.Value62Chars", "contactInformationPageData.Value62AlphaSpecialChars", 60, 60);
    }

    public void verifyOutsideCity(){
        verifyField("osCityInput", "contactInformationPageData.Value62Chars", "contactInformationPageData.Value62AlphaSpecialChars", 50, 50);
    }

    public void verifyOutsideCountryDDOptions(){
        verifyDDOptions("osCountryDD", "contactInformationPageData.");
    }

    public void enterDataInOutsideFields(){
        clear(contactInformationPageProperties.getProperty("mailingAdd1"));
        sendKeysToElement(contactInformationPageProperties.getProperty("mailingAdd1"), getValueFromUIJSON(environment, "validDataAddressOutsideUSAAndCanada.adderess1"));
        selectOption(contactInformationPageProperties.getProperty("osCountryDD"), "text="+getValueFromUIJSON(environment, "validDataAddressOutsideUSAAndCanada.country"));
        clear(contactInformationPageProperties.getProperty("mailingAdd2"));
        sendKeysToElement(contactInformationPageProperties.getProperty("mailingAdd2"), getValueFromUIJSON(environment, "validDataAddressOutsideUSAAndCanada.address2"));
        clear(contactInformationPageProperties.getProperty("osCityInput"));
        sendKeysToElement(contactInformationPageProperties.getProperty("osCityInput"), getValueFromUIJSON(environment, "validDataAddressOutsideUSAAndCanada.city"));
        performJSClickElement(contactInformationPageProperties.getProperty("osSaveBtn"));
    }

    public void verifyEmailVerbiage(){
        verifyVerbiage("Email Address", "contactInformationPageData.emailVerbiage");
    }

    public void verifyPhoneVerbiage(){
        verifyVerbiage("Phone Numbers", "contactInformationPageData.phoneVerbiage");
    }

    public void verifyCurrentEmailAddress(){
        String actEmail = getTextOfElement(contactInformationPageProperties.getProperty("currentEmailAddress"));
        Assert.assertEquals(actEmail, getValueFromUIJSON(environment, "contactInformationPageData.currentEmailAddress"), "act email not verified");
    }

    public void verifyEmailRequiredFields(){
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        verifyErrorRequiredField("ContactInformation_CustomerEmailAddress");
        verifyErrorRequiredField("ContactInformation_CustomerEmailAddressConfirm");
    }

    public void verifyInvalidEmailError(){
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("newEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.invalidEmailId"));
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.invalidEmailId"));
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        String actError = getTextOfElement(contactInformationPageProperties.getProperty("errorField").replace("field", "ContactInformation_CustomerEmailAddress"));
        Assert.assertEquals(actError, getValueFromUIJSON(environment, "contactInformationPageData.invalidEmailErrorMsg"), "top error not verified");
        verifyTopError();
    }
    public void verifyTopError(){
        String actEmail = getTextOfElement(contactInformationPageProperties.getProperty("topError"));
        Assert.assertEquals(actEmail, getValueFromUIJSON(environment, "contactInformationPageData.topErrormsg"), "top error not verified");
    }

    public void verifyEmailMismatchError(){
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("newEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.emailMismatch"));
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.currentEmailAddress"));
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        String actError = getTextOfElement(contactInformationPageProperties.getProperty("errorField").replace("field", "ContactInformation_CustomerEmailAddressConfirm"));
        Assert.assertEquals(actError, getValueFromUIJSON(environment, "contactInformationPageData.mismatchEmailError"), "Mismatch error not verified");
        verifyTopError();
    }

    public void verifyEmailExistingError(){
        String actEmail = getTextOfElement(contactInformationPageProperties.getProperty("currentEmailAddress"));
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("newEmailAddress"), actEmail);
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("confirmEmailAddress"), actEmail);
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        String actError = getTextOfElement(contactInformationPageProperties.getProperty("topError"));
        Assert.assertEquals(actError, getValueFromUIJSON(environment, "contactInformationPageData.existingMailErrorMsg"), "Exisiting error message not verified");
    }

    public void updateEmail(){
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("newEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.newEmail"));
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.newEmail"));
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        verifyUpdateMsgEmailing();
    }
    public void reUpdateEmail(){
        clear(contactInformationPageProperties.getProperty("newEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("newEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.currentEmailAddress"));
        clear(contactInformationPageProperties.getProperty("confirmEmailAddress"));
        sendKeysToElement(contactInformationPageProperties.getProperty("confirmEmailAddress"), getValueFromUIJSON(environment, "contactInformationPageData.currentEmailAddress"));
        performJSClickElement(contactInformationPageProperties.getProperty("emailSaveBtn"));
        verifyUpdateMsgEmailing();
    }
    public void verifyPhoneNumberFields(){
        verifyDisplayed3("Current Home Phone");
        verifyDisplayed3("Current Mobile Phone");
        verifyDisplayed3("Current Work Phone");
    }

    public void verifyDisplayed3(String field){
        boolean status = isElementDisplayed(getElement(contactInformationPageProperties.getProperty("fields3").replace("fieldName", field)));
        assertThat(status, "element displayed", "element not displayed");
    }
    public void verifyMilitaryAddressFields(){

        verifyDisplayed2("cityMilitaryUsa");
        verifyDisplayed2("stateMilitaryUsa");
        verifyDisplayed("Zip Code");

    }
    public void verifyRequiredFieldsMilitaryAddress() throws InterruptedException {
        clear(contactInformationPageProperties.getProperty("streetNameMilitary"));
//        clear(contactInformationPageProperties.getProperty("streetNameInput"));
//        clear(contactInformationPageProperties.getProperty("cityInput"));
        clear(contactInformationPageProperties.getProperty("zipcodeMilitary"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
        verifyErrorRequiredField("ContactInformation_MilitaryAddressLine1");
        verifyErrorRequiredField("ContactInformation_MailingAddressMilitaryState");
        verifyErrorRequiredField("ContactInformation_MailingAddressZipPostalCode");

    }
    public void verifyMilitaryStreetName(){
        verifyField("streetNameMilitary", "testDataRegistrationPage.nameFieldValue52Chars", "contactInformationPageData.streetFieldSpecChar", 50, 50);
    }
    public void verifyCityMilitaryDDOptions(){
        verifyDDOptions("cityDDMilitary", "contactInformationPageData.cityMilitaryFieldDD");
    }
    public void verifyStateMilitaryDDOptions(){
        verifyDDOptions("stateDDMilitary", "contactInformationPageData.stateMilitaryFieldDD");
    }
    public void verifyZipCodeMilitary(){
        verifyField( "zipcodeMilitary", "contactInformationPageData.poBoxPhysicalUsa", "contactInformationPageData.postalSpecChar", 10, 0);
    }
    public void validDataMilitary(){
        clear(contactInformationPageProperties.getProperty("streetNameMilitary"));
        sendKeysToElement(contactInformationPageProperties.getProperty("streetNameMilitary"), getValueFromUIJSON(environment, "validDataMilitaryUsaAddress.streetName"));
        selectOption(contactInformationPageProperties.getProperty("cityDDMilitary"), "text="+getValueFromUIJSON(environment, "validDataMilitaryUsaAddress.city"));
        selectOption(contactInformationPageProperties.getProperty("stateDDMilitary"), "text="+getValueFromUIJSON(environment, "validDataMilitaryUsaAddress.state"));
        clear(contactInformationPageProperties.getProperty("zipcodeMilitary"));
        sendKeysToElement(contactInformationPageProperties.getProperty("zipcodeMilitary"), getValueFromUIJSON(environment, "validDataMilitaryUsaAddress.zipCode"));
        performJSClickElement(contactInformationPageProperties.getProperty("saveBtn"));
    }
    public void verifyUpdateMsgMilitaryMailing(){
        verifyUpdatedMsg("mailingUSPhysicalSuccessMsg","contactInformationPageData.mailingUsPhysiacalSuccessText");
    }


}
