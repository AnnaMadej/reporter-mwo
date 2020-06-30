package Services.ChartMakers;

import Model.Report;

public abstract class ReportChartMaker {

	protected Report report;

	public abstract void makeChart(Report sourceReport);

}