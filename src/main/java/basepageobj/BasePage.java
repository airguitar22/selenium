package basepageobj;

import driverandserver.webdrivermanager.WebDriverManager;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.LoadableComponent;
import properties.FinalProperties;
import testcontext.PropertiesInitializer;
import wdwrappers.ElementActions;
import wdwrappers.WaitManager;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Rich Mischler on 9/22/16.
 *
 * Description: Abstract base class from which all page objects inherit.
 */

public abstract class BasePage extends LoadableComponent<BasePage> {

    private static final File CONFIG_FILE =
            Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "config.xml").toFile();

    protected WebDriver driver;
    protected WebDriverManager driverManager;
    protected WaitManager waitManager;
    protected ElementActions elActions;

    public static FinalProperties finalProperties;
    protected String adminUsername;
    protected String adminPassword;

    public BasePage(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driverManager.getDriver();
        this.waitManager = new WaitManager(driverManager.createWait(30));
        Actions actions = driverManager.getActionsObj();
        this.elActions = new ElementActions(waitManager, actions);
        driverManager.initPageElementsWithImplicitWait(this, 30);

        //Gets the properties from the Config File for use on all Page Object classes
        try {
            finalProperties = new PropertiesInitializer(CONFIG_FILE).initRequestedProperties();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        this.adminUsername = finalProperties.get("admin-username");
        this.adminPassword = finalProperties.get("admin-password");
    }
}
