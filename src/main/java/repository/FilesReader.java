package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import model.Employee;
import model.ScanError;
import model.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import services.ReadErrorsChecker;
import services.ReadErrorsHolder;

public class FilesReader {

    public Employee readFile(File file) throws IOException, InvalidFormatException {

        String fileLocation = this.extractFileLocation(file);

        if (!ReadErrorsChecker.filenameIsValid(file)) {
            addFilenameError(file);
            return null;
        }
        if (!ReadErrorsChecker.locationYearIsValid(fileLocation) 
                || !ReadErrorsChecker.locationMonthIsValid(fileLocation)) {
            addLocationError(file);
            return null;
        }

        String fileName = file.getName();
        String employeeName = this.extractEmployeeName(fileName);
        String employeeSurname = this.extractEmployeeSurname(fileName);
        Employee employee = new Employee(employeeName, employeeSurname);
        new ArrayList<Task>();
        Workbook wb = WorkbookFactory.create(file);

        String project;
        String description;
        double time;

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            if (!ReadErrorsChecker.sheetHasProperolumnNames(sheet)) {
                addColumnsError(file, sheet);
                continue;
            }

            project = sheet.getSheetName();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (ReadErrorsChecker.rowIsEmpty(row)) {
                    addEmptyRowError(file, sheet, j);
                    continue;
                }

                Cell dateCell = row.getCell(0);

                if (!ReadErrorsChecker.isValidDateCell(dateCell)) {
                    addDateCellError(file, sheet, j);
                    continue;
                }

                Cell descriptionCell = row.getCell(1);
                if (!ReadErrorsChecker.isValidDesctiptionCell(descriptionCell)) {
                    addDescriptionCellError(file, sheet, j);
                    continue;
                }

                Cell hoursCell = row.getCell(2);
                if (!ReadErrorsChecker.isValidHoursCell(hoursCell)) {
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
                if (!ReadErrorsChecker.locationYearEqualsTaskYear(calendar, fileLocation)) {
                    addYearLocationError(file, sheet, j);
                    continue;
                }

                if (!ReadErrorsChecker.locationMonthEqualsTaskMonth(calendar, fileLocation)) {
                    addMonthLocationError(file, sheet, j);
                    continue;
                }

                description = descriptionCell.getStringCellValue();
                time = hoursCell.getNumericCellValue();

                Task task = new Task(date, project, description, time);
                employee.addTask(task);

            }

        }
        wb.close();
        return employee;
    }
    
    private String extractEmployeeName(String fileName) {
        return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
    }

    private String extractEmployeeSurname(String fileName) {
        return fileName.substring(0, fileName.indexOf("_"));
    }

    private String extractFileLocation(File file) {
        return file.getParent();
    }

    private void addMonthLocationError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                        "DATA", "miesiąc nie zgadza się z lokalizacją pliku!"));
    }

    private void addYearLocationError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                        "DATA", "rok nie zgadza się z lokalizacją pliku!"));
    }

    private void addHoursCellError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                        "CZAS", "błędnie wypełniona komórka!"));
    }

    private void addDescriptionCellError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                        "OPIS", "błędnie wypełniona komórka!"));
    }

    private void addDateCellError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                        "DATA", "błędnie wypełniona komórka!"));
    }

    private void addEmptyRowError(File file, Sheet sheet, int j) throws IOException {
        ReadErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), j + 1, "pusty wiersz!"));
    }

    private void addColumnsError(File file, Sheet sheet) throws IOException {
        ReadErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), "Arkusz nie zawiera odpowiednich kolumn"));
    }

    private void addLocationError(File file) throws IOException {
        ReadErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                file.getCanonicalPath(), "Zła lokalizacja pliku!"));
    }

    private void addFilenameError(File file) throws IOException {
        ReadErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), "", "", "zła nazwa pliku!"));
    }

}
