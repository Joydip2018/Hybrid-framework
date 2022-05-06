package tct.utils;

import org.openqa.selenium.WebDriver;
import java.io.IOException;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.Status;
import org.testng.ITestResult;
import org.testng.IRetryAnalyzer;
import tct.base.TestBase;

public class RetryAnalyzer implements IRetryAnalyzer
{
    private static int maxTry;
    private int count;
    
    static {
        RetryAnalyzer.maxTry = 0;
    }
    
    public RetryAnalyzer() {
        this.count = 0;
    }
    
    public void extendReportsFailOperations(final ITestResult iTestResult) {
        final Object testClass = iTestResult.getInstance();
        final WebDriver webDriver = ((TestBase)testClass).getDriver();
        ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(String.valueOf(iTestResult.getName()) + " Test case FAILED due to below issues:", ExtentColor.RED));
        ExtentTestManager.getTest().fail(iTestResult.getThrowable());
        try {
            final String screenShotPath = TestUtils.capture(webDriver, iTestResult);
            ExtentTestManager.getTest().fail("Snapshot below: " + ExtentTestManager.getTest().addScreenCaptureFromPath(screenShotPath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean retry(final ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (this.count < RetryAnalyzer.maxTry) {
                ++this.count;
                iTestResult.setStatus(2);
                this.extendReportsFailOperations(iTestResult);
                return true;
            }
        }
        else {
            iTestResult.setStatus(1);
        }
        return false;
    }
}
