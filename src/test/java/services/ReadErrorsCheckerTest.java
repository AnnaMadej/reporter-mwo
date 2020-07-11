package services;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadErrorsCheckerTest {

    private static Workbook wb;
    private static Sheet sheet;
    private static Row columnNamesRow;
    private static Row dataRow;

    @Before
    public void init() {
        wb = new HSSFWorkbook();
        sheet = wb.createSheet();
        columnNamesRow = sheet.createRow(0);
        dataRow = sheet.createRow(1);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNullSheet() {
        Sheet sheet = null;
        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithProperNames() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("Data");
        cell1.setCellValue("Zadanie");
        cell2.setCellValue("Czas [h]");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithOnlySomeProperColumnNames() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("Data");
        cell1.setCellValue("aa");
        cell2.setCellValue("bb");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithOnlySomeProperColumnNames2() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("aa");
        cell1.setCellValue("Zadanie");
        cell2.setCellValue("bb");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithOnlySomeProperColumnNames3() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("aa");
        cell1.setCellValue("bb");
        cell2.setCellValue("Czas [h]");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithOnlySomeProperColumnNames4() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("Data");
        cell1.setCellValue("Zadanie");
        cell2.setCellValue("cc");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNullCells() {

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNumberNames() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue(1);
        cell1.setCellValue(2);
        cell2.setCellValue(3);

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNumberNames2() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("aaa");
        cell1.setCellValue(2);
        cell2.setCellValue(3);

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNumberNames3() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue(1);
        cell1.setCellValue("aaa");
        cell2.setCellValue(3);

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNumberNames4() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue(1);
        cell1.setCellValue(2);
        cell2.setCellValue("aaa");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNumberNames5() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("aaa");
        cell1.setCellValue("bbb");
        cell2.setCellValue(3);

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNullDateCollumnName() {
        
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell1.setCellValue("Zadanie");
        cell2.setCellValue("Czas [h]");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNullDescriptionCollumnName() {
        
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("Data");
        cell2.setCellValue("Czas [h]");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testSheetHasProperColumnNamesWithNullHoursCollumnName() {
        
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);

        cell0.setCellValue("Data");
        cell1.setCellValue("Zadanie");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithEmptylNames() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue(new String());
        cell1.setCellValue(new String());
        cell2.setCellValue(new String());

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithBadNames() {
        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("aa");
        cell1.setCellValue("bb");
        cell2.setCellValue("cc");

        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNoColumns() {
        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNoRows() {
        Sheet sheet = wb.createSheet();
        Boolean result = ReadErrorsChecker.sheetHasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testRowIsEmptyWithEmptyRow() {
        Row someEmptyRow = sheet.createRow(5);
        Boolean result = ReadErrorsChecker.rowIsEmpty(someEmptyRow);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testRowIsEmptyWithNullRow() {
        Boolean result = ReadErrorsChecker.rowIsEmpty(null);
        Assert.assertTrue(result);
    }

    @Test
    public final void testRowIsEmptyWithNotEmptyRow() {
        Row someNotEmptyRow = sheet.createRow(5);
        Cell cell = someNotEmptyRow.createCell(0);
        cell.setCellValue("aaaa");
        Boolean result = ReadErrorsChecker.rowIsEmpty(someNotEmptyRow);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDesctiptionCellNullCell() {
        Boolean result = ReadErrorsChecker.isValidDesctiptionCell(null);
       
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testIsValidDesctiptionCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = ReadErrorsChecker.isValidDesctiptionCell(cell);
       
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testIsValidDesctiptionCellStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = ReadErrorsChecker.isValidDesctiptionCell(cell);
       
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testIsValidDesctiptionCellEmptyStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = ReadErrorsChecker.isValidDesctiptionCell(cell);
       
        Assert.assertFalse(result);
    }
    @Test
    public final void testIsValidDesctiptionCellNumberStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(7);
        Boolean result = ReadErrorsChecker.isValidDesctiptionCell(cell);
       
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithNullLocation() {
        Boolean result = ReadErrorsChecker.locationYearIsValid(null);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithBadLocation() {
        String location = "aldijweiofweoifwf";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithWrongSizeOfYear() {
        String location = "c:/201/12";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithGoodLocation() {
        String location = "c:/2012/01";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithWrongSlashes() {
        String location = "c:A2012A01";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithOneDigitMonthLocation() {
        String location = "c:/2012/1";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithNoMonth() {
        String location = "c:/2012";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithTooEarlyYear() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR)-26;
        
        String location = "c:/" + year + "/04";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithTooLateYear() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR)+26;
        
        String location = "c:/" + year + "/04";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationYearIsValidWithStringYear() {
        String location = "c:/abcd/04";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidNoMonth() {
        String location = "c:/2012";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationMonthIsValidStringMonth() {
        String location = "c:/2012/ab";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationMonthIsValidStrangeLocation() {
        String location = "adasdasdasdasd";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationMonthIsValidSingleDigitMonth() {
        String location = "c:/2012/1";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationMonthIsValidGoodLocation() {
        String location = "c:/2012/08";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testLocationMonthIsValidZeroMonth() {
        String location = "c:/2012/00";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testLocationMonthIsValid13Month() {
        String location = "c:/2012/13";
        Boolean result = ReadErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }



}
