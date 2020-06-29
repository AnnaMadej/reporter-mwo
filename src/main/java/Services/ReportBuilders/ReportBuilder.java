package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Employee;
import Model.Report;
import Services.EmployeesFilter;
import Services.PossibleDataRetriever;



public abstract class ReportBuilder {

	protected List<Employee> employees = new ArrayList<Employee>();
	protected Report report = new Report();
	protected PossibleDataRetriever possibleDataRetriever;
	
	
	protected List<EmployeesFilter> filters = new ArrayList<EmployeesFilter>();
	

	public Report buildReport() {
		filterEmployees();
		setReportTitle();
		setReportCollumnNames();
		setReportRows();
		return report;
	};

	abstract void filterEmployees();

	abstract void setReportTitle();

	abstract void setReportCollumnNames();

	abstract void setReportRows();

	public void addInputParam(int filterIndex, String filterParameter) {
		this.filters.get(filterIndex).setFilterParameter(filterParameter);
	}
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
		
	protected void addEmployeesFilter(EmployeesFilter employeesFilter) {
		this.filters.add(employeesFilter);
	}
	
	public int getNumberOfFilters() {
		return filters.size();
	}
	
	public String getFilterParamName(int filterIndex) {
		return filters.get(filterIndex).getFilterParameterName();
	}
	
	public Set<String> getPossibleFilterData(List<Employee> employees, int filterIndex){
		return filters.get(filterIndex).getPossibleData(employees);	
	}
	
	public void setFilterParameter(String filterParameter, int filterIndex) {
		filters.get(filterIndex).setFilterParameter(filterParameter);
	}
}
