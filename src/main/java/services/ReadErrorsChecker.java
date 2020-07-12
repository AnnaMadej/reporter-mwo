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

    public static boolean sheetHasProperColumnNames(Sheet sheet) {
        if (sheet == null) {
            return false;
        }
        if (sheet.getRow(0) == null) {
            return false;
        }
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
        if (row.getCell(0) == null && row.getCell(1) == null && row.getCell(2) == null) {
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
        if (fileLocation == null) {
            return false;
        }
        Integer fileYearFromLocation = extractFileYear(fileLocation);
        if (fileYearFromLocation == -1) {
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
        if (fileLocation.equals("")) {
            return -1;
        }
        final int sizeOfYandMWithSlashes = 8;
        final char charBeforeYear;
        try {
            charBeforeYear = fileLocation
                    .charAt(fileLocation.length() - sizeOfYandMWithSlashes);
        } catch (StringIndexOutOfBoundsException e) {
            return -1;
        }

        if (charBeforeYear != '/') {
            return -1;
        }
        final int fileLocationYandMSize = 7;
        final int monthWithSlashSize = 3;
        String fileYear = fileLocation.substring(fileLocation.length() - fileLocationYandMSize,
                fileLocation.length() - monthWithSlashSize);
        Integer fileYearFromLocation;
        try {
            fileYearFromLocation = Integer.valueOf(fileYear);
        } catch (NumberFormatException e) {
            return -1;
        }
        return fileYearFromLocation;
    }

    public static boolean locationMonthIsValid(String fileLocation) {
        Integer fileMonthFromLocation = extractFileMonth(fileLocation);
        if (fileMonthFromLocation == -1) {
            return false;
        }
        final int numberOfMonths = 12;
        if (fileMonthFromLocation < 1 || fileMonthFromLocation > numberOfMonths) {
            return false;
        }
        return true;
    }

    private static Integer extractFileMonth(String fileLocation) {
        if (fileLocation.equals("")) {
            return -1;
        }
        final char charBeforeMonth = fileLocation.charAt(fileLocation.length() - 3);
        if (charBeforeMonth != '/') {
            return -1;
        }
        String monthString = fileLocation.substring(fileLocation.length() - 2);

        Integer fileMonthFromLocation;
        try {
            fileMonthFromLocation = Integer.valueOf(monthString);
        } catch (NumberFormatException e) {
            return -1;
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

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        final int yearsDifference = 25;
        c.add(Calendar.YEAR, -yearsDifference); 
        Date minDate = c.getTime();
        c.add(Calendar.YEAR, yearsDifference * 2);
        Date maxDate = c.getTime();
        Date date = dateCell.getDateCellValue();
        if (date.before(minDate) || date.after(maxDate)) {
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
        final int maxNumberOfHours = 16;
        if (hoursCell.getNumericCellValue() > maxNumberOfHours) {
            return false;
        }
        return true;
    }

    public static boolean filenameIsValid(File file) throws IOException {
        if (file == null) {
            return false;
        }
        String fileWithExtension = file.getName();
        if (!fileWithExtension.matches("[A-Z]{1}[a-z]+_{1}[A-Z]{1}[a-z]+.[a-z]{3,4}")) {
            return false;
        }
        return true;
    }

    public static boolean locationYearEqualsDateYear(Calendar calendar, String fileLocation) {
        if (calendar == null) {
            return false;
        }
        if (fileLocation == null) {
            return false;
        }
        Integer fileYearFromLocation = extractFileYear(fileLocation);
        if (fileYearFromLocation == -1) {
            return false;
        }
        if (calendar.get(Calendar.YEAR) != fileYearFromLocation) {
            return false;
        }
        return true;
    }

    public static boolean locationMonthEqualsDateMonth(Calendar calendar, String location) {
        if (calendar == null) {
            return false;
        }
        if (location == null) {
            return false;
        }
        Integer fileMonthFromLocation = extractFileMonth(location);
        if (calendar.get(calendar.MONTH) + 1  != fileMonthFromLocation) {
            return false;
        }
        return true;
    }
}
