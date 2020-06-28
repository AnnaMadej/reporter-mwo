package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Employee;
import Model.Report;
import Services.PossibleDataRetriever;

public abstract class ReportBuilder {
	protected List<String> inputParamsNames = new ArrayList<String>();
	protected List<String> inputParams = new ArrayList<String>();
	protected List<Set<String>> possibleInputParams = new ArrayList<Set<String>>();
	protected PossibleDataRetriever possibleDataRetriever;
	protected List<Employee> employees;

	public void addInputParam(String inputParam) {
		this.inputParams.add(inputParam);
	}

	public abstract Report buildReport();

	public List<String> getInputParamsNames() {
		return inputParamsNames;
	}

	public List<Set<String>> getPossibleInputParams() {
		return possibleInputParams;
	}

	public abstract void retrievePossibleInputData();

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
		retrievePossibleInputData();
	}

	public void setInputParamsNames(List<String> inputParamsNames) {
		this.inputParamsNames = inputParamsNames;
	}

	public void setPossibleInputParams(List<Set<String>> possibleInputParams) {
		this.possibleInputParams = possibleInputParams;
	}
}
