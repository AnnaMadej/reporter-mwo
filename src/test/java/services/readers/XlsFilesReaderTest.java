package services.readers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import model.Employee;
import model.ScanError;
import model.Task;
import repository.FilesFinder;
import services.ReadErrorsChecker;
import services.ReadErrorsHolder;
import services.XlsReadErrorsChecker;

public class XlsFilesReaderTest {

    private static ReadErrorsHolder readErrorsHolder;

    @Spy
    private static XlsFilesReader filesReader = new XlsFilesReader(readErrorsHolder);
    private static ReadErrorsChecker readErrorsChecker;
    private static FilesFinder filesFinder;
    private static File file1;
    private static List<File> filesList;
    private static String path = "somePath";
    private static List<Employee> employees;
    private static String location;
    private static Sheet sheet;
    private static Row row;
    private static Cell cell1;
    private static Cell cell2;
    private static Cell cell3;

    @Before
    public final void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        readErrorsChecker = Mockito.mock(XlsReadErrorsChecker.class);
        filesFinder = Mockito.mock(FilesFinder.class);
        readErrorsHolder = Mockito.mock(ReadErrorsHolder.class);

        filesReader.setFilesFinder(filesFinder);
        filesReader.setReadErrorsChecker(readErrorsChecker);
        filesReader.setReadErrorsHolder(readErrorsHolder);

        file1 = new File("C:/2012/01/Nowak_Jan.xls");
        filesList = new ArrayList<>();
        filesList.add(file1);
        location = file1.getParent().replace("\\", "/");

        Mockito.when(filesFinder.findFiles(path)).thenReturn(filesList);

