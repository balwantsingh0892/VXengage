package org.engage.POM;

import org.engage.Base.BaseUtilities;
import org.engage.Constants.FilePaths;

import java.util.*;

import static org.engage.Base.BaseUtilities.*;
import static org.engage.Base.BaseUtilities.getValueFromUIJSON;
import static org.engage.POM.HomePagePOM.homePageProperties;

public class InboxPOM {

    public static Properties inboxProperties;
    public static List<String> environment;

    public InboxPOM(List<String> environment){
        InboxPOM.environment = environment;
        inboxProperties = BaseUtilities.readPropertyfile(FilePaths.INBOX_PROPERTIES);
    }

    public void inboxHeadersValidation(){
        performJSClickElement(homePageProperties.getProperty("messageOption"));
        performJSClickElement(homePageProperties.getProperty("inboxOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "inboxValidationData.inboxTxt"), "Inbox Option is verified", "Inbox Option is not verified");
        performJSClickElement(inboxProperties.getProperty("composeBtn"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.composeMessageTxt"), "Compose Message Option is verified", "Compose message Option is not verified");
        performJSClickElement(homePageProperties.getProperty("inboxOption"));
        assertThat(Objects.requireNonNull(getTextOfElement(homePageProperties.getProperty("cardHeader"))), getValueFromUIJSON(environment, "homePageValidationData.inboxTxt"), "Inbox Option is verified", "Inbox Option is not verified");
    }

    public void verifyColumns(){
        List<String> actHeaders = getHeaders(inboxProperties.getProperty("formTableHeaders"));
        String[] expHeaders = getValueFromUIJSON(environment, "inboxValidationData.columns").split(",");
        boolean status = false;
        for(String header : expHeaders){
           status = actHeaders.contains(header);
           if(!status){
               break;
           }
        }
        assertThat(status, "all columns are displayed", "all columns are not displayed");
    }

    public void verifySorting(){
        clickOnElement(inboxProperties.getProperty("subjectHeader"), false);
        List<String> actHeaders = getHeaders(inboxProperties.getProperty("subject"));
        List<String> expHeaders = new ArrayList<>(actHeaders);
        Collections.sort(expHeaders);
        boolean status = actHeaders.equals(expHeaders);
        assertThat(status, "sorted in asc", "not sorted");
    }

    public void clickOnMessage() throws InterruptedException {
        Thread.sleep(1000);
        clickOnElement(inboxProperties.getProperty("firstMsg"), false);
        String actHeader = getTextOfElement(inboxProperties.getProperty("header"));
        assert actHeader != null;
        assertThat(actHeader, getValueFromUIJSON(environment, "inboxValidationData.header"), "navigated to message page", "not navigated to message page");
        navigateToBack();
    }

    public void verifyMarkAsReadTooltip() throws InterruptedException {
        refreshPage();
        hoverElement(inboxProperties.getProperty("markAsReadBtn"));
        Thread.sleep(2000);
        String actInfo = getTextOfElement(inboxProperties.getProperty("markAsReadTooltip"));
        assert actInfo != null;
        assertThat(actInfo, getValueFromUIJSON(environment, "inboxValidationData.MarkAsRead"), "tooltip info verified", "tooltip info not verified");
    }

    public void verifyMarkAsRead(){
        clickOnElement(inboxProperties.getProperty("markAsReadBtn"), false);
        String actTitle = getAttribute(inboxProperties.getProperty("markAsUnread"), "title");
        assertThat(actTitle, getValueFromUIJSON(environment, "inboxValidationData.MarkAsUnReadInfo"), "mark as read working", "mark as read not working");
    }

    public void verifyMarkAsUnRead(){
        clickOnElement(inboxProperties.getProperty("markAsUnreadBtn"), false);
        String actTitle = getAttribute(inboxProperties.getProperty("markAsReadBtn"), "title");
        assertThat(actTitle, getValueFromUIJSON(environment, "inboxValidationData.MarkAsRead"), "mark as read working", "mark as read not working");
    }

    public void verifyArchiveIcon() throws InterruptedException {
        clickOnElement(inboxProperties.getProperty("archiveBtn"), false);
        String actPopup = getTextOfElement(inboxProperties.getProperty("archivePopup"));
        assert actPopup != null;
        assertThat(actPopup, getValueFromUIJSON(environment, "inboxValidationData.archivePopup"), "archive popup displayed", "archive popup not displayed");
        clickOnElement(inboxProperties.getProperty("archiveCancelBtn"), false);
        Thread.sleep(2000);
        clickOnElement(inboxProperties.getProperty("archiveBtn"), false);
        clickOnElement(inboxProperties.getProperty("archiveConfirmBtn"), false);
        clickOnElement(inboxProperties.getProperty("archiveOk"), false);
    }

    public void verifyArchivedMessage(){
        String value = getAttribute(inboxProperties.getProperty("archiveBtn2"), "title");
        assert value != null;
        assertThat(value, getValueFromUIJSON(environment, "inboxValidationData.archiveMsg"), "archive messages displayed", "archive messages not displayed");
    }

    public void     verifyHideArchivedMessage(){
        performJSClickElement(inboxProperties.getProperty("hideArchiveBtn"));
        clickOnElement(inboxProperties.getProperty("hideArchiveBtn"), false);
        String value = getAttribute(inboxProperties.getProperty("archiveBtn2"), "title");
        assert value != null;
        assertThat(value, getValueFromUIJSON(environment, "inboxValidationData.unArchiveMsg"), "archive messages displayed", "archive messages not displayed");
    }

    public void unArchiveMsg(){
        performJSClickElement(inboxProperties.getProperty("hideArchiveBtn"));
        clickOnElement(inboxProperties.getProperty("unArchiveBtn"), false);
        clickOnElement(inboxProperties.getProperty("archiveConfirmBtn"), false);
        clickOnElement(inboxProperties.getProperty("archiveOk"), false);
    }

}
