package tct.utils;

import com.aventstack.extentreports.ExtentReporter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.ExtentReports;

public class ExtentManager
{
    private static ExtentReports extent;
    
    private static ExtentHtmlReporter getHtmlReporter() {
        final File htmlReporterDirectory = new File(TestUtils.EXTENTREPORT_PATH);
        try {
            FileUtils.forceMkdir(htmlReporterDirectory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        final ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(String.valueOf(TestUtils.EXTENTREPORT_PATH) + TestUtils.EXTENTREPORTS_NAME);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("TCT HTML UI - Automation Report");
        htmlReporter.config().setReportName("TCT HTML UI - Automation Regression Cycle Report");
        return htmlReporter;
    }
    
    public static synchronized ExtentReports getReporter() {
        if (ExtentManager.extent == null) {
            (ExtentManager.extent = new ExtentReports()).attachReporter(new ExtentReporter[] { (ExtentReporter)getHtmlReporter() });
        }
        return ExtentManager.extent;
    }
}
