package services.chartmakers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;

import model.Report;

public class Report4PieChartMaker extends ReportChartMaker {
    private final List<PieChart> charts = new ArrayList<PieChart>();

    @Override
    public void makeChart(Report sourceReport) {

        this.report = sourceReport;
        this.report.getRows();
        final int numberOfCharts = this.report.getRows().size();

        for (int employeeIndex = 0; employeeIndex < numberOfCharts; employeeIndex++) {
            final PieChart chart = makeSingleChart(employeeIndex);
            this.charts.add(chart);
        }

        final JFrame frame = new SwingWrapper<PieChart>(this.charts).displayChartMatrix();
        javax.swing.SwingUtilities.invokeLater(() -> frame
                        .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE));

    }

    private PieChart makeSingleChart(int employeeIndex) {

        final int chartWidth = 400;
        final int chartHeight = 360;
        final PieChart chart = new PieChartBuilder().width(chartWidth).height(chartHeight)
                        .title(this.getClass().getSimpleName()).build();

        chart.setTitle(this.report.getRows().get(employeeIndex).get(1));
        for (int projectIndex = 2; projectIndex < this.report.getColumnNames()
                        .size(); projectIndex++) {
            String percentValue = "";
            percentValue = this.report.getRows().get(employeeIndex).get(projectIndex);
            if (percentValue.equals("")) {
                percentValue = "0%";
            }
            percentValue = percentValue.substring(0, percentValue.length() - 1);
            final double percentValueDouble = Double.valueOf(percentValue);
            final String projectName = this.report.getColumnNames().get(projectIndex);
            chart.addSeries(projectName, percentValueDouble);
        }

        return chart;
    }

}