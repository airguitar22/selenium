package basetest;

import driverandserver.BrowserCapabilities;
import driverandserver.RunLocationFactory;
import driverandserver.WebDriverFactory;
import driverandserver.remote.sauce.SauceJobManager;
import driverandserver.webdrivermanager.WebDriverManager;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import properties.FinalProperties;
import reporting.PageSourceHtmlCapture;
import reporting.Screenshot;
import requirementscoverage.RequirementsCoverage;
import testcontext.AllureEnvPropertiesInitializer;
import testcontext.PropertiesInitializer;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by Rich Mischler on 9/22/16.
 * Description: Abstract base class that generates the test context based upon requested properties.
 *
 * Included Steps:
 * 1. Compiles requested properties from xml and cli
 * 2. Creates WebDriver instance for requested run location (local, sauce).
 *
 * Optional Steps:
 * 1. Initialize the SauceJobManager (along with the test watcher to mark passes/fails).
 */
@RunWith(Parameterized.class)
public abstract class BaseTest {

    private static final File CONFIG_FILE =
            Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "config.xml").toFile();
    private static final File ALLURE_ENV_FILE =
            Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "environment.properties").toFile();

    public static FinalProperties finalProperties;

    @Rule
    public TestName testName = new TestName();
    protected WebDriver driver;
    protected WebDriverManager driverManager;
    protected RequirementsCoverage requirementsCoverage;
    protected String homePageUrl;
    protected String adminUsername;
    protected String adminPassword;

    private WebDriverFactory webDriverFactory;
    private SauceJobManager sauceJobManager;

    @Rule
    public TestWatcher watcher = new TestWatcher() {

        @Override
        protected void succeeded(Description description) {
            sauceJobManager.flagTestPassed();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            sauceJobManager.flagTestFailed();
        }
    };

    public BaseTest(String browserName, String browserVersion, String osName, String osVersion, String device) {
        BrowserCapabilities browserCapabilities = new BrowserCapabilities(browserName, browserVersion, osName, osVersion, device);
        RunLocationFactory runLocationFactory = new RunLocationFactory(finalProperties, browserCapabilities);
        webDriverFactory = runLocationFactory.getWebDriverFactoryForLocation();
        new AllureEnvPropertiesInitializer(ALLURE_ENV_FILE, finalProperties).initAllureEnvironmentProps();
        homePageUrl = finalProperties.get("home-page-url");
    }

    //Gets the properties and browser configurations from the Config File for use on all Test classes
    @Parameterized.Parameters(name = "{0} {1}")
    public static Collection parameters() throws ConfigurationException {
        finalProperties = new PropertiesInitializer(CONFIG_FILE).initRequestedProperties();
        return finalProperties.getBrowserConfigurations();
    }

    @Before
    public void baseSetUp() {
        requirementsCoverage = new RequirementsCoverage();
        WebDriver driver = webDriverFactory.getWebDriverSession();
        sauceJobManager = new SauceJobManager(finalProperties, driver);
        sauceJobManager.addTestName(testName.getMethodName());

        driverManager = WebDriverManager.createInstance(driver);
        this.driver = driverManager.getDriver();
        this.adminUsername = finalProperties.get("admin-username");
        this.adminPassword = finalProperties.get("admin-password");
    }

    @After
    public void tearDown() {
        new Screenshot(driverManager.getScreenshotShooter(), "After test window").captureScreenshot();
        new PageSourceHtmlCapture(driver.getPageSource()).capturePageSource();
        driver.quit();
    }
}