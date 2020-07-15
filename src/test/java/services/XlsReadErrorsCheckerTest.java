package services;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import services.errors.ReadErrorsChecker;
import services.errors.XlsReadErrorsChecker;

public class XlsReadErrorsCheckerTest {

    private static Workbook wb;
    private static Sheet sheet;
    private static Row columnNamesRow;
    private static Row dataRow;
    private static ReadErrorsChecker readErrorsChecker= new XlsReadErrorsChecker();

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
        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNullCells() {

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNullDateCollumnName() {

        Cell cell1 = columnNamesRow.createCell(1);
        Cell cell2 = columnNamesRow.createCell(2);

        cell1.setCellValue("Zadanie");
        cell2.setCellValue("Czas [h]");

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNullDescriptionCollumnName() {

        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell2 = columnNamesRow.createCell(2);

        cell0.setCellValue("Data");
        cell2.setCellValue("Czas [h]");

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNullHoursCollumnName() {

        Cell cell0 = columnNamesRow.createCell(0);
        Cell cell1 = columnNamesRow.createCell(1);

        cell0.setCellValue("Data");
        cell1.setCellValue("Zadanie");

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
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

        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNoColumns() {
        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testSheetHasProperColumnNamesWithNoRows() {
        Sheet sheet = wb.createSheet();
        Boolean result = readErrorsChecker.hasProperColumnNames(sheet);
        Assert.assertFalse(result);
    }

    @Test
    public final void testRowIsEmptyWithEmptyRow() {
        Row someEmptyRow = sheet.createRow(5);
        Boolean result = readErrorsChecker.rowIsEmpty(someEmptyRow);
        Assert.assertTrue(result);
    }

    @Test
    public final void testRowIsEmptyWithNullRow() {
        Boolean result = readErrorsChecker.rowIsEmpty(null);
        Assert.assertTrue(result);
    }

    @Test
    public final void testRowIsEmptyWithNotEmptyRow() {
        Row someNotEmptyRow = sheet.createRow(5);
        Cell cell = someNotEmptyRow.createCell(0);
        cell.setCellValue("aaaa");
        Boolean result = readErrorsChecker.rowIsEmpty(someNotEmptyRow);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testRowIsEmptyWithNotEmptyRow2() {
        Row someNotEmptyRow = sheet.createRow(5);
        Cell cell = someNotEmptyRow.createCell(1);
        cell.setCellValue("aaaa");
        Boolean result = readErrorsChecker.rowIsEmpty(someNotEmptyRow);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testRowIsEmptyWithNotEmptyRow3() {
        Row someNotEmptyRow = sheet.createRow(5);
        Cell cell = someNotEmptyRow.createCell(2);
        cell.setCellValue("aaaa");
        Boolean result = readErrorsChecker.rowIsEmpty(someNotEmptyRow);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDesctiptionCellNullCell() {
        Boolean result = readErrorsChecker.isValidDescriptionField(null);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDesctiptionCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = readErrorsChecker.isValidDescriptionField(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDesctiptionCellValidCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = readErrorsChecker.isValidDescriptionField(cell);

        Assert.assertTrue(result);
    }

    @Test
    public final void testIsValidDesctiptionCellEmptyStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = readErrorsChecker.isValidDescriptionField(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDesctiptionCellNumberStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(7);
        Boolean result = readErrorsChecker.isValidDescriptionField(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithNullLocation() {
        Boolean result = readErrorsChecker.locationYearIsValid(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithBadLocation() {
        String location = "aldijweiofweoifwf";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithWrongSizeOfYear() {
        String location = "c:/201/12";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithGoodLocation() {
        String location = "c:/2012/01";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertTrue(result);
    }

    @Test
    public final void testLocationYearIsValidWithWrongSlashes() {
        String location = "c:/A2012A01";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithOneDigitMonthLocation() {
        String location = "c:/2012/1";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithNoMonth() {
        String location = "c:/2012";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithTooEarlyYear() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) - 26;

        String location = "c:/" + year + "/04";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithTooLateYear() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) + 26;

        String location = "c:/" + year + "/04";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationYearIsValidWithStringYear() {
        String location = "c:/abcd/04";
        Boolean result = readErrorsChecker.locationYearIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidNoMonth() {
        String location = "c:/2012";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidStringMonth() {
        String location = "c:/2012/ab";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidStrangeLocation() {
        String location = "adasdasdasdasd";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidSingleDigitMonth() {
        String location = "c:/2012/1";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValidGoodLocation() {
        String location = "c:/2012/08";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertTrue(result);
    }

    @Test
    public final void testLocationMonthIsValidZeroMonth() {
        String location = "c:/2012/00";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testLocationMonthIsValid13Month() {
        String location = "c:/2012/13";
        Boolean result = readErrorsChecker.locationMonthIsValid(location);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = readErrorsChecker.isValidDateField(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellNumberCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(7);
        Boolean result = readErrorsChecker.isValidDateField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellValidCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(new Date());
        Boolean result = readErrorsChecker.isValidDateField(cell);
        Assert.assertTrue(result);
    }

    @Test
    public final void testIsValidDateCellNullCell() {
        Boolean result = readErrorsChecker.isValidDateField(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = readErrorsChecker.isValidDateField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellSpaceCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(" ");
        Boolean result = readErrorsChecker.isValidDateField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidDateCellBlankCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = readErrorsChecker.isValidDateField(cell);
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
        Boolean result = readErrorsChecker.isValidDateField(cell);
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
        Boolean result = readErrorsChecker.isValidDateField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellStringCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("abc");
        Boolean result = readErrorsChecker.isValidHoursField(cell);

        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellValidCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(4.5);
        Boolean result = readErrorsChecker.isValidHoursField(cell);
        Assert.assertTrue(result);
    }

    @Test
    public final void testIsValidHoursCellNullCell() {
        Boolean result = readErrorsChecker.isValidHoursField(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellEmptyCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue("");
        Boolean result = readErrorsChecker.isValidHoursField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellSpaceCell() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(" ");
        Boolean result = readErrorsChecker.isValidHoursField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellBlankCell() {
        Cell cell = dataRow.createCell(1);
        Boolean result = readErrorsChecker.isValidHoursField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testIsValidHoursCellTooManyHours() {
        Cell cell = dataRow.createCell(1);
        cell.setCellValue(25);
        Boolean result = readErrorsChecker.isValidHoursField(cell);
        Assert.assertFalse(result);
    }

    @Test
    public final void testfilenmeIsValidNullFile() throws IOException {
        Boolean result = readErrorsChecker.filenameIsValid(null);
        Assert.assertFalse(result);
    }

    @Test
    public final void testfilenmeIsValidStrangeNameFile() throws IOException {
        File file = new File("someStrangeNameFile");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidEmptyNameFile() throws IOException {
        File file = new File("");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidGoodNameFile() throws IOException {
        File file = new File("C:/2012/01/Adam_Nowak.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testfilenmeIsValidNameWithSpace() throws IOException {
        File file = new File("C:/2012/01/Adam Nowak.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidNameWithLetter() throws IOException {
        File file = new File("C:/2012/01/AdamBNowak.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidDoubleName() throws IOException {
        File file = new File("C:/2012/01/Adam_Ignacy_Nowak.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    @Test
    public final void testfilenmeIsValidLowerCase() throws IOException {
        File file = new File("C:/2012/01/adam_nowak.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidUpperCase() throws IOException {
        File file = new File("C:/2012/01/ADAM_NOWAK.xls");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testfilenmeIsValidXlsx() throws IOException {
        File file = new File("C:/2012/01/Adam_Nowak.xlsx");
        Boolean result = readErrorsChecker.filenameIsValid(file);
        Assert.assertTrue(result);
    }
    
    
    @Test
    public final void locationYearEqualsDateYearNulllocation() throws IOException {
        int year = 2012;
        Boolean result = readErrorsChecker.locationYearEqualsDateYear(year, null);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearCorrect() throws IOException {
        int year = 2012;
        String location = "c:/2012/01";
        Boolean result = readErrorsChecker.locationYearEqualsDateYear(year, location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearDifferent() throws IOException {
        int year = 2012;
        String location = "c:/2013/01";
        Boolean result = readErrorsChecker.locationYearEqualsDateYear(year, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearStrangeLocation() throws IOException {
        int year = 2012;
        String location = "qwdqwdqwdqwdqwd31";
        Boolean result = readErrorsChecker.locationYearEqualsDateYear(year, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationYearEqualsDateYearEmptyLocation() throws IOException {
        int year = 2012;
        String location = "";
        Boolean result = readErrorsChecker.locationYearEqualsDateYear(year, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthNulllocation() throws IOException {
        int month = 3;
        Boolean result = readErrorsChecker.locationMonthEqualsDateMonth(month, null);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthCorrect() throws IOException {
        int month = 1;
        String location = "c:/2012/01";
        Boolean result = readErrorsChecker.locationMonthEqualsDateMonth(month, location);
        Assert.assertTrue(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthDifferent() throws IOException {
        int month = 2;
        String location = "c:/2012/01";
        Boolean result = readErrorsChecker.locationMonthEqualsDateMonth(month, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthStrangeLocation() throws IOException {
        int month = 2;
        String location = "dwdwedwedwedwedwed";
        Boolean result = readErrorsChecker.locationMonthEqualsDateMonth(month, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void locationMonthEqualsDateMonthEmptyLocation() throws IOException {
        int month = 2;
        String location = "";
        Boolean result = readErrorsChecker.locationMonthEqualsDateMonth(month, location);
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testFindDatesWithInvalidHoursTooManyHours(){
        Map<Date, Double> hoursOfDates = new HashMap<Date, Double>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 01,01);
        Date date = calendar.getTime();
        hoursOfDates.put(date, 25.0);
        
        List<Date> result = readErrorsChecker.findDatesWithInvalidHours(hoursOfDates);
        Assert.assertTrue(result.contains(date));   
    }
    
    @Test
    public final void testFindDatesWithInvalidHoursCorrectHours(){
        Map<Date, Double> hoursOfDates = new HashMap<Date, Double>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 01,01);
        Date date = calendar.getTime();
        hoursOfDates.put(date, 24.0);
        
        List<Date> result = readErrorsChecker.findDatesWithInvalidHours(hoursOfDates);
        Assert.assertFalse(result.contains(date));   
    }
    
    @Test
    public final void testFindDatesWithInvalidHoursNegativeHours(){
        Map<Date, Double> hoursOfDates = new HashMap<Date, Double>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 01,01);
        Date date = calendar.getTime();
        hoursOfDates.put(date, -5.0);
        
        List<Date> result = readErrorsChecker.findDatesWithInvalidHours(hoursOfDates);
        Assert.assertTrue(result.contains(date));   
    }
    
    @Test
    public final void testHasProperColumnNamesWithBadInstance() {
        boolean result = readErrorsChecker.hasProperColumnNames(new Object());
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testRowIsEmptyWithBadInstance() {
        boolean result = readErrorsChecker.rowIsEmpty(new Object());
        Assert.assertTrue(result);
    }
    
    @Test
    public final void testIsValidDesctiptionFieldBadInstance() {
        boolean result = readErrorsChecker.isValidDescriptionField(new Object());
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testIsValidDateFieldBadInstance() {
        boolean result = readErrorsChecker.isValidDateField(new Object());
        Assert.assertFalse(result);
    }
    
    @Test
    public final void testIsValidHoursFieldBadInstance() {
        boolean result = readErrorsChecker.isValidHoursField(new Object());
        Assert.assertFalse(result);
    }
}
