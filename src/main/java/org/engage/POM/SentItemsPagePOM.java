package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;

import java.util.*;

import static org.engage.Base.BaseUtilities.*;

public class SentItemsPagePOM {

    public static Properties sentItemsPageProperties;
    public static List<String> environment;

    public SentItemsPagePOM(List<String> environment){
        SentItemsPagePOM.environment=environment;
       sentItemsPageProperties = BaseUtilities.readPropertyfile(FilePaths.SENT_ITEMS_PAGE_PROPERTIES);
    }

    public void clickOnMessageMenu() {
      scrollDown(200);
      performJSClickElement(sentItemsPageProperties.getProperty("messageBtn"));
        }
    public void selectSubMenu(String expSubMenu) {
       performJSClickElement(sentItemsPageProperties.getProperty("menu").replace("^^^", expSubMenu));
        }

    public void verifySentItemsTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of Sent Items Page");
        String pageHeader = getTextOfElement(sentItemsPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "sentItemsPageData.sentItemsHeader"), "Header validated successfully", "Header not  validated successfully");
    }

    public void clickComposeButton(){
        performJSClickElement(sentItemsPageProperties.getProperty("composeBtn"));
    }
    public void verifyComposeMessageTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of compose message Page");
        String pageHeader = getTextOfElement(sentItemsPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "sentItemsPageData.composeMessageHeader"), "Header validated successfully", "Header not  validated successfully");
    }
    public void verifyColumns(String locator, String column){
        List<String> actHeadersList = getHeaders(sentItemsPageProperties.getProperty(locator));
        List<String> expHeaders = Arrays.asList(Objects.requireNonNull(getValueFromUIJSON(environment, column)).split(","));
        System.out.println("actual headers"+actHeadersList);
        System.out.println("expected headers"+expHeaders);
        assertThat(new HashSet<>(actHeadersList).containsAll(expHeaders), "all headers are displayed", "all headers are not displayed");
    }

    public void verifySentItemsColumns(){
        verifyColumns("sentItemTableHeaders", "sentItemsPageData.sentItemsTableColumns");
    }

    public void clickOnMessage() throws InterruptedException {

        performJSClickElement(sentItemsPageProperties.getProperty("firstElementLnk"));
        Thread.sleep(2000);
    }
    public void verifyMessageTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of compose message Page");
        String pageHeader = getTextOfElement(sentItemsPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "sentItemsPageData.successNavigationToMessageCard"), "Header validated successfully", "Header not  validated successfully");
    }
    public void verifySortingOfColumns() throws InterruptedException {
        performJSClickElement(sentItemsPageProperties.getProperty("catColLnk"));
        Thread.sleep(2000);
        performJSClickElement(sentItemsPageProperties.getProperty("catColLnk"));
        Thread.sleep(2000);
        List<WebElement> elements = getElements(sentItemsPageProperties.getProperty("catColData"));
        List<String> actData = new ArrayList<>();
        assert elements != null;
        for(WebElement element : elements){
            String data = getTextOfElements(element);
            actData.add(data);
        }
        List<String> expData = new ArrayList<>(actData);
        Collections.sort(expData);
        boolean status = false;
        for(int i=0; i<expData.size(); i++){
            status = actData.get(i).equals(expData.get(i));
            if(!status){
                break;
            }
        }
        assertThat(status, "data sorted", "data not sorted");
    }
}
