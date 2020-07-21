package services.reportbuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import model.Employee;
import model.Report;
import services.chartmakers.ReportChartMaker;
import services.employeefilters.EmployeesFilter;
import services.possibledataretrievers.PossibleDataRetriever;

public abstract class ReportBuilder {

    protected List<Employee> employees = new ArrayList<Employee>();
    protected Report report = new Report();
    protected PossibleDataRetriever possibleDataRetriever;
    protected ReportChartMaker reportChartMaker = null;

    protected List<EmployeesFilter> filters = new ArrayList<EmployeesFilter>();

    protected void addEmployeesFilter(EmployeesFilter employeesFilter) {
        this.filters.add(employeesFilter);
    }

    public Report buildReport() {
        this.setReportTitle();
        this.setReportCollumnNames();
        this.setReportRows();
        return this.report;
    }

    public void filterEmployees() {
        for (EmployeesFilter filter : this.filters) {
            this.employees = filter.filterEmployees(this.employees);
        }
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public String getFilterParamName(int filterIndex) {
        EmployeesFilter filter = this.filters.get(filterIndex);
        return filter.getFilterParameterName();
    }

    protected List<EmployeesFilter> getFilters() {
        return this.filters;
    }

    public int getNumberOfFilters() {
        return this.filters.size();
    }

    public Set<String> getPossibleFilterData(int filterIndex) {
        EmployeesFilter filter = this.filters.get(filterIndex);
        return filter.getPossibleData(this.employees);
    }

    public void makeChart() {
        if (this.reportChartMaker != null && this.report.getRows().size() > 0) {
            this.reportChartMaker.makeChart(this.report);
        }
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setFilterParameter(String filterParameter, int filterIndex) {
        EmployeesFilter filter = this.filters.get(filterIndex);
        filter.setFilterParameter(filterParameter);
    }

    public void setInputParam(int filterIndex, String filterParameter) {
        this.filters.get(filterIndex).setFilterParameter(filterParameter);
        this.usefilter(filterIndex);

    }

    protected abstract void setReportCollumnNames();

    protected abstract void setReportRows();

    protected abstract void setReportTitle();

    private void usefilter(int filterIndex) {
        EmployeesFilter filter = this.filters.get(filterIndex);
        this.employees = filter.filterEmployees(this.employees);
    }
    
    protected void removeFilters() {
        this.filters = new ArrayList<EmployeesFilter>();
    }

    protected void setReportChartMaker(ReportChartMaker reportChartMaker) {
        this.reportChartMaker = reportChartMaker;
    }

    public void setReport(Report report) {
        this.report = report;
    }
    
    
    
}
