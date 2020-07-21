package services.errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Report;
import model.ScanError;
import services.ReportStringer;

public class ReadErrorsHolder {

    private List<ScanError> scanErrors = new ArrayList<ScanError>();

    public void addScanError(ScanError scanError) {
        scanErrors.add(scanError);
    }

    public void clearScanErrors() {
        scanErrors = new ArrayList<ScanError>();
    }

    public List<ScanError> getScanErrors() {
        return scanErrors;
    }

    public String showScanErrors() {

        if (scanErrors.size() == 0) {
            return ">> Brak błędów w odczytanych plikach z danymi.";
        } else {
            return stringScanErrorsTable();
        }

    }

    private String stringScanErrorsTable() {
        Report report = new Report();
        report.setTitle("Błędy odczytu plików (" + scanErrors.size() + ")");
        
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("Plik");
        columnNames.add("Projekt");
        columnNames.add("Wiersz");
        columnNames.add("Komórka");
        columnNames.add("Opis błędu");

        report.setColumnNames(columnNames);
        
        List<List<String>> rows = new ArrayList<List<String>>();
        
        for (ScanError scanError : scanErrors) {
            List<String> row = new ArrayList<String>();
            row.add(scanError.getFilename());
            row.add(scanError.getProject());
            row.add(scanError.getRow() == null ? "" : scanError.getRow().toString());
            row.add(scanError.getCell() == null ? "" : scanError.getCell().toString());
            row.add(scanError.getDescription() == null ? "" : scanError.getDescription());
            rows.add(row);
        }
       
        report.setRows(rows);
        
        return ReportStringer.stringReport(report);
    }

}