        Workbook wb = new HSSFWorkbook();
        Mockito.doReturn(wb).when(filesReader).createWorkBook(file1);
        sheet = wb.createSheet();
        row = sheet.createRow(1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 01, 23);
        cell1 = row.createCell(0);
        cell1.setCellValue(calendar.getTime());
        cell2 = row.createCell(1);
        cell2.setCellValue("opis");
        cell3 = row.createCell(2);
        cell3.setCellValue(4);
    }

    @Test
    public final void testReadFilesWithInvalidFilename()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(Mockito.any(File.class))).thenReturn(false);

        employees = filesReader.readFiles(path);

        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(
                new ScanError(file1.getCanonicalPath(), "", "", "zła nazwa pliku!"));

        Assert.assertEquals(0, employees.size());
    }

    @Test
    public final void testExtractFileLocation() {
        Assert.assertEquals(location, filesReader.extractFileLocation(file1));
    }

    @Test
    public final void testReadFilesWithInvalidMonthInLocation()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(false);

        employees = filesReader.readFiles(path);

        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(
                new ScanError(file1.getCanonicalPath(), "", "Zła lokalizacja pliku!"));

        Assert.assertEquals(0, employees.size());
    }

    @Test
    public final void testReadFilesWithInvalidYearInLocation()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(false);

        employees = filesReader.readFiles(path);

        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(
                new ScanError(file1.getCanonicalPath(), "", "Zła lokalizacja pliku!"));

        Assert.assertEquals(0, employees.size());
    }

    @Test
    public final void testReadFileWithBadColumnNames()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        "Arkusz nie zawiera odpowiednich kolumn"));
    }

    @Test
    public final void testReadFileWithEmptyRow() throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(true);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "pusty wiersz!"));
    }

    @Test
    public final void testReadFileWithInvalidDateField()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "DATA", "błędnie wypełniona komórka!"));
    }

    @Test
    public final void testReadFileWithInvalidDescriptionField()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "OPIS", "błędnie wypełniona komórka!"));
    }

    @Test
    public final void testReadFileWithInvalidHoursField()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidHoursField(cell3)).thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "CZAS", "błędnie wypełniona komórka!"));
    }

    @Test
    public final void testReadFileWithYearNotEqualToLocation()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidHoursField(cell3)).thenReturn(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cell1.getDateCellValue());
        Mockito.when(readErrorsChecker.locationYearEqualsDateYear(calendar.get(Calendar.YEAR),
                location)).thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "DATA",
                        "rok nie zgadza się z lokalizacją pliku!"));
    }

    @Test
    public final void testReadFileWithMonthNotEqualToLocation()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidHoursField(cell3)).thenReturn(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cell1.getDateCellValue());
        Mockito.when(readErrorsChecker.locationYearEqualsDateYear(calendar.get(Calendar.YEAR),
                location)).thenReturn(true);
        Mockito.when(readErrorsChecker
                .locationMonthEqualsDateMonth(calendar.get(Calendar.MONTH) + 1, location))
                .thenReturn(false);
        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "DATA",
                        "miesiąc nie zgadza się z lokalizacją pliku!"));
    }

    @Test
    public final void testReadFileWithNullDateCell()
            throws IOException, InvalidFormatException {
        String date = null;
        cell1.setCellValue(date);
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);

        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), sheet.getSheetName(),
                        row.getRowNum() + 1, "DATA", "błędnie wypełniona komórka!"));
    }

    @Test
    public final void testReadFileWithToManyHoursDateCell()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidHoursField(cell3)).thenReturn(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cell1.getDateCellValue());
        Mockito.when(readErrorsChecker.locationYearEqualsDateYear(calendar.get(Calendar.YEAR),
                location)).thenReturn(true);
        Mockito.when(readErrorsChecker
                .locationMonthEqualsDateMonth(calendar.get(Calendar.MONTH) + 1, location))
                .thenReturn(true);

        List<Date> invalidDates = new ArrayList<Date>();
        invalidDates.add(cell1.getDateCellValue());

        Mockito.when(readErrorsChecker.findDatesWithInvalidHours(Mockito.any(Map.class)))
                .thenReturn(invalidDates);

        employees = filesReader.readFiles(path);
        Assert.assertEquals(0, employees.size());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = format.format(cell1.getDateCellValue());
        Mockito.verify(readErrorsHolder, Mockito.times(1))
                .addScanError(new ScanError(file1.getCanonicalPath(), "",
                        "Niepoprawna suma godzin w dniu: " + stringDate));
    }
    
    @Test
    public final void createsWorkBook() throws IOException {
        Mockito.reset(filesReader);
        Workbook wb = filesReader.createWorkBook(new File("src/test/testing-data/blankFile.xls"));
        Assert.assertTrue(wb != null);
    }
    
    @Test
    public final void testReadFileWithNoErrors()
            throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.hasProperColumnNames(sheet)).thenReturn(true);
        Mockito.when(readErrorsChecker.rowIsEmpty(row)).thenReturn(false);
        Mockito.when(readErrorsChecker.isValidDateField(cell1)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidDescriptionField(cell2)).thenReturn(true);
        Mockito.when(readErrorsChecker.isValidHoursField(cell3)).thenReturn(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cell1.getDateCellValue());
        Mockito.when(readErrorsChecker.locationYearEqualsDateYear(calendar.get(Calendar.YEAR),
                location)).thenReturn(true);
        Mockito.when(readErrorsChecker
                .locationMonthEqualsDateMonth(calendar.get(Calendar.MONTH) + 1, location))
                .thenReturn(true);

        Mockito.when(readErrorsChecker.findDatesWithInvalidHours(Mockito.any(Map.class)))
                .thenReturn(new ArrayList<Date>());

        employees = filesReader.readFiles(path);
        Assert.assertEquals(1, employees.size());
        Task task = new Task(cell1.getDateCellValue(), sheet.getSheetName(), cell2.getStringCellValue(), cell3.getNumericCellValue());
        Employee employee = employees.get(0);
        Assert.assertEquals("Jan", employee.getName());
        Assert.assertEquals("Nowak", employee.getSurname());
        Assert.assertTrue(employee.getTaskList().contains(task));
        
    }
    
    @Test
    public void testSumHoursOfDate() {
        Date date = new Date();
        filesReader.sumHoursOfDate(2, date);
        filesReader.sumHoursOfDate(3, date);
        
        Map<Date, Double> hoursOfDate = filesReader.getHoursOfDate();
        Assert.assertTrue(hoursOfDate.containsKey(date));
        Assert.assertTrue(hoursOfDate.get(date) == 5.0);
    }
    
    @Test
    public void testMergeEmployee() {
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee = new Employee("Karolina", "Kwiatkowska");
        Task task = new Task(new Date(), "projekt1", "opis1", 3);
        employee.addTask(task);
        Employee employee2 = new Employee("Karolina", "Kwiatkowska");
        Task task2 = new Task(new Date(), "projekt2", "opis2", 1);
        employee2.addTask(task2);
        
        filesReader.mergeEmployee(employees, employee);
        filesReader.mergeEmployee(employees, employee2);
        
        Assert.assertEquals(1, employees.size());
        Employee mergedEmployee = employees.get(0);
        List<Task> mergedEmployeeTasks = mergedEmployee.getTaskList();
        Assert.assertEquals(2, mergedEmployeeTasks.size());
        
        Assert.assertTrue(mergedEmployeeTasks.contains(task));
        Assert.assertTrue(mergedEmployeeTasks.contains(task2));
    }
}
