package Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.ScanError;

public class ScanErrorsHolder {

	private static List<ScanError> scanErrors = new ArrayList<ScanError>();

	public static void addScanError(ScanError scanError) {
		scanErrors.add(scanError);
	}

	public static void clearScanErrors() {
		scanErrors = new ArrayList<ScanError>();
	}

	public static List<ScanError> getScanErrors() {
		return scanErrors;
	}

	public static String showScanErrors() {

		if (scanErrors.size() == 0) {
			return ">> Brak błędów w odczytanych plikach z danymi.";
		}

		else {
			return stringScanErrorsTable();
		}

	}

	private static String stringScanErrorsTable() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(String.join("", Collections.nCopies(25, "-"))));
		sb.append(String.format("| Błędy odczytu plików: | "));
		sb.append(String.format(String.join("", Collections.nCopies(149, "-"))));
		sb.append("\n");
		sb.append(String.format("%-120s%-14s%-10s%-10s%-45s%-1s", "| Plik", "| Projekt", "| Wiersz", "| Komórka",
				"| Opis błędu", "|"));
		sb.append("\n");
		sb.append(String.join("", Collections.nCopies(199, "-")));
		sb.append("\n");
		for (ScanError scanError : scanErrors) {
			sb.append(String.format("%-120s%-14s%-10s%-10s%-45s%-1s", "| " + scanError.getFilename(),
					"| " + scanError.getProject(), scanError.getRow() == null ? "| " + "" : "| " + scanError.getRow(),
					"| " + scanError.getCell(), "| " + scanError.getDescription(), "|"));
			sb.append("\n");
		}
		sb.append(String.join("", Collections.nCopies(199, "-")));
		return sb.toString();
	}

}
