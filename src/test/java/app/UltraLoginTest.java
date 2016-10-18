package app;

import app.domain_entities.User;
import org.junit.Before;
import org.junit.Test;

import app.pages.UltraLoginPage;
import base_test.BaseTest;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rich Mischler on 10/13/16.
 *
 * Description: Tests valid and invalid login to the site.
 */

public class UltraLoginTest extends BaseTest {

    public UltraLoginTest(String browserName, String browserVersion, String osName, String osVersion, String device) {
        super(browserName, browserVersion, osName, osVersion, device);
    }

    private UltraLoginPage ultraLoginPage;

    @Before
    public void setUp() {
        super.setUp();
        ultraLoginPage = new UltraLoginPage(driverManager, homePageUrl);
        ultraLoginPage.get();
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F001")
    @Test
    public void whenEnterValidCredentialsThenSignInSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F001 -- login using valid credentials");

        ultraLoginPage.enterCredentials(User.USERNAME);
        ultraLoginPage.clickSignIn();
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F002")
    @Test
    public void whenEnterInvalidPasswordThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F002 -- login using invalid password");

        ultraLoginPage.enterCredentials(User.INVALID_PASSWORD);
        ultraLoginPage.clickSignIn();
        assertTrue(ultraLoginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F003")
    @Test
    public void whenEnterInvalidUsernameThenSignInUnsuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1001-F003 -- login using invalid username");

        ultraLoginPage.enterCredentials(User.INVALID_USERNAME);
        ultraLoginPage.clickSignIn();
        assertTrue(ultraLoginPage.loginError.isDisplayed());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F004")
    @Test
    public void whenLeavePasswordBlankThenSignInDisabled() {
        requirementsCoverage.writeToFile("ULTRA-1001-F004 -- login using blank password");

        ultraLoginPage.enterCredentials(User.BLANK_PASSWORD);
        assertFalse(ultraLoginPage.signIn.isEnabled());
    }

    @Features("ULTRA-1001")
    @Stories("ULTRA-1001-F005")
    @Test
    public void whenLeaveUsernameBlankThenSignInDisabled() {
        requirementsCoverage.writeToFile("ULTRA-1001-F005 -- login using blank username");

        ultraLoginPage.enterCredentials(User.BLANK_USERNAME);
        assertFalse(ultraLoginPage.signIn.isEnabled());
    }
}
