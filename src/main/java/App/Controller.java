package App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Model.Employee;
import Reader.FilesScanner;
import Report.Report;
import Report.ReportBuilder;
import Report.ReportBuilderFactory;

public class Controller {
	List<Employee> employees = new ArrayList<Employee>();
	ReportBuilder reportBuilder;

	public void addReportInputParam(String inputParam) {
		reportBuilder.addInputParam(inputParam);
	}

	public List<String> getInputParamNames() {
		return reportBuilder.getInputParamsNames();
	}

	public List<Set<String>> getPossibleInputParams() {
		return reportBuilder.getPossibleInputParams();
	}

	public Report getReport() {
		return reportBuilder.buildReport();
	}

	public void setEmployees(String path) {
		try {
			FilesScanner fileScanner = new FilesScanner();
			employees = fileScanner.scanFiles(path);
		} catch (IOException e) {
			System.err.println("Nie znaleziono pliku");
		} catch (InvalidFormatException e) {
			System.err.println("Bład odczytu pliku");
		}
	}

	public void setReportType(String userOption) {
		this.reportBuilder = ReportBuilderFactory.getReportBuilder(userOption);
		reportBuilder.setEmployees(employees);
	}
}
