package services.readers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.Employee;
import model.Task;
import repository.FilesFinder;
import services.errors.ReadErrorsHolder;
import services.errors.XlsReadErrorsChecker;

public class XlsFilesReader extends FilesReader {

    private Map<Date, Double> hoursOfDate = new HashMap<Date, Double>();

    public XlsFilesReader(ReadErrorsHolder readErrorsHolder) {
        this.readErrorsChecker = new XlsReadErrorsChecker();
        this.filesFinder = new FilesFinder("xls", "xlsx");
        this.readErrorsHolder = readErrorsHolder;
    }

    protected Workbook createWorkBook(File file) throws IOException {
        final Workbook wb = WorkbookFactory.create(file);
        return wb;
    }

    public Map<Date, Double> getHoursOfDate() {
        return this.hoursOfDate;
    }

    @Override
    protected Employee readFile(File file) throws IOException, InvalidFormatException {

        String fileLocation;

        this.hoursOfDate = new HashMap<Date, Double>();

        if (!this.readErrorsChecker.filenameIsValid(file)) {
            addFilenameError(file);
            return null;
        }

        fileLocation = extractFileLocation(file);

        if (!this.readErrorsChecker.locationMonthIsValid(fileLocation)
                        || !this.readErrorsChecker.locationYearIsValid(fileLocation)) {
            addLocationError(file);
            return null;
        }

        final String fileName = file.getName();
        final String employeeName = extractEmployeeName(fileName);
        final String employeeSurname = extractEmployeeSurname(fileName);
        final Employee employee = new Employee(employeeName, employeeSurname);
        final List<Task> tasksToAdd = new ArrayList<Task>();
        final Workbook wb = createWorkBook(file);

        String project;
        String description;
        double time;

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            final Sheet sheet = wb.getSheetAt(i);
            if (!this.readErrorsChecker.hasProperColumnNames(sheet)) {
                addColumnsError(file, sheet);
                continue;
            }
            project = sheet.getSheetName();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                final Row row = sheet.getRow(j);
                if (this.readErrorsChecker.rowIsEmpty(row)) {
                    addEmptyRowError(file, sheet, j);
                    continue;
                }

                final Cell dateCell = row.getCell(0);

                if (!this.readErrorsChecker.isValidDateField(dateCell)) {
                    addDateCellError(file, sheet, j);
                    continue;
                }

                final Cell descriptionCell = row.getCell(1);
                if (!this.readErrorsChecker.isValidDescriptionField(descriptionCell)) {
                    addDescriptionCellError(file, sheet, j);
                    continue;
                }

                final Cell hoursCell = row.getCell(2);
                if (!this.readErrorsChecker.isValidHoursField(hoursCell)) {
                    addHoursCellError(file, sheet, j);
                    continue;
                }

                Date date;
                final Calendar calendar = new GregorianCalendar();

                date = dateCell.getDateCellValue();
                calendar.setTime(date);

                if (!this.readErrorsChecker.locationYearEqualsDateYear(
                                calendar.get(Calendar.YEAR), fileLocation)) {
                    addYearLocationError(file, sheet, j);
                    continue;
                }

                if (!this.readErrorsChecker.locationMonthEqualsDateMonth(
                                calendar.get(Calendar.MONTH) + 1, fileLocation)) {
                    addMonthLocationError(file, sheet, j);
                    continue;
                }
                description = descriptionCell.getStringCellValue();
                time = hoursCell.getNumericCellValue();

                sumHoursOfDate(time, date);

                final Task task = new Task(date, project, description, time);
                tasksToAdd.add(task);
            }
        }

        final List<Date> invalidDates = this.readErrorsChecker
                        .findDatesWithInvalidHours(this.hoursOfDate);

        if (invalidDates.size() > 0) {
            for (final Date date : invalidDates) {
                addHoursError(file, date);
            }
            return null;
        } else {
            for (final Task task : tasksToAdd) {
                employee.addTask(task);
            }
        }
        wb.close();
        if (employee.getTaskList().size() == 0) {
            return null;
        }
        return employee;
    }

    protected void sumHoursOfDate(double time, Date date) {
        if (this.hoursOfDate.containsKey(date)) {
            this.hoursOfDate.put(date, this.hoursOfDate.get(date) + time);
        } else {
            this.hoursOfDate.put(date, time);
        }
    }

}
