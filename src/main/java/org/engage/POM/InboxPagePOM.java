package org.engage.POM;

import io.cucumber.java.sl.In;
import org.engage.Base.BaseUtilities;
import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.Constants.FilePaths;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.POM.BillingAndUsageHistoryPagePOM.billingAndUsageHistoryPagePOM;
import static org.engage.POM.SentItemsPagePOM.sentItemsPageProperties;

public class InboxPagePOM {

    public static Properties inboxPageProperties;
    public static List<String> environment;
    public static Map<String, Map<String, String>> mailData;

    public  InboxPagePOM(List<String> environment){
        InboxPagePOM.environment = environment;
        inboxPageProperties = BaseUtilities.readPropertyfile(FilePaths.INBOX_PAGE_PROPERTIES);

    }
    public void selectAccount() throws InterruptedException {
        selectOption(billingAndUsageHistoryPagePOM.getProperty("accountDropdown"), "value="+getValueFromUIJSON(environment, "inboxPageData.account"));
        Thread.sleep(3000);
    }
    public void verifyInboxTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of compose message Page");
        String pageHeader = getTextOfElement(inboxPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "inboxPageData.inboxTitleCard"), "Header validated successfully", "Header not  validated successfully");
    }

    public void verifyMessageArea(){
        switchToFrame(inboxPageProperties.getProperty("replyTxtIframe"));
        boolean status = isElementDisplayed(getElement(inboxPageProperties.getProperty("messageTextArea")));
        assertThat(status, "message area displayed", "message area not displayed");
        switchToDefault();
    }

    public void clickOnMessage(String categoryName) throws InterruptedException {
        clickOnElement(inboxPageProperties.getProperty("messageLink").replaceAll("category", Objects.requireNonNull(getValueFromUIJSON(environment, categoryName))), false);
//        performJSClickElement(inboxPageProperties.getProperty("messageLink").replaceAll("category", Objects.requireNonNull(getValueFromUIJSON(environment, categoryName))));
        Thread.sleep(2000);
    }

    public void clickOnMessageBySubject(String subject) throws InterruptedException {
        clickOnElement(inboxPageProperties.getProperty("messageLinkBySubject").replaceAll("subject", Objects.requireNonNull(getValueFromUIJSON(environment, subject))), false);
//        performJSClickElement(inboxPageProperties.getProperty("messageLink").replaceAll("category", Objects.requireNonNull(getValueFromUIJSON(environment, categoryName))));
        Thread.sleep(2000);
    }

    public void verifyMessageTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Header of message Page");
        String pageHeader = getTextOfElement(inboxPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "inboxPageData.messageTitleCard"), "Header validated successfully", "Header not  validated successfully");
    }

    public void verifyReply()  {
        clickOnElement(inboxPageProperties.getProperty("replyBtn"),false);
        String sendReply = getTextOfElement(inboxPageProperties.getProperty("sendReply"));
        assert sendReply != null;
        assertThat(sendReply, getValueFromUIJSON(environment, "inboxPageData.sendReplyText"), " validated successfully", "Header not  validated successfully");
    }

    public void verifyBackToInboxBtn(){
        performJSClickElement(inboxPageProperties.getProperty("backToInboxBtn"));
    }

    public void VerifyArchiveBtn(){
        performJSClickElement(inboxPageProperties.getProperty("archiveBtn"));
        String sendReply = getTextOfElement(inboxPageProperties.getProperty("archiveMessage"));
        assert sendReply != null;
        assertThat(sendReply, getValueFromUIJSON(environment, "inboxPageData.archiveMessage"), " Archive message validated successfully", "Archive message not  validated successfully");
        performJSClickElement(inboxPageProperties.getProperty("archiveAlertClose"));

    }

    public void unArchiveAMessage(){
        performJSClickElement(inboxPageProperties.getProperty("showArchiveBtn"));
        performJSClickElement(inboxPageProperties.getProperty("unArchiveBtn"));
        performJSClickElement(inboxPageProperties.getProperty("unArchiveAlertConformation"));
        String unArchiveMessage = getTextOfElement(inboxPageProperties.getProperty("archiveMessage"));
        assert unArchiveMessage != null;
        assertThat(unArchiveMessage, getValueFromUIJSON(environment, "inboxPageData.unArchiveMessage"), " Archive message validated successfully", "Archive message not  validated successfully");
        performJSClickElement(inboxPageProperties.getProperty("archiveAlertClose"));
    }

    public void getMailData(String bodyElement){
        List<String> header = new ArrayList<>();
        List<WebElement> headerElements = getElements(inboxPageProperties.getProperty("formHeaderElements"));
        List<WebElement> catElements = getElements(inboxPageProperties.getProperty("formCatElements"));
        assert headerElements != null;
        for(WebElement ele : headerElements){
            header.add(getTextOfElements(ele));
        }
        List<String> cat = new ArrayList<>();
        assert catElements != null;
        for(WebElement ele : catElements){
            cat.add(getTextOfElements(ele));
        }
        List<WebElement> bodyElements = getElements(inboxPageProperties.getProperty(bodyElement));
        mailData = new LinkedHashMap<>();
        Map<String, String> da = new LinkedHashMap<>();
        int count = 0;
        int cou = 0;
        for(int i = 0; i< Objects.requireNonNull(bodyElements).size(); i++){
            da.put(header.get(count), getTextOfElements(bodyElements.get(i)));
            count++;
            if(count == 4){
                count = 0;
                cou++;
                mailData.put(cat.get(cou), da);
                da = new LinkedHashMap<>();
                break;
            }
        }
    }

    public void getMailDataBySubject(String subject, String bodyElement){
        List<String> header = new ArrayList<>();
        List<WebElement> headerElements = getElements(inboxPageProperties.getProperty("formHeaderElements"));
//        List<WebElement> catElements = getElements(inboxPageProperties.getProperty("formCatElements"));
        assert headerElements != null;
        for(WebElement ele : headerElements){
            header.add(getTextOfElements(ele));
        }
//        List<String> cat = new ArrayList<>();
//        assert catElements != null;
//        for(WebElement ele : catElements){
//            cat.add(getTextOfElements(ele));
//        }
        List<WebElement> bodyElements = getElements(inboxPageProperties.getProperty(bodyElement));
        mailData = new LinkedHashMap<>();
        Map<String, String> da = new LinkedHashMap<>();
        int count = 0;
        int cou = 0;
        for(int i = 0; i< Objects.requireNonNull(bodyElements).size(); i++){
            da.put(header.get(count), getTextOfElements(bodyElements.get(i)));
            count++;
            if(count == 4){
                count = 0;
                cou++;
                mailData.put(subject, da);
                da = new LinkedHashMap<>();
                break;
            }
        }
    }

    public void verifyMessageDetailsField(String catName, String bodyElement, boolean st) throws InterruptedException {
        getMailDataBySubject(getValueFromUIJSON(environment, catName) ,bodyElement);
        Map<String, String> data = mailData.get(getValueFromUIJSON(environment, catName));
        clickOnMessageBySubject(catName);
//        if(catName.contains("sent")){
//
//        }else{
//            clickOnMessageBySubject("inboxPageData.subjectTextInbox");
//        }
        String from = getTextOfElement(inboxPageProperties.getProperty("fromText"));
        assert from != null;
        if(!catName.contains("sent")) {
            assertThatContains(from, data.get("From"), "matched with expected", "not matched with expected");
            String date = getTextOfElement(inboxPageProperties.getProperty("dateText"));
            assert date != null;
            assertThatContains(date, data.get("Date/Time"), "matched with expected", "not matched with expected");
        }
        else{
            assertThatContains(from, getValueFromUIJSON(environment, "validCredentials[0].username"), "matched with expected", "not matched with expected");
            String date = getTextOfElement(inboxPageProperties.getProperty("dateText"));
            assert date != null;
            String[] d = data.get("Date").split(" ");
            int m = getMonthInNumFormat(d[0]);
            String mon = "";
            if(m < 10){
                mon = "0" + String.valueOf(m);
            }else{
                mon = String.valueOf(m);
            }
            String da = "";
            da = d[1].replaceAll("[^0-9]", "");
            if(Integer.parseInt(da) < 10){
                da = "0" + da;
            }
            String actDate = mon + "/" + da + "/" + d[2];
            assertThatContains(date, actDate, "matched with expected", "not matched with expected");
        }
        String subject = getTextOfElement(inboxPageProperties.getProperty("subject"));
        assert subject != null;
        assertThat(subject, data.get("Subject"), "matched with expected", "not matched with expected");
        boolean status = isElementDisplayed(getElement(inboxPageProperties.getProperty("subjectMsg")));
        assertThat(status, "message displayed", "message not displayed");
        status = isElementDisplayed(getElement(inboxPageProperties.getProperty("attachmentLnk")));
        assertThat(status, "attachment displayed", "attachment not displayed");
        if(st){
            clickShowRepliesBtn();
            from = getTextOfElement(inboxPageProperties.getProperty("repliesFromText"));
            assert from != null;
            if(catName.contains("sent")){
                assertThatContains(from, getValueFromUIJSON(environment, "validCredentials[0].username"), "matched with expected", "not matched with expected");
            }else{
                assertThatContains(from, data.get("From"), "matched with expected", "not matched with expected");
            }
            status = isElementDisplayed(getElement(inboxPageProperties.getProperty("repliesDate")));
            assertThat(status, "date displayed", "date not displayed");
            subject = getTextOfElement(inboxPageProperties.getProperty("repliesSubject"));
            assert subject != null;
            assertThat(subject, data.get("Subject"), "matched with expected", "not matched with expected");
            status = isElementDisplayed(getElement(inboxPageProperties.getProperty("repliesMsg")));
            assertThat(status, "message displayed", "message not displayed");
        }
    }

    public void verifyMessageDetailsFieldBySubject(String catName, String bodyElement, boolean st) throws InterruptedException {
        getMailData(bodyElement);
        Map<String, String> data = mailData.get(getValueFromUIJSON(environment, catName));
        clickOnMessageBySubject("inboxPageData.subjectTextInbox");
        String from = getTextOfElement(inboxPageProperties.getProperty("fromText"));
        assert from != null;
        if(!catName.contains("sent")) {
            assertThatContains(from, data.get("From"), "matched with expected", "not matched with expected");
            String date = getTextOfElement(inboxPageProperties.getProperty("dateText"));
            assert date != null;
            assertThatContains(date, data.get("Date/Time"), "matched with expected", "not matched with expected");
        }
        else{
            assertThatContains(from, getValueFromUIJSON(environment, "validCredentials[0].username"), "matched with expected", "not matched with expected");
            String date = getTextOfElement(inboxPageProperties.getProperty("dateText"));
            assert date != null;
            String[] d = data.get("Date").split(" ");
            int m = getMonthInNumFormat(d[0]);
            String mon = "";
            if(m < 10){
                mon = "0" + String.valueOf(m);
            }else{
                mon = String.valueOf(m);
            }
            String da = "";
            da = d[1].replaceAll("[^0-9]", "");
            if(Integer.parseInt(da) < 10){
                da = "0" + da;
            }
            String actDate = mon + "/" + da + "/" + d[2];
            assertThatContains(date, actDate, "matched with expected", "not matched with expected");
        }
        String subject = getTextOfElement(inboxPageProperties.getProperty("subject"));
        assert subject != null;
        assertThat(subject, data.get("Subject"), "matched with expected", "not matched with expected");
        boolean status = isElementDisplayed(getElement(inboxPageProperties.getProperty("subjectMsg")));
        assertThat(status, "message displayed", "message not displayed");
        status = isElementDisplayed(getElement(inboxPageProperties.getProperty("attachmentLnk")));
        assertThat(status, "attachment displayed", "attachment not displayed");
        if(st){
            clickShowRepliesBtn();
            from = getTextOfElement(inboxPageProperties.getProperty("repliesFromText"));
            assert from != null;
            if(catName.contains("Sent")){
                assertThatContains(from, getValueFromUIJSON(environment, "validCredentials[0].username"), "matched with expected", "not matched with expected");
            }else{
                assertThatContains(from, data.get("From"), "matched with expected", "not matched with expected");
            }
            status = isElementDisplayed(getElement(inboxPageProperties.getProperty("repliesDate")));
            assertThat(status, "date displayed", "date not displayed");
            subject = getTextOfElement(inboxPageProperties.getProperty("repliesSubject"));
            assert subject != null;
            assertThat(subject, data.get("Subject"), "matched with expected", "not matched with expected");
            status = isElementDisplayed(getElement(inboxPageProperties.getProperty("repliesMsg")));
            assertThat(status, "message displayed", "message not displayed");
        }
    }


    public void clickDiscardBtn() throws InterruptedException {
        performJSClickElement(inboxPageProperties.getProperty("discardBtn"));
        Thread.sleep(1000);
        refreshPage();
    }

    public void clickPreviousBtn(){
        performJSClickElement(inboxPageProperties.getProperty("previousBtn"));
    }

    public String messageAreaText(SoftAssert softAssert){
        switchToFrame(inboxPageProperties.getProperty("replyTxtIframe"));
        String moreThan2000 = generateRandomString(2020);
        System.out.println(moreThan2000);
        sendKeysToElement(inboxPageProperties.getProperty("messageTextArea"), moreThan2000);
        switchToDefault();
        clickOnElement(inboxPageProperties.getProperty("sendReply"),false);
        softAssert.assertEquals(getTextOfElement(inboxPageProperties.getProperty("errorMessage")),getValueFromUIJSON(environment,"composeMessagePageData.subjectMoreThan2000CharMessage"));
        switchToFrameWithIndex(0);
        WebElement element = getElement(inboxPageProperties.getProperty("messageTextArea"));
        assert element != null;
        element.clear();
        String withIn2000 = generateRandomString(200);
        System.out.println(withIn2000);
        sendKeysToElement(inboxPageProperties.getProperty("messageTextArea"), withIn2000);
        switchToDefault();
        return withIn2000;
    }

    public void verifyAttachmentVerbiage(SoftAssert softAssert){
        String actVerbiage = getTextOfElement(inboxPageProperties.getProperty("attachmentVerbiage"));
        assert actVerbiage != null;
        softAssert.assertEquals(actVerbiage ,getValueFromUIJSON(environment,"inboxPageData.uploadCriteriaMessage"));
    }

    public void verifyUnsupportedDoc(SoftAssert softAssert){
        sendKeysToElement(inboxPageProperties.getProperty("uploadFileBtn"),FilePaths.UNSUPPORTED_DOCUMENT_TYPE);
        clickOnElement(inboxPageProperties.getProperty("sendReply"),false);
        String actErrorMsg = getTextOfElement(inboxPageProperties.getProperty("errorMessage"));
        //Assert.assertEquals(actErrorMsg,getValueFromUIJSON(environment,"documentsValidationData.invalidFileFormatErrorMessage"));
        assert actErrorMsg != null;
        softAssert.assertEquals(actErrorMsg, getValueFromUIJSON(environment,"documentsValidationData.invalidFileFormatErrorMessage"));
        WebElement fileInputElement = getElement(inboxPageProperties.getProperty("uploadFileBtn"));
        resetFileInputElement(fileInputElement);
        sendKeysToElement(inboxPageProperties.getProperty("uploadFileBtn"),FilePaths.UPLOAD_FILE_SIZE_MORE_THAN_40MB);
        clickOnElement(inboxPageProperties.getProperty("sendReply"),false);
        actErrorMsg = getTextOfElement(inboxPageProperties.getProperty("errorMessage"));
        assert actErrorMsg != null;
        softAssert.assertEquals(actErrorMsg, getValueFromUIJSON(environment,"documentsValidationData.fileSizeMoreThan40MBErrorMessage"));
    }

    public void sendReply() throws InterruptedException {
        WebElement fileInputElement = getElement(inboxPageProperties.getProperty("uploadFileBtn"));
        resetFileInputElement(fileInputElement);
        sendKeysToElement(inboxPageProperties.getProperty("uploadFileBtn"),FilePaths.VALID_FILE_EXTENSION_SIZE);
        clickElement(inboxPageProperties.getProperty("sendReply"));
        Thread.sleep(2000);

    }

    public void verifyReplyTo(){
        boolean status = isElementDisplayed(getElement(inboxPageProperties.getProperty("replyTo")));
        assertThat(status, "reply to displayed", "reply to not displayed");
    }
    public void alertClose(){
        performJSClickElement(inboxPageProperties.getProperty("alertClose"));
        //assertURL(getValueFromUIJSON(environment,"assertURLs.sentItemMessageCenterURL"));
    }

    public void clickShowRepliesBtn(){
        performJSClickElement(inboxPageProperties.getProperty("showRepliesBtn"));
    }
    public void enterDataMessageField() throws InterruptedException {
        performJSClickElement(inboxPageProperties.getProperty("messageTextArea"));
        switchToFrame(inboxPageProperties.getProperty("replyTxtIframe"));
        sendKeysToElement(inboxPageProperties.getProperty("messageTextArea"),getValueFromUIJSON(environment,"inboxPageData.messageData"));
        sendReply();
    }
    public void verifySentItemsTitleCard(){
        ExtentSparkReport.getExtentLogger().info("Verifying Page Header of Sent Items Page");
        String pageHeader = getTextOfElement(sentItemsPageProperties.getProperty("headerTitle"));
        assert pageHeader != null;
        assertThat(pageHeader, getValueFromUIJSON(environment, "sentItemsPageData.sentItemsHeader"), "Header validated successfully", "Header not  validated successfully");
    }

    public void verifyReply(String expMsg){
        clickShowRepliesBtn();
        String actMsg = getTextOfElement(inboxPageProperties.getProperty("firstReplyTxt"));
        assert actMsg != null;
        assertThat(actMsg, expMsg, "sent message is verified", "sent message is not verified");
    }


}
