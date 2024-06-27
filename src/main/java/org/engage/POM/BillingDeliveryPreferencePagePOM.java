package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


import static org.engage.Base.BaseUtilities.*;

public class BillingDeliveryPreferencePagePOM  {

    public static Properties billingDeliveryPreferencePageProperties;
    public static List<String> environment;

    public BillingDeliveryPreferencePagePOM(List<String> environment) {
        BillingDeliveryPreferencePagePOM.environment=environment;
        billingDeliveryPreferencePageProperties = BaseUtilities.readPropertyfile(FilePaths.BILLING_PREFERENCE_PAGE_PROPERTIES);
    }

    public static void selectSubMenu(String expSubMenu){
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("subMenu").replace("^^^", expSubMenu));
//        performJSClickElement(billingDeliveryPreferencePageProperties.getProperty("menu").replace("^^^", expSubMenu));
    }

    public void verifyPageTitleAndCardTitle(){
        String actHeader = Objects.requireNonNull(getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("headerTitle"))).trim();
        Assert.assertEquals(actHeader, getValueFromUIJSON(environment,"billingDeliveryPreferenceData.header"), "header not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the Cards headers in billing  preference page.");
        String actCardTitle = Objects.requireNonNull(getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("cardTitle"))).trim();
        Assert.assertEquals(actCardTitle, getValueFromUIJSON(environment,"billingDeliveryPreferenceData.cardTitle"), "card title not matched with expected");
        ExtentSparkReport.getExtentLogger().info("Verified the card headers in billing preference page.");
    }

    public void verifyBillingDeliveryOptions(){
        List<WebElement> elements =  getElements(billingDeliveryPreferencePageProperties.getProperty("deliveryOptions"));
        String[] options = {"Paperless Billing", "Paper Bill"};
        boolean status = false;
        assert elements != null;
        int i = 0;
        for(WebElement element : elements){
            String option = Objects.requireNonNull(getTextOfElements(element)).trim();
            status = option.contains(options[i]);
            if(!status){
                break;
            }
            i++;
        }
        Assert.assertTrue(status, "billing options not verified");
    }

    public void verifyDefaultSelectedOption(){
        String value = getAttribute(billingDeliveryPreferencePageProperties.getProperty("paperBillingOption"), "checked");
        Assert.assertEquals(value, "true", "billing option not default selected");
        ExtentSparkReport.getExtentLogger().info("Billing Option selected.");
    }

    public void verifyVerbiagePaperLessBilling(){
        String paperLessBillingVerbiage1 = getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("paperLessBillingVerbiage1"));
        String paperLessBillingVerbiage2 = getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("paperLessBillingVerbiage2"));
        assert paperLessBillingVerbiage1 != null;
        assert paperLessBillingVerbiage2 != null;
        Assert.assertTrue(paperLessBillingVerbiage1.equals(getValueFromUIJSON(environment,"billingDeliveryPreferenceData.paperLessBillingVerbiage1")) && Objects.equals(paperLessBillingVerbiage2, getValueFromUIJSON(environment,"billingDeliveryPreferenceData.paperLessBillingVerbiage2")), "verbiage not verified");
        ExtentSparkReport.getExtentLogger().info("Verbiage verified for Paperless billing.");
    }

    public void verifyVerbiagePaperBilling(){
        String paperBillVerbiage = getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("paperBillVerbiage"));
        Assert.assertEquals(getValueFromUIJSON(environment,"billingDeliveryPreferenceData.paperBillVerbiage"), paperBillVerbiage, "verbiage not verified");
        ExtentSparkReport.getExtentLogger().info("Verbiage verified for Paper billing.");
    }

    public void changeEmail() throws InterruptedException {
        String email;
        performJSClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailBtn"));
        boolean status = isElementDisplayed(getElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailHeader")));
        Assert.assertTrue(status, "Profile email Address popup not displayed");
        String currentEmail = getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("currentEmailAddress")));
        assert currentEmail != null;
        if(currentEmail.contains("nandha")){
            email = getValueFromUIJSON(environment,"testDataRegistrationPage.email2");
        } else if (currentEmail.contains("balwa")) {
            email = getValueFromUIJSON(environment,"testDataRegistrationPage.email1");
        }
        else{
            email = getValueFromUIJSON(environment,"testDataRegistrationPage.email1");
        }
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"), email);
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("confirmEmailInput"), email);
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"), false);
        Thread.sleep(9000);
        String existingEmail = getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("existingEmailAddress")));
        assert existingEmail != null;
        assert email != null;
        Assert.assertEquals(getValueFromUIJSON(environment,"billingDeliveryPreferenceData.emailUpdate"), getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("successMsg"))), "email updated msg not displayed");
        Assert.assertEquals(email, existingEmail, "email not updated");
        ExtentSparkReport.getExtentLogger().info("Verified the email updation in billing preference.");
    }

    public void verifyBillingPreference() throws InterruptedException {
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("paperlessBillInput"), false);
        scrollDown(650);
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("cardSaveBtn"), false);
        Thread.sleep(6000);
        Assert.assertEquals(getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("successMsg")), getValueFromUIJSON(environment,"billingDeliveryPreferenceData.paperLessBillSuccessMsg"), "banner not showed");
        ExtentSparkReport.getExtentLogger().info("Verbiage verified for Paper billing.");
        String val = getAttribute(billingDeliveryPreferencePageProperties.getProperty("paperLessBillRadioBtn"), "checked");
        if(val == null){
            val = "false";
        }
        boolean status = Boolean.parseBoolean(val);
        Assert.assertTrue(status, "radio btn not selected");
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("paperBillInput"), false);
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("cardSaveBtn"), false);
        Assert.assertEquals(getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("successMsg")), getValueFromUIJSON(environment,"billingDeliveryPreferenceData.paperLessBillSuccessMsg"), "banner not showed");
        status = Boolean.parseBoolean(getAttribute(billingDeliveryPreferencePageProperties.getProperty("paperLessBillRadioBtn"), "checked"));
        Assert.assertTrue(status, "radio btn not selected");
        ExtentSparkReport.getExtentLogger().info("Radio button selected in billing preference.");
    }

    public void verifySubmitButtonWithoutChangingPreference() throws InterruptedException {
        scrollDown(600);
        performJSClickElement(billingDeliveryPreferencePageProperties.getProperty("cardSaveBtn"));
        Thread.sleep(2000);

        Assert.assertEquals(getTextOfElement(billingDeliveryPreferencePageProperties.getProperty("successMsg")), getValueFromUIJSON(environment,"billingDeliveryPreferenceData.withoutChangesErrorMsg"), "banner not showed");
        ExtentSparkReport.getExtentLogger().info("No change error message verified.");
    }

    public void verifyProfileEmailAddressPopup() throws InterruptedException {
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailBtn"));
        boolean status = isElementDisplayed(getElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailHeader")));
        Assert.assertTrue(status, "Profile email Address popup not displayed");
        ExtentSparkReport.getExtentLogger().info("Verified the profile email address.");
        Thread.sleep(2000);
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("closeBtn"));
    }

    public void verifyCurrentEmailAddress() throws InterruptedException {
        Thread.sleep(2000);
        String existingEmail = getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("existingEmailAddress")));
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailBtn"));
        boolean status = isElementDisplayed(getElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailHeader")));
        Assert.assertTrue(status, "Profile email Address popup not displayed");
        String currentEmail = getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("currentEmailAddress")));
        Assert.assertEquals(existingEmail, currentEmail, "current email address not verified");
        ExtentSparkReport.getExtentLogger().info("Verified the current email address message.");

    }

    public void verifyEmailRequiredField() throws InterruptedException {
        clickOnElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"), false);
        Thread.sleep(2000);
        Assert.assertEquals(getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("emailRequiredError"))),
                getValueFromUIJSON(environment,"errorMessagesRegistrationPage.errorMessage"), "required field not verified");
        ExtentSparkReport.getExtentLogger().info("Verified the required field for email in billing preference page.");
    }

    public void verifyCharLimitOfEmailAddress(){
        performJSClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailBtn"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"), getValueFromUIJSON(environment,"billingDeliveryPreferenceData.email52Chars"));
        WebElement emailValue = getElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"));
        ExtentSparkReport.getExtentLogger().info("Extracting the entered Characters from UI");
        assert emailValue != null;
        String enteredValue = emailValue.getAttribute("value");
        System.out.println(enteredValue.length());
        ExtentSparkReport.getExtentLogger().info("Matching the value with Allowed Characters.");
        if (enteredValue.length() == 50) {
            Assert.assertTrue(true);
            ExtentSparkReport.getExtentLogger().info("Successfully Verified - Entered Characters are 50");
            emailValue.clear();
        } else {
            Assert.fail("Validation failed: Entered more than 50 Characters");
        }

    }

    public void invalidEmailAddress() {
        clear(billingDeliveryPreferencePageProperties.getProperty("emailInput"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"), getValueFromUIJSON(environment,"testDataRegistrationPage.invalidEmail"));
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"));
        Assert.assertEquals(getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("emailRequiredError"))),
                getValueFromUIJSON(environment,"billingDeliveryPreferenceData.invalidEmailFormatError"), "invalid email format not verified");
        ExtentSparkReport.getExtentLogger().info("Verified the invalid email error message.");
        System.out.println("Invalid email address verified");
    }

    public void verifyConfirmEmailRequiredField() {
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"));
        Assert.assertEquals(getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("confirmEmailRequiredError"))),
                getValueFromUIJSON(environment,"errorMessagesRegistrationPage.errorMessage"), "required field not verified");
        ExtentSparkReport.getExtentLogger().info("verified the confirmation email required message.");
        System.out.println("Required Fields Verified");
    }

    public void verifyEmailMismatch() {
        clear(billingDeliveryPreferencePageProperties.getProperty("emailInput"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"), getValueFromUIJSON(environment,"billingDeliveryPreferenceData.invalidFormatEmail"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("confirmEmailInput"), getValueFromUIJSON(environment,"testDataRegistrationPage.email1"));
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"));
        Assert.assertEquals(getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("confirmEmailRequiredError"))),
                getValueFromUIJSON(environment,"errorMessagesRegistrationPage.invalidConfirmEmailCombination"), "confirmation error not verified");
        ExtentSparkReport.getExtentLogger().info("Verified the email mismatch error message");
        System.out.println("Email mismatch verified");
    }

    public void verifyWithExistingEmail() {
        String currentEmail = getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("currentEmailAddress")));
        clear(billingDeliveryPreferencePageProperties.getProperty("emailInput"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("emailInput"), currentEmail);
        clear(billingDeliveryPreferencePageProperties.getProperty("confirmEmailInput"));
        sendKeysToElement(billingDeliveryPreferencePageProperties.getProperty("confirmEmailInput"), currentEmail);
        performActionClickElement(billingDeliveryPreferencePageProperties.getProperty("changeEmailSaveBtn"));
        Assert.assertEquals(getTextOfElements(getElement(billingDeliveryPreferencePageProperties.getProperty("emailErrorMsgContainer"))),
                getValueFromUIJSON(environment,"billingDeliveryPreferenceData.noChangesError"), "confirmation error not verified");
        ExtentSparkReport.getExtentLogger().info("Confirmation meaasge verified using existing mail.");
        System.out.println("Conformation  message verified");
    }

    public static void clickOnProfileMenu()
    {
        scrollDown(1000);
        performJSClickElement(billingDeliveryPreferencePageProperties.getProperty("profileMenu"));
    }



}
