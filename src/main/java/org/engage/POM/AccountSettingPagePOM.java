package org.engage.POM;

import com.aventstack.extentreports.Status;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Database.CISQueries;
import org.engage.Database.DatabaseConnection;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Objects;
import java.util.Properties;
import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.getValueFromUIJSON;
import static org.engage.Database.DatabaseOperations.executeQueryAndGetRows;
import static org.engage.POM.BillingDeliveryPreferencePagePOM.clickOnProfileMenu;
import static org.engage.POM.BillingDeliveryPreferencePagePOM.selectSubMenu;
import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;
import static org.engage.POM.LoginPagePOM.*;

import java.util.*;
import java.util.logging.Logger;

public class AccountSettingPagePOM {

    public static Properties accountSettingPageProperties;
    public String newAcc, originalAcc;
    Logger logger = Logger.getLogger(this.getClass().getName());
    public static List<String> environment;

    public AccountSettingPagePOM(List<String> environment)
    {
        AccountSettingPagePOM.environment=environment;
        accountSettingPageProperties = BaseUtilities.readPropertyfile(FilePaths.ACCOUNT_SETTING_PAGE_PROPERTIES);
    }

    public void verifyPageTitleAndCardTitle(){
        String actHeader = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("headerTitle")));
        String expHeader = getValueFromUIJSON(environment, "accountSettingPageData.header");
        assertThat(actHeader, expHeader, "header " +actHeader+ " matched with expected " + expHeader, "header " +actHeader+ " not matched with expected " + expHeader);
        ExtentSparkReport.getExtentLogger().info("Successfully Validated title cards and page headers.");
        List<WebElement> cardElements = getElements(accountSettingPageProperties.getProperty("cardTitles"));
        assert cardElements != null;
        String[] expHeaders = Objects.requireNonNull(getValueFromUIJSON(environment, "accountSettingPageData.cardHeader")).split(",");
        for(int i=0; i<cardElements.size(); i++){
            actHeader = getTextOfElements(cardElements.get(i));
            assert actHeader != null;
            assertThat(actHeader, expHeaders[i], "act card header " +actHeader+ " matched with expected " + expHeaders[i], "act card header " +actHeader+ " not matched with expected " + expHeaders[i]);
        }
    }

    public void verifyVerbiageDefaultAcc(){
        String actVerb  = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("defaultAccVerbiage"))).trim();
        assertThat(actVerb, getValueFromUIJSON(environment, "accountSettingPageData.defaultAccountVerbiage"), "actual verbiage matched with expected " + actVerb, "actual verbiage not matched with expected " + actVerb);
        ExtentSparkReport.getExtentLogger().info("Successfully validated Default account verbiage.");
    }

    public void verifyLabel(){
        String actLabel = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("currentAccLabel"))).trim();
        assertThat(actLabel, getValueFromUIJSON(environment, "accountSettingPageData.label1"), "actual label matched with expected " + actLabel, "actual label not matched with expected " + actLabel);
        ExtentSparkReport.getExtentLogger().info("Successfully validated current account Label-1 ");
        actLabel = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("newCurrentAccLabel"))).trim();
        assertThat(actLabel, getValueFromUIJSON(environment, "accountSettingPageData.label2"), "actual label matched with expected " + actLabel, "actual label not matched with expected " + actLabel);
        ExtentSparkReport.getExtentLogger().info("Successfully Validated current account Label-2.");
    }

    public void verifyPrepopulatedAcc(){
        String expAcc = getAttribute(accountSettingPageProperties.getProperty("currentAccSelected"), "title").trim();
        String actAcc = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("currentAcc"))).trim();
        assertThat(actAcc, expAcc, "acc prepopulated verified " + actAcc, "acc prepopulated not verified " + actAcc);
        ExtentSparkReport.getExtentLogger().info("Successfully Validated prepopulated account.");
    }

    public void verifyAccountsInDropdown(){
        List<String> actAcc = getAllDropdownOption(accountSettingPageProperties.getProperty("expAccountsDropdown"));
        List<String> expAcc = getAllDropdownOption(accountSettingPageProperties.getProperty("defaultAccDropdown"));
        if(actAcc.size() == expAcc.size()){
            assertThat(new HashSet<>(actAcc).containsAll(expAcc), "all accounts present in dropdown", "all accounts not present in dropdown");
        }else{
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "acc details not matched with expected");
            Assert.fail();
        }
    }

    public void updateDefaultAcc(){
        clickOnElement(accountSettingPageProperties.getProperty("defaultAccDropDown"), false);
        List<WebElement> actAccElements = getElements(accountSettingPageProperties.getProperty("actAccountList"));
        originalAcc = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("currentAcc"))).trim();
        assert actAccElements != null;
        for(WebElement element : actAccElements){
            if(!Objects.equals(getTextOfElements(element), originalAcc)){
                newAcc = getTextOfElements(element);
                element.click();
                break;
            }
        }
    }

    public void verifySaveBtn(){
        updateDefaultAcc();
        performJSClickElement(accountSettingPageProperties.getProperty("defaultAccSaveBtn"));
        String actMsg = getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"));
        assert actMsg != null;
        assertThat(actMsg, getValueFromUIJSON(environment, "accountSettingPageData.defaultAccUpdatedSuccessMsg"), "default acc updated", "default acc not updated");
        ExtentSparkReport.getExtentLogger().info("Successfully Validated prepopulated account.");
        performJSClickElement(accountSettingPageProperties.getProperty("defaultAccSaveBtn"));
        actMsg = getTextOfElement(accountSettingPageProperties.getProperty("actErrorMsg"));
        assert actMsg != null;
        assertThat(actMsg, getValueFromUIJSON(environment, "accountSettingPageData.defaultAccUpdatedErrorMsg"), "default acc not updated", "default acc updated");
    }

    public void verifyNewAccReLogin(){
        doLogout();
        enterUserName(getValueFromUIJSON(environment, "validCredentials[0].username"));
        enterPassword(getValueFromUIJSON(environment, "validCredentials[0].password"));
        performLogin();
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Account Settings");
        String actAcc = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("currentAcc"))).trim();
        assertThat(actAcc, newAcc, "acc prepopulated verified " + actAcc, "acc prepopulated not verified " + actAcc);
    }

    public void revertToOriginalAcc(){
        clickOnElement(accountSettingPageProperties.getProperty("defaultAccDropDown"), false);
        List<WebElement> actAccElements = getElements(accountSettingPageProperties.getProperty("actAccountList"));
        assert actAccElements != null;
        for(WebElement element : actAccElements){
            if(Objects.equals(getTextOfElements(element), originalAcc)){
                element.click();
                break;
            }
        }
        performJSClickElement(accountSettingPageProperties.getProperty("defaultAccSaveBtn"));
        String actMsg = getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"));
        assert actMsg != null;
        assertThat(actMsg, getValueFromUIJSON(environment, "accountSettingPageData.defaultAccUpdatedSuccessMsg"), "default acc reverted successfully", "default acc not reverted successfully");
    }


    public void verifyNickNameLabel(){
        String actLabel = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("nickNameLabel"))).trim();
        assertThat(actLabel, getValueFromUIJSON(environment, "accountSettingPageData.nickNameLabel"), "actual label matched with expected " + actLabel, "actual label not matched with expected " + actLabel);
        actLabel = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("newNickNameLabel"))).trim();
        assertThatContains(actLabel, getValueFromUIJSON(environment, "accountSettingPageData.newNickNameLabel"), "actual label matched with expected " + actLabel, "actual label not matched with expected " + actLabel);
    }

    public void verifyVerbiageNewNickName(){
        String actVerb  = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("newNickNameVerb"))).trim();
        assertThat(actVerb, getValueFromUIJSON(environment, "accountSettingPageData.newNickNameVerbiage"), "actual verbiage matched with expected " + actVerb, "actual verbiage not matched with expected " + actVerb);
    }

    public void verifyNickNameSaveBtn(){
        sendKeysToElement(accountSettingPageProperties.getProperty("newNickNameInput"), getValueFromUIJSON(environment, "accountSettingPageData.newNickName") + "1");
        performJSClickElement(accountSettingPageProperties.getProperty("newNickNameSaveBtn"));
        clear(accountSettingPageProperties.getProperty("newNickNameInput"));
        sendKeysToElement(accountSettingPageProperties.getProperty("newNickNameInput"), getValueFromUIJSON(environment, "accountSettingPageData.newNickName"));
        performJSClickElement(accountSettingPageProperties.getProperty("newNickNameSaveBtn"));
        String actMsg = getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"));
        assert actMsg != null;
        assertThat(actMsg, getValueFromUIJSON(environment, "accountSettingPageData.accNickNameUpdated"), "nick name updated", "nick name not updated");
        verifyNickNameAssigned();
        performJSClickElement(accountSettingPageProperties.getProperty("newNickNameSaveBtn"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        performJSClickElement(accountSettingPageProperties.getProperty("newNickNameSaveBtn"));
        actMsg = getTextOfElement(accountSettingPageProperties.getProperty("actErrorMsg"));
        assert actMsg != null;
        assertThat(actMsg, getValueFromUIJSON(environment, "accountSettingPageData.defaultAccUpdatedErrorMsg"), "nick name not updated", "nick name updated");
    }

    public void verifyNickNameAssigned(){
        String actAcc = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("currentAcc"))).trim();
        assertThatContains(actAcc, getValueFromUIJSON(environment, "accountSettingPageData.newNickName"), "nick name updated", "nick name not updated");
    }

    public void clickAddAccountBtn(){
        clickOnElement(accountSettingPageProperties.getProperty("addAccountButton"),false);
    }

    public void validateAccountNumberMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Account Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(accountSettingPageProperties.getProperty("errorMsgAddAccountNumber"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            assert actualErrorMessage != null;
            Assert.assertEquals(actualErrorMessage,expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated mandatory account number field.");
        } catch (Exception e) {
            Assert.fail("Failed to validate account number mandatory field error message. Error message: " + e.getMessage());
        }
    }

    public void validateLastNameMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Last Name Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(accountSettingPageProperties.getProperty("errorMsgLastName"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
            ExtentSparkReport.getExtentLogger().info("Successfully Validated mandatory last name field.");
        } catch (Exception e) {
            Assert.fail("Failed to validate last name field error message. Error message: " + e.getMessage());
        }

    }

    public void accountFieldValidationAlphaSpecialCharacters() {
        try {
            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), getValueFromUIJSON(environment, "accountSettingPageData.alphabetsAndSpecialChar"));
            String enteredSpecialCharValue = getAttribute(accountSettingPageProperties.getProperty("addAccountNumberTextField"), "value");
            assertThat(enteredSpecialCharValue.isEmpty(), "No Special Character Entered.", "Special Characters Allowed.");
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }
    }

    public void selectResidentialCustomerTypeAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Clicking on residential.");
            clickOnElement(accountSettingPageProperties.getProperty("residentialButton"), false);
            boolean residentialRadio = isElementSelected(accountSettingPageProperties.getProperty("residentialButton"));
            assertThat(residentialRadio, "residential customer type selected", "residential customer type not selected");
        }catch (Exception e) {
            Assert.fail("Failed to click on commercial. Error message: " + e.getMessage());
        }
    }

    public void selectCommercialCustomerTypeAndValidate() {
        try {
            ExtentSparkReport.getExtentLogger().info("Clicking on commercial.");
            clickOnElement(accountSettingPageProperties.getProperty("commercialButton"),false);
            boolean commercialRadio = isElementSelected(accountSettingPageProperties.getProperty("commercialButton"));
            assertThat(commercialRadio, "residential customer type selected", "residential customer type not selected");
        }catch (Exception e) {
            Assert.fail("Failed to click on commercial. Error message: " + e.getMessage());
        }
    }

    public void verifyLastNameMoreThan40Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 40 Characters in Last Name Field.");
            clear(accountSettingPageProperties.getProperty("lastNameTextField"));
            sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.userNameValue35Char"));
            WebElement lastNameValue = getElement(accountSettingPageProperties.getProperty("lastNameTextField"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
            assert lastNameValue != null;
            String enteredValue = lastNameValue.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            System.out.println(enteredValue.length());
            System.out.println(enteredValue);
            if (enteredValue.length() <= 40) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 40");
                lastNameValue.clear();
            } else {
                System.out.println("Validation failed: Entered more than 40 Characters");
                Assert.fail("Validation failed: Entered more than 40 Characters");
            }
        } catch (Exception e) {
            Assert.fail("Failed to verify if Last Name has more than 40 characters. Error message: " + e.getMessage());
        }
    }

    public void lastNameFieldValidationSpecialCharacters() {
        try {
            sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"), getValueFromUIJSON(environment, "accountSettingPageData.specialCharLastName"));
            String enteredSpecialCharValue = getAttribute(accountSettingPageProperties.getProperty("lastNameTextField"), "value");
            assertThat(!enteredSpecialCharValue.isEmpty(), "Special Character Entered.", "No Special Characters Allowed.");
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }
    }

    public void accountFieldValidationGreaterThanSevenDigits() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering More than 7 digits in Account Number Field.");
            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), getValueFromUIJSON(environment, "testDataRegistrationPage.accountNumericValue"));
            WebElement accountNumberField = getElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"));
            ExtentSparkReport.getExtentLogger().info("Extracting the entered value for Account Number field from UI.");
            Objects.requireNonNull(accountNumberField, "Account number field is null");
            String enteredValue = accountNumberField.getAttribute("value");
            ExtentSparkReport.getExtentLogger().info("Validating the Entered Value should be equal to 7 digits");
            if (enteredValue.length() == 7) {
                Assert.assertTrue(true);
                ExtentSparkReport.getExtentLogger().info("Validated Successfully - Entered Digits are 7.");
                accountNumberField.clear();
            } else {
                Assert.fail("Validation failed: Entered more than 7 digits. Actual length: " + enteredValue.length());
            }
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation. Error message: " + e.getMessage());
        }
    }

   public void clickOnCheckbox(){
        clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"),false);
    }

    public  void checkBoxVerbiage(){
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("checkboxVerbiage"))),getValueFromUIJSON(environment,"accountSettingPageData."),"Account Nick name label is validated","Account Nick name label is not validated");
    }

    public void verifyAccountNickNameLabel(){
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accountNickNameLabel"))),getValueFromUIJSON(environment,"accountSettingPageData.accountNickNameLabel"),"Account Nick name label is validated","Account Nick name label is not validated");
    }

    public void verifyAccountNickNameVerbiage(){
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accountNickNameVerbiage"))),getValueFromUIJSON(environment,"accountSettingPageData.accountNickNameVerbiage"),"Account Nick name verbiage is validated","Account Nick name verbiage is not validated");
    }

    public  void validResidentialData(){
        sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"),getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.accountNumber"));
        sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"),getValueFromUIJSON(environment,"validRequiredFieldDataForRegistration.lastName"));
        clickAddAccountBtn();
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("errorMsgAddAccount"))),getValueFromUIJSON(environment,"accountSettingPageData.accountAddSuccessMessage"),"Account added message validated","Account added message not validated");
    }

    public void verifyAddAccountErrorMessageWithInvalidData() throws InterruptedException {
        sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"),getValueFromUIJSON(environment,"accountSettingPageData.invalidAccNo"));
        clear(accountSettingPageProperties.getProperty("lastNameTextField"));
        sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"),getValueFromUIJSON(environment,"accountSettingPageData.invalidLastName"));
        clickAddAccountBtn();
        Thread.sleep(2000);
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("errorMsgAddAccount2"))),getValueFromUIJSON(environment,"accountSettingPageData.invalidDataErrorMessage"),"invalid details error message validated","invalid details error message not validated");
    }

    public void verifyAddAccountWithNickNameWithValidData() throws InterruptedException {
        clear(accountSettingPageProperties.getProperty("addAccountNumberTextField"));
        sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.accountNumber"));
        clear(accountSettingPageProperties.getProperty("lastNameTextField"));
        sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.lastName"));
        sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNickNameTextField"), getValueFromUIJSON(environment, "accountSettingPageData.newNickName"));
        Thread.sleep(1000);
        clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"), false);
        clickAddAccountBtn();
        String successMessageLocator = accountSettingPageProperties.getProperty("accSuccessMsg");
        String existingAccountMessageLocator = accountSettingPageProperties.getProperty("actErrorMsg");
        String successMessage = getTextOfElement(successMessageLocator);
        String existingAccountMessage = getTextOfElement(existingAccountMessageLocator);
        if (successMessage != null && successMessage.equals("Account added!")) {
            assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"))), getValueFromUIJSON(environment, "accountSettingPageData.accountAddSuccessMessage"), "Account added message validated", "Account added message not validated");
            scrollDown(500);
//            clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"), false);
//            clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
//            Thread.sleep(2000);
//            Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));
        }
        else {
            assert existingAccountMessage != null;
            if(existingAccountMessage.equals("Account already added. Please add another account."))
            {
                String removeAccount = getTextOfElement(accountSettingPageProperties.getProperty("removeAccountDropDown"));
                String targetValue = "23877 (TestNewNickName)";
                assert removeAccount != null;
                if(removeAccount.equals(targetValue)) {
                    scrollDown(500);
                    clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"),false);
                    clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"),false);
                    Thread.sleep(2000);
                    Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));

                } else {
                    clickOnElement(accountSettingPageProperties.getProperty("removeAccountDropDown"),false);
                    List<WebElement> element = getElements(accountSettingPageProperties.getProperty("removeAccountList"));
                    for(int i = 0; i < Objects.requireNonNull(element).size(); i++)
                    {
                        String text = element.get(i).getText();
                        if(text.contains("23877"))
                        {
                            element.get(i).click();
                            break;
                        }
                    }
                    scrollDown(500);
                    Thread.sleep(1000);
                    clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"), false);
                    clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
                    Thread.sleep(2000);
                    Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));
                    Thread.sleep(2000);
                }
                sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.accountNumber"));
                sendKeysToElement(accountSettingPageProperties.getProperty("lastNameTextField"), getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.lastName"));
                sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNickNameTextField"), getValueFromUIJSON(environment, "accountSettingPageData.newNickName"));
                Thread.sleep(1000);
                clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"), false);
                clickAddAccountBtn();
                Thread.sleep(2000);
                assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"))), getValueFromUIJSON(environment, "accountSettingPageData.accountAddSuccessMessage"), "Account added message validated", "Account added message not validated");
                verifyDefaultAccount(getValueFromUIJSON(environment, "validRequiredFieldDataForRegistration.accountNumber"));
            }
        }

    }



    public void verifyDefaultAccount(String accNo){
        String actAcc = getAttribute(accountSettingPageProperties.getProperty("currentAccSelected"), "title").trim();
        assertThatContains(actAcc, accNo, "acc default verified " + actAcc, "acc default not verified " + actAcc);
    }

    public void verifyAddAccountNickNameLabel(){
        String actLabel = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("addAccountNickNameLabel"))).trim();
        assertThatContains(actLabel, getValueFromUIJSON(environment, "accountSettingPageData.accountNickNameLabel"), "actual label matched with expected " + actLabel, "actual label not matched with expected " + actLabel);
    }

    public void verifyAddAccountVerbiageNewNickName(){
        String actVerb  = Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("addAccountNickNameVerbiage"))).trim();
        assertThat(actVerb, getValueFromUIJSON(environment, "accountSettingPageData.addAccountNickNameVerbiage"), "actual verbiage matched with expected " + actVerb, "actual verbiage not matched with expected " + actVerb);
    }

    public void validateCompanyNameMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Account Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(accountSettingPageProperties.getProperty("requiredErrorCompanyName"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            assert actualErrorMessage != null;
            assertThat(actualErrorMessage,expectedErrorMessage,"Account number error message  validated","Account number error message not validated");
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate account number mandatory field error message. Error message: " + e.getMessage());
        }
    }

    public void validateTaxIDMandatoryFieldErrorMessage() {
        try {
            ExtentSparkReport.getExtentLogger().info("Validate Account Field Error Message to Populate for Required Field.");
            String actualErrorMessage = getTextOfElement(accountSettingPageProperties.getProperty("requiredErrorTaxID"));
            String expectedErrorMessage = getValueFromUIJSON(environment, "errorMessagesRegistrationPage.errorMessage");
            assert actualErrorMessage != null;
            assertThat(actualErrorMessage,expectedErrorMessage,"Account number error message  validated","Account number error message not validated");
            ExtentSparkReport.getExtentLogger().info("Successfully Validated.");
        } catch (Exception e) {
            Assert.fail("Failed to validate account number mandatory field error message. Error message: " + e.getMessage());
        }
    }

    public void verifyCompanyNameMoreThan40Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering 40 Characters in company Name Field.");
            clear(accountSettingPageProperties.getProperty("inputCompanyName"));
            sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.userNameValue35Char"));
            String enteredValue = getAttribute(accountSettingPageProperties.getProperty("inputCompanyName"), "value");
            assert enteredValue != null;
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            assertThat(enteredValue.length() <= 40, "Successfully Verified - Entered Characters are 40", "Entered more than 40 Characters");
            clear(accountSettingPageProperties.getProperty("inputCompanyName"));
        } catch (Exception e) {
            Assert.fail("Failed to verify if company Name has more than 40 characters. Error message: " + e.getMessage());
        }
    }

    public void verifyTaxIDMoreThan9Characters() {
        try {
            ExtentSparkReport.getExtentLogger().info("Entering more than 9 Characters in Last Name Field.");
            clear(accountSettingPageProperties.getProperty("inputTaxID"));
            sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"),
                    getValueFromUIJSON(environment,"testDataRegistrationPage.TaxIDValue10Chars"));
            String enteredValue = getAttribute(accountSettingPageProperties.getProperty("inputTaxID"), "value");
            assert enteredValue != null;
            int a = enteredValue.length();
            ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
            assertThat(enteredValue.length() <= 10, "Successfully Verified - Entered Characters are 9", "Entered more than 9 Characters");
            clear(accountSettingPageProperties.getProperty("inputTaxID"));
        } catch (Exception e) {
            Assert.fail("Failed to verify if tax id has more than 9 characters. Error message: " + e.getMessage());
        }
    }

    public void companyNameFieldValidationSpecialCharacters() {
        try {
            sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"), getValueFromUIJSON(environment, "accountSettingPageData.specialCharLastName"));
            String enteredSpecialCharValue = getAttribute(accountSettingPageProperties.getProperty("inputCompanyName"), "value");
            assertThat(!enteredSpecialCharValue.isEmpty(), "Special Character Entered.", "No Special Characters Allowed.");
        } catch (Exception e) {
            Assert.fail("Failed to perform company field validation for special characters. Error message: " + e.getMessage());
        }
    }

    public void taxIdFieldValidationAlphaSpecialCharacters() {
        try {
            sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"), getValueFromUIJSON(environment, "accountSettingPageData.alphabetsAndSpecialChar"));
            String enteredSpecialCharValue = getAttribute(accountSettingPageProperties.getProperty("inputTaxID"), "value");
            assertThat(enteredSpecialCharValue.isEmpty(), "No Special Character Entered.", "Special Characters Allowed.");
        } catch (Exception e) {
            Assert.fail("Failed to perform account field validation for special characters. Error message: " + e.getMessage());
        }
    }

    public void verifyAddAccountErrorMessageWithInvalidDataCommercial() throws InterruptedException {
        clear(accountSettingPageProperties.getProperty("addAccountNumberTextField"));
        sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"),getValueFromUIJSON(environment,"accountSettingPageData.invalidAccNo"));
        clear(accountSettingPageProperties.getProperty("inputCompanyName"));
        sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"),getValueFromUIJSON(environment,"accountSettingPageData.invalidLastName"));
        clear(accountSettingPageProperties.getProperty("inputTaxID"));
        sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"),getValueFromUIJSON(environment,"accountSettingPageData.invalidTaxId"));
        clickAddAccountBtn();
        Thread.sleep(2000);
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("errorMsgAddAccount2"))),getValueFromUIJSON(environment,"accountSettingPageData.invalidDataErrorMessage"),"invalid details error message validated","invalid details error message not validated");
    }

    public void verifyAddAccountWithNickNameCommercial() {
        List<Map<String, String>> resultList = executeQueryAndGetRows(CISQueries.SELECT_COMMERCIAL_CUSTOMER_DATA, DatabaseConnection.DatabaseType.CIS);
        Map<String, String> result = null;
        try {
            ExtentSparkReport.getExtentLogger().info("Entering valid data.");
            if (!resultList.isEmpty()) {
                result = resultList.get(0);
                String accountNumber = result.get("UMACT");
                clear(accountSettingPageProperties.getProperty("addAccountNumberTextField"));
                sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), accountNumber);
                clear(accountSettingPageProperties.getProperty("inputCompanyName"));
                String companyName = result.get("MSLNM");
                sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"), companyName);
                clear(accountSettingPageProperties.getProperty("inputTaxID"));
                String federalID = result.get("E_FEDERAL_TAX_ID");
                String actualFederalID = federalID.replace("F", "");
                String part1 = actualFederalID.substring(0, 2);
                String part2 = actualFederalID.substring(2);
                String validFederalID = part1 + "-" + part2;
                sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"), validFederalID);
                String randomNickName = generateRandomString(10);
                sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNickNameTextField"), randomNickName);
                clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"), false);
                clickAddAccountBtn();
                String successMessageLocator = accountSettingPageProperties.getProperty("accSuccessMsg");
                String existingAccountMessageLocator = accountSettingPageProperties.getProperty("actErrorMsg");
                String successMessage = getTextOfElement(successMessageLocator);
                String existingAccountMessage = getTextOfElement(existingAccountMessageLocator);
                if (successMessage!=null && successMessage.equals("Account added!")){
                    assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"))), getValueFromUIJSON(environment, "accountSettingPageData.accountAddSuccessMessage"), "Account added message validated", "Account added message not validated");
                    scrollDown(500);
//                    clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"), false);
//                    clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
//                    Thread.sleep(2000);
//                    Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));
                }
                else {
                    assert existingAccountMessage != null;
                    if (existingAccountMessage.equals("Account already added. Please add another account.")) {
                        String removeAccount = getTextOfElement(accountSettingPageProperties.getProperty("removeAccountDropDown"));
                        assert removeAccount != null;
                        if (removeAccount.contains(accountNumber)) {
                            scrollDown(500);
                            clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"), false);
                            clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
                            Thread.sleep(2000);
                            Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));
                            Thread.sleep(2000);
                            ExtentSparkReport.getExtentLogger().info("Entering valid data.");
                            clickOnElement(accountSettingPageProperties.getProperty("commercialButton"),false);
                            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), accountNumber);
                            sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"), companyName);
                            sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"), validFederalID);
                            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNickNameTextField"), randomNickName);
                            clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"), false);
                            clickAddAccountBtn();
                            Thread.sleep(2000);
                            assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"))), getValueFromUIJSON(environment, "accountSettingPageData.accountAddSuccessMessage"), "Account added message validated", "Account added message not validated");
                            verifyDefaultAccount(accountNumber);
                        } else {
                            scrollDown(500);
                            clickOnElement(accountSettingPageProperties.getProperty("removeAccountDropDown"),false);
                            List<WebElement> element = getElements(accountSettingPageProperties.getProperty("removeAccountList"));
                            for (int i = 0; i < Objects.requireNonNull(element).size(); i++) {
                                String text = element.get(i).getText();
                                if (text.contains(accountNumber)) {
                                    element.get(i).click();
                                    break;
                                }
                            }
                            Thread.sleep(1000);
                            clickOnElement(accountSettingPageProperties.getProperty("removeAccButton"), false);
                            clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
                            Thread.sleep(2000);
                            Assert.assertEquals(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg")), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"));
                            Thread.sleep(2000);
                            ExtentSparkReport.getExtentLogger().info("Entering valid data.");
                            clickOnElement(accountSettingPageProperties.getProperty("commercialButton"),false);
                            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNumberTextField"), accountNumber);
                            sendKeysToElement(accountSettingPageProperties.getProperty("inputCompanyName"), companyName);
                            sendKeysToElement(accountSettingPageProperties.getProperty("inputTaxID"), validFederalID);
                            sendKeysToElement(accountSettingPageProperties.getProperty("addAccountNickNameTextField"), randomNickName);
                            clickOnElement(accountSettingPageProperties.getProperty("setDefaultCheckbox"), false);
                            clickAddAccountBtn();
                            Thread.sleep(2000);
                            assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("accSuccessMsg"))), getValueFromUIJSON(environment, "accountSettingPageData.accountAddSuccessMessage"), "Account added message validated", "Account added message not validated");
                            verifyDefaultAccount(accountNumber);
                        }
                    }
                }
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


        public void verifyRemoveAccountVerbiage (){
                String actVerbiage = getTextOfElement(accountSettingPageProperties.getProperty("removeAccVerbiage"));
                assert actVerbiage != null;
                assertThat(actVerbiage, getValueFromUIJSON(environment, "accountSettingPageData.removeAccVerbiage"), "remove acc verbiage verified", "remove acc verbiage not verified");
            }

            public void verifyLinks (){
                scrollDown(500);
                clickOnElement(accountSettingPageProperties.getProperty("transferLnk"), false);
                String actHeader = getTextOfElement(accountSettingPageProperties.getProperty("serviceHeader"));
                assert actHeader != null;
                assertThatContains(actHeader, getValueFromUIJSON(environment, "accountSettingPageData.transferHeader"), "navigated to transfer", "not navigated to transfer");
                navigateToBack();
                clickOnElement(accountSettingPageProperties.getProperty("stopLnk"), false);
                actHeader = getTextOfElement(accountSettingPageProperties.getProperty("serviceHeader"));
                assert actHeader != null;
                assertThatContains(actHeader, getValueFromUIJSON(environment, "accountSettingPageData.stopSerViceHeader"), "navigated to stop service", "not navigated to stop service");
                navigateToBack();
            }

    public void verifyAndRemoveAccountIfPresent(String acc) throws InterruptedException {
        if (isAccountPresentInDropdown( acc)) {
            verifyRemoveAccountsInDropdown(acc);
        } else {
            System.out.println("Account " + getValueFromUIJSON(environment, acc) + " not present in the dropdown list.");
        }
    }

    private boolean isAccountPresentInDropdown(String acc) {
        List<String> allAccounts = getAllDropdownOption(accountSettingPageProperties.getProperty("removeAccDropDown"));
        String account = getValueFromUIJSON(environment, acc);
        System.out.println(account);
        for (String accOption : allAccounts) {
            if (accOption.contains(account)) {
                return true;
            }
        }
        return false;
    }


    private void verifyRemoveAccountsInDropdown(String acc) throws InterruptedException {
        scrollDown(1000);
        clickOnElement(accountSettingPageProperties.getProperty("removeAccDropdown"), false);
        clickOnElement(accountSettingPageProperties.getProperty("accToRemove").replace("^^^", getValueFromUIJSON(environment, acc)), false);
        clickOnElement(accountSettingPageProperties.getProperty("removeAccBtn"), false);
        clickOnElement(accountSettingPageProperties.getProperty("removeAccYesBtn"), false);
        assertThat(Objects.requireNonNull(getTextOfElement(accountSettingPageProperties.getProperty("removeAccHeader"))), getValueFromUIJSON(environment, "accountSettingPageData.accountRemoveSuccessMessage"), "acc removed", "acc not removed");
        List<String> expAcc = getAllDropdownOption(accountSettingPageProperties.getProperty("expAccountsDropdown"));
        assertThat(!expAcc.contains(getValueFromUIJSON(environment, acc)), "acc removed", "acc not removed");
        Thread.sleep(2000);
    }


}

