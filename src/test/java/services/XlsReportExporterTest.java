package services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Report;
import repository.XlsFilesWriter;

public class XlsReportExporterTest extends XlsReportExporter {
    private Report report = new Report();
    private XlsReportExporter xlsReportExporter = new XlsReportExporter();

    @Before
    public void init() {
        report.setTitle("tytuł raportu");

        List<String> columnNames = new ArrayList();
        columnNames.add("Kolumna 1");
        columnNames.add("Kolumna 2");
        columnNames.add("Kolumna 3");

        report.setColumnNames(columnNames);

        List<List<String>> rows = new ArrayList<List<String>>();

        List<String> row1 = new ArrayList<String>();
        row1.add("1.1");
        row1.add("1.2");
        row1.add("1.3");

        List<String> row2 = new ArrayList<String>();
        row2.add("2.1");
        row2.add("2.2");
        row2.add("2.3");

        rows.add(row1);
        rows.add(row2);

        report.setRows(rows);
    }

    @Test
    public final void shouldCreateCorrectWorkbookTest() throws IOException {
        Workbook wb = xlsReportExporter.createXlsWorkbook(report);
        Sheet sheet = wb.getSheet("Raport");

        Row titleRow = sheet.getRow(3);
        String xlsTitle = titleRow.getCell(0).getStringCellValue();
        Assert.assertEquals("tytuł raportu", xlsTitle);

        Row columnNamesRow = sheet.getRow(5);

        Cell column1NameCell = columnNamesRow.getCell(0);

        Cell column2NameCell = columnNamesRow.getCell(1);

        Cell column3NameCell = columnNamesRow.getCell(2);

        Assert.assertEquals("Kolumna 1", column1NameCell.getStringCellValue());

        Assert.assertEquals("Kolumna 2", column2NameCell.getStringCellValue());

        Assert.assertEquals("Kolumna 3", column3NameCell.getStringCellValue());

        Row row1 = sheet.getRow(6);

        Cell row1firstCell = row1.getCell(0);

        Cell row1SecondCell = row1.getCell(1);

        Cell row1ThirdCell = row1.getCell(2);

        Assert.assertEquals("1.1", row1firstCell.getStringCellValue());

        Assert.assertEquals("1.2", row1SecondCell.getStringCellValue());

        Assert.assertEquals("1.3", row1ThirdCell.getStringCellValue());

        Row row2 = sheet.getRow(7);
        
        Cell row2firstCell = row2.getCell(0);
        
        Cell row2SecondCell = row2.getCell(1);
        
        Cell row2ThirdCell = row2.getCell(2);

        Assert.assertEquals("2.1", row2firstCell.getStringCellValue());

        Assert.assertEquals("2.2", row2SecondCell.getStringCellValue());

        Assert.assertEquals("2.3", row2ThirdCell.getStringCellValue());
    }

    @Test
    public final void shouldUseXlsFilesWriter() throws IOException {
        XlsFilesWriter filesWriter = Mockito.mock(XlsFilesWriter.class);
        xlsReportExporter.setFilesWriter(filesWriter);
        
        xlsReportExporter.exportToXls(report);
        
        Mockito.verify(filesWriter, Mockito.times(1)).writeToFile(Mockito.any(Workbook.class));
    }
}
