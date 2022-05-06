package tct.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class reportUtils
{
   // private String DomainName;
    public static String xmlname;
    
    static {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        final LocalDateTime now = LocalDateTime.now();
        reportUtils.xmlname = dtf.format(now).toString();
    }
        
    @BeforeSuite(alwaysRun = true)
    @Parameters({ "Reportname" })
    public void setup(final String Reportname) {
    	reportUtils.xmlname = String.valueOf(Reportname) + "-" + reportUtils.xmlname;
        System.out.println(xmlname);
    }
}