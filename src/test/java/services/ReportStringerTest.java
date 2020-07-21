package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.SubstringMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;

import model.Report;

public class ReportStringerTest {

    @Test
    public final void testPrintReportFullReport() {
        String reportTitle = "Tytul raportu";
        List<String> columnNames = new ArrayList<String>();
        List<List<String>> rows = new ArrayList<List<String>>();

        columnNames.add("kolumna1");
        columnNames.add("kolumna2");
        columnNames.add("kolumna3");

        List<String> row1 = new ArrayList<String>();
        row1.add("1.1");
        row1.add("1.2");
        row1.add("1.3");

        List<String> row2 = new ArrayList<String>();
        row2.add("kolumna2.wiersz1");
        row2.add("kolumna2.wiersz2");
        row2.add("kolumna2.wiersz3");

        rows.add(row1);
        rows.add(row2);

        Report report = new Report();
        report.setTitle(reportTitle);
        report.setColumnNames(columnNames);
        report.setRows(rows);

        String reportString = ReportStringer.stringReport(report);

        MatcherAssert.assertThat(reportString, CoreMatchers.startsWith("Tytul raportu"));

        Scanner sc = new Scanner(reportString);

        List<String> reportLines = new ArrayList<String>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.replace(" ", "").replace("|", " ").replace("-", "");
            if (!line.contains("Tytul Raportu") && !line.equals("")) {
                reportLines.add(line);
            }

        }

        String columnsLine = reportLines.get(1);
        MatcherAssert.assertThat(columnsLine,
                CoreMatchers.containsString("kolumna1 kolumna2 kolumna3"));

        for (int lineIndex = 2; lineIndex < reportLines.size(); lineIndex++) {
            String line = reportLines.get(lineIndex);
            MatcherAssert.assertThat(line,
                    CoreMatchers.anyOf(CoreMatchers.containsString("1.1 1.2 1.3"),
                            CoreMatchers.containsString("kolumna2.wiersz1 kolumna2.wiersz2 kolumna2.wiersz3")));
        }

        Assert.assertNotEquals(reportLines.get(2), reportLines.get(3));
        Assert.assertEquals(4, reportLines.size());

    }

    @Test
    public final void testPrintReportEmptyReport() {
        String reportTitle = "Tytul raportu";
        List<String> columnNames = new ArrayList<String>();

        columnNames.add("kolumna1");
        columnNames.add("kolumna2");
        columnNames.add("kolumna3");

        Report report = new Report();
        report.setTitle(reportTitle);
        report.setColumnNames(columnNames);

        String reportString = ReportStringer.stringReport(report);
        MatcherAssert.assertThat(reportString, CoreMatchers.startsWith("Tytul raportu"));
        MatcherAssert.assertThat(reportString,
                CoreMatchers.containsString("RAPORT JEST PUSTY"));

        Scanner sc = new Scanner(reportString);
        int linesSum = 0;
        while (sc.hasNextLine()) {
            sc.nextLine();
            linesSum++;
        }
        Assert.assertEquals(2, linesSum);
    }

    @Test
    public final void testPrintReportNullReport() {
        String reportString = ReportStringer.stringReport(null);
        MatcherAssert.assertThat(reportString, CoreMatchers.equalTo("Brak raportu!"));
        Scanner sc = new Scanner(reportString);
        int linesSum = 0;
        while (sc.hasNextLine()) {
            sc.nextLine();
            linesSum++;
        }
        Assert.assertEquals(1, linesSum);
    }
    
    @Test
    public final void testPrintReportNoColumns() {
        ReportStringer reportStringer = new ReportStringer();
        Report report = new Report();
        report.setTitle("Tytul raportu");
        String reportString = reportStringer.stringReport(report);
        MatcherAssert.assertThat(reportString, CoreMatchers.startsWith("Tytul raportu"));
        MatcherAssert.assertThat(reportString,
                CoreMatchers.containsString("Raport nie zawiera nazw kolumn!"));
        Scanner sc = new Scanner(reportString);
        int linesSum = 0;
        while (sc.hasNextLine()) {
            sc.nextLine();
            linesSum++;
        }
        Assert.assertEquals(2, linesSum);
    }

}
