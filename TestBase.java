package tct.base;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
//import java.util.function.Function;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import java.util.Properties;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import java.io.Serializable;

public class TestBase implements Serializable
{
	private static final long serialVersionUID = 1L;
    public static WebDriver driver;
    public static EventFiringWebDriver eventFiringWebDriver;
    public static String REMOTE_WEBDRIVER;
    public long PAGE_LOAD_TIMEOUT;
    public long IMPLICIT_WAIT;
    public Properties prop;
    public static String USERNAME;
    public static String PASSWORD;
    public static String TESTNAME;
    public static String FilestoregePath;
    
    @FindBy(id="identifierId")
	@CacheLookup
	WebElement Username;
    @FindBy(name="password")
	@CacheLookup
	WebElement Password;
    @FindBy(xpath="//div//span[contains(text(),'Next')]")
	@CacheLookup
	WebElement Nextbtn;
    @FindBy(xpath="//span[contains(text(),'Nex')]")
	@CacheLookup
	WebElement Nextbtn1;
    @FindBy(xpath="//a[@title ='Google apps']")
	@CacheLookup
	WebElement Gbtn;
    @FindBy(xpath="//span[contains(text(),'Gmail')]")
	@CacheLookup
	WebElement Gmailbtn;
    public static long EXPLICIT_WAIT;
    
