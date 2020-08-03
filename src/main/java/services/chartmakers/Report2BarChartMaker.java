package services.chartmakers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import model.Report;

public class Report2BarChartMaker extends ReportChartMaker {

    private final ArrayList<String> projects = new ArrayList<String>();
    private final ArrayList<Double> hours = new ArrayList<Double>();

    @Override
    public void makeChart(Report sourceReport) {

        this.report = sourceReport;

        for (final List<String> row : this.report.getRows()) {
            this.hours.add(Double.parseDouble(row.get(2)));
        }

        for (final List<String> row : this.report.getRows()) {
            this.projects.add(row.get(1));
        }

        final int chartWidth = 1000;
        final int chartHeight = 500;
        final CategoryChart chart = new CategoryChartBuilder().width(chartWidth)
                        .height(chartHeight).title(this.report.getTitle())
                        .xAxisTitle(this.report.getColumnNames().get(1))
                        .yAxisTitle(this.report.getColumnNames().get(2)).build();

        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("Czasochłonność", this.projects, this.hours);

        final JFrame frame = new SwingWrapper<CategoryChart>(chart).displayChart();
        javax.swing.SwingUtilities.invokeLater(() -> frame
                        .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));

    }

}