package org.engage.POM;

import com.aventstack.extentreports.Status;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.engage.Database.CISQueries;
import org.engage.Database.DatabaseConnection;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Database.DatabaseOperations.executeQueryAndGetRows;

public class HomePagePOM {
    public static Properties homePageProperties;

    public static List<String> environment;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public HomePagePOM(List<String> environment) {
        HomePagePOM.environment = environment;
        homePageProperties = BaseUtilities.readPropertyfile(FilePaths.HOME_PAGE_PROPERTIES);
    }
    public static void clickOnPopUpRemindMeLater() {
        try {
            assertURL(getValueFromUIJSON(environment, "assertURLs.accountSummaryURL"));
            System.out.println("URL Asserted Successfully.");
            WebElement remindMeLaterButton = getElement(homePageProperties.getProperty("alertRemindMeLater"));

            if (remindMeLaterButton != null && verifyElementIsClickable(remindMeLaterButton)) {
                remindMeLaterButton.click();
                System.out.println("Clicked on Pop-up");

                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
                wait.until(ExpectedConditions.stalenessOf(remindMeLaterButton));
            }
        } catch (TimeoutException | NoSuchElementException | ElementNotInteractableException e) {
            System.out.println("Pop-up not found or not clickable or element is stale now and has been removed from the Dom: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    public static void validateSideBarMenus() {
        try {
            // Validate Account Summary
            assertElementText(homePageProperties.getProperty("menuAccountSummary"), homePageProperties.getProperty("accountSummaryText"));
        } catch (TimeoutException e) {
            System.out.println("Account Summary Menu is not present on UI or not configured on client");
        }

        try {
            // Validate Billing Menu
            assertElementText(homePageProperties.getProperty("menuBilling"), homePageProperties.getProperty("billingText"));
        } catch (TimeoutException e) {
            System.out.println("Billing Menu is not present on UI or not configured on client");
        }

        try {
            // Validate Usage Insight Menu
            assertElementText(homePageProperties.getProperty("menuUsageInsights"), homePageProperties.getProperty("usageInsightText"));
        } catch (TimeoutException e) {
            System.out.println("Usage Insights Menu is not present on UI or not configured on client");
        }

        try {
            // Validate Payment Menu
            assertElementText(homePageProperties.getProperty("menuPayments"), homePageProperties.getProperty("paymentText"));
        } catch (TimeoutException e) {
            System.out.println("Payments Menu is not present on UI or not configured on client");
        }
    }
    public static void uploadDocument(String filePath) {
        try {
            clickOnElement(homePageProperties.getProperty("menuDocument"), false);
            clickOnElement(homePageProperties.getProperty("uploadDocument"), false);
            clickOnElement(homePageProperties.getProperty("selectCategory"), false);
            clickOnElement(homePageProperties.getProperty("selectDocument"), false);
            sendKeysToElement(homePageProperties.getProperty("selectFile"), FilePaths.TEST_RESOURCE_PATH + filePath);
        } catch (TimeoutException e) {
            System.out.println("Documents Menu is not present on UI or not configured on client");
        }
        try {
            clickOnElement(homePageProperties.getProperty("addButton"), false);

            WebElement messageElement = clickOnElement(homePageProperties.getProperty("messageText"), false);

            if (messageElement.isDisplayed()) {
                System.out.println("Popup is present in Upload Documents");
                assertElementText(homePageProperties.getProperty("messageText"), homePageProperties.getProperty("fileUploaded"));
                clickOnElement(homePageProperties.getProperty("closePopUp"), false);
            }
        } catch (TimeoutException e) {
            System.out.println("PopUp is not present in upload Documents");
            try {
                assertElementText(homePageProperties.getProperty("fileUploadMessage"), homePageProperties.getProperty("fileUploadedMessage"));
            } catch (TimeoutException a) {
                System.out.println("Assertion Element not found");
            }
        }
    }
    public void openBillingMenuAndValidate() throws ParseException {

        try {
            performActionClickElement(homePageProperties.getProperty("menuBilling"));
        } catch (TimeoutException e) {
            System.out.println("Billing Menu is not visible or not configured on this client");
        }

        try {
            performActionClickElement(homePageProperties.getProperty("billingUsageHistory"));
            WebElement abc = isElementVisible(homePageProperties.getProperty("headerBillingUsageHistory"));
            System.out.println(abc);
        } catch (TimeoutException e) {
            System.out.println("Billing Usage History Menu is not visible or not configured on this client");
        }


        String startDate = Objects.requireNonNull(getElement(homePageProperties.getProperty("billingStartDate"))).getAttribute("data-min-date");

        System.out.println(startDate);
        String startText = getTextOfElement((homePageProperties.getProperty("irsTextFrom")));
        System.out.println(startText);
        String startMonth = convertDateToMonth("yyyy-MM-dd", startDate);
        System.out.println("Start Month: " + startMonth);
        Assert.assertEquals(startMonth, startText);

        String endDate = Objects.requireNonNull(getElement(homePageProperties.getProperty("billingEndDate"))).getAttribute("data-max-date");

        System.out.println(endDate);
        String endText = getTextOfElement((homePageProperties.getProperty("irsTextTo")));
        System.out.println(endText);
        String endMonth = convertDateToMonth("yyyy-MM-dd", endDate);
        System.out.println("End Month: " + endMonth);
        Assert.assertEquals(endMonth, endText);

        dragAndDrop(getElement(homePageProperties.getProperty("irsFrom")), 100);
        String afterDropFrom = getTextOfElement((homePageProperties.getProperty("irsTextFrom")));
        System.out.println(afterDropFrom);
        String afterStartDropDate = getElementValueJS(getElement(homePageProperties.getProperty("billingStartDate")));
        System.out.println(afterStartDropDate);
        String afterStartDropMonth = convertDateToMonth("MM/dd/yyyy", afterStartDropDate);
        System.out.println(afterStartDropMonth);
        Assert.assertEquals(afterStartDropMonth, afterDropFrom);

        assert startText != null;
        if (!startText.equals(afterDropFrom)) {
            System.out.println("Slider moved successfully.");
        } else {
            System.out.println("Slider did not move.");
        }

    }


    public static void openUsageInsightsMenuAndValidate() {

        try {

            performActionClickElement(homePageProperties.getProperty("menuUsageInsights"));
            //clickOnElement(homePageProperties.getProperty("menuUsageInsights"));
        } catch (TimeoutException e) {
            System.out.println("Usage Insights Menu is not visible or not configured on this client");
        }

        try {
            //scrollDown(500);
            performActionClickElement(homePageProperties.getProperty("understandUsage"));
            //clickOnElement(homePageProperties.getProperty("understandUsage"));
            WebElement abc = isElementVisible(homePageProperties.getProperty("usageIntervals"));
            System.out.println(abc);
            performActionClickElement(homePageProperties.getProperty("menuUsageInsights"));
        } catch (TimeoutException e) {
            System.out.println("Understand Usage Menu is not visible or not configured on this client");
        }

    }

    public static void openPaymentsMenuAndValidate() {
        try {
            performActionClickElement(homePageProperties.getProperty("menuPayments"));
            //clickOnElement(homePageProperties.getProperty("menuPayments"));
        } catch (TimeoutException e) {
            System.out.println("Payment Menu is not visible or not configured on this client");
        }

        try {
            performActionClickElement(homePageProperties.getProperty("makePayment"));
            //clickOnElement(homePageProperties.getProperty("makePayment"));
            WebElement abc = isElementVisible(homePageProperties.getProperty("selectAccount"));
            System.out.println(abc);
            performActionClickElement(homePageProperties.getProperty("menuPayments"));
        } catch (TimeoutException e) {
            System.out.println("Make a Payment Menu is not visible or not configured on this client");
        }

    }

    public static void openProductsAddonsMenuAndValidate() {
        try {
            performActionClickElement(homePageProperties.getProperty("productsAddons"));
            //clickOnElement(homePageProperties.getProperty("productsAddons"));

        } catch (TimeoutException e) {
            System.out.println("Products & Addons Menu is not visible or not configured on this client");
        }
        //scrollDown(500);
        try {

            performActionClickElement(homePageProperties.getProperty("orderHistory"));
            //clickOnElement(homePageProperties.getProperty("orderHistory"));
            String abc = getTitle();
            assertStringElementText(abc, homePageProperties.getProperty("orderHistoryTitle"));
            performActionClickElement(homePageProperties.getProperty("productsAddons"));
        } catch (TimeoutException e) {
            System.out.println("Order History Menu is not visible or not configured on this client");
        }

    }

    public void clickOnProfileLnk() {
        try {
            clickOnElement(homePageProperties.getProperty("profileLnk"), false);
        } catch (Exception e) {
            System.out.println("Element not found");
        }
    }

    public void clickOnProfileMenu(String locator) {
        try {
            clickOnElement(locator, false);
        } catch (Exception e) {
            System.out.println("Element not found");
        }
    }

    public void selectAccountLeftMenuNav() throws InterruptedException {
        selectAccount("billingAndHistoryUsagePageData.account");
        Thread.sleep(2000);
    }

    public void selectAccount(String acc) throws InterruptedException {
        clickOnElement(homePageProperties.getProperty("accDropDown"), false);
        Thread.sleep(2000);
        clickOnElement(homePageProperties.getProperty("accOptions").replaceAll("accNo", getValueFromUIJSON(environment, acc)), false);
    }

    public void verifyCompanyLogoHeader() {
        boolean element = isElementDisplayed(getElement(homePageProperties.getProperty("companyLogo")));
        System.out.println(element + " The Company Logo is displayed");

    }

    public void verifyIconHeaders() {
        boolean element = isElementDisplayed(getElement(homePageProperties.getProperty("languageIconDropDown")));
        System.out.println(element + " The Language Icon is displayed");
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("supportIconDropdown")));
        System.out.println(element1 + " The Support Icon is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("notificationsIconDropDown")));
        System.out.println(element2 + " The Notification Icon is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("profileButton")));
        System.out.println(element3 + " The Profile Icon is displayed");

    }

    public void verifyChangeLanguage() throws InterruptedException {
        try {

            ExtentSparkReport.getExtentLogger().info("Verifying Home page Header Icon ");
            performJSClickElement(homePageProperties.getProperty("languageIconDropDown"));
            selectLanguage(getValueFromUIJSON(environment, "homePageValidationData.language"));
            Thread.sleep(2000);

            boolean status = isElementDisplayed(getElement(homePageProperties.getProperty("langPopupText")));

            if(status){
                String actText = getTextOfElement(homePageProperties.getProperty("langPopupText"));
                assert actText != null;
                assertThat(actText, getValueFromUIJSON(environment, "homePageValidationData.changeLanguageModalTxt2"), "pop up text verified", "popup text not matched with expected");
                Thread.sleep(2000);
                String actText1 = getTextOfElement(homePageProperties.getProperty("changeLanguageText1"));
                assert actText1 != null;
                assertThat(actText1, getValueFromUIJSON(environment, "homePageValidationData.changeLanguageModalTxt1"), "pop up text verified", "popup text not matched with expected");
                clickOnElement(homePageProperties.getProperty("cancelBtn"), false);
                performJSClickElement(homePageProperties.getProperty("languageIconDropDown"));
                selectLanguage(getValueFromUIJSON(environment, "homePageValidationData.language"));
                clickOnElement(homePageProperties.getProperty("proceedBtn"), false);
            }else{
                ExtentSparkReport.getExtentLogger().info("popup not displayed");
            }

            Thread.sleep(2000);
            performJSClickElement(homePageProperties.getProperty("languageIconDropDown"));
            selectLanguage(getValueFromUIJSON(environment, "homePageValidationData.language1"));
            if(status){
                clickOnElement(homePageProperties.getProperty("proceedBtnFilipino"), false);
            }
            Thread.sleep(2000);
        }catch (TimeoutException e){
            performJSClickElement(homePageProperties.getProperty("languageIconDropDown"));
            selectLanguage(getValueFromUIJSON(environment,"homePageValidationData.language1"));
        }


    }

    public void selectLanguage(String lang) {
        clickOnElement(homePageProperties.getProperty("language").replaceAll("lang", lang), false);
    }

    public void verifySupportIcon() {
        clickOnElement(homePageProperties.getProperty("supportIcon"), false);
        boolean status = isElementDisplayed(getElement(homePageProperties.getProperty("faqLnk")));
        assertThat(status, "FAQ displayed", "FAQ not displayed");
    }

    public void verifyFAQ() {
        String parent = getDriver().getWindowHandle();
        clickOnElement(homePageProperties.getProperty("faqLnk"), false);
        switchToNewTab();
        boolean status = isElementDisplayed(getElement(homePageProperties.getProperty("softwareSol")));
        assertThat(status, "navigated to FAQ", "not navigated to FAQ");
        switchToParentTab(parent);
    }

    public void verifyMsgIcon() {
        clickOnElement(homePageProperties.getProperty("msgIcon"), false);
        boolean status = isElementDisplayed(getElement(homePageProperties.getProperty("inboxLnk")));
        assertThat(status, "inbox displayed", "inbox not displayed");
    }

    public void verifyAccount() {
        clickOnElement(homePageProperties.getProperty("accountIcon"), false);
        String actUser = getTextOfElement(homePageProperties.getProperty("userName"));
        assert actUser != null;
        assertThat(actUser.equals(getValueFromUIJSON(environment, "validCredentials[0].username")), "user name matched with expected", "user name not matched with expected");

    }

    public void verifyLinks() {
        clickOnElement(homePageProperties.getProperty("contactInfoLnk"), false);
        String actHeader = getTextOfElement(homePageProperties.getProperty("header"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, "homePageValidationData.contactInformationText"), "page navigated", "page not navigated");
        navigateToBack();
        clickOnElement(homePageProperties.getProperty("accountIcon"), false);
        clickOnElement(homePageProperties.getProperty("accountSettingsLnk"), false);
        actHeader = getTextOfElement(homePageProperties.getProperty("header"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, "homePageValidationData.accountSettingText"), "page navigated", "page not navigated");
        navigateToBack();
        clickOnElement(homePageProperties.getProperty("accountIcon"), false);
        clickOnElement(homePageProperties.getProperty("logoutLnk"), false);
        actHeader = getTextOfElement(homePageProperties.getProperty("userNameText"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, "homePageValidationData.userNameText"), "page navigated", "page not navigated");

    }

    public void FooterLogoAndAboutSectionVerification() throws InterruptedException {
        scrollDown(2000);
        boolean status = isElementDisplayed(getElement(homePageProperties.getProperty("footerLogo")));
        assertThat(status, "footerLogo displayed", "footerLogo not displayed");
        status = isElementDisplayed(getElement(homePageProperties.getProperty("aboutSection")));
        assertThat(status, "about displayed", "about not displayed");
        status = isElementDisplayed(getElement(homePageProperties.getProperty("supportSection")));
        assertThat(status, "supportSection displayed", "supportSection not displayed");
        status = isElementDisplayed(getElement(homePageProperties.getProperty("socialMediaSection")));
        assertThat(status, "socialMediaSection displayed", "socialMediaSection not displayed");
    }

    public void verifyAboutSectionLinks() {
        aboutUsLink();
        projectInYourAreaLink();
        privacyPolicyLink();
        termsOfUseLink();
    }
    public void verifySupportSectionLinks(){
        frequentlyAskedQuestions();
        sendAnEmailLink();
        fileCompliantLink();
    }

    public void verifySocialMediaSectionLink() {
        faceBookPageLink();
        twitterLink();
        linkedinPageLink();
        youtubePageLink();

    }

    public void validateHomePageLinks(String locator, String expectedData) {
        scrollDown(2000);
        clickOnElement(homePageProperties.getProperty(locator), false);
        String parent = getDriver().getWindowHandle();
        switchToNewTab();
        assertThatContains(getCurrentUrl(), (getValueFromUIJSON(environment, expectedData)), "Actual URL "+ getCurrentUrl() +" matched with expected URL " + expectedData, "Actual URL "+ getCurrentUrl() +" not matched with expected URL " + expectedData);
//        assertURL(getValueFromUIJSON(environment, expectedData));
        switchToParentTabAndCloseChildTab(parent);
    }

    public void aboutUsLink() {
        validateHomePageLinks("aboutUsLink", "homePageValidationData.aboutUsURL");
    }

    public void projectInYourAreaLink() {
        validateHomePageLinks("projectInYourAreaLink", "homePageValidationData.projectInYourArea");
    }

    public void privacyPolicyLink() {
        validateHomePageLinks("privacyPolicyLink", "homePageValidationData.privacyPolicyUrl");
    }

    public void termsOfUseLink() {
        validateHomePageLinks("termsOfUseLink", "homePageValidationData.termsOfUse");
    }

    public void frequentlyAskedQuestions() {
        validateHomePageLinks("frequentlyAskedQuestionsLink", "homePageValidationData.frequentlyAskedQuestionsLink");
    }

    public void sendAnEmailLink() {
        validateHomePageLinks("sendAnEmailLink", "homePageValidationData.sendAnEmailLink");
    }

    public void fileCompliantLink() {
        validateHomePageLinks("fileCompliantLink", "homePageValidationData.fileCompliantLink");
    }

    public void faceBookPageLink() {
        validateHomePageLinks("facebookLink", "homePageValidationData.facebookLink");
    }

    public void twitterLink() {
        validateHomePageLinks("twitterLink", "homePageValidationData.twitterLink");
    }

    public void linkedinPageLink() {
        validateHomePageLinks("linkedinLink", "homePageValidationData.linkedinLink");
    }

    public void youtubePageLink() {
        validateHomePageLinks("youtubeLink", "homePageValidationData.youtubeLink");
    }
    public void verifyInformationCenterCard(){
        boolean element1 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard1")));
        System.out.println(element1 +" The Information Card-1 is displayed");
        boolean element2 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard2")));
        System.out.println(element2 +" The Information Card-2 is displayed");
        boolean element3 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard3")));
        System.out.println(element3  +" The Information Card-3 is displayed");
        boolean element4 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard4")));
        System.out.println(element4  +" The Information Card-4 is displayed");
        boolean element5 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard5")));
        System.out.println(element5 +" The Information Card-5 is displayed");
        boolean element6 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard6")));
        System.out.println(element6 +" The Information Card-6 is displayed");
        boolean element7 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard7")));
        System.out.println(element7  +" The Information Card-7 is displayed");
        boolean element8 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterCard8")));
        System.out.println(element8  +" The Information Card-8 is displayed");
        performJSClickElement(homePageProperties.getProperty("informationCenterCard7"));
        String expectedURL = getValueFromUIJSON(environment,"homePageValidationData.vxSmartLink");
        String currentURL = getCurrentUrl();
        currentURL.contains(expectedURL);

    }

    public void accountSummaryPageCardVerification() throws InterruptedException {
        performJSClickElement(homePageProperties.getProperty("iWantToDropDown"));

        boolean element =  isElementDisplayed(getElement(homePageProperties.getProperty("menuAccountSummary")));
        System.out.println(element +" The Account Summary Card is displayed");
        boolean element1 =  isElementDisplayed(getElement(homePageProperties.getProperty("billingTxt")));
        System.out.println(element1 +" The Billing Card is displayed");
        boolean element2 =  isElementDisplayed(getElement(homePageProperties.getProperty("servicesTxt")));
        System.out.println(element2 +" The Services Card is displayed");
        boolean element3 =  isElementDisplayed(getElement(homePageProperties.getProperty("compareUsageTxt")));
        System.out.println(element3  +" The Compare Usage Casd is displayed");
        boolean element4 =  isElementDisplayed(getElement(homePageProperties.getProperty("waterUsageTxt")));
        System.out.println(element4  +" The Water Usage Card is displayed");
        boolean element5 =  isElementDisplayed(getElement(homePageProperties.getProperty("useDetailsTxt")));
        System.out.println(element5 +" The Use Details Card is displayed");
        boolean element6 =  isElementDisplayed(getElement(homePageProperties.getProperty("informationCenterTxt")));
        System.out.println(element6 +" The Information Center Card is displayed");
        boolean element7 =  isElementDisplayed(getElement(homePageProperties.getProperty("selectProgramsTxt")));
        System.out.println(element7  +" The Select Program Option Card is displayed");

    }

    public void paperlessBillingInActive() {

        performJSClickElement(homePageProperties.getProperty("setUpPaperlessBillingBtn"));
        String actHeader = getTextOfElement(homePageProperties.getProperty("headerTitle"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, "homePageValidationData.header"), "header matched with expected", "header not matched with expected");


    }

    public void verifyBillingSubMenu() throws InterruptedException {
        Thread.sleep(2000);
        performJSClickElement(homePageProperties.getProperty("billingOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("billingOption")));
        System.out.println(element1 + " The Billing Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("billingAndUsageOption")));
        System.out.println(element2 + " The Services Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("submitMeterReadingOption")));
        System.out.println(element3 + " The Compare Usage Casd is displayed");
        boolean element4 = isElementDisplayed(getElement(homePageProperties.getProperty("waterBudgetEstimatorOption")));
        System.out.println(element4 + " The Water Usage Card is displayed");
        boolean element5 = isElementDisplayed(getElement(homePageProperties.getProperty("billingFormsOption")));
        System.out.println(element5 + " The Water Usage Card is displayed");
        performJSClickElement(homePageProperties.getProperty("billingFormsOption"));
        navigateToBack();// text used in xpath  change it
//        assertThat(homePageProperties.getProperty("cardHeader"),getValueFromUIJSON(environment,"homePageValidationData.onlineText"),"Online Forms Text is validated","Online Forms Text is not validated");

    }

    public void validateBillinngSubMenuOptions() {
        performJSClickElement(homePageProperties.getProperty("billingOption"));
        performJSClickElement(homePageProperties.getProperty("billingAndUsageOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.header"), "Billing and usage history page verified", "Billing and usage history page not verified");
        performJSClickElement(homePageProperties.getProperty("submitMeterReadingOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.submitMeterTxt"), "Submit Meter page verified", "Submit Meter page not verified");
        performJSClickElement(homePageProperties.getProperty("waterBudgetEstimatorOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.wateBudgetTxt"), "Water Budget page verified", "Water Budget page not verified");
        performJSClickElement(homePageProperties.getProperty("billingFormsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("onlineFormsHeader"))), getValueFromUIJSON(environment, "homePageValidationData.billingFormsTxt"), "Billing Forms page verified", "Billing Forms page not verified");

    }

    public void verifyUsageInsightSubMenu() {
        performJSClickElement(homePageProperties.getProperty("usageInsightOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("usageInsightOption")));
        System.out.println(element1 + " The Usage Insight Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("understandUsageOption")));
        System.out.println(element2 + " The Understand Usage Option Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("leakHistoryOption")));
        System.out.println(element3 + " The Leak History Card is displayed");
        boolean element4 = isElementDisplayed(getElement(homePageProperties.getProperty("waysToSaveOption")));
        System.out.println(element4 + " The Ways To Save Water Card is displayed");
        boolean element5 = isElementDisplayed(getElement(homePageProperties.getProperty("houseHoldUsageProfit")));
        System.out.println(element5 + " The Household Usage Profit Card is displayed");

    }
    public void validateUsageInsightSubMenuOption(){
//        performJSClickElement(homePageProperties.getProperty("usageInsightOption"));
        performJSClickElement(homePageProperties.getProperty("understandUsageOption"));
//        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))),getValueFromUIJSON(environment,"homePageValidationData.usageUnderstandText"),"Understand usage page verified","Understand usage page not verified");
        performJSClickElement(homePageProperties.getProperty("leakHistoryOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.leakHistoryTxt"), "Leak History page verified", "Leak History page not verified");
        performJSClickElement(homePageProperties.getProperty("waysToSaveOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.waysToSaveWaterTxt"), "Ways to save water page verified", "Ways to save water page not verified");
        performJSClickElement(homePageProperties.getProperty("houseHoldUsageProfit"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.houseHoldUsage"), "Household Usage page verified", "Household Usage  page not verified");
    }

    public void verifyPaymentSubMenu() {
        performJSClickElement(homePageProperties.getProperty("paymentsOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("makePaymentOption")));
        assertThat(element1, "Make Payment Option is verified", "Make Payment Option is not verified");
        System.out.println(element1 + " The Make payment Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("automaticPaymentsOption")));
        assertThat(element2, "Automatic Payment Option is verified", "Automatic Payment Option is not verified");
        System.out.println(element2 + " The Automatic Payment Option Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("paymentMethodOption")));
        assertThat(element3, "Automatic Method Option is verified", "Automatic Method Option is not verified");
        System.out.println(element3 + " The Payment method Card is displayed");
        boolean element4 = isElementDisplayed(getElement(homePageProperties.getProperty("paymentOption")));
        assertThat(element4, " Payment Option is verified", " Payment Option is not verified");
        System.out.println(element4 + " The Payment Card is displayed");
        boolean element5 = isElementDisplayed(getElement(homePageProperties.getProperty("paymentHistoryOption")));
        assertThat(element5, "Payment History Option is verified", "Payment History Option is not verified");
        System.out.println(element5 + " The Payment History Card is displayed");
        boolean element6 = isElementDisplayed(getElement(homePageProperties.getProperty("scheduledPaymentsOption")));
        assertThat(element6, "Scheduled Payment Option is verified", "Scheduled Payment Option is not verified");
        System.out.println(element6 + " The Scheduled Payments Card is displayed");
        boolean element7 = isElementDisplayed(getElement(homePageProperties.getProperty("ledgerHistoryOption")));
        assertThat(element7, "Ledger History Option is verified", "Ledger History Option is not verified");
        System.out.println(element7 + " The Ledger History Card is displayed");
        boolean element8 = isElementDisplayed(getElement(homePageProperties.getProperty("paymentArrangementOption")));
        assertThat(element8, "Payment Arrangement Option is verified", "Payment Arrangement Option is not verified");
        System.out.println(element8 + " The Payment Arrangement Card is displayed");
        boolean element9 = isElementDisplayed(getElement(homePageProperties.getProperty("paymentExtensionOption")));
        assertThat(element5, "Payment Extension Option is verified", "Payment History Option is not verified");
        System.out.println(element9 + " The Payment Extension Card is displayed");

    }
    public void validatePaymentSubMenuOptions(){
//        performJSClickElement(homePageProperties.getProperty("paymentsOption"));
        performJSClickElement(homePageProperties.getProperty("makePaymentOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.makePaymentTxt"), "Make Payment page verified", "Make Payment page not verified");
        performJSClickElement(homePageProperties.getProperty("automaticPaymentsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.autoPaymentTxt"), "Auto Payment page verified", "Auto Payment page not verified");
        performJSClickElement(homePageProperties.getProperty("paymentMethodOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.manageWalletTxt"), "Manage Wallet page verified", "Manage Wallet page not verified");
        performJSClickElement(homePageProperties.getProperty("paymentOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.paymentOptionTxt"), "Payment Option page verified", "Payment Option  page not verified");
        performJSClickElement(homePageProperties.getProperty("paymentHistoryOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.paymentHistoryTxt"), "Payment History page verified", "Payment History page not verified");
        performJSClickElement(homePageProperties.getProperty("scheduledPaymentsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.scheduledPaymentsTxt"), "Scheduled Payments page verified", "Scheduled Payments page not verified");
        performJSClickElement(homePageProperties.getProperty("ledgerHistoryOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.ledgerHistoryTxt"), "Ledger History page verified", "Ledger History page not verified");
        performJSClickElement(homePageProperties.getProperty("paymentArrangementOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.paymentArrangementTxt"), "Payment Arrangement page verified", "Payment Arrangement  page not verified");
        performJSClickElement(homePageProperties.getProperty("paymentExtensionOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("paymentExtensionHeader"))), getValueFromUIJSON(environment, "homePageValidationData.paymentExtensionTxt"), "Payment Extension page verified", "Payment Extension  page not verified");
    }

    public void verifyServiceRequestSubMenu() {
        performJSClickElement(homePageProperties.getProperty("serviceRequestOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("startServiceOption")));
        assertThat(element1, "Start service Option is verified", "Start service Option is not verified");
        System.out.println(element1 + " The Start service Option Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("transferServiceOption")));
        assertThat(element2, "Transfer service Option is verified", "Transfer service Option is not verified");
        System.out.println(element2 + " The Transfer service Option Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("stopServiceOption")));
        assertThat(element3, "Stop service Option is verified", "Stop service Option is not verified");
        System.out.println(element3 + " The Stop service Card is displayed");
        boolean element4 = isElementDisplayed(getElement(homePageProperties.getProperty("serviceHistoryOption")));
        assertThat(element4, " Service History Option is verified", " Service History Option is not verified");
        System.out.println(element4 + " The Service History Card is displayed");
        boolean element5 = isElementDisplayed(getElement(homePageProperties.getProperty("letterOfCreditOption")));
        assertThat(element5, "Letter of credit Option is verified", "Letter of credit Option is not verified");
        System.out.println(element5 + " The Letter of credit Card is displayed");
        boolean element6 = isElementDisplayed(getElement(homePageProperties.getProperty("reportOutageOption")));
        assertThat(element6, "reportOutage Option is verified", "reportOutage Option is not verified");
        System.out.println(element6 + " The report Outage Option Card is displayed");
        boolean element7 = isElementDisplayed(getElement(homePageProperties.getProperty("serviceRequestFormsOption")));
        assertThat(element7, "Service Request form Option is verified", "Service Request form Option is not verified");
        System.out.println(element7 + " The Service Request formry Card is displayed");

    }
    public void validateServiceRequestSubMenuOptions(){
//        performJSClickElement(homePageProperties.getProperty("serviceRequestOption"));
        performJSClickElement(homePageProperties.getProperty("startServiceOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.startServiceTxt"), "Start service Option is verified", "Start service Option is not verified");
        performJSClickElement(homePageProperties.getProperty("transferServiceOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.transferServiceTxt"), "Transfer Service page verified", "Transfer Service page not verified");
        performJSClickElement(homePageProperties.getProperty("stopServiceOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.stopServiceTxt"), "Stop service page verified", "Stop service page not verified");
        performJSClickElement(homePageProperties.getProperty("serviceHistoryOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.serviceHistoryTxt"), "Service HIstory page verified", "Service HIstory page not verified");
        performJSClickElement(homePageProperties.getProperty("letterOfCreditOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.letterCreditTxt"), "Letter credit page verified", "Letter credit page not verified");
        performJSClickElement(homePageProperties.getProperty("reportOutageOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.reportOutageTxt"), "Report Outage page verified", "Report Outage  page not verified");
        performJSClickElement(homePageProperties.getProperty("serviceRequestFormsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("onlineFormsHeader"))), getValueFromUIJSON(environment, "homePageValidationData.serviceRequestFormsTxt"), "Service request forms page verified", "Service request forms not verified");
    }

    public void verifyDocumentSubMenu() {
        performJSClickElement(homePageProperties.getProperty("documentsOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("viewDocumentsOption")));
        assertThat(element1, "View Document Option is verified", "View Document Option is not verified");
        System.out.println(element1 + " The View Document Option Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("uploadDocumentsOption")));
        assertThat(element2, "Upload Documents Option is verified", "Upload Documents Option is not verified");
        System.out.println(element2 + " The Upload Documents Option Card is displayed");
    }

    public void validateDocumentSubMenuOptions() {
//        performJSClickElement(homePageProperties.getProperty("documentsOption"));
        performJSClickElement(homePageProperties.getProperty("viewDocumentsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.viewDocumentTxt"), "View Document page verified", "View Documentpage not verified");
        performJSClickElement(homePageProperties.getProperty("uploadDocumentsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("uploadDocumentHeader"))), getValueFromUIJSON(environment, "homePageValidationData.uploadDocumentTxt"), "Upload Document page verified", "Upload Document page not verified");
    }

    public void validateOutagesOption() {
        performJSClickElement(homePageProperties.getProperty("outagesOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.outagesTxt"), "Outages page verified", "Outages not verified");
    }

    public void verifyMessageSubMenu() {

        performJSClickElement(homePageProperties.getProperty("messageOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("inboxOption")));
        assertThat(element1, "Inbox Option is verified", "Inbox Option is not verified");
        System.out.println(element1 + " Inbox Option Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("sentItemsOption")));
        assertThat(element2, "Sent Items Option is verified", "Sent Items Option is not verified");
        System.out.println(element2 + " Sent Items Option Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("composeMessageOption")));
        assertThat(element3, "Compose Message Option is verified", "Compose message Option is not verified");
        System.out.println(element3 + " Compose Message Option Card is displayed");
    }

    public void validateMessageSubMenuOptions() {
//        performJSClickElement(homePageProperties.getProperty("messageOption"));
        performJSClickElement(homePageProperties.getProperty("inboxOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.inboxTxt"), "Inbox Option is verified", "Inbox Option is not verified");
        performJSClickElement(homePageProperties.getProperty("sentItemsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.sentItemsTxt"), "Sent Items Option is verified", "Sent Items Option is not verified");
        performJSClickElement(homePageProperties.getProperty("composeMessageOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.composeMessageTxt"), "Compose Message Option is verified", "Compose message Option is not verified");
    }

    public void validateOnlineFormsOption() {
        performJSClickElement(homePageProperties.getProperty("onlineFormsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("onlineFormsHeader"))), getValueFromUIJSON(environment, "homePageValidationData.serviceRequestFormsTxt"), "Online Forms page verified", "Online Forms not verified");
    }

    public void verifyProfileSubMenu() {
        performJSClickElement(homePageProperties.getProperty("profileOption"));
        boolean element1 = isElementDisplayed(getElement(homePageProperties.getProperty("contactInformationOption")));
        assertThat(element1, "Contact Information Option is verified", "Contact Information Option is not verified");
        System.out.println(element1 + " The contact Information Option Card is displayed");
        boolean element2 = isElementDisplayed(getElement(homePageProperties.getProperty("manageNotificationOption")));
        assertThat(element2, "Manage Notification Option is verified", "Manage Notification Option is not verified");
        System.out.println(element2 + " The Manage Notification Option Card is displayed");
        boolean element3 = isElementDisplayed(getElement(homePageProperties.getProperty("changePasswordOption")));
        assertThat(element3, "Change Password  is verified", "Change Password Option is not verified");
        System.out.println(element3 + " The Change Password  Card is displayed");
        boolean element4 = isElementDisplayed(getElement(homePageProperties.getProperty("billDeliveryPreferenceOption")));
        assertThat(element4, " Bill delivery preference Option is verified", " Bill delivery preference Option is not verified");
        System.out.println(element4 + " The Bill delivery preference Card is displayed");
        boolean element5 = isElementDisplayed(getElement(homePageProperties.getProperty("accountSettingOption")));
        assertThat(element5, "Account Setting Option is verified", "Account Setting Option is not verified");
        System.out.println(element5 + " The Account Setting Card is displayed");
        boolean element6 = isElementDisplayed(getElement(homePageProperties.getProperty("accountFormsOption")));
        assertThat(element6, "Account Forms Option is verified", "Account Forms Option is not verified");
        System.out.println(element6 + " The Account Forms Option Card is displayed");
    }
    public void validateProfileSubMenuOptions(){
//        performJSClickElement(homePageProperties.getProperty("profileOption"));
        performJSClickElement(homePageProperties.getProperty("contactInformationOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.contactInformationTxt"), "Contact Information Option is verified", "Contact Information Option is not verified");
        performJSClickElement(homePageProperties.getProperty("manageNotificationOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.manageNotificationTxt"), "Manage Notification Option is verified", "Manage Notification Option is not verified");
        performJSClickElement(homePageProperties.getProperty("changePasswordOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.changePasswordTxt"), "Change Password  is verified", "Change Password Option is not verified");
        performJSClickElement(homePageProperties.getProperty("billDeliveryPreferenceOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.billDeliverPrefTxt"), " Bill delivery preference Option is verified", " Bill delivery preference Option is not verified");
        performJSClickElement(homePageProperties.getProperty("accountSettingOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.accountSettingTxt"), "Account Setting Option is verified", "Account Setting Option is not verified");
        performJSClickElement(homePageProperties.getProperty("accountFormsOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("onlineFormsHeader"))), getValueFromUIJSON(environment, "homePageValidationData.serviceRequestFormsTxt"), "Account Forms Option is verified", "Account Forms Option is not verified");
    }

    public void verifyAccDetails() throws InterruptedException {
        String actName = getTextOfElement(homePageProperties.getProperty("accName"));
        assert actName != null;
        Thread.sleep(2000);
        assertThat(actName, getValueFromUIJSON(environment, "homePageValidationData.accountName"), "act data matched with exp data", "act data not matched with exp data");
        String actAddress = getTextOfElement(homePageProperties.getProperty("accAddress"));
        assert actAddress != null;
        assertThat(actAddress, getValueFromUIJSON(environment, "homePageValidationData.accountAddress"), "act data matched with exp data", "act data not matched with exp data");
    }

    public void verifyLinkOnWaterCard() {
        clickOnElement(homePageProperties.getProperty("accAddress"), false);
        String actText = getTextOfElement(homePageProperties.getProperty("waterScorePopUp"));
        String actCount = "";
        assert actText != null;
        String[] a1 = actText.split(";");
        for (String a : a1) {
            if (a.contains("waterUseTranslated")) {
                actCount = a.split("=")[1].replaceAll("[^0-9]", "");
                break;
            }
        }
        assertThat(actCount, getValueFromUIJSON(environment, ""), "count matched with expected", "count not matched with expected");
    }

    public void verifyInstallFaucetLnk() {
        clickOnElement(homePageProperties.getProperty("installFaucetLnk"), false);
        String actHeader = getTextOfElement(homePageProperties.getProperty("installFaucetHeader"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, ""), "navigated to install faucet screen", "not navigated to install faucet screen");
        navigateToBack();
    }

    public void verifyWaysToSaveBtn() {
        clickOnElement(homePageProperties.getProperty("waysToSaveBtn"), false);
        String actHeader = getTextOfElement(homePageProperties.getProperty("cardHeader"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, ""), "navigated to ways to save", "not navigated ways to save");
        navigateToBack();
    }
    public void verifyLogOutLink() throws InterruptedException {

        clickOnElement(homePageProperties.getProperty("accountIcon"), false);
        clickOnElement(homePageProperties.getProperty("logoutLnk"), false);
        Thread.sleep(3000);
        navigateToBack();
        Thread.sleep(3000);
        assertURL(getValueFromUIJSON(environment, "homePageValidationData.logOutValidationUrl"));
    }


    public void validateBillingCardData() {
        try {
            List<Map<String, String>> resultList = executeQueryAndGetRows(CISQueries.SELECT_LAST_NAME, DatabaseConnection.DatabaseType.CIS);
            Map<String, String> result = resultList.get(0);

            String lastName = result.get("MSLNM");
            String firstName = result.get("MSFNM");
            String name = firstName + " " + lastName;
            logger.log(Level.INFO, "Database Name: " + name);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Database Name: " + name);

            Thread.sleep(4000);
            String uiName = getTextOfElement(homePageProperties.getProperty("firstLastName"));
            logger.log(Level.INFO, "UI Name: " + uiName);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "UI Name: " + uiName);

            waitForTextToBeVisible(homePageProperties.getProperty("firstLastNameXpath"), name, 20);

            String amountValue = getTextOfElement(homePageProperties.getProperty("currentBillAmount"));
            logger.log(Level.INFO, "Current Bill Amount: " + amountValue);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Bill Amount: " + amountValue);

            String previousAmount = getTextOfElement(homePageProperties.getProperty("previousBillAmount"));
            logger.log(Level.INFO, "Previous Bill Amount: " + previousAmount);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Previous Bill Amount: " + previousAmount);

            String lastPaymentReceived = getTextOfElement(homePageProperties.getProperty("lastPayment"));
            logger.log(Level.INFO, "Last Payment Received: " + lastPaymentReceived);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Last Payment Received: " + lastPaymentReceived);

            List<Map<String, String>> resultSet = executeQueryAndGetRows(CISQueries.SELECT_AMOUNT_AND_DATE_FIELDS, DatabaseConnection.DatabaseType.CIS);
            Map<String, String> result1 = resultSet.get(0);

            String currentBillAmount = result1.get("BHSCUR");
            String previousBillAmount = result1.get("BHSPAS");
            String lastPayment = result1.get("BHSPAY");
            String lastPaymentValue = lastPayment.replace("-", "$");

            Assert.assertEquals("$" + currentBillAmount, amountValue);
            logger.log(Level.INFO, "Current Bill Amount Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Current Bill Amount Validation Passed");

            Assert.assertEquals("$" + previousBillAmount, previousAmount);
            logger.log(Level.INFO, "Previous Bill Amount Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Previous Bill Amount Validation Passed");

            Assert.assertEquals(lastPaymentValue, lastPaymentReceived);
            logger.log(Level.INFO, "Last Payment Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Last Payment Validation Passed");

            String billDate = result1.get("BHSBLD");
            String dbDate = convertDateToMonth("yyyyMMdd", "MMMM yyyy", billDate);
            String uiDate = getTextOfElement(homePageProperties.getProperty("billSummaryDate"));
            Assert.assertEquals("Bill - " + dbDate, uiDate);
            logger.log(Level.INFO, "Bill Date Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Bill Date Validation Passed");

            String dueDate = result1.get("BHSPDD");
            String dbDueDate = convertDateToMonth("yyyyMMdd", "MMM dd, yyyy", dueDate);
            String uiDueDate = getTextOfElement(homePageProperties.getProperty("dueDate"));
            Assert.assertEquals("Due Date: " + dbDueDate, uiDueDate);
            logger.log(Level.INFO, "Due Date Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Due Date Validation Passed");

            // Tooltip validation
            WebElement infoIcon = getElement(homePageProperties.getProperty("iIcon"));
            Actions actions = new Actions(getDriver());
            actions.moveToElement(infoIcon).click().perform();

            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(infoIcon, "aria-describedby"));

            String tooltipId = infoIcon.getAttribute("aria-describedby");
            WebElement tooltipNew= getElement(homePageProperties.getProperty("tooltipID").replace("[]",tooltipId));
            assert tooltipNew != null;
            String tooltipMessage = tooltipNew.getText();
            logger.log(Level.INFO, "Tooltip Message: " + tooltipMessage);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Tooltip Message: " + tooltipMessage);

            String expectedTooltipMessage = getValueFromUIJSON(environment, "homePageValidationData.toolTipMessage");
            Assert.assertEquals(tooltipMessage, expectedTooltipMessage);
            logger.log(Level.INFO, "Tooltip Message Validation Passed");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Tooltip Message Validation Passed");

            clickOnElement(homePageProperties.getProperty("viewBill"), false);
            logger.log(Level.INFO, "Clicked on View Bill");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on View Bill");

            Thread.sleep(2000);

            String text = getTextOfElement(homePageProperties.getProperty("viewBillText"));
            logger.log(Level.INFO, "View Bill Text: " + text);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "View Bill Text: " + text);
            System.out.println(text);
            Assert.assertEquals(text, getValueFromUIJSON(environment, "homePageValidationData.viewBills"));

            String viewBillBody = getTextOfElement(homePageProperties.getProperty("viewBillBody"));
            logger.log(Level.INFO, "View Bill Body: " + viewBillBody);
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "View Bill Body: " + viewBillBody);
            System.out.println(viewBillBody);
            Assert.assertEquals(viewBillBody, getValueFromUIJSON(environment, "homePageValidationData.viewBillBody"));

            clickOnElement(homePageProperties.getProperty("closeViewBill"), false);
            logger.log(Level.INFO, "Closed View Bill");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Closed View Bill");

            clickOnElement(homePageProperties.getProperty("makeAPayment"), false);
            logger.log(Level.INFO, "Clicked on Make A Payment");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "Clicked on Make A Payment");

            assertURL(getValueFromUIJSON(environment, "assertURLs.payNowURL"));
            logger.log(Level.INFO, "URL validation passed for Pay Now page");
            ExtentSparkReport.getExtentLogger().log(Status.INFO, "URL validation passed for Pay Now page");

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Interrupted Exception: " + e.getMessage(), e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Interrupted Exception: " + e.getMessage());
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Parse Exception: " + e.getMessage(), e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Parse Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected Exception: " + e.getMessage(), e);
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "Unexpected Exception: " + e.getMessage());
        }
    }

}





