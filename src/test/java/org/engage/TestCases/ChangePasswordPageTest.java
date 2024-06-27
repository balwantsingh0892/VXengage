package org.engage.TestCases;

import org.engage.CommonUtilities.ExtentSparkReport;
import org.engage.TestBase.TestUtils;
import org.testng.annotations.Test;

import static org.engage.POM.BillingDeliveryPreferencePagePOM.clickOnProfileMenu;
import static org.engage.POM.BillingDeliveryPreferencePagePOM.selectSubMenu;
import static org.engage.POM.HomePagePOM.clickOnPopUpRemindMeLater;

//Scenario_17

public class ChangePasswordPageTest extends TestUtils {

    @Test(description = "To Verify page elements in Change Password page", priority = 1, alwaysRun = true, groups = "Phase1_Group2")
    public void pageElementVerificationChangePassword(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Change Password");
        changePasswordPagePOM.verifyPageTitleCard();
        changePasswordPagePOM.verifyTitleCards();
    }

  @Test(description = "To Verify password field by entering characters more than 30 ", priority = 2, alwaysRun = true, groups = "Phase1_Group2")
    public void validatePasswordGreaterThan30Characters(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Change Password");
        changePasswordPagePOM.verifyPasswordMoreThan30Characters();
    }

    @Test(description = " Verifiying password field with different combination of passwords in change password page ", priority = 3, alwaysRun = true, groups = "Phase1_Group2")
    public void verifyChangePassword(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Change Password");
        changePasswordPagePOM.verifyRequiredFields();
        changePasswordPagePOM.verifyPasswordCriteriaErrorMessage();
        changePasswordPagePOM.verifySameNewAndOldPassword();
        changePasswordPagePOM.verifyPasswordAndConfirmPasswordMismatchErrorMessage();
        changePasswordPagePOM.verifyInvalidOldPasswordError();
        changePasswordPagePOM.validatePasswordVerbiage();
    }

    @Test(description = "To Verify Happy Flow of Change Password page", priority = 4, alwaysRun = true, groups = "Phase1_Group2")
    public void happyFlowChangePassword(){
        ExtentSparkReport.getExtentLogger().assignAuthor("Nandhakumar").assignCategory("Phase1_Group2");
        loginApplication("validCredentials[0].username", "validCredentials[0].password",testDataFilePaths);
        clickOnPopUpRemindMeLater();
        clickOnProfileMenu();
        selectSubMenu("Change Password");
        changePasswordPagePOM.happyFlowChangePassword();
        changePasswordPagePOM.changePasswordBackToExistingPassword();

    }

}
