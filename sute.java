package tct.target.contract;

//import java.util.LinkedHashMap;
//import java.util.ArrayList;
//import java.util.List;
import org.testng.annotations.Test;
//import org.testng.Assert;
//import com.ipc.test.umsui.test.utils.extentreports.ExtentTestManager;
import org.testng.annotations.DataProvider;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.openqa.selenium.support.PageFactory;
import tct.base.ExcelToDataProvider;
import tct.base.TestBase;
import tct.pages.page;
import tct.utils.*;
import java.io.Serializable;

public class sute extends TestBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected page varPage;
    protected utils varutils;
    
    public sute() {
        PageFactory.initElements(sute.driver, (Object)this);
    }
    
    @Parameters({ "browser" })
    @BeforeClass
    public void init(final String browser) throws Exception {
        super.setupApplication(browser);
        super.doSingleLogin();
        this.varPage = new page();
        this.varutils = new utils();
    }
    
    @AfterClass
    public void quit() {
        this.varPage = null;
        super.closeSingleApplication();
    }
    
    @BeforeMethod
    public void setUp() 
    {
    }
    
    @AfterMethod
    public void tearDown() 
    {
    }
    
    @DataProvider(name = "CreateContractDataProvider")
    public Object[][] CreateContractDataProvider() throws InvalidFormatException {
        Object[][] data = null;
        ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();
        final Workbook excelWorkbook = excelToDataProvider.openExcelWorkbook("./src/test/java/tct/target/contract", "contract");
        final Sheet excelSheet = excelToDataProvider.getExcelSheet(excelWorkbook, "Createcontract");
        data = excelToDataProvider.getSheetData(excelSheet);
        excelToDataProvider.closeExcelWorkbook();
        excelToDataProvider = null;
        return data;
    }
    
    @Test(enabled = true, priority = 16, dataProvider = "CreateContractDataProvider")
    public void createcontract(final String testType, final String testComment) {
    	System.out.println(testType);
    	System.out.println(testComment);
        //ExtentTestManager.getTest().info("Executing " + testType.toUpperCase() + " scenario test. " + testComment);
        //ExtentTestManager.getTest().info("'VerifydownloadFunctionality' - Report Download functionality going to start.");
    }
}
