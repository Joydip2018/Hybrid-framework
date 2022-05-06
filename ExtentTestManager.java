package tct.utils;

import java.util.HashMap;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.util.Map;

public class ExtentTestManager
{
    static Map<Integer, ExtentTest> extentTestMap;
    static ExtentReports extent;
    
    static {
        ExtentTestManager.extentTestMap = new HashMap<Integer, ExtentTest>();
        ExtentTestManager.extent = ExtentManager.getReporter();
    }
    
    public static synchronized void endTest() {
        ExtentTestManager.extent.flush();
    }
    
    public static synchronized ExtentTest getTest() {
        return ExtentTestManager.extentTestMap.get((int)Thread.currentThread().getId());
    }
    
    public static synchronized ExtentTest startTest(final String testName, final String desc, final String browserInfo) {
        final ExtentTest test = ExtentTestManager.extent.createTest(testName, desc);
        test.assignAuthor(new String[] { "Tata Consultancy Services" });
        test.assignCategory(new String[] { browserInfo });
        ExtentTestManager.extentTestMap.put((int)Thread.currentThread().getId(), test);
        return test;
    }
}