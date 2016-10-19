package app.pages;

import app.domain_entities.User;
import base_page_obj.BasePage;
import driver_and_server.webdriver_manager.WebDriverManager;
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

    public HomePage(WebDriverManager driverManager, LoadableComponent<BasePage> parent) {
        super(driverManager);
        this.parent = parent;
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
