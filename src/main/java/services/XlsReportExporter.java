package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import model.Report;
import repository.FilesWriter;
import repository.XlsFilesWriter;

public class XlsReportExporter {

    private FilesWriter filesWriter = new XlsFilesWriter();
    private Workbook wb;
    private Sheet sheet1;
    private List<String> columnNames = new ArrayList<String>();
    private List<List<String>> rows = new ArrayList<List<String>>();
    private String title = "";

    private void createHeaders(int columnNamesRow) {
        Row row = sheet1.createRow(columnNamesRow);
        int cellsCounter = 0;

        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setItalic(false);

        CellStyle headersCellStyle = wb.createCellStyle();
        headersCellStyle.setFont(font);
        headersCellStyle.setAlignment(HorizontalAlignment.CENTER);
        for (String columnName : columnNames) {
            Cell cell = row.createCell(cellsCounter);
            cell.setCellStyle(headersCellStyle);
            cell.setCellValue(columnName);
            cellsCounter++;
        }
        cellsCounter = 0;
    }

    private void createRows() {
        Row row;
        final int numberOfStartingRow = 6;
        int rowsCounter = numberOfStartingRow;
        int cellsCounter = 0;
        for (List<String> reportRow : rows) {
            row = sheet1.createRow(rowsCounter);
            rowsCounter++;
            for (String entry : reportRow) {
                Cell cell = row.createCell(cellsCounter);
                cell.setCellValue(entry);
                cellsCounter++;
            }
            cellsCounter = 0;
        }
        new Date();
    }

    private void createTittle(int titleRow) {
        Row row = sheet1.createRow(titleRow);
        Cell titleCell = row.createCell(0);

        sheet1.addMergedRegion(
                new CellRangeAddress(titleRow, titleRow, 0, columnNames.size() - 1));
        titleCell.setCellValue(title);
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = wb.createFont();
        final int fontHeight = 25;
        font.setFontHeightInPoints((short) fontHeight);
        font.setBold(true);
        titleCellStyle.setFont(font);
        titleCell.setCellStyle(titleCellStyle);
    }

    public Workbook createXlsWorkbook(Report report) throws IOException {
        wb = new HSSFWorkbook();
        sheet1 = wb.createSheet("Raport");

        columnNames = report.getColumnNames();
        rows = report.getRows();
        title = report.getTitle();

        final int reportWidth = 200 * 256;

        final int titleRow = 3;
        createTittle(titleRow);
        final int columnNamesRow = 5;
        createHeaders(columnNamesRow);
        createRows();

        for (int i = 0; i < columnNames.size(); i++) {
            sheet1.setColumnWidth(i, reportWidth / columnNames.size());
        }

        return wb;
    }

    public File exportToXls(Report report) throws IOException {
        Workbook wb = createXlsWorkbook(report);
        return filesWriter.writeToFile(wb);
    }

    public void setFilesWriter(FilesWriter filesWriter) {
        this.filesWriter = filesWriter;
    }

}