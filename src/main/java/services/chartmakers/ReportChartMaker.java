package services.chartmakers;

import model.Report;

public abstract class ReportChartMaker {

    protected Report report;

    public abstract void makeChart(Report sourceReport);

}