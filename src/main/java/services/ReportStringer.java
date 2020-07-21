package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Report;

public class ReportStringer {

    static List<String> columnNames = new ArrayList<String>();
    static List<List<String>> rows = new ArrayList<List<String>>();
    static String title = "";
    static List<Integer> columnsWidths = new ArrayList<Integer>();
    static int lineWidth = 0;

    static StringBuilder sb = new StringBuilder();

    private static void addColumnNames() {
        for (int i = 0; i < columnNames.size(); i++) {
            int columnWidth = columnsWidths.get(i);
            sb.append(String.format("%-1s %-" + columnWidth + "s", "|", columnNames.get(i)));
        }
        sb.append(String.format("%-1s \n", "|"));
    }

    private static void addEmptyInfo() {
        sb.append("RAPORT JEST PUSTY" + "\n");
    }

    private static void addLine() {
        sb.append(String.join("", Collections.nCopies(lineWidth, "-")) + "\n");
    }

    private static void addRows() {
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

    private static void addTitle() {
        sb.append(title + "\n");
    }

    public static String stringReport(Report report) {

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

    private static int findColumnWidth(int columnNumber, Report report) {
        int columnWidth = report.getColumnNames().get(columnNumber).length() + 2;
        for (List<String> row : report.getRows()) {
            if (row.get(columnNumber).length() + 2 > columnWidth) {
                columnWidth = row.get(columnNumber).length() + 2;
            }
        }
        return columnWidth;
    }

    private static void findColumnsWidths(Report report) {
        for (int i = 0; i < report.getColumnNames().size(); i++) {
            columnsWidths.add(findColumnWidth(i, report));
        }
    }
}