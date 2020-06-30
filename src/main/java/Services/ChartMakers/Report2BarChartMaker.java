package Services.ChartMakers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import Model.Report;

public class Report2BarChartMaker extends ReportChartMaker {

	private ArrayList<String> projects = new ArrayList<String>();
	private ArrayList<Double> hours = new ArrayList<Double>();

	@Override
	public void makeChart(Report sourceReport) {

		this.report = sourceReport;

		for (List<String> row : this.report.getRows()) {
			this.hours.add(Double.parseDouble(row.get(2)));
		}

		for (List<String> row : this.report.getRows()) {
			this.projects.add(row.get(1));
		}

		CategoryChart chart = new CategoryChartBuilder().width(1000).height(500).title(this.report.getTitle())
				.xAxisTitle(this.report.getColumnNames().get(1)).yAxisTitle(this.report.getColumnNames().get(2))
				.build();

		chart.getStyler().setHasAnnotations(true);

		chart.addSeries("Czasochłonność", this.projects, this.hours);

		JFrame frame = new SwingWrapper<CategoryChart>(chart).displayChart();
		javax.swing.SwingUtilities.invokeLater(() -> frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));

	}

}