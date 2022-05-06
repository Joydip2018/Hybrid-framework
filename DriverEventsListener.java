/*package tct.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class DriverEventsListener extends TestListener implements WebDriverEventListener
{
    private static final Logger logger;
    
    static {
        logger = LogManager.getLogger((Class)TestListener.class);
    }
    
    public DriverEventsListener() {
        PageFactory.initElements(DriverEventsListener.driver, (Object)this);
    }
    
    private String getDriverName(final WebDriver driver) {
        String DRIVER_NAME = null;
        try {
            if (driver.toString().toLowerCase().contains("chromedriver") || driver.toString().toLowerCase().contains("remotewebdriver: chrome")) {
                DRIVER_NAME = "ChromeDriver";
            }
            else if (driver.toString().toLowerCase().contains("internetexplorerdriver") || driver.toString().toLowerCase().contains("remotewebdriver: internet explorer")) {
                DRIVER_NAME = "IEDriver";
            }
            else if (driver.toString().toLowerCase().contains("edgedriver") || driver.toString().toLowerCase().contains("remotewebdriver: microsoftedge")) {
                DRIVER_NAME = "EdgeDriver";
            }
            else {
                DRIVER_NAME = driver.toString();
            }
        }
        catch (Exception e) {
            DriverEventsListener.logger.info((Object)("Exception getting driver name :" + e.getMessage()));
        }
        return DRIVER_NAME;
    }
    
    public void afterAlertAccept(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + "-> Accepted the alert"));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterAlertDismiss(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + "-> Dismissed the alert"));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterChangeValueOf(final WebElement we, final WebDriver driver, final CharSequence[] arg2) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " changed the value of WebElement: " + we));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterClickOn(final WebElement we, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        if (!DRIVER_NAME.toLowerCase().contains("ie") && !DriverEventsListener.testcasename.equalsIgnoreCase("enterPasswordAndCloseTab")) {
            this.waitTillLoader();
        }
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Clicked on WebElement: " + we));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterFindBy(final By by, final WebElement we, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Found WebElement -> By: " + by));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public <X> void afterGetScreenshotAs(final OutputType<X> arg0, final X arg1) {
        DriverEventsListener.logger.info((Object)"+SUCCESSFULLY Captured screen shot");
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterGetText(final WebElement we, final WebDriver driver, final String retrivedText) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Retrieved the text: " + retrivedText + " from WebElement: " + we));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterNavigateBack(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Navigated back"));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterNavigateForward(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Navigated forward"));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterNavigateRefresh(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+SUCCESSFULLY " + DRIVER_NAME + " Refreshed the current page"));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterNavigateTo(final String url, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("SUCCESSFULLY " + DRIVER_NAME + " Navigated to url:: " + url));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterScript(final String js, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("SUCCESSFULLY " + DRIVER_NAME + " Executed the javascript: " + js));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void afterSwitchToWindow(final String windowName, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("SUCCESSFULLY " + DRIVER_NAME + " Switched to the window: " + windowName));
        DriverEventsListener.logger.info((Object)"---------------------------------------------------------------------");
    }
    
    public void beforeAlertAccept(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Accepting alert"));
    }
    
    public void beforeAlertDismiss(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Dismissing alert"));
    }
    
    public void beforeChangeValueOf(final WebElement we, final WebDriver driver, final CharSequence[] arg2) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Changing value of WebElement: " + we));
    }
    
    public void beforeClickOn(final WebElement we, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Clicking on WebElement: " + we));
    }
    
    public void beforeFindBy(final By by, final WebElement we, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Finding WebElement -> By: " + by));
    }
    
    public <X> void beforeGetScreenshotAs(final OutputType<X> arg0) {
        DriverEventsListener.logger.info((Object)"+Capturing screenshot");
    }
    
    public void beforeGetText(final WebElement we, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Getting text of WebElement: " + we));
    }
    
    public void beforeNavigateBack(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Navigating back"));
    }
    
    public void beforeNavigateForward(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Navigating forward"));
    }
    
    public void beforeNavigateRefresh(final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Refreshing current page"));
    }
    
    public void beforeNavigateTo(final String arg0, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Navigating to url:: " + arg0));
    }
    
    public void beforeScript(final String js, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Executing javascript: " + js));
    }
    
    public void beforeSwitchToWindow(final String windowName, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> Switching to window: " + windowName));
    }
    
    public void onException(final Throwable e, final WebDriver driver) {
        final String DRIVER_NAME = this.getDriverName(driver);
        DriverEventsListener.logger.info((Object)("+" + DRIVER_NAME + " -> FAILED: Exception: " + e.getMessage()));
    }
}
*/