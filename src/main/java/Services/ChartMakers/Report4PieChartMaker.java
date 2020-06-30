package Services.ChartMakers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;

import Model.Report;

public class Report4PieChartMaker extends ReportChartMaker {
	private List<PieChart> charts = new ArrayList<PieChart>();

	@Override
	public void makeChart(Report sourceReport) {

		this.report = sourceReport;
		this.report.getRows();
		int numberOfCharts = this.report.getRows().size();

		for (int employeeIndex = 0; employeeIndex < numberOfCharts; employeeIndex++) {
			PieChart chart = this.makeSingleChart(employeeIndex);
			this.charts.add(chart);
		}

		JFrame frame = new SwingWrapper<PieChart>(this.charts).displayChartMatrix();
		javax.swing.SwingUtilities.invokeLater(() -> frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));

	}

	private PieChart makeSingleChart(int employeeIndex) {
		PieChart chart = new PieChartBuilder().width(480).height(360).title(this.getClass().getSimpleName()).build();

		chart.setTitle(this.report.getRows().get(employeeIndex).get(1));
		for (int projectIndex = 2; projectIndex < this.report.getColumnNames().size(); projectIndex++) {
			String projectName = this.report.getColumnNames().get(projectIndex);
			String percentValue = "";
			percentValue = this.report.getRows().get(employeeIndex).get(projectIndex);
			if (percentValue.equals("")) {
				percentValue = "0%";
			}
			percentValue = percentValue.substring(0, percentValue.length() - 1);
			double percentValueDouble = Double.valueOf(percentValue);
			chart.addSeries(projectName, percentValueDouble);
		}

		Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(246, 199, 182), new Color(230, 105, 62),
				new Color(236, 143, 110), new Color(243, 180, 159) };
		chart.getStyler().setSeriesColors(sliceColors);
		return chart;
	}

}