package app;

import app.domainentities.User;
import app.pages.HomePage;
import org.junit.Before;
import org.junit.Test;

import app.pages.LoginPage;
import basetest.BaseTest;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static org.junit.Assert.assertTrue;

/**
 * Created by Rich Mischler on 10/13/16.
 *
 * Description: Tests valid and invalid login to the site.
 */

public class LoginTest extends BaseTest {

    public LoginTest(String browserName, String browserVersion, String osName, String osVersion, String device) {
        super(browserName, browserVersion, osName, osVersion, device);
    }

    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driverManager, homePageUrl);
        loginPage.get();
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F001")
    @Test
    public void whenEnterValidCredentialsThenSignInSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F001 -- login using valid credentials");

        loginPage.enterCredentials(User.USERNAME);
        homePage = new HomePage(driverManager, null);

        assertTrue(homePage.logout.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F002")
    @Test
    public void whenEnterInvalidPasswordThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F002 -- login using invalid password");

        loginPage.enterCredentials(User.INVALID_PASSWORD);
        assertTrue(loginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F003")
    @Test
    public void whenEnterInvalidUsernameThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F003 -- login using invalid username");

        loginPage.enterCredentials(User.INVALID_USERNAME);
        assertTrue(loginPage.loginError.isDisplayed());
    }
}
