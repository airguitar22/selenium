package app;

import app.domain_entities.User;
import org.junit.Before;
import org.junit.Test;

import app.pages.LearnLoginPage;
import base_test.BaseTest;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static org.junit.Assert.assertTrue;

/**
 * Created by Rich Mischler on 10/13/16.
 *
 * Description: Tests valid and invalid login to the site.
 */

public class LearnLoginTest extends BaseTest {

    public LearnLoginTest(String browserName, String browserVersion, String osName, String osVersion, String device) {
        super(browserName, browserVersion, osName, osVersion, device);
    }

    private LearnLoginPage learnLoginPage;

    @Before
    public void setUp() {
        super.setUp();
        learnLoginPage = new LearnLoginPage(driverManager, homePageUrl);
        learnLoginPage.get();
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F001")
    @Test
    public void whenEnterValidCredentialsThenSignInSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F001 -- login using valid credentials");

        learnLoginPage.enterCredentials(User.USERNAME);
        assertTrue(learnLoginPage.logout.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F002")
    @Test
    public void whenEnterInvalidPasswordThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F002 -- login using invalid password");

        learnLoginPage.enterCredentials(User.INVALID_PASSWORD);
        assertTrue(learnLoginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F003")
    @Test
    public void whenEnterInvalidUsernameThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F003 -- login using invalid username");

        learnLoginPage.enterCredentials(User.INVALID_USERNAME);
        assertTrue(learnLoginPage.loginError.isDisplayed());
    }
}
