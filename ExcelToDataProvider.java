package tct.base;

//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.IOException;
import java.io.FileInputStream;

public class ExcelToDataProvider
{
    FileInputStream excelFIS;
    //public static final String EXCELFILEPATH = "./src/resources/dataproviderxlsxs";
    
    public ExcelToDataProvider() {
        this.excelFIS = null;
    }
    
    public void closeExcelWorkbook() {
        if (this.excelFIS != null) {
            try {
                this.excelFIS.close();
            }
            catch (IOException ex) {}
        }
    }
    
    public int getColumnCount(final Sheet excelSheet) {
        final Row row = excelSheet.getRow(0);
        final int colCount = row.getLastCellNum();
        return colCount;
    }
    
    public Sheet getExcelSheet(final Workbook excelWorkbook, final String excelSheetName) {
        Sheet excelSheet = null;
        excelSheet = excelWorkbook.getSheet(excelSheetName);
        return excelSheet;
    }
    
    public int getRowCount(final Sheet excelSheet) {
        final int rowCount = excelSheet.getLastRowNum() + 1;
        return rowCount;
    }
    
    public Object[][] getSheetData(final Sheet excelSheet) {
        final int rowCount = this.getRowCount(excelSheet);
        final int columnCount = this.getColumnCount(excelSheet);
        final Object[][] data = new Object[rowCount - 1][columnCount];
        for (int i = 0; i < rowCount - 1; ++i) {
            final Row row = excelSheet.getRow(i + 1);
            if (row != null) {
                for (int j = 0; j < columnCount; ++j) {
                    final Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell == null) {
                        data[i][j] = "";
                    }
                    else {
                        switch (cell.getCellType()) {
                            case STRING: {
                                data[i][j] = cell.getStringCellValue().trim();
                                break;
                            }
                            case NUMERIC: {
                                final double numericValue = cell.getNumericCellValue();
                                data[i][j] = new StringBuilder().append(new Double(numericValue).longValue()).toString();
                                break;
                            }
                            case BOOLEAN: {
                                data[i][j] = cell.getBooleanCellValue();
                                break;
                            }
                            default: {
                                data[i][j] = "";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return data;
    }
    
    public Object[][] getSheetData(final Sheet excelSheet, final int rowCount, final int columnCount) {
        final Object[][] data = new Object[rowCount - 1][columnCount];
        for (int i = 0; i < rowCount - 1; ++i) {
            final Row row = excelSheet.getRow(i + 1);
            if (row != null) {
                for (int j = 0; j < columnCount; ++j) {
                    final Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell == null) {
                        data[i][j] = "";
                    }
                    else {
                        switch (cell.getCellType()) {
                            case STRING: {
                                data[i][j] = cell.getStringCellValue().trim();
                                break;
                            }
                            case NUMERIC: {
                                final double numericValue = cell.getNumericCellValue();
                                data[i][j] = new StringBuilder().append(new Double(numericValue).longValue()).toString();
                                break;
                            }
                            case BOOLEAN: {
                                data[i][j] = cell.getBooleanCellValue();
                                break;
                            }
                            default: {
                                data[i][j] = "";
                                break;
                            }
                        }
                    }
                }
            }
        }
        return data;
    }
    
    public Workbook openExcelWorkbook(final String excelWorkbookName) {
        Workbook excelWorkbook = null;
        try {
            this.excelFIS = new FileInputStream("./src/resources/dataproviderxlsxs" + File.separator + excelWorkbookName + ".xlsx");
            excelWorkbook = WorkbookFactory.create((InputStream)this.excelFIS);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return excelWorkbook;
    }
    
    public Workbook openExcelWorkbook(final String excelWorkbookPath, final String excelWorkbookName) throws InvalidFormatException {
        Workbook excelWorkbook = null;
        try {
            this.excelFIS = new FileInputStream(String.valueOf(excelWorkbookPath) + File.separator + excelWorkbookName + ".xlsx");
            excelWorkbook = WorkbookFactory.create((InputStream)this.excelFIS);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return excelWorkbook;
    }
}
