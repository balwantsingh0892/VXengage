package org.engage.POM;

import com.aventstack.extentreports.Status;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static org.engage.Base.BaseUtilities.*;


public class BillingAndUsageHistoryPagePOM {

    public static Properties billingAndUsageHistoryPagePOM;
    public static List<String> environment;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public BillingAndUsageHistoryPagePOM(List<String> environment) {
        BillingAndUsageHistoryPagePOM.environment=environment;
        billingAndUsageHistoryPagePOM = BaseUtilities.readPropertyfile(FilePaths.BILLING_USAGE_AND_HISTORY_PAGE_PROPERTIES);
    }

    public void clickOnBillingMenu() {
        scrollDown(200);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("billingMenu"),false);
    }

    public void selectSubMenu(String expSubMenu) {
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("menu").replace("^^^", expSubMenu),false);
    }

    public void verifyPageHeader() {
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of billing and usage history");
        String pageHeader = getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.header"), "Header validated successfully", "Header not  validated successfully");
    }

    public void verifyCardHeader() {
        ExtentSparkReport.getExtentLogger().info("Verifying Card Header of billing and usage history");
        String newPassError = getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("cardTitle"));
        assert newPassError != null;
        assertThat(newPassError, getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.header"), "Card Header validated successfully", "Card Header not validated successfully");
    }

    public void verifyTabs() throws InterruptedException {

        ExtentSparkReport.getExtentLogger().info("Verifying Card Tabs in  billing and usage history");
        String newPassError = getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("billingTab"));
        assert newPassError != null;
        assertThat(newPassError, getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.tabHeader"), "Card Tab1 validated successfully", "Card Tab1 not validated successfully");
        Thread.sleep(2000);
    }

    public void verifyStartEndDate(String startLoc, String endLoc) throws InterruptedException {
        ExtentSparkReport.getExtentLogger().info("Verify Start date and End date in  billing and usage history");
        boolean status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("startDate").replace("^^^", startLoc)));
        assertThat(status, "start date displayed", "start date not displayed");
        status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("endDate").replace("^^^", endLoc)));
        assertThat(status, "end date displayed", "end date not displayed");
        Thread.sleep(2000);
    }

    public void clickOnMeterDropDown() {
        performJSClickElement(billingAndUsageHistoryPagePOM.getProperty("meterSelect"));
    }

    public void verifyElements(String startLoc, String expHeader, String field){
       boolean status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("startDate").replace("^^^", startLoc)));
       assertThat(status, "calender displayed", "calender not displayed");
       expHeader = getValueFromUIJSON(environment, expHeader);
       String actHeader = getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("header"));
       assert actHeader != null;
       assertThat(actHeader.equals(expHeader), "text billing displayed", "text billing not displayed");
       status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("irsFrom").replace("^^^", field)));
       assertThat(status, "slider displayed", "slider not displayed");
       status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("prevBtn").replace("^^^", field)));
       assertThat(status, "previous button displayed", "previous button not displayed");
       status = isElementDisplayed(getElement(billingAndUsageHistoryPagePOM.getProperty("nextBtn").replace("^^^", field)));
       assertThat(status, "next button displayed", "next button not displayed");
   }

       public void selectDateFromDatePicker(String startDate, String endDate, String startDateLoc, String endDateLoc) {
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");
        String startDay = startArray[0];
        String startMon = startArray[1];
        String startYear = startArray[2];
        String endDay = endArray[0];
        String endMon = endArray[1];
        String endYear = endArray[2];
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("startDate").replace("^^^", startDateLoc), false);
        while (true) {
            performJSClickElement(billingAndUsageHistoryPagePOM.getProperty("startDaysNextIcon").replace("^^^", startDateLoc));
            String currentMon = Objects.requireNonNull(getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("startCurrentMon").replace("^^^", startDateLoc)));
            if (currentMon.equals(startMon + " " + startYear)) {
                String xpath = billingAndUsageHistoryPagePOM.getProperty("startDay").replace("^^^", startDateLoc).replace("repDate", startDay);
                clickElement(xpath);
                break;
            }
        }
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("endDate").replace("^^^", endDateLoc), false);
        while (true) {
            performJSClickElement(billingAndUsageHistoryPagePOM.getProperty("endDaysPrevIcon").replace("^^^", endDateLoc));
            String currentMon = Objects.requireNonNull(getTextOfElement(billingAndUsageHistoryPagePOM.getProperty("endCurrentMon").replace("^^^", endDateLoc)));
            if (currentMon.equals(endMon + " " + endYear)) {
                String xpath = billingAndUsageHistoryPagePOM.getProperty("endDay").replace("^^^", endDateLoc).replace("repDate", endDay);
                clickElement(xpath);
                break;
            }
        }
    }

    public void verifyPrevBtn(String field, String graphLoc){
        String beforeDropFrom = getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextFrom").replace("^^^", graphLoc)));
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("prevBtn").replace("^^^", field), false);
        String afterDropFrom = Objects.requireNonNull(getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextFrom").replace("^^^", graphLoc)))).toUpperCase();
//        String expDate =  DateUtils.getMonNumToStringFormat(DateUtils.getPrevMonInNum(beforeDropFrom));
        assert beforeDropFrom != null;
        assertThat(!beforeDropFrom.equals(afterDropFrom), "previous btn verified", "previous btn not verified");
    }

    public void verifyNextBtn(String field, String graphLoc) throws InterruptedException {
        String beforeDropTo = getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextTo").replace("^^^", graphLoc)));
        scrollDown(200);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("nextBtn").replace("^^^", field), false);
        String afterDropTo = Objects.requireNonNull(getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextTo").replace("^^^", graphLoc)))).toUpperCase();
//        String expDate =  DateUtils.getMonNumToStringFormat(DateUtils.getNextMonInNum(beforeDropTo));
        assert beforeDropTo != null;
        assertThat(!beforeDropTo.equals(afterDropTo), "next btn verified", "next btn not verified");
    }

    public void verifyColumns(String locator, String column){
        List<String> actHeadersList = getHeaders(billingAndUsageHistoryPagePOM.getProperty(locator));
        List<String> expHeaders = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, column)).split(","));
        assertThat(new HashSet<>(actHeadersList).containsAll(expHeaders), "all headers are displayed", "all headers are not displayed");
        System.out.println("actual headers"+actHeadersList);
        System.out.println("expected headers"+expHeaders);
    }

    public void verifyFileDownload(String expName) {
        File file = new File(System.getProperty("user.home") + "\\Downloads");
        File[] fa = file.listFiles();
        assert fa != null;

        boolean status = false;
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        for (File f : fa) {
            String fileDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(f.lastModified()));
            if (todayDate.equals(fileDate)) {
                if (f.getName().contains(expName)) {
                    status = true;
                    break;
                }
            }
        }
        Assert.assertTrue(status);
    }

    public void verifyMeterDropDownOptions(){
     try {
        List<WebElement> meterElements = getElements(billingAndUsageHistoryPagePOM.getProperty("meterOptionsBtn"));
         List<WebElement> meterElementsLi = getElements(billingAndUsageHistoryPagePOM.getProperty("meterOptionsLi"));
        List<String> expMeterOptions = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.meterOptions")).split(","));
        assert meterElementsLi != null;
        List<String> actOptions = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(meterElements).size(); i++) {
            String actualMeter = meterElements.get(i).getText();
            System.out.println("Actual Meter: "+actualMeter);
            String actualMeter2 = meterElementsLi.get(i).getText();
            System.out.println("Act meter: "+actualMeter2);
            actOptions.add(actualMeter);
            actOptions.add(actualMeter2);
        }
        assertThat(new HashSet<>(actOptions).containsAll(expMeterOptions),"all the options are verified", "all the options are not verified");
        ExtentSparkReport.getExtentLogger().info("Verified all the meters under dropdown.");
    } catch (Exception e) {
        Assert.fail("Unexpected Error. Error message: " + e.getMessage());
    }
}
    public void selectMeter(String option) throws InterruptedException {
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("meterDropDown"),false);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty(option),false);
    }

    public void verifySlider(String startDateLoc, String endDateLoc, String graphLoc) throws ParseException {
        String startDate = Objects.requireNonNull(getElement(billingAndUsageHistoryPagePOM.getProperty("startDate").replace("^^^", startDateLoc))).getAttribute("data-min-date");
        System.out.println(startDate);
        String startText= getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextFrom").replace("^^^", graphLoc)));
        System.out.println(startText);
        String startMonth = convertDateToMonth(startDate);
        System.out.println("Start Month: " + startMonth);
        Assert.assertEquals(startMonth,startText);

        String endDate = Objects.requireNonNull(getElement(billingAndUsageHistoryPagePOM.getProperty("endDate").replace("^^^", endDateLoc))).getAttribute("data-max-date");
        System.out.println(endDate);
        String endText= getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextTo").replace("^^^", graphLoc)));
        System.out.println(endText);
        String endMonth = convertDateToMonth(endDate);
        System.out.println("End Month: " + endMonth);
        Assert.assertEquals(endMonth,endText);

        String expStartMonth = getMonNumToStringFormat(getNextMonInNum(startText));
        dragAndDrop(getElement(billingAndUsageHistoryPagePOM.getProperty("irsFrom").replace("^^^", graphLoc)),100);
//        String dt = Objects.requireNonNull(getElement(billingAndUsageHistoryPagePOM.getProperty("startDate").replace("^^^", startDateLoc))).getAttribute("innerHTML");
        String afterDropFrom = getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextFrom").replace("^^^", graphLoc)));
        System.out.println(afterDropFrom);


        assert startText != null;
        assert afterDropFrom != null;
        if (expStartMonth.equals(afterDropFrom.toUpperCase())) {
            System.out.println("Slider moved successfully.");
        } else {
            System.out.println("Slider did not move.");
        }

        String expEndMonth = getMonNumToStringFormat(getPrevMonInNum(endText));
        dragAndDrop(getElement(billingAndUsageHistoryPagePOM.getProperty("irsTo").replace("^^^", graphLoc)),-100);
        String afterDropTo = getTextOfElement((billingAndUsageHistoryPagePOM.getProperty("irsTextTo").replace("^^^", graphLoc)));
        System.out.println(afterDropTo);

        assert endText != null;
        assert afterDropTo != null;
        if (expEndMonth.equals(afterDropTo.toUpperCase())) {
            System.out.println("Slider moved successfully.");
        } else {
            System.out.println("Slider did not move.");
        }
    }

    public void verifyBillingElements(){
        verifyElements("billingHistory_StartDate", "billingAndHistoryUsagePageData.billingHeader", "billingHistory");
    }

    public void verifyBillingDate() throws InterruptedException {
        verifyStartEndDate("billingHistory_StartDate", "billingHistory_EndDate");
    }

    public void verifySelectDateBilling(){
        selectDateFromDatePicker(Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingStartDate")), Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingEndDate")), "billingHistory_StartDate", "billingHistory_EndDate");
    }

    public void verifyBillingSlider() throws ParseException {
        verifySlider("billingHistory_StartDate", "billingHistory_EndDate", "billingHistoryGraphSection_dragChart");
    }

    public void verifyBillingNextBtn() throws InterruptedException {
        verifyNextBtn("billingHistory-chartRightButtonContainer", "billingHistoryGraphSection_dragChart");
    }

    public void verifyBillingPrevBtn(){
        verifyPrevBtn("billingHistory-chartLeftButtonContainer", "billingHistoryGraphSection_dragChart");
    }

    public void verifyBillingColumns(){
        verifyColumns("billingTableHeaders", "billingAndHistoryUsagePageData.billingTableColumns");
    }

    public void verifyBillingFileDownload() throws InterruptedException {
        scrollDown(2000);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("downloadBtn"),false);
        Thread.sleep(3000);
        verifyFileDownload(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingMeterFile"));
    }

    public void verifyWaterBillingElements(){
        verifyElements("waterusage_StartDate", "billingAndHistoryUsagePageData.billingHeader", "waterusage");
    }

    public void verifyWaterBillingDate() throws InterruptedException {
        verifyStartEndDate("waterusage_StartDate", "waterusage_EndDate");
    }

    public void verifyWaterSelectDateBilling(){
        selectDateFromDatePicker(Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingStartDate")), Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingEndDate")), "waterusage_StartDate", "waterusage_EndDate");
    }

    public void verifyWaterBillingSlider() throws ParseException {
        verifySlider("waterusage_StartDate", "waterusage_EndDate", "waterusageGraphSection_dragChart");
    }

    public void verifyWaterBillingNextBtn() throws InterruptedException {
        verifyNextBtn("waterusage-chartRightButtonContainer", "waterusageGraphSection_dragChart");
    }

    public void verifyWaterBillingPrevBtn(){
        verifyPrevBtn("waterusage-chartLeftButtonContainer", "waterusageGraphSection_dragChart");
    }

    public void verifyWaterBillingColumns(){
        verifyColumns("waterTableHeaders", "billingAndHistoryUsagePageData.waterTableColumns");
    }

    public void verifyWaterBillingFileDownload() throws InterruptedException {
        scrollDown(1000);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("downloadBtn"),false);
        Thread.sleep(2000);
        verifyFileDownload(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingFile"));
    }

    public void verifyElectricBillingElements(){
        verifyElements("electricusage_StartDate", "billingAndHistoryUsagePageData.billingHeader", "electricusage");
    }

    public void verifyElectricBillingDate() throws InterruptedException {
        verifyStartEndDate("electricusage_StartDate", "electricusage_EndDate");
    }

    public void verifyElectricSelectDateBilling(){
        selectDateFromDatePicker(Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingStartDate")), Objects.requireNonNull(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingEndDate")), "electricusage_StartDate", "electricusage_EndDate");
    }

    public void verifyElectricBillingSlider() throws ParseException {
        verifySlider("electricusage_StartDate", "electricusage_EndDate", "electricusageGraphSection_dragChart");
    }

    public void verifyElectricBillingNextBtn() throws InterruptedException {
        verifyNextBtn("electricusage-chartRightButtonContainer", "electricusageGraphSection_dragChart");
    }

    public void verifyElectricBillingPrevBtn(){
        verifyPrevBtn("electricusage-chartLeftButtonContainer", "electricusageGraphSection_dragChart");
    }

    public void verifyElectricBillingColumns(){
        verifyColumns("electricTableHeaders", "billingAndHistoryUsagePageData.electricTableColumns");
    }

    public void verifyElectricBillingFileDownload() throws InterruptedException {
        scrollDown(1000);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("downloadBtn"),false);
        Thread.sleep(2000);
        verifyFileDownload(getValueFromUIJSON(environment, "billingAndHistoryUsagePageData.billingFile"));
    }

    public void verifyAccountsInDropdown(){
        List<String> actAcc = getAllDropdownOption(billingAndUsageHistoryPagePOM.getProperty("expAccountsDropdown"));
        List<String> expAcc = getAllDropdownOption(billingAndUsageHistoryPagePOM.getProperty("defaultAccDropdown"));
        if(actAcc.size() == expAcc.size()){
            assertThat(new HashSet<>(actAcc).containsAll(expAcc), "all accounts present in dropdown", "all accounts not present in dropdown");
        }else{
            ExtentSparkReport.getExtentLogger().log(Status.FAIL, "acc details not matched with expected");
            Assert.fail();
        }
    }
    public void selectAccount(String acc) throws InterruptedException{
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("accDropDown"), false);
        Thread.sleep(2000);
        clickOnElement(billingAndUsageHistoryPagePOM.getProperty("accOptions").replaceAll("accNo", getValueFromUIJSON(environment, acc)), false);
    }
    public void selectAccountForBillingAndHistoryUsage() throws InterruptedException {
        selectAccount("billingAndHistoryUsagePageData.account");
        Thread.sleep(2000);
    }


}