package tct.utils;

import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Map;
import java.sql.SQLException;
import java.util.Iterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.InputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import org.openqa.selenium.WebElement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Collection;
import java.util.ArrayList;
import org.testng.Assert;
import java.io.Reader;
import java.util.Properties;
import java.io.FileReader;
import org.openqa.selenium.support.PageFactory;
import java.io.Serializable;

import tct.base.TestBase;

public class utils extends TestBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    public String SQLQuery;
    
    public utils() {
        PageFactory.initElements(TestBase.driver, (Object)this);
    }
    
    public String readKeyword(final String keyword_value) {
        String keyword = "";
        try {
            final FileReader reader = new FileReader("./src/test/resources/properties/Keyword.properties");
            final Properties p = new Properties();
            p.load(reader);
            keyword = p.getProperty(keyword_value).toString();
        }
        catch (Exception e) {
            Assert.assertTrue(false, "Unable to read keyword from properties file");
        }
        return keyword;
    }
    
    public ArrayList<String> SwitchTab() {
        final ArrayList<String> tabs = new ArrayList<String>(utils.driver.getWindowHandles());
        utils.driver.switchTo().window((String)tabs.get(1));
        return tabs;
    }
    
    public void TabClose(final ArrayList<String> tabs) {
        utils.driver.close();
        utils.driver.switchTo().window((String)tabs.get(0));
    }
    
    private String getfileextension() {
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String fileName = getLatestFile.getName();
        final String[] Name = fileName.split("\\.");
        final int size = Name.length;
        final String ext = Name[size - 1];
        return ext;
    }
    
    private File getLatestFilefromDir(final String dirPath) {
        final File dir = new File(dirPath);
        final File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; ++i) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }
    
    public void PDFDownload(final String Runline) {
        final File basereportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/report/");
        final File reportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/report/" + Runline + "/");
        try {
            FileUtils.forceMkdir(basereportdir);
            FileUtils.forceMkdir(reportdir);
        }
        catch (IOException ex) {}
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("pdf"), "Downloaded file is not PDF file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        ((JavascriptExecutor)utils.driver).executeScript("window.open()", new Object[0]);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        final File LatestFile = new File(Link);
        final ArrayList<String> tabs = new ArrayList<String>(utils.driver.getWindowHandles());
        utils.driver.switchTo().window((String)tabs.get(1));
        utils.driver.get(Link);
        try {
            Thread.sleep(10000L);
            utils.driver.findElement(By.id("plugin")).isDisplayed();
        }
        catch (Exception e) {
            Assert.assertTrue(false, "PDF file not downloaded properly");
        }
        try {
            FileUtils.copyFileToDirectory(LatestFile, reportdir);
        }
        catch (IOException ex2) {}
        utils.driver.close();
        utils.driver.switchTo().window((String)tabs.get(0));
    }
    
    public void excelDownload(final String Runline) {
        final File basereportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/report/");
        final File reportdir = new File(String.valueOf(System.getProperty("user.dir")) + "/src/test/resources/report/" + Runline + "/");
        try {
            FileUtils.forceMkdir(basereportdir);
            FileUtils.forceMkdir(reportdir);
        }
        catch (IOException ex) {}
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("csv"), "Downloaded file is not CSV file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        final File LatestFile = new File(Link);
        try {
            FileUtils.copyFileToDirectory(LatestFile, reportdir);
        }
        catch (IOException ex2) {}
    }
    
    public void cleanFolder() {
        try {
            FileUtils.cleanDirectory(new File(utils.FilestoregePath));
        }
        catch (IOException ex) {}
    }
    
    public int SQLConnector() {
        final String dbUrl = "jdbc:mysql://" + this.prop.getProperty("IP") + ":3306/dunkin";
        final String username = this.prop.getProperty("DBusername");
        final String password = this.prop.getProperty("DBPassword");
        final String query = this.SQLQuery;
        int rowcount = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection con = DriverManager.getConnection(dbUrl, username, password);
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ++rowcount;
            }
            con.close();
        }
        catch (Exception e) {
            Assert.assertTrue(false, "DB not connected successfully");
        }
        return rowcount;
    }
    
    public String serverDate() {
        final String dbUrl = "jdbc:mysql://" + this.prop.getProperty("IP") + ":3306/dunkin";
        final String username = this.prop.getProperty("DBusername");
        final String password = this.prop.getProperty("DBPassword");
        final String datequery = "SELECT DAY(CURRENT_DATE())";
        String sDate = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection con = DriverManager.getConnection(dbUrl, username, password);
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(datequery);
            while (rs.next()) {
                sDate = rs.getString(1);
            }
            con.close();
        }
        catch (Exception e) {
            Assert.assertTrue(false, "DB not connected successfully");
        }
        return sDate;
    }
   /* public int rowcount(final String Rowname) {
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("csv"), "Downloaded file is not CSV file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        int count = 0;
        try {
            final Scanner scanner = new Scanner(new File(Link));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                final String CellData = scanner.next();
                if (CellData.equalsIgnoreCase(Rowname)) {
                    count = -1;
                }
                else if (CellData.equalsIgnoreCase("No Records Found")) {
                    count = 0;
                    break;
                }
                ++count;
                scanner.nextLine();
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {}
        return count;
    }
    
    public List<String> tableHeading(final String Firstrow, final String Lastrow) {
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("csv"), "Downloaded file is not CSV file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        final boolean found = false;
        final List<String> str = new ArrayList<String>();
        final List<String> TableHeading = new ArrayList<String>();
        try {
            final Scanner scanner = new Scanner(new File(Link));
            scanner.useDelimiter("[,\n\r]");
            while (scanner.hasNextLine() && !found) {
                str.add(scanner.next().trim());
                final String stp = str.get(0);
                if (stp.equalsIgnoreCase(Firstrow)) {
                    while (stp.equalsIgnoreCase(Firstrow)) {
                        str.add(scanner.next().trim());
                        final String stp2 = str.get(str.size() - 1).trim();
                        if (stp2.equalsIgnoreCase(Lastrow)) {
                            str.removeAll(Collections.singleton(""));
                            TableHeading.addAll(str);
                            str.clear();
                            break;
                        }
                        if (!stp2.equalsIgnoreCase(Firstrow)) {
                            continue;
                        }
                        final String obj = str.get(0);
                        str.clear();
                        str.add(obj);
                    }
                }
                else {
                    scanner.nextLine();
                    str.clear();
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException ex) {}
        return TableHeading;
    }
    
    public LinkedHashMap<String, String> VerifySearchCriteria(final String Rowname, final ArrayList<String> Labelvalue) {
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("csv"), "Downloaded file is not CSV file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        final LinkedHashMap<String, String> HM = new LinkedHashMap<String, String>();
        final ArrayList<String> Label = new ArrayList<String>();
        Label.addAll(Labelvalue);
        System.out.println("Label--" + Label);
        int i = 0;
        try {
            final Scanner scanner = new Scanner(new File(Link));
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()) {
                final String str = scanner.next();
                if (str.trim().equalsIgnoreCase(Label.get(i))) {
                    String stp = scanner.next();
                    for (int z = 0; stp.equals("") && z < 2; stp = scanner.next(), ++z) {}
                    HM.put(Label.get(i), stp);
                    if (++i == Label.size()) {
                        break;
                    }
                    continue;
                }
                else {
                    if (str.equalsIgnoreCase(Rowname)) {
                        break;
                    }
                    continue;
                }
            }
            System.out.println(HM);
            scanner.close();
        }
        catch (FileNotFoundException ex) {}
        return HM;
    }
    
    public LinkedHashMap<String, String> VerifySearchCriteriaExceptional(final String Rowname, final ArrayList<String> Labelvalue) {
        final String ext = this.getfileextension();
        Assert.assertTrue(ext.equalsIgnoreCase("csv"), "Downloaded file is not CSV file");
        final File getLatestFile = this.getLatestFilefromDir(utils.FilestoregePath);
        final String Link = String.valueOf(utils.FilestoregePath) + getLatestFile.getName();
        final LinkedHashMap<String, String> HM = new LinkedHashMap<String, String>();
        final ArrayList<String> Label = new ArrayList<String>();
        Label.addAll(Labelvalue);
        System.out.println("joy" + Label);
        int i = 0;
        try {
            final Scanner scanner = new Scanner(new File(Link));
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()) {
                final String str = scanner.next();
                if (str.equalsIgnoreCase(Label.get(i))) {
                    String stp = scanner.next();
                    for (int z = 0; stp.equals("") && z < 1; stp = scanner.next(), ++z) {}
                    HM.put(Label.get(i), stp);
                    if (++i == Label.size()) {
                        break;
                    }
                    continue;
                }
                else {
                    if (str.equalsIgnoreCase(Rowname)) {
                        break;
                    }
                    continue;
                }
            }
            System.out.println(HM);
            scanner.close();
        }
        catch (FileNotFoundException ex) {}
        return HM;
    }
    
    public void current_Time(final List<WebElement> Calendar_date) {
        String a = this.serverDate();
        final int day = Integer.parseInt(a);
        if (day < 10) {
            a = a.replaceFirst("0", "");
        }
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {}
        final List<WebElement> Daylist = Calendar_date;
        for (int Count = Daylist.size(), j = 0; j < Count; ++j) {
            if (Integer.parseInt(Daylist.get(j).getText()) == 30 || (Integer.parseInt(Daylist.get(j).getText()) == 31 && j == 0) || j == 1) {
                ++j;
            }
            final String Cal_Date = Daylist.get(j).getText();
            final int Date = Integer.parseInt(Cal_Date);
            if (Date == day) {
                Daylist.get(j).click();
                break;
            }
        }
    }
    
    public void day_Monday(final List<WebElement> Calendar_date) {
        String a = this.serverDate();
        final int day = Integer.parseInt(a);
        if (day < 10) {
            a = a.replaceFirst("0", "");
        }
        final int current_date = Integer.parseInt(a);
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {}
        final List<WebElement> Daylist = Calendar_date;
        for (int Count = Daylist.size(), j = 0; j < Count; ++j) {
            final String Cal_Date = Daylist.get(j).getText();
            final int Date = Integer.parseInt(Cal_Date);
            if (Date == current_date) {
                if (j >= 29) {
                    Daylist.get(29).click();
                    break;
                }
                if (j >= 22) {
                    Daylist.get(22).click();
                    break;
                }
                if (j >= 15) {
                    Daylist.get(15).click();
                    break;
                }
                if (j >= 8) {
                    Daylist.get(8).click();
                    break;
                }
                if (j >= 1) {
                    Daylist.get(1).click();
                    break;
                }
            }
        }
    }
    
    public void past_Time(final List<WebElement> Calendar_date) {
        String a = this.serverDate();
        final int day = Integer.parseInt(a);
        if (day < 10) {
            a = a.replaceFirst("0", "");
        }
        final int current_date = Integer.parseInt(a);
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {}
        final List<WebElement> Daylist = Calendar_date;
        for (int Count = Daylist.size(), j = 0; j < Count; ++j) {
            final String Cal_Date = Daylist.get(j).getText();
            final int Date = Integer.parseInt(Cal_Date);
            if (Date == 1) {
                Daylist.get(j - 2).click();
                break;
            }
            if (Date == 2) {
                Daylist.get(j - 2).click();
                break;
            }
            if (Date == current_date - 2) {
                Daylist.get(j).click();
                break;
            }
        }
    }
    
    public void LOGINTIME(final List<WebElement> Calendar_date) {
        String a = this.serverDate();
        final int day = Integer.parseInt(a);
        if (day < 10) {
            a = a.replaceFirst("0", "");
        }
        final int current_date = Integer.parseInt(a);
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {}
        final List<WebElement> Daylist = Calendar_date;
        for (int Count = Daylist.size(), j = 0; j < Count; ++j) {
            final String Cal_Date = Daylist.get(j).getText();
            final int Date = Integer.parseInt(Cal_Date);
            if (Date == 1) {
                Daylist.get(j - 2).click();
                break;
            }
            if (Date == 2) {
                Daylist.get(j - 2).click();
                break;
            }
            if (Date == current_date - 2) {
                Daylist.get(j).click();
                break;
            }
        }
    }
    
    public ArrayList<String> Readfielddatafromexcel(final String path) {
        final ArrayList<String> Getfielddata = new ArrayList<String>();
        try {
            final File excelFile = new File(path);
            final FileInputStream fis = new FileInputStream(excelFile);
            final XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fis);
            final XSSFSheet sheet = workbook.getSheet("QueryData");
            final Iterator<Row> rowIt = (Iterator<Row>)sheet.rowIterator();
            final Row row = rowIt.next();
            final Iterator<Cell> cellIterator = (Iterator<Cell>)row.cellIterator();
            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                Getfielddata.add(cell.toString());
            }
            workbook.close();
            fis.close();
            Getfielddata.remove(0);
            Getfielddata.remove(0);
        }
        catch (IOException e) {
            Assert.assertTrue(false, "Unable to get lables");
        }
        return Getfielddata;
    }
    
    public ArrayList<String> ReadQuerydatafromexcel(final String path, final String testComment_Value) {
        final ArrayList<String> GetQuerydata = new ArrayList<String>();
        try {
            final File excelFile = new File(path);
            final FileInputStream fis = new FileInputStream(excelFile);
            final XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fis);
            final XSSFSheet sheet = workbook.getSheet("QueryData");
            for (final Row row : sheet) {
                final Iterator<Cell> cellIterator = (Iterator<Cell>)row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.toString().equals(testComment_Value)) {
                        while (cellIterator.hasNext()) {
                            cell = cellIterator.next();
                            GetQuerydata.add(cell.toString());
                        }
                    }
                }
            }
            workbook.close();
            fis.close();
        }
        catch (IOException e) {
            Assert.assertTrue(false, "Query data not fatch from xlsx file");
        }
        return GetQuerydata;
    }
    
    public void ReadDBQueryfromexcel(final String Rowname, final LinkedHashMap<String, String> Custdata, final String path, final String testComment_Value) {
        final ArrayList<String> getfielddata = this.Readfielddatafromexcel(path);
        final ArrayList<String> getQuerydata = this.ReadQuerydatafromexcel(path, testComment_Value);
        final ArrayList<String> addQueryField = new ArrayList<String>();
        final ArrayList<String> addQueryData = new ArrayList<String>();
        for (int i = 0; i < getQuerydata.size(); ++i) {
            if (!getQuerydata.get(i).isEmpty()) {
                addQueryField.add(getfielddata.get(i));
                addQueryData.add(getQuerydata.get(i));
            }
        }
        final String[] queryField = new String[getfielddata.size() + 1];
        final String[] queryData = new String[addQueryField.size() + 1];
        for (int j = 0; j < addQueryField.size(); ++j) {
            final int k = j;
            queryField[k + 1] = addQueryField.get(j);
            queryData[k + 1] = addQueryData.get(j);
        }
        try {
            final File excelFile = new File("./ReportDBsetup/DBQuery.xlsx");
            final FileInputStream fis = new FileInputStream(excelFile);
            final XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fis);
            final XSSFSheet sheet = workbook.getSheetAt(0);
            for (final Row row : sheet) {
                final Iterator<Cell> cellIterator = (Iterator<Cell>)row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.toString().equals(Rowname)) {
                        cell = cellIterator.next();
                        this.SQLQuery = cell.toString();
                        for (int l = 1; l <= addQueryField.size(); ++l) {
                            final String sheetqueryField = "queryField\\[[" + l + "]]";
                            final String sheetqueryData = "queryData\\[[" + l + "]]";
                            if (queryData[l].equalsIgnoreCase("past_Time") || queryData[l].equalsIgnoreCase("day_Monday") || queryData[l].equalsIgnoreCase("LOGINTIME") || queryData[l].equalsIgnoreCase("SYSTEMREGISTERDATE") || queryData[l].equalsIgnoreCase("CALLOCCUREDDATE")) {
                                queryData[l] = Custdata.get("Date Time Start");
                            }
                            else if (queryData[l].equalsIgnoreCase("current_Time")) {
                                queryData[l] = Custdata.get("Date Time End");
                            }
                            else if (queryData[l].equalsIgnoreCase("IPADDRESS1")) {
                                queryData[l] = this.readKeyword("IPADDRESS1");
                            }
                            else if (queryData[l].equalsIgnoreCase("BROADCAST_GRPNAME")) {
                                queryData[l] = this.readKeyword("BROADCAST_GRPNAME");
                            }
                            else if (queryData[l].equalsIgnoreCase("BROADCASTGROUP_USERNAME")) {
                                queryData[l] = this.readKeyword("BROADCASTGROUP_USERNAME");
                            }
                            else if (queryData[l].equalsIgnoreCase("PRIVATELINE_Z1_I1")) {
                                queryData[l] = this.readKeyword("PRIVATELINE_Z1_I1");
                            }
                            else if (queryData[l].equalsIgnoreCase("MG_CHANNEL")) {
                                queryData[l] = this.readKeyword("MG_CHANNEL");
                            }
                            else if (queryData[l].equalsIgnoreCase("MG_NAME")) {
                                queryData[l] = this.readKeyword("MG_NAME");
                            }
                            else if (queryData[l].equalsIgnoreCase("ENTERPRISE")) {
                                queryData[l] = this.readKeyword("ENTERPRISE");
                            }
                            this.SQLQuery = this.SQLQuery.replaceAll(sheetqueryField, queryField[l]);
                            this.SQLQuery = this.SQLQuery.replaceAll(sheetqueryData, queryData[l]);
                        }
                        System.out.println(this.SQLQuery);
                    }
                }
            }
            workbook.close();
            fis.close();
        }
        catch (IOException e) {
            Assert.assertTrue(false, "DB Query not fatch from xlsx file");
        }
    }
    
    public void executeveiwquery(String getview) {
        final String dbUrl = "jdbc:mysql://" + this.prop.getProperty("IP") + ":3306/dunkin";
        final String username = this.prop.getProperty("DBusername");
        final String password = this.prop.getProperty("DBPassword");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection con = DriverManager.getConnection(dbUrl, username, password);
            final Statement stmt = con.createStatement();
            getview = getview.replaceAll("\"", "'");
            stmt.executeUpdate(getview);
            Thread.sleep(3000L);
            con.close();
        }
        catch (SQLException ex) {}
        catch (ClassNotFoundException ex2) {}
        catch (InterruptedException ex3) {}
    }
    
    public void ReadViewfromexcel(final ArrayList<String> getview) {
        for (final String view : getview) {
            this.executeveiwquery(view);
        }
    }*/
    
    /*public ArrayList<String> GetLablesFunctionality(final String path) {
        final ArrayList<String> GetLables = new ArrayList<String>();
        try {
            final File excelFile = new File(path);
            final FileInputStream fis = new FileInputStream(excelFile);
            final XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fis);
            final XSSFSheet sheet = workbook.getSheetAt(0);
            final Iterator<Row> rowIt = (Iterator<Row>)sheet.rowIterator();
            while (rowIt.hasNext()) {
                Row row = rowIt.next();
                row = rowIt.next();
                final Iterator<Cell> cellIterator = (Iterator<Cell>)row.cellIterator();
                while (cellIterator.hasNext()) {
                    final Cell cell = cellIterator.next();
                    GetLables.add(cell.toString());
                }
            }
            workbook.close();
            fis.close();
            GetLables.remove(0);
            GetLables.remove(0);
            System.out.println("lable:-" + GetLables);
        }
        catch (IOException e) {
            Assert.assertTrue(false, "Unable to get lables");
        }
        return GetLables;
    }
    
    public LinkedHashMap<String, String> getDate(final LinkedHashMap<String, String> valuesSearchCriteria) {
        final LinkedHashMap<String, String> getDatevalue = valuesSearchCriteria;
        System.out.println(String.valueOf(getDatevalue.size()) + "   " + getDatevalue);
        final LinkedHashMap<String, String> customDatelist = new LinkedHashMap<String, String>();
        String Date_Time_End = "";
        String Date_Time_Start = "";
        for (final Map.Entry<String, String> entry : getDatevalue.entrySet()) {
            final String key = entry.getKey();
            if (key.equalsIgnoreCase("Date Time End") || key.equalsIgnoreCase("EndTime") || key.equalsIgnoreCase("End Time") || key.equalsIgnoreCase("Event Time To") || key.equalsIgnoreCase("Duration To") || key.equalsIgnoreCase("To") || key.equalsIgnoreCase("Date of Last Logon") || key.equalsIgnoreCase("End Date Time")) {
                Date_Time_End = key;
            }
            else {
                if (!key.equalsIgnoreCase("Date Time Start") && !key.equalsIgnoreCase("Start Time") && !key.equalsIgnoreCase("Event Time From") && !key.equalsIgnoreCase("Duration From") && !key.equalsIgnoreCase("From") && !key.equalsIgnoreCase("Date of Last Registration") && !key.equalsIgnoreCase("Start Date Time")) {
                    continue;
                }
                Date_Time_Start = key;
            }
        }
        for (int i = 0; i < getDatevalue.size(); ++i) {
            if (!getDatevalue.get(Date_Time_End).isEmpty() || !getDatevalue.get(Date_Time_End).equals("")) {
                final String customDate = getDatevalue.get(Date_Time_End);
                customDatelist.put("Date Time End", customDate);
                getDatevalue.remove("Date Time End");
                break;
            }
            final String customDate = "";
        }
        for (int i = 0; i < getDatevalue.size(); ++i) {
            if (!getDatevalue.get(Date_Time_Start).isEmpty() || !getDatevalue.get(Date_Time_Start).equals("")) {
                final String customDate = getDatevalue.get(Date_Time_Start);
                customDatelist.put("Date Time Start", customDate);
                getDatevalue.remove("Date Time Start");
                break;
            }
            final String customDate = "";
        }
        return customDatelist;
    }
    
    public String getIP() {
        String IPadd = "";
        try {
            final InetAddress localhost = InetAddress.getLocalHost();
            IPadd = localhost.getHostAddress().trim();
        }
        catch (UnknownHostException ex) {}
        return IPadd;
    }
    
    public String Dateformate(final String pickDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:m:s");
        String formatted = "";
        try {
            final Date date2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(pickDate);
            formatted = sdf.format(date2);
        }
        catch (ParseException ex) {}
        return formatted;
    }
    
    public String serverDateformate(final String pickDate) {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd h:m:s");
        Date date = null;
        String output = null;
        try {
            date = df.parse(pickDate);
            output = outputformat.format(date);
        }
        catch (Exception ex) {}
        return output;
    }*/
}
