package org.engage.Database;

public class CISQueries {
    public static final String SELECT_UCCL =
            "SELECT * FROM VTXSITFILE.UCCL FETCH FIRST 2 ROW ONLY";

    public static final String SELECT_RESIDENTIAL_CUSTOMER_DATA =
            "SELECT UMACT,MSLNM,SOCIAL_SECURITY_LAST_4 FROM VTXSITFILE.UACT UACT " +
                    "INNER JOIN VTXSITFILE.UCUS UCUS " +
                    "ON UACT.UMCUS=UCUS.MSCUS " +
                    "INNER JOIN VTXSITFILE.UNPPI UNPPI " +
                    "ON UCUS.MSNPPIID=UNPPI.NPPI_ID " +
                    "WHERE UMACT= 23877";

    public static final String SELECT_COMMERCIAL_CUSTOMER_DATA =
            "SELECT UMACT,MSLNM,E_FEDERAL_TAX_ID FROM VTXSITFILE.UACT " +
                    "INNER JOIN VTXSITFILE.UCUS " +
                    "ON VTXSITFILE.UACT.UMCUS=VTXSITFILE.UCUS.MSCUS " +
                    "INNER JOIN VTXSITFILE.UNPPI " +
                    "ON VTXSITFILE.UCUS.MSNPPIID=VTXSITFILE.UNPPI.NPPI_ID " +
                    "WHERE UMACT= 158307";

    public static final String SELECT_AMOUNT_AND_DATE_FIELDS =
            "select BHSCUR,BHSPAS,BHSPAY,BHSBLD,BHSPDD,BHSLPD " +
                    "from VTXSITFILE.UBLH " +
                    "where BHSACT=23956 " +
                    "ORDER BY BHSTR# DESC " +
                    "LIMIT 1";

    public static final String SELECT_LAST_NAME =
            "select TRIM(MSFNM) AS MSFNM, TRIM(MSLNM) AS MSLNM from vtxsitfile.UCUS where MSCUS=1023956";
}
