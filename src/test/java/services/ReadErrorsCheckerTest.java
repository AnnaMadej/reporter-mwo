package services;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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
    public final void testIsValidDesctiptionCellValidCell() {
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
        String location = "c:/A2012A01";
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
        int year = calendar.get(Calendar.YEAR) - 26;

        String location = "c:/" + year + "/04";
        Boolean result = ReadErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithTooLateYear() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) + 26;

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

    @Test
    public final void testIsValidDateCellStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellNumberCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(7);
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellValidCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(new Date());
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertTrue(result);
    }

    @Test
    public final void testIsValidDateCellNullCell() {
        Boolean result = ReadErrorsChecker.isValidDateCell(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellSpaceCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(" ");
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellBlankCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellTooEarlyDate() {
        Cell cell = dataRow.createCell(1);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        final int yearsDifference = 26;
        c.add(Calendar.YEAR, -yearsDifference);
        Date date = c.getTime();
        cell.setCellValue(date);
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellTooLateDate() {
        Cell cell = dataRow.createCell(1);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        final int yearsDifference = 26;
        c.add(Calendar.YEAR, yearsDifference);
        Date date = c.getTime();
        cell.setCellValue(date);
        Boolean result = ReadErrorsChecker.isValidDateCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellValidCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(4.5);
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);
        Assert.assertTrue(result);
    }

    @Test
    public final void testIsValidHoursCellNullCell() {
        Boolean result = ReadErrorsChecker.isValidHoursCell(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellSpaceCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(" ");
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellBlankCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellTooManyHours() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(24);
        Boolean result = ReadErrorsChecker.isValidHoursCell(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testfilenmeIsValidNullFile() throws IOException {
        Boolean result = ReadErrorsChecker.filenameIsValid(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testfilenmeIsValidStrangeNameFile() throws IOException {
        File file = new File("someStrangeNameFile");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidEmptyNameFile() throws IOException {
        File file = new File("");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidGoodNameFile() throws IOException {
        File file = new File("C:/2012/01/Adam_Nowak.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testfilenmeIsValidNameWithSpace() throws IOException {
        File file = new File("C:/2012/01/Adam Nowak.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidNameWithLetter() throws IOException {
        File file = new File("C:/2012/01/AdamBNowak.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidDoubleName() throws IOException {
        File file = new File("C:/2012/01/Adam_Ignacy_Nowak.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidLowerCase() throws IOException {
        File file = new File("C:/2012/01/adam_nowak.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidUpperCase() throws IOException {
        File file = new File("C:/2012/01/ADAM_NOWAK.xls");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidXlsx() throws IOException {
        File file = new File("C:/2012/01/Adam_Nowak.xlsx");
        Boolean result = ReadErrorsChecker.filenameIsValid(file);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearNullCalendar() throws IOException {
        String location = "c:/2012/01";
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(null, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearNulllocation() throws IOException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(calendar, null);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearCorrect() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "c:/2012/01";
        calendar.set(2012,01,01);
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(calendar, location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearDifferent() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "c:/2013/01";
        calendar.set(2012,01,01);
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(calendar, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearStrangeLocation() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "qwdqwdqwdqwdqwd31";
        calendar.set(2012,01,01);
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(calendar, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearEmptyLocation() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "";
        calendar.set(2012,01,01);
        Boolean result = ReadErrorsChecker.locationYearEqualsDateYear(calendar, location);
        Assert.assertFalse(result);
    }
    
    
    @Test
    public final void locationMonthEqualsDateMonthNullCalendar() throws IOException {
        String location = "c:/2012/01";
        Boolean result = ReadErrorsChecker.locationMonthEqualsDateMonth(null, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthNulllocation() throws IOException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Boolean result = ReadErrorsChecker.locationMonthEqualsDateMonth(calendar, null);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthCorrect() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "c:/2012/01";
        calendar.set(2012,01,01);
        calendar.add(Calendar.MONTH, -1);
        Boolean result = ReadErrorsChecker.locationMonthEqualsDateMonth(calendar, location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthDifferent() throws IOException {
        
        Calendar calendar = Calendar.getInstance();
        String location = "c:/2012/02";
        calendar.set(2012,01,01);
        calendar.add(Calendar.MONTH, -1);
        Boolean result = ReadErrorsChecker.locationMonthEqualsDateMonth(calendar, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthStrangeLocation() throws IOException {
        Calendar calendar = Calendar.getInstance();
        String location = "dwdwedwedwedwedwed";
        calendar.set(2012,01,01);
        Boolean result = ReadErrorsChecker.locationMonthEqualsDateMonth(calendar, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthEmptyLocation() throws IOException {
        ReadErrorsChecker checker = new ReadErrorsChecker();
        Calendar calendar = Calendar.getInstance();
        String location = "";
        calendar.set(2012,01,01);
        Boolean result = checker.locationMonthEqualsDateMonth(calendar, location);
        Assert.assertFalse(result);
    }
}
