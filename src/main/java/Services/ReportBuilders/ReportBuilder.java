package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Employee;
import Model.Report;
import Services.EmployeeFilters.EmployeesFilter;
import Services.PossibleDataRetrievers.PossibleDataRetriever;

public abstract class ReportBuilder {

	protected List<Employee> employees = new ArrayList<Employee>();
	protected Report report = new Report();
	protected PossibleDataRetriever possibleDataRetriever;

	protected List<EmployeesFilter> filters = new ArrayList<EmployeesFilter>();

	protected void addEmployeesFilter(EmployeesFilter employeesFilter) {
		this.filters.add(employeesFilter);
	}

	public void addInputParam(int filterIndex, String filterParameter) {
		this.filters.get(filterIndex).setFilterParameter(filterParameter);
		this.usefilter(filterIndex);

	}

	public Report buildReport() {
		// filterEmployees();
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
		return this.filters.get(filterIndex).getFilterParameterName();
	}

	public int getNumberOfFilters() {
		return this.filters.size();
	}

	public Set<String> getPossibleFilterData(int filterIndex) {
		return this.filters.get(filterIndex).getPossibleData(this.employees);
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void setFilterParameter(String filterParameter, int filterIndex) {
		this.filters.get(filterIndex).setFilterParameter(filterParameter);
	}

	abstract void setReportCollumnNames();

	abstract void setReportRows();

	abstract void setReportTitle();

	private void usefilter(int filterIndex) {
		this.employees = this.filters.get(filterIndex).filterEmployees(this.employees);
	}
}
