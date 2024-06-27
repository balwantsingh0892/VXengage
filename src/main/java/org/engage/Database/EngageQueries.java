package org.engage.Database;

public class EngageQueries {
    public static final String UPDATE_IP_ADDRESS_COUNT_MORE_THAN_15 =
            "UPDATE auth_ip_addresses SET FAILED_LOGIN_ATTEMPTS=0 WHERE FAILED_LOGIN_ATTEMPTS>15";

    public static final String UPDATE_CUSTOMER_VIEW_FOR_UPLOADED_FILES_CURRENT_DATE =
            "UPDATE docadv_documents " +
                    "SET customer_viewable = 0 " +
                    "WHERE account_number = '23956' " +
                    "AND customer_viewable = 1 " +
                    "AND DATE(created_date_time) = CURDATE();";


    public static final String SELECT_MASTER_KEY_FOR_CAPTCHA =
            "SELECT value FROM sys_config WHERE `KEY` = 'MasterKeyForCaptcha';";


    public static final String SELECT_ACTIVE_EMAIL_ADDRESS =
            "SELECT email_address " +
                    "FROM auth_users " +
                    "WHERE STATUS = 'A' " +
                    "AND email_address LIKE '%karan%' " +
                    "ORDER BY user_id DESC " +
                    "LIMIT 1;";

    public static final String SELECT_ACTIVE_USERNAME_EMAIL_ADDRESS =
                "SELECT user_name , email_address " +
                        "FROM auth_users " +
                        "WHERE STATUS = 'A' " +
                        "AND email_address LIKE '%karan%' " +
                        "ORDER BY user_id DESC " +
                        "LIMIT 1;";

    public static final String SELECT_MASTER_KEY_FOR_MFA=
            "SELECT value FROM sys_config WHERE `KEY` = 'MasterKeyForMFA'";

    public static final String SELECT_STREET_TYPES =
            "SELECT value FROM sys_config WHERE `key` ='StreetTypes'";
}


