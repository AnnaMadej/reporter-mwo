package services.readers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Employee;
import model.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import repository.FilesFinder;
import services.ReadErrorsHolder;
import services.XlsReadErrorsChecker;


public class XlsFilesReader extends FilesReader {
    
    private WorkbookFactory wb;
    
    public XlsFilesReader(ReadErrorsHolder readErrorsHolder) {
        this.readErrorsChecker = new XlsReadErrorsChecker();
        this.filesFinder = new FilesFinder("xls", "xlsx");
        this.readErrorsHolder = readErrorsHolder;
    }

    protected Employee readFile(File file) throws IOException, InvalidFormatException {

        String fileLocation;
        Map<Date, Double> hoursOfDate = new HashMap<Date, Double>(); 
       

        if (!readErrorsChecker.filenameIsValid(file)) {
            addFilenameError(file);
            return null;
        }
        
        fileLocation = this.extractFileLocation(file);
        
        if (!readErrorsChecker.locationMonthIsValid(fileLocation)
                || !readErrorsChecker.locationYearIsValid(fileLocation)) {
            addLocationError(file);
            return null;
        }

        String fileName = file.getName();
        String employeeName = this.extractEmployeeName(fileName);
        String employeeSurname = this.extractEmployeeSurname(fileName);
        Employee employee = new Employee(employeeName, employeeSurname);
        List<Task> tasksToAdd = new ArrayList<Task>();
        Workbook wb = createWorkBook(file);
        
        String project;
        String description;
        double time;

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            if (!readErrorsChecker.hasProperColumnNames(sheet)) {
                addColumnsError(file, sheet);
                continue;
            }
            project = sheet.getSheetName();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (readErrorsChecker.rowIsEmpty(row)) {
                    addEmptyRowError(file, sheet, j);
                    continue;
                }

                Cell dateCell = row.getCell(0);

                if (!readErrorsChecker.isValidDateField(dateCell)) {
                    addDateCellError(file, sheet, j);
                    continue;
                }

                Cell descriptionCell = row.getCell(1);
                if (!readErrorsChecker.isValidDesctiptionField(descriptionCell)) {
                    addDescriptionCellError(file, sheet, j);
                    continue;
                }

                Cell hoursCell = row.getCell(2);
                if (!readErrorsChecker.isValidHoursField(hoursCell)) {
                    addHoursCellError(file, sheet, j);
                    continue;
                }

                Date date;
                Calendar calendar = new GregorianCalendar(); 
                try {
                    date = dateCell.getDateCellValue();
                    calendar.setTime(date);
                } catch (NullPointerException e) {
                    addDateCellError(file, sheet, j);
                    continue;
                }
                if (!readErrorsChecker.locationYearEqualsDateYear(
                        calendar.get(Calendar.YEAR), fileLocation)) {
                    addYearLocationError(file, sheet, j);
                    continue;
                }

                if (!readErrorsChecker.locationMonthEqualsDateMonth(
                        calendar.get(Calendar.MONTH) + 1, fileLocation)) {
                    addMonthLocationError(file, sheet, j);
                    continue;
                }
                description = descriptionCell.getStringCellValue();
                time = hoursCell.getNumericCellValue();

                if (hoursOfDate.containsKey(date)) {
                    hoursOfDate.put(date, hoursOfDate.get(date) + time);
                } else {
                    hoursOfDate.put(date, time);
                }
                Task task = new Task(date, project, description, time);
                tasksToAdd.add(task);
                employee.addTask(task);

            }
        }

        List<Date> invalidDates = readErrorsChecker.findDatesWithInvalidHours(hoursOfDate);

        if (invalidDates.size() > 0) {
            for (Date date : invalidDates) {
                addHoursError(file, date);
            }
        } else {
            for (Task task : tasksToAdd) {
                employee.addTask(task);
            }
        }
        wb.close();
        return employee;
    }

    private Workbook createWorkBook(File file) throws IOException {
        Workbook wb = WorkbookFactory.create(file);
        return wb;
    }



}
