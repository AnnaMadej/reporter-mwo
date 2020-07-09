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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import services.ScanErrorsHolder;

public class FilesReader {

    private Integer fileMonthFromLocation;
    private Integer fileYearFromLocation;
    private String fileLocation;

    private String extractEmployeeName(String fileName) {
        return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
    }

    private String extractEmployeeSurname(String fileName) {
        return fileName.substring(0, fileName.indexOf("_"));
    }

    private void extractFileLocation(File file) {
        this.fileLocation = file.getParent();
    }

    private boolean filenameIsValid(File file) throws IOException {
        String filename = file.getName().substring(0, file.getName().indexOf("."));
        if (!filename.matches("[A-z]+_[A-z]+")) {
            return false;
        }
        return true;
    }

    private boolean isValidDateCell(Cell dateCell) {
        if (dateCell == null || dateCell.getCellTypeEnum() == CellType.BLANK) {
            return false;
        }
        if (!dateCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            return false;
        }
        return true;
    }

    private boolean isValidDesctiptionCell(Cell descriptionCell) {
        if (descriptionCell == null || descriptionCell.getCellTypeEnum() == CellType.BLANK) {
            return false;
        }
        if (descriptionCell.getCellTypeEnum() != CellType.STRING) {
            return false;
        }
        if (descriptionCell.getStringCellValue().trim().equals("")) {
            return false;
        }
        return true;
    }

    private boolean isValidHoursCell(Cell hoursCell) {
        if (hoursCell == null || hoursCell.getCellTypeEnum() == CellType.BLANK) {
            return false;
        }
        if (hoursCell.getCellTypeEnum() != CellType.NUMERIC) {
            return false;
        }
        final int maxNumberOfHours = 24;
        if (hoursCell.getNumericCellValue() > maxNumberOfHours) {
            return false;
        }
        return true;
    }

    private boolean locationMonthEqualsTaskMonth(Calendar calendar) {
        if (calendar.get(Calendar.MONTH) + 1 != this.fileMonthFromLocation) {
            return false;
        }
        return true;
    }

    private boolean locationMonthIsValid(File file) throws IOException {
        String monthString = this.fileLocation.substring(this.fileLocation.length() - 2);
        if (monthString.length() < 1 || monthString.length() > 2) {
            return false;
        }
        try {
            this.fileMonthFromLocation = Integer.valueOf(monthString);
        } catch (NumberFormatException e) {
            return false;
        }
        final int numberOfMonths = 12;
        if (this.fileMonthFromLocation < 1 || this.fileMonthFromLocation > numberOfMonths) {
            return false;
        }
        return true;
    }

    private boolean locationYearEqualsTaskYear(Calendar calendar) {
        if (calendar.get(Calendar.YEAR) != this.fileYearFromLocation) {
            return false;
        }
        return true;
    }

    private boolean locationYearIsValid() {
        final int fileLocationYandMSize = 7;
        final int fileExtensionSize = 3;
        final int sizeOfYear = 4;
        String fileYear = this.fileLocation.substring(
                this.fileLocation.length() - fileLocationYandMSize,
                this.fileLocation.length() - fileExtensionSize);
        if (fileYear.length() != sizeOfYear) {
            return false;
        }
        try {
            this.fileYearFromLocation = Integer.valueOf(fileYear);
        } catch (NumberFormatException e) {
            return false;
        }
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        final int yearsRange = 25;
        if (this.fileYearFromLocation < currentYear - yearsRange
                || this.fileYearFromLocation > currentYear + yearsRange) {
            return false;
        }
        return true;
    }

    public Employee readFile(File file) throws IOException, InvalidFormatException {

        this.extractFileLocation(file);

        if (!this.filenameIsValid(file)) {
            ScanErrorsHolder.addScanError(
                    new ScanError(file.getCanonicalPath(), "", "", "zła nazwa pliku!"));
            return null;
        }
        if (!this.locationYearIsValid() || !this.locationMonthIsValid(file)) {
            ScanErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                    file.getCanonicalPath(), "Zła lokalizacja pliku!"));
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
            if (!this.sheetHasProperolumnNames(sheet)) {
                ScanErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                        sheet.getSheetName(), "Arkusz nie zawiera odpowiednich kolumn"));
                continue;
            }

            project = sheet.getSheetName();
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                if (this.rowIsEmpty(row)) {
                    ScanErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                            sheet.getSheetName(), j + 1, "pusty wiersz!"));
                    continue;
                }

                Cell dateCell = row.getCell(0);

                if (!this.isValidDateCell(dateCell)) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "DATA", "błędnie wypełniona komórka!"));
                    continue;
                }

                Cell descriptionCell = row.getCell(1);
                if (!this.isValidDesctiptionCell(descriptionCell)) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "OPIS", "błędnie wypełniona komórka!"));
                    continue;
                }

                Cell hoursCell = row.getCell(2);
                if (!this.isValidHoursCell(hoursCell)) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "CZAS", "błędnie wypełniona komórka!"));
                    continue;
                }

                Date date;
                Calendar calendar = new GregorianCalendar();
                try {
                    date = dateCell.getDateCellValue();
                    calendar.setTime(date);
                } catch (NullPointerException e) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "DATA", "błędnie wypełniona komórka!"));
                    continue;
                }
                if (!this.locationYearEqualsTaskYear(calendar)) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "DATA", "rok nie zgadza się z lokalizacją pliku!"));
                    continue;
                }

                if (!this.locationMonthEqualsTaskMonth(calendar)) {
                    ScanErrorsHolder.addScanError(
                            new ScanError(file.getCanonicalPath(), sheet.getSheetName(), j + 1,
                                    "DATA", "miesiąc nie zgadza się z lokalizacją pliku!"));
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

    private boolean rowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        return false;
    }

    private boolean sheetHasProperolumnNames(Sheet sheet) {
        if (sheet.getRow(0).getCell(0) == null || sheet.getRow(0).getCell(1) == null
                || sheet.getRow(0).getCell(2) == null
                || !sheet.getRow(0).getCell(0).getCellTypeEnum().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(1).getCellTypeEnum().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(2).getCellTypeEnum().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(0).getStringCellValue().equals("Data")
                || !sheet.getRow(0).getCell(1).getStringCellValue().equals("Zadanie")
                || !sheet.getRow(0).getCell(2).getStringCellValue().equals("Czas [h]")) {
            return false;
        }
        return true;
    }
}
