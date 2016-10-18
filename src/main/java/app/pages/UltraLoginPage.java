package app.pages;

import app.domain_entities.User;
import base_page_obj.BasePage;
import driver_and_server.webdriver_manager.WebDriverManager;
import io.appium.java_client.pagefactory.WithTimeout;

import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.allure.annotations.Step;

import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Rich Mischler on 10/13/16.
 *
 * Description:
 */

public class UltraLoginPage extends BasePage {

    private String homePageUrl;

    @FindBy(id = "user_id")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    private WebElement userName;

    @FindBy(id = "password")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    private WebElement password;

    @FindBy(id = "entry-login")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement signIn;

    @FindBy(id = "loginErrorMessage")
    @WithTimeout(time = 60, unit = TimeUnit.SECONDS)
    public WebElement loginError;

    public UltraLoginPage(WebDriverManager webDriverManager, String homePageUrl) {
        super(webDriverManager);
        this.homePageUrl = homePageUrl;
    }

    @Override
    protected void load() {
        driverManager.navigateToUrl(homePageUrl);
    }

    @Override
    protected void isLoaded() throws Error {
        assertThat(driverManager.getPageTitle(), containsString("Blackboard Learn"));
        assertThat(driverManager.getPageSource(), containsString("Create an Account"));
    }

    @Step
    public void enterCredentials(User user) {
        this.userName.sendKeys(user.getUser());
        this.password.sendKeys(user.getPassword());
    }

    @Step
    public void clickSignIn() {
        signIn.click();
    }
}
