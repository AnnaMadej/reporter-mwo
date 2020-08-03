package services.errors;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.Report;
import model.ScanError;

public class ReadErrorsHolderUnitTests {

    private ReadErrorsHolder errorsHolder = new ReadErrorsHolder();

    @Test
    public final void testsAddsScanErrorsToList() {
        ScanError error = new ScanError("sciezka", "arkusz", "opis");
        errorsHolder.addScanError(error);
        Assert.assertEquals(1, errorsHolder.getScanErrors().size());
        Assert.assertTrue(errorsHolder.getScanErrors().contains(error));
    }

    @Test
    public final void testClearsScanErrorsList() {
        ScanError error = new ScanError("sciezka", "arkusz", "opis");
        errorsHolder.addScanError(error);
        Assert.assertEquals(1, errorsHolder.getScanErrors().size());
        errorsHolder.clearScanErrors();
        Assert.assertEquals(0, errorsHolder.getScanErrors().size());
    }

    @Test
    public final void testCreatesErrorsReport() {
        ScanError error = new ScanError("sciezka", "arkusz", "opis");
        errorsHolder.addScanError(error);
        ScanError error2 = new ScanError("nazwaPliku", "Projekt", 1, "Komórka", "Opis");
        errorsHolder.addScanError(error2);

        Report report = errorsHolder.getErrorsReport();

        Assert.assertEquals("Błędy odczytu plików (2)", report.getTitle());

        List<String> columnNames = new ArrayList<String>();
        columnNames.add("Plik");
        columnNames.add("Projekt");
        columnNames.add("Wiersz");
        columnNames.add("Komórka");
        columnNames.add("Opis błędu");

        Assert.assertEquals(columnNames, report.getColumnNames());

        Assert.assertEquals(2, errorsHolder.getScanErrors().size());

        List<String> row1 = new ArrayList<String>();
        row1.add("sciezka");
        row1.add("arkusz");
        row1.add("");
        row1.add("");
        row1.add("opis");

        List<String> row2 = new ArrayList<String>();
        row2.add("nazwaPliku");
        row2.add("Projekt");
        row2.add("1");
        row2.add("Komórka");
        row2.add("Opis");

        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(row1);
        rows.add(row2);

        Assert.assertEquals(2, report.getRows().size());
        Assert.assertTrue(report.getRows().containsAll(rows));
    }

}