    public TestBase() {
        this.PAGE_LOAD_TIMEOUT = 50L;
        this.IMPLICIT_WAIT = 10L;
        try {
            this.prop = new Properties();
            final FileInputStream ip = new FileInputStream("./src/test/resources/properties/config.properties");
            this.prop.load(ip);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    @AfterSuite
    public void Copyfolder() {
        /*final File basereportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/report/");
        final File reportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/SeleniumReport/");
        try {
            FileUtils.forceMkdir(reportdir);
            FileUtils.copyDirectory(basereportdir, reportdir);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }*/
    }
    
    public void doSingleLogin() {
   
        TestBase.USERNAME = this.prop.getProperty("username");
        TestBase.PASSWORD = this.prop.getProperty("password");
            
        PageFactory.initElements(TestBase.driver, (Object)this);
        WebDriverWait wait=new WebDriverWait(TestBase.driver, 50);
        Username.sendKeys(USERNAME);
       /* wait.until(ExpectedConditions.visibilityOf(Nextbtn));
        Nextbtn.click();
        Password.sendKeys(PASSWORD);
        int i=0;
		while(i<1)
		{
			try
			{
				wait.until(ExpectedConditions.visibilityOf(Nextbtn1));
				Nextbtn1.click();
			}
			catch(StaleElementReferenceException e)
			{
			}
			i++;
		}*/
    }
    
    public void closeSingleApplication() {
        TestBase.driver.quit();
    }
    
    public WebDriver getDriver() {
        return TestBase.driver;
    }
    
    public EventFiringWebDriver getEventFiringWebDriver() {
        return TestBase.eventFiringWebDriver;
    }
    
    public void setupApplication(final String browser) throws Exception {
    	final String currentDir = System.getProperty("user.dir");
    	TestBase.FilestoregePath = String.valueOf(currentDir) + "\\src\\test\\resources\\seleniumdownloads\\";
        if (browser.equalsIgnoreCase("chrome")) {
            final ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("excludeSwitches", (Object)new String[] { "enable-automation" });
            chromeOptions.setExperimentalOption("useAutomationExtension", (Object)false);
            chromeOptions.setAcceptInsecureCerts(true);
            System.setProperty("webdriver.chrome.driver", "src/test/resources/driverexes/chromedriver.exe");
            TestBase.driver = (WebDriver)new ChromeDriver(chromeOptions);
            TestBase.eventFiringWebDriver = new EventFiringWebDriver(TestBase.driver);
            //TestBase.DriverEventsListener = (WebDriverEventListener)new DriverEventsListener();
            //TestBase.eventFiringWebDriver.register(TestBase.DriverEventsListener);
            TestBase.driver = (WebDriver)TestBase.eventFiringWebDriver;
            TestBase.driver.manage().deleteAllCookies();
            TestBase.driver.get(this.prop.getProperty("url"));
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            final FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setAcceptInsecureCerts(true);
            firefoxOptions.setCapability("marionette", true);
            System.setProperty("webdriver.gecko.driver", "src/test/resources/driverexes/geckodriver-v0_23_0.exe");
            TestBase.driver = (WebDriver)new FirefoxDriver(firefoxOptions);
            //TestBase.eventFiringWebDriver = new EventFiringWebDriver(TestBase.driver);
            //TestBase.DriverEventsListener = (WebDriverEventListener)new DriverEventsListener();
            //TestBase.eventFiringWebDriver.register(TestBase.DriverEventsListener);
            //TestBase.driver = (WebDriver)TestBase.eventFiringWebDriver;
            TestBase.driver.manage().deleteAllCookies();
            TestBase.driver.get(this.prop.getProperty("url"));
        }
        else if (browser.equalsIgnoreCase("edge")) {
            final EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setCapability("acceptSslCerts", true);
            edgeOptions.setCapability("acceptInsecureCerts", true);
            System.setProperty("webdriver.edge.driver", "src/test/resources/driverexes/MicrosoftWebDriver_2.exe");
            TestBase.driver = (WebDriver)new EdgeDriver();
            //TestBase.eventFiringWebDriver = new EventFiringWebDriver(TestBase.driver);
            //TestBase.DriverEventsListener = (WebDriverEventListener)new DriverEventsListener();
            //TestBase.eventFiringWebDriver.register(TestBase.DriverEventsListener);
            //TestBase.driver = (WebDriver)TestBase.eventFiringWebDriver;
            TestBase.driver.manage().deleteAllCookies();
            TestBase.driver.get(this.prop.getProperty("url"));
        }
        else {
            if (!browser.equalsIgnoreCase("ie")) {
                throw new Exception("Browser is not correct");
            }
            final InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
            internetExplorerOptions.setCapability("acceptSslCerts", true);
            internetExplorerOptions.setCapability("enablePersistentHover", false);
            internetExplorerOptions.setCapability("ignoreZoomSetting", true);
            internetExplorerOptions.setCapability("requireWindowFocus", true);
            internetExplorerOptions.setCapability("unexpectedAlertBehaviour", "accept");
            internetExplorerOptions.setCapability("ie.ensureCleanSession", true);
            System.setProperty("webdriver.ie.driver", "src/test/resources/driverexes/IEDriverServer.exe");
            TestBase.driver = (WebDriver)new InternetExplorerDriver(internetExplorerOptions);
            //TestBase.eventFiringWebDriver = new EventFiringWebDriver(TestBase.driver);
            //TestBase.DriverEventsListener = (WebDriverEventListener)new DriverEventsListener();
            //TestBase.eventFiringWebDriver.register(TestBase.DriverEventsListener);
            //TestBase.driver = (WebDriver)TestBase.eventFiringWebDriver;
            TestBase.driver.manage().deleteAllCookies();
            TestBase.driver.get(this.prop.getProperty("url"));
        }
        TestBase.driver.manage().window().maximize();
        TestBase.driver.manage().timeouts().pageLoadTimeout(this.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        TestBase.driver.manage().timeouts().implicitlyWait(this.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }
    
    public static WebElement waitUntil(final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, 5L);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }
    
    public static void explicitlyWait(final WebDriver driver, final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public static List<WebElement> waitUntillist(final List<WebElement> element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, 5L);
        wait.until(ExpectedConditions.visibilityOfAllElements((List)element));
        return element;
    }
    
    public void disablewait(final WebElement element) {
        for (int max_Limit = 150, i = 0; i < max_Limit; ++i) {
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Boolean btn = element.isEnabled();
            if (btn.equals(false)) {
                break;
            }
        }
    }
    
    public static WebElement waitUntilvisible(final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, 10L);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }
    
    public void clickWait(final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, TestBase.EXPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("Expected element is clicked");
        }
        catch (Exception e) {
            System.err.println("Element is not visible ");
            throw e;
        }
    }
    
    public void visibleWait(final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, TestBase.EXPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("Expected element is clicked");
        }
        catch (Exception e) {
            System.err.println("Element is not visible ");
            throw e;
        }
    }
    
