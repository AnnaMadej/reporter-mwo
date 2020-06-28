package Services;

import java.util.Collections;
import java.util.List;

import Model.Report;

public class ReportPrinter {
	
	static String reportPrint = "";
	static List<String> columnNames;
	static List<List<String>> rows;
	static String title;
	static int lineLength;
	
	public static String stringReport(Report report) {

		title = report.getTitle();
		columnNames = report.getColumnNames();
		rows = report.getRows();
		report.getTitle();
		lineLength = columnNames.size() * 32;

		addTitle();
		
		if (rows.size() == 0) {
			addEmptyInfo();
		} else {
			addLine();
			addColumnNames();
			addLine();
			addRows();
			addLine();
		}
		String toReturn = reportPrint;
		reportPrint = "";
		return toReturn;
	}

	private static void addLine() {
		reportPrint += String.join("", Collections.nCopies(lineLength, "-")) + "\n";
	}

	private static void addRows() {
		for (List<String> row : rows) {
			for (String cell : row) {
				reportPrint += String.format("%-1s %-30s", "|", cell);

			}
			reportPrint += String.format("%-1s \n", "|");
		}
	}

	private static void addColumnNames() {
		for (String columnName : columnNames) {
			reportPrint += String.format("%-1s %-30s", "|", columnName);
		}
		reportPrint += String.format("%-1s \n", "|");
	}

	private static void addEmptyInfo() {
		reportPrint += "RAPORT JEST PUSTY" + "\n";
	}

	private static void addTitle() {
		reportPrint += title + "\n";
	}
}