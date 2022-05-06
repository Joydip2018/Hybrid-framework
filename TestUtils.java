package tct.utils;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import java.io.File;

public class TestUtils
{
    public static final String EXTENTREPORTS = "ExtentReports";
    public static final String EXTENTREPORT_PATH;
    public static final String SCREENSHOTS = "ErrorScreenshots";
    public static final String ERROR_SCREENSHOTS_PATH;
    public static final String ERROR_SCREENSHOTS_EMBED_PATH;
    public static String EXTENTREPORTS_NAME;
    public static String SUITENAME;
    public static final String DATETIME_FORMAT = "MM-dd-yyyy-HH-mm-ss-SSS";
    
    static {
        EXTENTREPORT_PATH = "." + File.separator + "ExtentReports" + File.separator + getDateAsString() + File.separator;
        ERROR_SCREENSHOTS_PATH = String.valueOf(TestUtils.EXTENTREPORT_PATH) + File.separator + "ErrorScreenshots" + File.separator;
        ERROR_SCREENSHOTS_EMBED_PATH = "." + File.separator + "ErrorScreenshots" + File.separator;
        TestUtils.EXTENTREPORTS_NAME = "";
        TestUtils.SUITENAME = "";
    }
    
    public static String capture(final WebDriver driver, final ITestResult iTestResult) throws IOException {
        final TakesScreenshot ts = (TakesScreenshot)driver;
        final File source = (File)ts.getScreenshotAs(OutputType.FILE);
        final String screenShotName = composeTestName(iTestResult);
        final String dest = String.valueOf(TestUtils.ERROR_SCREENSHOTS_PATH) + screenShotName + ".png";
        final String embed_dest = String.valueOf(TestUtils.ERROR_SCREENSHOTS_EMBED_PATH) + screenShotName + ".png";
        final File destination = new File(dest);
        FileUtils.copyFile(source, destination);
        return embed_dest;
    }
    
    public static String composeTestName(final ITestResult iTestResult) {
        final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss-SSS");
        final Date date = new Date();
        final StringBuffer completeFileName = new StringBuffer();
        completeFileName.append(iTestResult.getTestClass().getRealClass().getSimpleName());
        completeFileName.append("_");
        completeFileName.append(iTestResult.getName());
        completeFileName.append("_");
        completeFileName.append(dateFormat.format(date));
        return completeFileName.toString().replace(":", "-");
    }
    
    public static String getDateAsString() {
        final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss-SSS");
        final Date date = new Date();
        return dateFormat.format(date);
    }
}
