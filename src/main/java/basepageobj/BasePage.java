package basepageobj;

import driverandserver.webdrivermanager.WebDriverManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.LoadableComponent;
import wdwrappers.ElementActions;
import wdwrappers.WaitManager;

/**
 * Created by Rich Mischler on 9/22/16.
 *
 * Description: Abstract base class from which all page objects inherit.
 */

public abstract class BasePage extends LoadableComponent<BasePage> {

    protected WebDriverManager driverManager;
    protected WaitManager waitManager;
    protected ElementActions elActions;

    public BasePage(WebDriverManager driverManager) {
        this.driverManager = driverManager;
        this.waitManager = new WaitManager(driverManager.createWait(30));
        Actions actions = driverManager.getActionsObj();
        this.elActions = new ElementActions(waitManager, actions);
        driverManager.initPageElementsWithImplicitWait(this, 30);
    }
}
