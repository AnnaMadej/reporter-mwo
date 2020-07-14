package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.ScanError;

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
        StringBuilder sb = new StringBuilder();
        final int numberOfLinesInHeader = 25;
        sb.append(String
                .format(String.join("", Collections.nCopies(numberOfLinesInHeader, "-"))));
        sb.append(String.format("| Błędy odczytu plików (" + scanErrors.size() + "): | "));
        final int spacerLines = 149;
        sb.append(String.format(String.join("", Collections.nCopies(spacerLines, "-"))));
        sb.append("\n");
        sb.append(String.format("%-120s%-14s%-10s%-10s%-45s%-1s", "| Plik", "| Projekt",
                "| Wiersz", "| Komórka", "| Opis błędu", "|"));
        sb.append("\n");
        final int numberOfLines = 199;
        sb.append(String.join("", Collections.nCopies(numberOfLines, "-")));
        sb.append("\n");
        for (ScanError scanError : scanErrors) {
            sb.append(String.format("%-120s%-14s%-10s%-10s%-45s%-1s",
                    "| " + scanError.getFilename(), "| " + scanError.getProject(),
                    scanError.getRow() == null ? "| " + "" : "| " + scanError.getRow(),
                    "| " + scanError.getCell(), "| " + scanError.getDescription(), "|"));
            sb.append("\n");
        }
        sb.append(String.join("", Collections.nCopies(numberOfLines, "-")));
        return sb.toString();
    }

}
