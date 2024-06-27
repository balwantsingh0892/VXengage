package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.BillingDeliveryPreferencePagePOM.clickOnProfileMenu;
import static org.engage.POM.BillingDeliveryPreferencePagePOM.selectSubMenu;
import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Sceanrio_18

public class BillingDeliveryPreferenceTest extends TestUtils {

    @Test(description = "To Verify page elements in Billing preference page", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void pageElementVerificationBillPreference() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Bill Delivery Preference");
        billingRefPOM.verifyPageTitleAndCardTitle();
        billingRefPOM.verifyBillingDeliveryOptions();
        billingRefPOM.verifyDefaultSelectedOption();
        billingRefPOM.verifyVerbiagePaperLessBilling();
        billingRefPOM.verifyVerbiagePaperBilling();
    }

    @Test(description = "Verifying fields in billing preference page", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyDetailedValidationBillPreference1() throws  InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Bill Delivery Preference");
        billingRefPOM.verifySubmitButtonWithoutChangingPreference();
        billingRefPOM.verifyProfileEmailAddressPopup();
        billingRefPOM.verifyCurrentEmailAddress();
        billingRefPOM.verifyEmailRequiredField();
    }

    @Test(description = "Verifying fields in billing preference page", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyDetailedValidationBillPreference2() {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Bill Delivery Preference");
        billingRefPOM.verifyCharLimitOfEmailAddress();
        billingRefPOM.invalidEmailAddress();
        billingRefPOM.verifyConfirmEmailRequiredField();
        billingRefPOM.verifyEmailMismatch();
        billingRefPOM.verifyWithExistingEmail();
    }

    @Test(description = "Verifiying happy flow Billing preference page", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void happyFlow() throws InterruptedException {
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Bill Delivery Preference");
        billingRefPOM.changeEmail();
        billingRefPOM.verifyBillingPreference();
    }
}
