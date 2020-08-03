package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Report;

public class ReportStringer {

    List<String> columnNames = new ArrayList<String>();
    List<List<String>> rows = new ArrayList<List<String>>();
    String title = "";
    List<Integer> columnsWidths = new ArrayList<Integer>();
    int lineWidth = 0;

    StringBuilder sb = new StringBuilder();

    private void addColumnNames() {
        for (int i = 0; i < columnNames.size(); i++) {
            int columnWidth = columnsWidths.get(i);
            sb.append(String.format("%-1s %-" + columnWidth + "s", "|", columnNames.get(i)));
        }
        sb.append(String.format("%-1s \n", "|"));
    }

    private void addEmptyInfo() {
        sb.append("RAPORT JEST PUSTY" + "\n");
    }

    private void addLine() {
        sb.append(String.join("", Collections.nCopies(lineWidth, "-")) + "\n");
    }

    private void addRows() {
        for (int i = 0; i < rows.size(); i++) {

            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                String cell = row.get(j);
                int columnWidth = columnsWidths.get(j);
                sb.append(String.format("%-1s %-" + columnWidth + "s", "|", cell));
            }
            sb.append(String.format("%-1s \n", "|"));
        }
    }

    private void addTitle() {
        sb.append(title + "\n");
    }

    public String stringReport(Report report) {

        if (report == null) {
            return "Brak raportu!";
        }

        columnsWidths = new ArrayList<Integer>();
        findColumnsWidths(report);
        lineWidth = report.getColumnNames().size() + 1 + report.getColumnNames().size();
        for (int columnWidth : columnsWidths) {
            lineWidth += columnWidth;
        }

        title = report.getTitle();
        columnNames = report.getColumnNames();
        rows = report.getRows();
        report.getTitle();

        addTitle();

        if (columnNames.size() == 0) {
            sb.append("Raport nie zawiera nazw kolumn!");
        } else {
            if (rows.size() == 0) {
                addEmptyInfo();
            } else {
                addLine();
                addColumnNames();
                addLine();
                addRows();
                addLine();
            }
        }

        String toReturn = sb.toString();
        sb = new StringBuilder();
        return toReturn;
    }

    private int findColumnWidth(int columnNumber, Report report) {
        int columnWidth = report.getColumnNames().get(columnNumber).length() + 2;
        for (List<String> row : report.getRows()) {
            if (row.get(columnNumber).length() + 2 > columnWidth) {
                columnWidth = row.get(columnNumber).length() + 2;
            }
        }
        return columnWidth;
    }

    private void findColumnsWidths(Report report) {
        for (int i = 0; i < report.getColumnNames().size(); i++) {
            columnsWidths.add(findColumnWidth(i, report));
        }
    }
}