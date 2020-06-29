package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Employee;
import Model.Report;
import Services.PossibleDataRetriever;



public abstract class ReportBuilder {

	protected List<String> inputParams = new ArrayList<String>();
	protected List<Employee> employees = new ArrayList<Employee>();
	protected Report report = new Report();
	protected List<String> inputParamsNames = new ArrayList<String>();
	protected List<Set<String>> possibleInputParams = new ArrayList<Set<String>>();
	protected PossibleDataRetriever possibleDataRetriever;
	
	

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

	public void addInputParam(String... params) {
		for (String param : params) {
			this.inputParams.add(param);
		}
	}
	
	public List<String> getInputParamsNames() {
		return inputParamsNames;
	}
	
	public List<Set<String>> getPossibleInputParams() {
		return possibleInputParams;
	}
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
		retrievePossibleInputData();
	}
	
	public abstract void retrievePossibleInputData();
}
