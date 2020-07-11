package services;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReadErrorsChecker {
    
    public static boolean sheetHasProperolumnNames(Sheet sheet) {
        if (sheet.getRow(0).getCell(0) == null || sheet.getRow(0).getCell(1) == null
                || sheet.getRow(0).getCell(2) == null
                || !sheet.getRow(0).getCell(0).getCellType().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(1).getCellType().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(2).getCellType().equals(CellType.STRING)
                || !sheet.getRow(0).getCell(0).getStringCellValue().equals("Data")
                || !sheet.getRow(0).getCell(1).getStringCellValue().equals("Zadanie")
                || !sheet.getRow(0).getCell(2).getStringCellValue().equals("Czas [h]")) {
            return false;
        }
        return true;
    }

    public static boolean rowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        return false;
    }

    public static boolean isValidDesctiptionCell(Cell descriptionCell) {
        if (descriptionCell == null || descriptionCell.getCellType() == CellType.BLANK) {
            return false;
        }
        if (descriptionCell.getCellType() != CellType.STRING) {
            return false;
        }
        if (descriptionCell.getStringCellValue().trim().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean locationYearIsValid(String fileLocation) {
        Integer fileYearFromLocation = extractFileYear(fileLocation);
        if (fileYearFromLocation == 0) {
            return false;
        }
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        final int yearsRange = 25;
        if (fileYearFromLocation < currentYear - yearsRange
                || fileYearFromLocation > currentYear + yearsRange) {
            return false;
        }
        return true;
    }

    private static Integer extractFileYear(String fileLocation) {
        final int fileLocationYandMSize = 7;
        final int fileExtensionSize = 3;
        String fileYear = fileLocation.substring(fileLocation.length() - fileLocationYandMSize,
                fileLocation.length() - fileExtensionSize);
        final int sizeOfYear = 4;
        if (fileYear.length() != sizeOfYear) {
            return 0;
        }
        Integer fileYearFromLocation;
        try {
            fileYearFromLocation = Integer.valueOf(fileYear);
        } catch (NumberFormatException e) {
            return 0;
        }
        return fileYearFromLocation;
    }

    public static boolean locationMonthIsValid(String fileLocation) throws IOException {
        Integer fileMonthFromLocation = extractFileMonth(fileLocation);
        if (fileMonthFromLocation == 0) {
            return false;
        }
        final int numberOfMonths = 12;
        if (fileMonthFromLocation < 1 || fileMonthFromLocation > numberOfMonths) {
            return false;
        }
        return true;
    }

    private static Integer extractFileMonth(String fileLocation) {
        String monthString = fileLocation.substring(fileLocation.length() - 2);
        if (monthString.length() < 1 || monthString.length() > 2) {
            return 0;
        }
        Integer fileMonthFromLocation;
        try {
            fileMonthFromLocation = Integer.valueOf(monthString);
        } catch (NumberFormatException e) {
            return 0;
        }
        return fileMonthFromLocation;
    }

    public static boolean isValidDateCell(Cell dateCell) {
        if (dateCell == null || dateCell.getCellType() == CellType.BLANK) {
            return false;
        }
        if (!dateCell.getCellType().equals(CellType.NUMERIC)) {
            return false;
        }
        return true;
    }

    public static boolean isValidHoursCell(Cell hoursCell) {
        if (hoursCell == null || hoursCell.getCellType() == CellType.BLANK) {
            return false;
        }
        if (hoursCell.getCellType() != CellType.NUMERIC) {
            return false;
        }
        final int maxNumberOfHours = 24;
        if (hoursCell.getNumericCellValue() > maxNumberOfHours) {
            return false;
        }
        return true;
    }

    public static boolean filenameIsValid(File file) throws IOException {
        String filename = file.getName().substring(0, file.getName().indexOf("."));
        if (!filename.matches("[A-z]+_[A-z]+")) {
            return false;
        }
        return true;
    }
    
    public static boolean locationYearEqualsTaskYear(Calendar calendar, String fileLocation) {
        Integer fileYearFromLocation = extractFileYear(fileLocation);
        if (calendar.get(Calendar.YEAR) != fileYearFromLocation) {
            return false;
        }
        return true;
    }
    
    public static boolean locationMonthEqualsTaskMonth(Calendar calendar, String location) {
        Integer fileMonthFromLocation = extractFileMonth(location);
        if (calendar.get(Calendar.MONTH) + 1 != fileMonthFromLocation) {
            return false;
        }
        return true;
    }
}
