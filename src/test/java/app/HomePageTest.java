package app;

import app.pages.HomePage;
import app.pages.LoginPage;
import basetest.BaseTest;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by Rich Mischler on 10/19/16.
 *
 * Description: Tests out the home page functions of the site.
 */
public class HomePageTest extends BaseTest {

    public HomePageTest(String browserName, String browserVersion, String osName, String osVersion, String device) {
        super(browserName, browserVersion, osName, osVersion, device);
    }

    private HomePage homePage;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driverManager, homePageUrl);
        homePage = new HomePage(driverManager, loginPage);
        homePage.get();
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F001")
    @Test
    public void whenClickLogoutThenLogoutSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F001 -- logout of app");

        // TODO: Remove this line and accept the alert once ULTRA-18179 is in
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        homePage.logout.click();
        assertTrue(loginPage.signIn.isDisplayed());
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F002")
    @Test
    public void whenClickCoursesTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F002 -- nav to courses tab");

        homePage.tabs.get(1).click();
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F003")
    @Test
    public void whenClickCommunityTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F003 -- nav to community tab");

        homePage.tabs.get(2).click();
        WebDriverWait wait = driverManager.createWait(10);
        assertTrue(wait.until(ExpectedConditions.titleContains("Community")));
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F004")
    @Test
    public void whenClickContentCollectionTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F004 -- nav to content collection tab");

        homePage.tabs.get(3).click();
        WebDriverWait wait = driverManager.createWait(10);
        assertTrue(wait.until(ExpectedConditions.titleContains("Content")));
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F005")
    @Test
    public void whenClickServicesTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F005 -- nav to services tab");

        homePage.tabs.get(4).click();
        WebDriverWait wait = driverManager.createWait(10);
        assertTrue(wait.until(ExpectedConditions.titleContains("Services")));
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F006")
    @Test
    public void whenClickSystemAdminTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F006 -- nav to system admin tab");

        homePage.tabs.get(5).click();
        WebDriverWait wait = driverManager.createWait(20);
        assertTrue(wait.until(ExpectedConditions.titleContains("System Admin")));
    }

    @Features("ULTRA-1002")
    @Stories("ULTRA-1002-F007")
    @Test
    public void whenClickOutcomesAssessmentTabContainerDisplaysSuccessful() {
        requirementsCoverage.writeToFile("ULTRA-1002-F007 -- nav to outcomes assessment tab");

        homePage.tabs.get(6).click();
        WebDriverWait wait = driverManager.createWait(10);
        assertTrue(wait.until(ExpectedConditions.titleContains("Assessment")));
    }
}
