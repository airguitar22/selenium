package app.pages;

import app.domainentities.User;
import basepageobj.BasePage;
import driverandserver.webdrivermanager.WebDriverManager;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.List;
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

    @FindBy(css = "table[id='appTabList'] > tbody > tr > td > a")
    public List<WebElement> tabs;
    // TODO: Split out tabs into individual objects

    public HomePage(WebDriverManager driverManager, LoadableComponent<BasePage> parent) {
        super(driverManager);
        this.parent = parent;
    }

    public boolean isMyInstitutionLoaded() {
        return waitManager.waitUntilTitleContains("");
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

    public boolean isOutcomesAssessmentLoaded() {
        return waitManager.waitUntilTitleContains("Assessment");
    }

    @Override
    protected void load() {
        if (this.parent instanceof LoginPage) {
            parent.get();
            ((LoginPage)parent).enterCredentials(User.USERNAME);
        } else {
            parent.get();
        }
        waitManager.waitForElementToBeVisible(logout);
    }

    @Override
    protected void isLoaded() throws Error{
        assertThat(driverManager.getPageTitle(), containsString("Notifications Dashboard"));
    }
}