    public void displaywait(final WebElement element) throws InterruptedException {
        for (int max_Limit = 150, i = 0; i < max_Limit; ++i) {
            Thread.sleep(2L);
            final Boolean btn = element.isDisplayed();
            if (btn.equals(true)) {
                break;
            }
        }
    }
    
    public static WebElement waitInvisible(final WebElement element) {
        final WebDriverWait wait = new WebDriverWait(TestBase.driver, 10L);
        wait.until(ExpectedConditions.invisibilityOf(element));
        return element;
    }
    
    public void clickOnElement(final String webElement) {
        final WebElement element = TestBase.driver.findElement(By.id(webElement.split("id:")[1].replaceAll("]", "").trim()));
        final Actions actions = new Actions(TestBase.driver);
        actions.moveToElement(element).click().perform();
    }
    
    public boolean checkIfToBeProcessed(final String fieldName) {
        return !this.getTestComment().contains(fieldName);
    }
    
    public void clearTextInput(final WebElement textInput, final String fieldName) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(textInput.toString());
        if (this.isClickable(textInput)) {
            textInput.sendKeys(new CharSequence[] { (CharSequence)Keys.CONTROL, "a" });
            textInput.sendKeys(new CharSequence[] { (CharSequence)Keys.BACK_SPACE });
        }
    }
    
    public boolean isClickable(final WebElement webElement) {
        try {
            final WebDriverWait webDriverWait = new WebDriverWait(TestBase.driver, 5L);
            webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public void setTextInput(final WebElement textInput, final String fieldName, final String value) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(textInput.toString());
        if (this.isClickable(textInput)) {
            textInput.sendKeys(new CharSequence[] { (CharSequence)Keys.CONTROL, "a" });
            textInput.sendKeys(new CharSequence[] { (CharSequence)Keys.BACK_SPACE });
            textInput.sendKeys(new CharSequence[] { value });
        }
    }
    
    public void readTextInput(final WebElement textInput, final String fieldName, final Map<String, String> valuesMap) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(textInput.toString());
        valuesMap.put(fieldName, textInput.getAttribute("value"));
    }
    
    public void clearComboBox(final WebElement comboBox, final String fieldName, final String value) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        final Select select = new Select(comboBox);
        select.selectByVisibleText(value);
    }
    
    public void setComboBox(final WebElement comboBox, final String fieldName, final String value) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(comboBox.toString());
        final Select select = new Select(comboBox);
        select.selectByVisibleText(value);
    }
    
    public void readComboBox(final WebElement comboBox, final String fieldName, final Map<String, String> valuesMap) {
        try {
            if (!this.checkIfToBeProcessed(fieldName)) {
                return;
            }
        }
        catch (NullPointerException e) {
            System.out.println("Test commemt Not available.");
        }
        this.clickOnElement(comboBox.toString());
        final Select select = new Select(comboBox);
        WebElement option;
        try {
            option = select.getFirstSelectedOption();
        }
        catch (NoSuchElementException e2) {
            option = comboBox.findElement(By.xpath("./.."));
        }
        valuesMap.put(fieldName, option.getText());
    }
    
    public void clearCheckBox(final WebElement checkBox, final String fieldName) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(checkBox.toString());
        if (this.isClickable(checkBox) && checkBox.isSelected()) {
            checkBox.click();
        }
    }
    
    public void setCheckBox(final WebElement checkBox, final String fieldName, final boolean value) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(checkBox.toString());
        if (this.isClickable(checkBox)) {
            if (value) {
                if (!checkBox.isSelected()) {
                    this.clickOnElement(checkBox.toString());
                }
            }
            else if (checkBox.isSelected()) {
                this.clickOnElement(checkBox.toString());
            }
        }
    }
    
    public void setCheckBox(final WebElement checkBox, final WebElement checkBoxLbl, final String fieldName, final boolean value) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(checkBoxLbl.toString());
        if (this.isClickable(checkBox)) {
            if (value) {
                if (!checkBox.isSelected()) {
                    this.clickOnElement(checkBox.toString());
                }
            }
            else if (checkBox.isSelected()) {
                this.clickOnElement(checkBox.toString());
            }
        }
    }
    
    public void readCheckBox(final WebElement checkBox, final WebElement checkBoxLbl, final String fieldName, final Map<String, String> valuesMap) {
        if (!this.checkIfToBeProcessed(fieldName)) {
            return;
        }
        this.clickOnElement(checkBoxLbl.toString());
        valuesMap.put(fieldName, Boolean.toString(checkBox.isSelected()));
    }
    
    public void readError(final WebElement errorLabel, final String errorName, final Map<String, String> valuesMap) {
        if (!this.checkIfToBeProcessed(errorName)) {
            return;
        }
        if (TestBase.driver.findElements(By.id(errorName)).size() != 0) {
            valuesMap.put(errorName, errorLabel.getText().trim());
        }
    }
    
    public void readLabel(final WebElement errorLabel, final String errorName, final Map<String, String> valuesMap) {
        if (!this.checkIfToBeProcessed(errorName)) {
            return;
        }
        this.clickOnElement(errorLabel.toString());
        if (TestBase.driver.findElements(By.id(errorName)).size() != 0) {
            valuesMap.put(errorName, errorLabel.getText().trim());
        }
    }
    
    public void readLabel1(final WebElement errorLabel, final String errorName, final Map<String, String> valuesMap) {
        if (!this.checkIfToBeProcessed(errorName)) {
            return;
        }
        this.clickOnElement(errorLabel.toString());
        final String webElement = errorLabel.toString();
        if (TestBase.driver.findElements(By.id(webElement.split("id:")[1].replaceAll("]", "").trim())).size() != 0) {
            valuesMap.put(errorName, errorLabel.getText().trim());
        }
    }
    
    public void setTestComment(final String value) {
        this.prop.setProperty("testComment", value);
    }
    
    public void resetTestComment() {
        this.setTestComment("");
    }
    
    public String getTestComment() {
        return this.prop.getProperty("testComment");
    }
    
    public static void disableWait(final WebElement element) {
        for (int max_Limit = 150, i = 0; i < max_Limit; ++i) {
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!element.isEnabled()) {
                break;
            }
        }
    }
    
    public static void unwantedPopUpClose() {
        if (TestBase.driver.findElements(By.id("cstProceed")).size() > 0 && TestBase.driver.findElement(By.id("cstProceed")).isEnabled()) {
            TestBase.driver.findElement(By.id("cstProceed")).click();
        }
        final Actions action = new Actions(TestBase.driver);
        action.sendKeys(new CharSequence[] { (CharSequence)Keys.ESCAPE }).perform();
    }
    
    public void scrollToTop(final String fieldList) {
        if (fieldList.contains(",")) {
            final String[] fields = fieldList.split(",");
            String firstField = "";
            for (int i = 0; i < fields.length; ++i) {
                firstField = fields[i].substring(0, fields[i].indexOf("_"));
                if (this.checkIfToBeProcessed(firstField)) {
                    this.scrollToElementAndClick(firstField);
                    break;
                }
            }
        }
        else {
            this.scrollToElementAndClick(fieldList);
        }
    }
    
    public void scrollToElementAndClick(final String id) {
        final WebElement element = TestBase.driver.findElement(By.id(id));
        final Actions actions = new Actions(TestBase.driver);
        try {
            actions.moveToElement(element).click().perform();
        }
        catch (StaleElementReferenceException e) {
            actions.moveToElement(element).click().perform();
        }
    }
    
    /*public void waitTillLoader() {
        for (int max_Limit = 150, i = 0; i < max_Limit; ++i) {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException ex) {}
            Boolean btn = this.getLoader().isDisplayed();
            if (btn.equals(false)) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex2) {}
                btn = this.getLoader().isDisplayed();
                if (btn.equals(false)) {
                    break;
                }
            }
        }
    }*/
 
}