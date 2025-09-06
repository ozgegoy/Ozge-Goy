package tests;

import base.BaseTest;
import config.ConfigReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import pages.InsiderPage;

public class InsiderTest extends BaseTest {

    @Test
    public void careerPage() {
        InsiderPage insiderPage = new InsiderPage(driver);
        navigateTo(ConfigReader.getProperty("baseUrl"));

        Assertions.assertTrue(insiderPage.isHomePageDisplayed(), "Home page should be visible");
        insiderPage.clickCompanyDropDown();
        insiderPage.clickCareersButton();
        Assertions.assertTrue(insiderPage.isTeamsVisible(), "Teams section should be visible");
        Assertions.assertTrue(insiderPage.isLocationsVisible(), "Locations section should be visible");
        Assertions.assertTrue(insiderPage.isLifeAtInsiderVisible(), "Life at Insider section should be visible");
    }

    @Test
    public void qualityAssurancePage() {
        InsiderPage insiderPage = new InsiderPage(driver);
        navigateTo(ConfigReader.getProperty("qaUrl"));

        insiderPage.clickSeeAllQaJobs();

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        insiderPage.clickFilterByLocation();
        insiderPage.clickFilterByDepartment();
        insiderPage.checkJobList();
        Assertions.assertTrue(insiderPage.isJobList("Quality Assurance"));
        Assertions.assertTrue(insiderPage.isJobList("Istanbul, Turkiye"));
        //insiderPage.checkViewRole();
        //insiderPage.clickViewRole();

    }
}