package org.engage.Constants;

import java.io.File;

public class FilePaths {

    public static final String RESOURCE_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String TEST_RESOURCE_PATH =  System.getProperty("user.dir")+"\\src\\test\\resources\\";
    public static final String GLOBAL_CONFIGS_PATH = RESOURCE_PATH + "CommonConfigProperties" + File.separator + "GlobalConfigs.properties";
    public static final String LOGIN_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "LoginPage.properties";
    public static final String REGISTRATION_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "RegistrationPage.properties";
    public static final String HOME_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "HomePage.properties";
    public static final String EXTENT_HTML_REPORT_PATH = System.getProperty("user.dir")+"\\target\\" + File.separator + "extentReportsOutput" + File.separator + "htmlReports" + File.separator;
    public static final String EXTENT_REPORT_PROPERTIES = RESOURCE_PATH + "CommonConfigProperties" + File.separator + "ExtentReport.properties";
    public static final String GLOBAL_CONFIG_PROPERTIES = RESOURCE_PATH + "CommonConfigProperties" + File.separator + "GlobalConfigs.properties";
    public static final String SCREENSHOTS_PATH = System.getProperty("user.dir")+"\\target\\" + File.separator + "extentReportsOutput" + File.separator + "screenshots" + File.separator;
    public static final String TEST_DATA_FOLDER = RESOURCE_PATH + "TestData" + File.separator;
    public static final String TEST_DATA_FOLDER_PHASE_2 = RESOURCE_PATH + "TestData_Phase_2" + File.separator;
    public static final String ENGAGE_TEST_DATA_JSON = TEST_DATA_FOLDER + "TestData.json";
    public static final String ENGAGE_TEST_DATA_SIT_JSON = TEST_DATA_FOLDER + "sit.json";
    public static final String ENGAGE_TEST_DATA_HOPE_JSON = TEST_DATA_FOLDER + "hope.json";
    public static final String ENGAGE_TEST_DATA_JSON_PHASE_2 = TEST_DATA_FOLDER_PHASE_2 + "TestData.json";
    public static final String ENGAGE_TEST_DATA_SIT_JSON_PHASE_2 = TEST_DATA_FOLDER_PHASE_2 + "sit.json";
    public static final String ENGAGE_TEST_DATA_HOPE_JSON_PHASE_2 = TEST_DATA_FOLDER_PHASE_2 + "hope.json";
    public static final String BILLING_PREFERENCE_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "BillingDeliveryPreferencePage.properties";
    public static final String FORGOT_USERNAME_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "ForgotUsernamePage.properties";
    public static final String FORGOT_PASSWORD_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "ForgotPasswordPage.properties";
    public static final String DOCUMENTS_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "DocumentsPage.properties";
    public static final String CHANGE_PASSWORD_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "ChangePasswordPage.properties";
    public static final String ACCOUNT_SUMMARY_DROPDOWN_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "AccountSummaryDropDown.properties";
    public static final String ACCOUNT_SETTING_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "AccountSettingPage.properties";
    public static final String START_SERVICE_RESIDENTIAL_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators_Phase_2" + File.separator + "StartServiceResidentialPage.properties";

    public static final String BILLING_USAGE_AND_HISTORY_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "BillingAndUsageHistoryPage.properties";
    public static final String SENT_ITEMS_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "SentItemsPage.properties";
    public static final String INBOX_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "InboxPage.properties";
    public static final String INBOX_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "Inbox.properties";
    public static final String CONTACT_INFORMATION_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "ContactInformationPage.properties";

    public static final String UNSUPPORTED_DOCUMENT_TYPE = System.getProperty("user.dir")+File.separator +RESOURCE_PATH + "TestFiles" + File.separator + "UnsupportedFileTest.java";
    public static final String UPLOAD_FILE_SIZE_MORE_THAN_40MB = System.getProperty("user.dir")+File.separator +RESOURCE_PATH + "TestFiles" + File.separator + "FileSizeMoreThan40MB.zip";
    public static final String VALID_FILE_EXTENSION_SIZE = System.getProperty("user.dir")+File.separator +RESOURCE_PATH + "TestFiles" + File.separator + "SupportedFileTest.txt";
    public static final String COMPOSE_MESSAGE_PAGE_PROPERTIES = RESOURCE_PATH + "UILocators" + File.separator + "ComposeMessagePage.properties";
}
