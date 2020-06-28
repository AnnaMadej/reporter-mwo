package Report;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Model.Employee;
import services.PossibleDataRetriever;

public abstract class ReportBuilder {
	protected List<String> inputParamsNames = new ArrayList<String>();
	protected List<String> inputParams = new ArrayList<String>();
	protected List<Set<String>> possibleInputParams = new ArrayList<Set<String>>();
	protected PossibleDataRetriever possibleDataRetriever;
	protected List<Employee> employees;
	



	public List<String> getInputParamsNames() {
		return inputParamsNames;
	}



	public void setInputParamsNames(List<String> inputParamsNames) {
		this.inputParamsNames = inputParamsNames;
	}



	public List<Set<String>> getPossibleInputParams() {
		return possibleInputParams;
	}



	public void setPossibleInputParams(List<Set<String>> possibleInputParams) {
		this.possibleInputParams = possibleInputParams;
	}



	public abstract Report buildReport();
	
	public void addInputParam(String inputParam) {
		this.inputParams.add(inputParam);
	}
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
		retrievePossibleInputData();
	}
	
	public abstract void retrievePossibleInputData();
}
