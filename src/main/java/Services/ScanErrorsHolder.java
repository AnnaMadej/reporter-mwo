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

	private static void printScanErrorsTable() {
		System.out.println(String.join("", Collections.nCopies(25, "-")));
		System.out.println("| Błędy odczytu plików: | ");
		System.out.println(String.join("", Collections.nCopies(159, "-")));
		System.out.format("%-80s%-14s%-10s%-10s%-45s%-1s", "| Plik", "| Projekt", "| Wiersz", "| Komórka",
				"| Opis błędu", "|");
		System.out.println();
		System.out.println(String.join("", Collections.nCopies(159, "-")));
		for (ScanError scanError : scanErrors) {
			System.out.format("%-80s%-14s%-10s%-10s%-45s%-1s", "| " + scanError.getFilename(),
					"| " + scanError.getProject(), scanError.getRow() == null ? "| " + "" : "| " + scanError.getRow(),
					"| " + scanError.getCell(), "| " + scanError.getDescription(), "|");
			System.out.println();
		}
		System.out.println(String.join("", Collections.nCopies(159, "-")));
	}

	public static void showScanErrors() {

		if (scanErrors.size() == 0) {
			System.out.println();
			System.out.println(">> Brak błędów w odczytanych plikach z danymi.");
			System.out.println();
		}

		else {
			printScanErrorsTable();

		}

	}

}
