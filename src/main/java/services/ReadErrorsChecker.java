package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public abstract class ReadErrorsChecker {
    
    protected static final double maxNumberOfHoursADay = 24;
    
    
    public abstract boolean hasProperColumnNames(Object projectSheet);
    
    public abstract boolean rowIsEmpty(Object dataRow);
    
    public abstract boolean isValidDescriptionField(Object descriptionField);
    
    public abstract boolean isValidDateField(Object dateField);
    
    public abstract boolean isValidHoursField(Object hoursField);
    
    public boolean locationYearIsValid(String fileLocation) {
        if (fileLocation == null) {
            return false;
        }
        Integer fileYearFromLocation = extractYear(fileLocation);
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

    protected Integer extractYear(String fileLocation) {
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

    public boolean locationMonthIsValid(String fileLocation) {
        Integer fileMonthFromLocation = extractMonth(fileLocation);
        if (fileMonthFromLocation == -1) {
            return false;
        }
        final int numberOfMonths = 12;
        if (fileMonthFromLocation < 1 || fileMonthFromLocation > numberOfMonths) {
            return false;
        }
        return true;
    }

    protected Integer extractMonth(String fileLocation) {
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
    
    public boolean locationYearEqualsDateYear(int year, String fileLocation) {
        if (fileLocation == null) {
            return false;
        }
        Integer fileYearFromLocation = this.extractYear(fileLocation);
        if (fileYearFromLocation == -1) {
            return false;
        }
        if (year != fileYearFromLocation) {
            return false;
        }
        return true;
    }

    public boolean locationMonthEqualsDateMonth(int month, String location) {
        if (location == null) {
            return false;
        }
        Integer fileMonthFromLocation = this.extractMonth(location);
        if (month != fileMonthFromLocation) {
            return false;
        }
        return true;
    }
    
    public List<Date> findDatesWithInvalidHours(Map<Date, Double> hoursOfDate) {
        List<Date> invalidDates = new ArrayList<Date>();
        for (Date date : hoursOfDate.keySet()) {
            Double hours = hoursOfDate.get(date);
            if (hours > maxNumberOfHoursADay || hours < 0) {
                invalidDates.add(date);
            }
        }
        return invalidDates;
    }
    
    public boolean filenameIsValid(File file) throws IOException {
        if (file == null) {
            return false;
        }
        String fileWithExtension = file.getName();
        if (!fileWithExtension.matches("[A-Z]{1}[a-z]+_{1}[A-Z]{1}[a-z]+.[a-z]{3,4}")) {
            return false;
        }
        return true;
    }
}
