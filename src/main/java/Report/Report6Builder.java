package Report;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import Model.Report;

public class Report6Builder {

	public CategoryChart plotBarChart(Report report, int year) {

		List<List<String>> rows = report.getRows();
		String[] projectNames = new String[rows.size()];
		Double[] projectHours = new Double[rows.size()];

		int i = 0;
		for (List<String> row : rows) {
			projectNames[i] = row.get(1);
			projectHours[i] = Double.valueOf(row.get(2));
			i++;
		}

		CategoryChart barChart = new CategoryChartBuilder().width(1200).height(800).theme(Styler.ChartTheme.GGPlot2)
				.title(report.getTitle()).build();

		barChart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);

		if (rows.size() != 0) {
			barChart.addSeries("projekty", Arrays.asList(projectNames), Arrays.asList(projectHours));
			JFrame frame = new SwingWrapper(barChart).displayChart();
			javax.swing.SwingUtilities
					.invokeLater(() -> frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));
		} else {
			System.out.println("Wprowadziłeś niepoprawne dane");
		}

		return barChart;
	}

}
