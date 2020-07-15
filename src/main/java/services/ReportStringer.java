package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Report;

public class ReportStringer {

    static List<String> columnNames = new ArrayList<String>();
    static List<List<String>> rows = new ArrayList<List<String>>();
    static String title = "";
    static int lineLength;

    static StringBuilder sb = new StringBuilder();

    private static void addColumnNames() {
        for (String columnName : columnNames) {
            sb.append(String.format("%-1s %-30s", "|", columnName));
        }
        sb.append(String.format("%-1s \n", "|"));
    }

    private static void addEmptyInfo() {
        sb.append("RAPORT JEST PUSTY" + "\n");
    }

    private static void addLine() {
        sb.append(String.join("", Collections.nCopies(lineLength, "-")) + "\n");
    }

    private static void addRows() {
        for (List<String> row : rows) {
            for (String cell : row) {
                sb.append(String.format("%-1s %-30s", "|", cell));

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

        title = report.getTitle();
        columnNames = report.getColumnNames();
        rows = report.getRows();
        report.getTitle();
        final int sizeOfSingleColumn = 32;
        lineLength = columnNames.size() * sizeOfSingleColumn;

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
}