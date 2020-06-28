package Report;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import Model.Report;

public class Report7Builder {

	public List<String> findEmployee(List<List<String>> rows, String name) {
		String[] splitName = name.toLowerCase().trim().split(" +");

		for (List<String> row : rows) {
			String[] splitID = row.get(1).toLowerCase().trim().split(" +");
			if ((splitName[0].equals(splitID[0]) && splitName[1].equals(splitID[1]))
					|| (splitName[1].equals(splitID[0]) && splitName[0].equals(splitID[1]))) {
				return row;
			}
		}
		return null;
	}

	public PieChart plotChart(Report report, String name, int year) {

		List<List<String>> rows = report.getRows();
		List<String> columnNames = report.getColumnNames();
		List<String> row = findEmployee(rows, name);

		PieChart chart = new PieChartBuilder().width(800).height(600)
				.title(name + ". Procentowe zaangażowanie w projekty za rok " + year).build();
		chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
		chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

		if (row != null) {
			for (int project = 2; project < columnNames.size(); project++) {
				String value = row.get(project);
				if (value != null && !value.equals("")) {

					value = value.substring(0, value.length() - 1);
					chart.addSeries(columnNames.get(project), Double.valueOf(value));
				}

			}

			JFrame frame = new SwingWrapper(chart).displayChart();
			javax.swing.SwingUtilities
					.invokeLater(() -> frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));
		} else {
			System.out.println("Wpisz poprawne dane pracownika");
		}
		return chart;
	}
}
