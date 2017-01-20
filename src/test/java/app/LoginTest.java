package app;

import api.LearnApi;
import app.pages.HomePage;
import org.junit.Before;
import org.junit.Test;

import app.pages.LoginPage;
import basetest.BaseTest;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

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
        loginPage = new LoginPage(driverManager, homePageUrl);
        loginPage.get();
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F001")
    @Test
    public void whenEnterValidCredentialsThenSignInSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F001 -- login using valid credentials");

        loginPage.enterCredentials(adminUsername, adminPassword);
        homePage = new HomePage(driverManager, null);
        assertTrue(homePage.logout.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F002")
    @Test
    public void whenEnterInvalidPasswordThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F002 -- login using invalid password");

        loginPage.enterCredentials(adminUsername, "blabla");
        assertTrue(loginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F003")
    @Test
    public void whenEnterInvalidUsernameThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F003 -- login using invalid username");

        loginPage.enterCredentials("blabla", adminPassword);
        assertTrue(loginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F004")
    @Test
    public void whenGetCurrentUserThenMatches() {
        requirementsCoverage.writeToFile("ULTRA-1001-F004 -- get current user info from api");

        LearnApi learnApi = new LearnApi(homePageUrl, adminUsername, adminPassword);
        assertThat(learnApi.usersEndpoint.getCurrentUser().getUserName(), equalTo(adminUsername));
    }
}
