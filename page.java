package tct.pages;

import java.util.concurrent.TimeUnit;
import java.util.LinkedHashMap;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import java.util.function.Function;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import java.io.Serializable;
import tct.base.TestBase;


public class page extends TestBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    @FindBy(id = "displayNameDiv")
    protected WebElement displayNameDiv;
    @FindBy(id = "panelErr")
    protected WebElement panelErr;
    @FindBy(id = "zone")
    protected WebElement zone;
    @FindBy(id = "zoneLbl")
    protected WebElement zoneLbl;
    @FindBy(id = "zoneErr")
    protected WebElement zoneErr;
    @FindBy(id = "instance")
    protected WebElement instance;
    @FindBy(id = "instanceLbl")
    protected WebElement instanceLbl;
    @FindBy(id = "instanceErr")
    protected WebElement instanceErr;
    @FindBy(id = "startTime")
    protected WebElement startTime;
    @FindBy(id = "startTimeLbl")
    protected WebElement startTimeLbl;
    @FindBy(id = "startTimeErr")
    protected WebElement startTimeErr;
    @FindBy(id = "endTime")
    protected WebElement endTime;
    @FindBy(id = "endTimeLbl")
    protected WebElement endTimeLbl;
    @FindBy(id = "endTimeErr")
    protected WebElement endTimeErr;
    @FindBy(id = "feature")
    protected WebElement feature;
    @FindBy(id = "featureLbl")
    protected WebElement featureLbl;
    @FindBy(id = "featureErr")
    protected WebElement featureErr;
    @FindBy(id = "update")
    protected WebElement update;
    @FindBy(id = "save")
    protected WebElement save;
    @FindBy(id = "revert")
    protected WebElement revert;
    @FindBy(id = "delete")
    protected WebElement delete;
    @FindBy(id = "cstProceed")
    protected WebElement continueBtn;
    @FindBy(id = "cstCancel")
    protected WebElement cstCancel;
    @FindBy(id = "back")
    protected WebElement back;
    
    public page() {
        PageFactory.initElements(page.driver, (Object)this);
    }
    
    public WebElement getDisplayNameDiv() {
        return this.displayNameDiv;
    }
    
    public void setDisplayNameDiv(final WebElement displayNameDiv) {
        this.displayNameDiv = displayNameDiv;
    }
    
    public WebElement getPanelErr() {
        return this.panelErr;
    }
    
    public void setPanelErr(final WebElement panelErr) {
        this.panelErr = panelErr;
    }
    
    public WebElement getZone() {
        return this.zone;
    }
    
    public void setZone(final WebElement zone) {
        this.zone = zone;
    }
    
    public WebElement getZoneLbl() {
        return this.zoneLbl;
    }
    
    public void setZoneLbl(final WebElement zoneLbl) {
        this.zoneLbl = zoneLbl;
    }
    
    public WebElement getZoneErr() {
        return this.zoneErr;
    }
    
    public void setZoneErr(final WebElement zoneErr) {
        this.zoneErr = zoneErr;
    }
    
    public WebElement getInstance() {
        return this.instance;
    }
    
    public void setInstance(final WebElement instance) {
        this.instance = instance;
    }
    
    public WebElement getInstanceLbl() {
        return this.instanceLbl;
    }
    
    public void setInstanceLbl(final WebElement instanceLbl) {
        this.instanceLbl = instanceLbl;
    }
    
    public WebElement getInstanceErr() {
        return this.instanceErr;
    }
    
    public void setInstanceErr(final WebElement instanceErr) {
        this.instanceErr = instanceErr;
    }
    
    public WebElement getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final WebElement startTime) {
        this.startTime = startTime;
    }
    
    public WebElement getStartTimeLbl() {
        return this.startTimeLbl;
    }
    
    public void setStartTimeLbl(final WebElement startTimeLbl) {
        this.startTimeLbl = startTimeLbl;
    }
    
    public WebElement getStartTimeErr() {
        return this.startTimeErr;
    }
    
    public void setStartTimeErr(final WebElement startTimeErr) {
        this.startTimeErr = startTimeErr;
    }
    
    public WebElement getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final WebElement endTime) {
        this.endTime = endTime;
    }
    
    public WebElement getEndTimeLbl() {
        return this.endTimeLbl;
    }
    
    public void setEndTimeLbl(final WebElement endTimeLbl) {
        this.endTimeLbl = endTimeLbl;
    }
    
    public WebElement getEndTimeErr() {
        return this.endTimeErr;
    }
    
    public void setEndTimeErr(final WebElement endTimeErr) {
        this.endTimeErr = endTimeErr;
    }
    
    public WebElement getFeature() {
        return this.feature;
    }
    
    public void setFeature(final WebElement feature) {
        this.feature = feature;
    }
    
    public WebElement getFeatureLbl() {
        return this.featureLbl;
    }
    
    public void setFeatureLbl(final WebElement featureLbl) {
        this.featureLbl = featureLbl;
    }
    
    public WebElement getFeatureErr() {
        return this.featureErr;
    }
    
    public void setFeatureErr(final WebElement featureErr) {
        this.featureErr = featureErr;
    }
    
    public WebElement getUpdate() {
        return this.update;
    }
    
    public void setUpdate(final WebElement update) {
        this.update = update;
    }
    
    public WebElement getSave() {
        return this.save;
    }
    
    public void setSave(final WebElement save) {
        this.save = save;
    }
    
    public WebElement getRevert() {
        return this.revert;
    }
    
    public void setRevert(final WebElement revert) {
        this.revert = revert;
    }
    
    public WebElement getDelete() {
        return this.delete;
    }
    
    public void setDelete(final WebElement delete) {
        this.delete = delete;
    }
    
    public WebElement getContinueBtn() {
        return this.continueBtn;
    }
    
    public void setContinueBtn(final WebElement continueBtn) {
        this.continueBtn = continueBtn;
    }
    
    public WebElement getCstCancel() {
        return this.cstCancel;
    }
    
    public void setCstCancel(final WebElement cstCancel) {
        this.cstCancel = cstCancel;
    }
    
    public WebElement getBack() {
        return this.back;
    }
    
    public void setBack(final WebElement back) {
        this.back = back;
    }
    
    @Override
    public boolean isClickable(final WebElement webElement) {
        try {
            final WebDriverWait webDriverWait = new WebDriverWait(page.driver, 5L);
            webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public void clickonElement(final String webElement) {
        final WebElement element = page.driver.findElement(By.id(webElement.split("id:")[1].replaceAll("]", "").trim()));
        final Actions actions = new Actions(page.driver);
        actions.moveToElement(element).click().perform();
    }
    
    public LinkedHashMap<String, String> getLabelsOfAnonymousLicense() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = new LinkedHashMap<String, String>();
        this.clickonElement(this.zoneLbl.toString());
        valuesAnonymousLicense.put("zoneLbl", this.zoneLbl.getText());
        this.clickonElement(this.instanceLbl.toString());
        valuesAnonymousLicense.put("instanceLbl", this.instanceLbl.getText());
        this.clickonElement(this.startTimeLbl.toString());
        valuesAnonymousLicense.put("startTimeLbl", this.startTimeLbl.getText());
        this.clickonElement(this.endTimeLbl.toString());
        valuesAnonymousLicense.put("endTimeLbl", this.endTimeLbl.getText());
        this.clickonElement(this.featureLbl.toString());
        valuesAnonymousLicense.put("featureLbl", this.featureLbl.getText());
        return valuesAnonymousLicense;
    }
    
    public LinkedHashMap<String, String> getErrorsOfPage() {
        page.driver.manage().timeouts().implicitlyWait(500L, TimeUnit.MILLISECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = new LinkedHashMap<String, String>();
        if (page.driver.findElements(By.id("panelErr")).size() != 0) {
            valuesAnonymousLicense.put("panelErr", this.panelErr.getText());
        }
        if (page.driver.findElements(By.id("zoneErr")).size() != 0) {
            valuesAnonymousLicense.put("zoneErr", this.zoneErr.getText());
        }
        if (page.driver.findElements(By.id("instanceErr")).size() != 0) {
            valuesAnonymousLicense.put("instanceErr", this.instanceErr.getText());
        }
        if (page.driver.findElements(By.id("startTimeErr")).size() != 0) {
            valuesAnonymousLicense.put("startTimeErr", this.startTimeErr.getText());
        }
        if (page.driver.findElements(By.id("endTimeErr")).size() != 0) {
            valuesAnonymousLicense.put("endTimeErr", this.endTimeErr.getText());
        }
        if (page.driver.findElements(By.id("featureErr")).size() != 0) {
            valuesAnonymousLicense.put("featureErr", this.featureErr.getText());
        }
        return valuesAnonymousLicense;
    }
    
    public boolean createpage() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = this.getErrorsOfPage();
        if (valuesAnonymousLicense.size() == 0) {
            this.update.click();
            return true;
        }
        return false;
    }
    
    public boolean updatePage() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = this.getErrorsOfPage();
        if (valuesAnonymousLicense.size() == 0) {
            this.update.click();
            return true;
        }
        return false;
    }
    
    public boolean mandatoryPageValidation() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = this.getErrorsOfPage();
        if (valuesAnonymousLicense.size() == 0) {
            this.update.click();
            return true;
        }
        return false;
    }
    
    public boolean nonMandatoryPageValidation() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        final LinkedHashMap<String, String> valuesAnonymousLicense = this.getErrorsOfPage();
        if (valuesAnonymousLicense.size() != 0) {
            this.update.click();
            final boolean varupdate = this.update.isEnabled();
            return varupdate;
        }
        return false;
    }
    
    public void unsavedWithClose() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        this.back.click();
        this.cstCancel.click();
    }
    
    public void unsavedWithContinue() {
        page.driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        this.back.click();
        this.continueBtn.click();
    }
}

