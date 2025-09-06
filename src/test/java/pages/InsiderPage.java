package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InsiderPage {
    private final WebDriverWait wait;

    public InsiderPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private final By homePage = By.cssSelector("body.home-page");
    private final By companyDropDown = By.xpath("//a[@id='navbarDropdownMenuLink' and normalize-space()='Company']");
    private final By careersButton = By.xpath(getTextLocator("Careers"));
    private final By teams = By.cssSelector("#career-find-our-calling a");
    private final By locations = By.cssSelector("#career-our-location h3");
    private final By lifeAtInsider = By.xpath(getTextLocator("Life at Insider"));
    private final By seeAllQaJobs = By.xpath("//a[contains(text(),'See all QA jobs')]");
    private final By filterByLocation = By.cssSelector("#select2-filter-by-location-container");
    private final By locationFilterOption = By.xpath("//li[contains(@id, 'select2-filter-by-location-result') and normalize-space(text())='Istanbul, Turkiye']");
    private final By filterByDepartment = By.cssSelector("#select2-filter-by-department-container");
    private final By departmentFilterOption = By.xpath("//li[contains(@id,'select2-filter-by-department-result') and normalize-space(text())='Quality Assurance']");
    private final By jobList = By.xpath("//*[@id='jobs-list']/div[1]");
    private final By secondJobList = By.xpath("//*[@id='jobs-list']/div[6]");
//    private final By viewRole = By.xpath("(//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5'])[1]");

    private String getTextLocator(String textLocator) {
        return String.format("//*[normalize-space()='%s']", textLocator.trim());
    }

    public Boolean isHomePageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(homePage)).isDisplayed();
    }

    public void clickCompanyDropDown() {
        wait.until(ExpectedConditions.elementToBeClickable(companyDropDown)).click();
    }

    public void clickCareersButton() {
        wait.until(ExpectedConditions.elementToBeClickable(careersButton)).click();
    }

    public Boolean isTeamsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(teams)).isDisplayed();
    }

    public Boolean isLocationsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locations)).isDisplayed();
    }

    public Boolean isLifeAtInsiderVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lifeAtInsider)).isDisplayed();
    }

    public void clickSeeAllQaJobs() {
        wait.until(ExpectedConditions.elementToBeClickable(seeAllQaJobs)).click();
    }

    public void clickFilterByLocation() {
        wait.until(driver -> {
            driver.findElement(filterByLocation).click();
            return ExpectedConditions.visibilityOfElementLocated(locationFilterOption).apply(driver);
        });

        wait.until(ExpectedConditions.elementToBeClickable(locationFilterOption)).click();
    }

    public void clickFilterByDepartment() {
        wait.until(ExpectedConditions.elementToBeClickable(filterByDepartment)).click();
        wait.until(ExpectedConditions.elementToBeClickable(departmentFilterOption)).click();
    }

    public void checkJobList(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(secondJobList));
    }

    public Boolean isJobList(String text) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(jobList)).getText().contains(text);
    }

//    public void checkViewRole() {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(viewRole));
//    }
//
//    public void clickViewRole() {
//        WebElement viewRoleElement = wait.until(ExpectedConditions.elementToBeClickable(viewRole));
//        viewRoleElement.click();
//
//    }
}
