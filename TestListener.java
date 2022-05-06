package tct.utils;


import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import tct.base.TestBase;


public class TestListener extends TestBase implements ITestListener
{
    private static final Logger logger;
    private static final long serialVersionUID = 1L;
    
    static {
        logger = LogManager.getLogger((Class)TestListener.class);
    }
    
    private static String getTestMethodName(final ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
    
    public void onFinish(final ITestContext iTestContext) {
        TestListener.logger.info((Object)("I am in onFinish method " + iTestContext.getName()));
        ExtentTestManager.endTest();
    }
    
    public void onStart(final ITestContext iTestContext) {
        TestListener.logger.info((Object)("I am in onStart method " + iTestContext.getName()));
        iTestContext.setAttribute("WebDriver", (Object)TestBase.driver);
        TestBase.TESTNAME = iTestContext.getName();
        TestUtils.SUITENAME = iTestContext.getSuite().getName();
        TestUtils.EXTENTREPORTS_NAME = String.valueOf(iTestContext.getSuite().getName()) + ".html";
        System.out.println("SuitName" + iTestContext.getName());
    }
    
    public void onTestFailedButWithinSuccessPercentage(final ITestResult iTestResult) {
        TestListener.logger.info((Object)("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult)));
    }
    
    public void onTestFailure(final ITestResult iTestResult) {
        TestListener.logger.info((Object)("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed"));
        final Object testClass = iTestResult.getInstance();
        final WebDriver webDriver = ((TestBase)testClass).getDriver();
        final String[][] data = new String[3][1];
        data[0][0] = MarkupHelper.createLabel(iTestResult.getInstanceName(), ExtentColor.RED).getMarkup();
        data[1][0] = MarkupHelper.createLabel(iTestResult.getName(), ExtentColor.RED).getMarkup();
        data[2][0] = MarkupHelper.createLabel("Test case FAILED due to below issues:", ExtentColor.RED).getMarkup();
        ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createTable(data));
        ExtentTestManager.getTest().fail(iTestResult.getThrowable());
        try {
            final String screenShotPath = TestUtils.capture(webDriver, iTestResult);
            ExtentTestManager.getTest().fail("Snapshot below: " + ExtentTestManager.getTest().addScreenCaptureFromPath(screenShotPath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void onTestSkipped(final ITestResult iTestResult) {
        TestListener.logger.info((Object)("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped"));
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }
    
    public void onTestStart(final ITestResult iTestResult) {
        TestListener.logger.info((Object)("I am in onTestStart method " + getTestMethodName(iTestResult) + " start"));
        System.out.println("joy");
        final Object testClass = iTestResult.getInstance();
        System.out.println(testClass);
        final EventFiringWebDriver eventFiringWebDriver = ((TestBase)testClass).getEventFiringWebDriver();
        System.out.println(eventFiringWebDriver);
        final Capabilities browserCapabilities = eventFiringWebDriver.getCapabilities();
        final String browserName = browserCapabilities.getBrowserName().toUpperCase();
        final String browserVersion = browserCapabilities.getVersion().toUpperCase();
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "", String.valueOf(browserName) + "-" + browserVersion);
        final StringBuilder sb = new StringBuilder();
        Object[] parameters2;
        for (int length = (parameters2 = iTestResult.getParameters()).length, i = 0; i < length; ++i) {
            final Object strTemp = parameters2[i];
            sb.append(String.valueOf(strTemp.toString()) + ", ");
        }
        String parameters = sb.toString();
        if (parameters.endsWith(", ")) {
            parameters = parameters.substring(0, parameters.length() - 2);
        }
        parameters = "(" + parameters + ")";
        ExtentTestManager.getTest().info("Method parameters: " + parameters);
    }
    
    public void onTestSuccess(final ITestResult iTestResult) {
        TestListener.logger.info((Object)("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed"));
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }
}