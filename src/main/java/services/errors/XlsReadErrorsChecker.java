package services.errors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class XlsReadErrorsChecker extends ReadErrorsChecker {
    
    public boolean hasProperColumnNames(Object projectSheet) {
        if (projectSheet == null) {
            return false;
        }
        if (!(projectSheet instanceof Sheet)) {
            return false;
        }
        Sheet sheet = (Sheet) projectSheet;
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

    public boolean rowIsEmpty(Object dataRow) {
        if (dataRow == null) {
            return true;
        }
        if (!(dataRow instanceof Row)) {
            return true;
        }
        
        Row row = (Row) dataRow;
        if (row.getPhysicalNumberOfCells() == 0) {
            return true;
        }
        return false;
    }

    public boolean isValidDescriptionField(Object descriptionField) {
        if (descriptionField == null) {
            return false;
        }
        if (!(descriptionField instanceof Cell)) {
            return false;
        }
        
        Cell descriptionCell = (Cell) descriptionField;
        if (descriptionCell.getCellType() == CellType.BLANK) {
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

  

    public boolean isValidDateField(Object dateField) {
        if (dateField == null) {
            return false;
        }        
        if (!(dateField instanceof Cell)) {
            return false;
        }
        
        Cell dateCell = (Cell) dateField;
        
        if (dateCell.getCellType() == CellType.BLANK) {
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

    public boolean isValidHoursField(Object hoursField) {
        if (hoursField == null) {
            return false;
        }        
        if (!(hoursField instanceof Cell)) {
            return false;
        }
        
        Cell hoursCell = (Cell) hoursField;
        
        
        if (hoursCell == null || hoursCell.getCellType() == CellType.BLANK) {
            return false;
        }
        if (hoursCell.getCellType() != CellType.NUMERIC) {
            return false;
        }
        if (hoursCell.getNumericCellValue() > maxNumberOfHoursADay) {
            return false;
        }
        return true;
    }

}
