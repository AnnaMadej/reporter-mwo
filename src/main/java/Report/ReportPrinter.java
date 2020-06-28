package Report;

import java.util.Collections;
import java.util.List;

public class ReportPrinter {
	public static void printReport(Report report) {

		List<String> columnNames = report.getColumnNames();
		List<List<String>> rows = report.getRows();
		String title = report.getTitle();
		int lineLength = columnNames.size() * 32;

		System.out.println(title);
		if (rows.size() == 0) {
			System.out.println("RAPORT JEST PUSTY");
			System.out.println();
		} else {

			System.out.println(String.join("", Collections.nCopies(lineLength, "-")));
			for (String columnName : columnNames) {
				System.out.format("%-1s %-30s", "|", columnName);

			}

			System.out.format("%-1s", "|");
			System.out.println();
			System.out.println(String.join("", Collections.nCopies(lineLength, "-")));
			for (List<String> row : rows) {
				for (String cell : row) {
					System.out.format("%-1s %-30s", "|", cell);

				}
				System.out.format("%-1s", "|");
				System.out.println();
			}
			System.out.println(String.join("", Collections.nCopies(lineLength, "-")));

		}
	}
}