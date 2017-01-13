package app.pages;

import basepageobj.BasePage;
import driverandserver.webdrivermanager.WebDriverManager;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Rich Mischler on 10/19/16.
 */
public class HomePage extends BasePage {

    private LoadableComponent parent;

    @FindBy(id = "topframe.logout.label")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement logout;

    @FindBy(xpath = "//span[contains(text(), 'My Institution')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement myInstitution;

    @FindBy(xpath = "//span[contains(text(), 'Courses')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement courses;

    @FindBy(xpath = "//span[contains(text(), 'Community')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement community;

    @FindBy(xpath = "//span[contains(text(), 'Content Collection')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement contentCollection;

    @FindBy(xpath = "//span[contains(text(), 'Services')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement services;

    @FindBy(xpath = "//span[contains(text(), 'System Admin')]")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement systemAdmin;

    public HomePage(WebDriverManager driverManager, LoadableComponent<BasePage> parent) {
        super(driverManager);
        this.parent = parent;
    }

    public boolean isMyInstitutionLoaded() {
        return waitManager.waitUntilTitleContains("My Institution");
    }

    public boolean isCoursesLoaded() {
        return waitManager.waitUntilTitleContains("Courses");
    }

    public boolean isCommunityLoaded() {
        return waitManager.waitUntilTitleContains("Community");
    }

    public boolean isContentCollectionLoaded() {
        return waitManager.waitUntilTitleContains("Content");
    }

    public boolean isServicesLoaded() {
        return waitManager.waitUntilTitleContains("Services");
    }

    public boolean isSystemAdminLoaded() {
        return waitManager.waitUntilTitleContains("System Admin");
    }

    @Override
    protected void load() {
        if (this.parent instanceof LoginPage) {
            parent.get();
            ((LoginPage)parent).enterCredentials(adminUsername, adminPassword);
        } else {
            parent.get();
        }
        waitManager.waitForElementToBeVisible(logout);
    }

    @Override
    protected void isLoaded() throws Error{
        assertThat(driver.getTitle(), containsString("My Institution"));
    }
}
