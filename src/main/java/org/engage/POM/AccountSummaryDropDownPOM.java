package org.engage.POM;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.getValueFromUIJSON;

public class AccountSummaryDropDownPOM {

    public static Properties accountSummaryDropDownProperties;
    public static List<String> environment;

    public AccountSummaryDropDownPOM(List<String> environment)
    {
        AccountSummaryDropDownPOM.environment=environment;
        accountSummaryDropDownProperties = BaseUtilities.readPropertyfile(FilePaths.ACCOUNT_SUMMARY_DROPDOWN_PROPERTIES);

    }

    public  void clickOnIWantToDropDown(){
        clickOnElement(accountSummaryDropDownProperties.getProperty("accountSummaryDropDownBtn"),false);
        ExtentSparkReport.getExtentLogger().info("Clicked on DropDown button.");
        System.out.println("Successfully clicked on Dropdown Button");
    }

    public void verifyWantToSelectOption(String option, String expUrl, String expHeader, String locator){
        clickOnIWantToDropDown();
        String xpath = accountSummaryDropDownProperties.getProperty("wantToNavigateOption").replaceAll("navigateMenu", option);
        performJSClickElement(xpath);
        String tabsTC = switchToNewTab();
        String[] tabHandlesTC = tabsTC.split(";");
        String parentTabTC = tabHandlesTC[0];
        String childTabTC = tabHandlesTC.length > 1 ? tabHandlesTC[1] : null;
        assertThatContains(getCurrentUrl(), expUrl, "Actual URL "+ getCurrentUrl() +" matched with expected URL " + expUrl, "Actual URL "+ getCurrentUrl() +" not matched with expected URL " + expUrl);
        if(!expHeader.isEmpty()){
            String actHeader = getTextOfElement(accountSummaryDropDownProperties.getProperty(locator));
            //assert actHeader != null;
            Assert.assertEquals(actHeader, expHeader);
            verifyHeader(expHeader, locator);
        }
        if(childTabTC != null){
            switchToChildTabAndClose(childTabTC);
            switchToParentTab(parentTabTC);
        }else{
            navigateToBack();
        }
    }
    public void verifyCityNewsLinks(){
        verifyWantToSelectOption("View City News",getValueFromUIJSON(environment,"accountSummaryDropDownData.viewCityNewsLink"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.viewCityNewsHeader")), "viewCityNewsHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on City News links.");
    }
    public void changeMyPaymentOptions(){
        verifyWantToSelectOption("Change my payment options",getValueFromUIJSON(environment,"accountSummaryDropDownData.changeMyPaymentOption"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.changePaymentOptionHeader")), "pageHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on Change Payment Option link.");
    }

    public void viewMyDocuments(){
        verifyWantToSelectOption("View my documents",getValueFromUIJSON(environment,"accountSummaryDropDownData.viewMyDocuments"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.viewDocumentsHeader")), "pageHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on View My Documents link.");
    }

    public void updateMyPreference(){
        verifyWantToSelectOption("Update my preferences",getValueFromUIJSON(environment,"accountSummaryDropDownData.updateMyPreferenceLink"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.billDeliveryPrefHeader")), "pageHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on Update my Preference link.");
    }

    public void needHelpWithBill(){
        verifyWantToSelectOption("Do you need help with your bill?",getValueFromUIJSON(environment,"accountSummaryDropDownData.doYouNeedHelpWithBill"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.helpWithBill")), "viewCityNewsHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on Need help with Bill link.");
    }

    public void reportMissedCollection(){
        verifyWantToSelectOption("Report a missed collection",getValueFromUIJSON(environment,"accountSummaryDropDownData.reportMissedCollection"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.reportMissedCollectionTxt")), "reportMissedCollectionHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on Report missed collection link.");
    }

    public void binReplacement(){
        verifyWantToSelectOption("Request a bin replacement/repair/pick up",getValueFromUIJSON(environment,"accountSummaryDropDownData.requestBinReplacement"), Objects.requireNonNull(getValueFromUIJSON(environment, "accountSummaryDropDownData.binReplacementPageTxt")), "binReplacementHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on bin Replacement link.");
    }

    public void requestLeakAdjustment(){
        verifyWantToSelectOption("Request a leak adjustment",getValueFromUIJSON(environment,"accountSummaryDropDownData.requestLeakAdjustment"), getValueFromUIJSON(environment,"accountSummaryDropDownData.requestLeakAdjustmentTxt"), "fixLeakHeader");
        ExtentSparkReport.getExtentLogger().info("Clicked on Request Adjustment link.");
    }

    public void verifyHeader(String expHeader, String locator){
        String actHeader = getTextOfElement(accountSummaryDropDownProperties.getProperty(locator));
        //assert actHeader != null;
        Assert.assertEquals(actHeader, expHeader);
    }

}
