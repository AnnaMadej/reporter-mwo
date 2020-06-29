package Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Model.Employee;
import Model.Report;
import Reader.FilesScanner;
import Services.ReportPrinter;
import Services.ReportXlsExporter;
import Services.ReportBuilders.ReportBuilder;
import Services.ReportBuilders.ReportBuilderFactory;

public class Controller {
	private List<Employee> employees = new ArrayList<Employee>();
	private ReportBuilder reportBuilder;
	private Report report;
	private String reportFilePath;



	public void buildReport() {
		this.report = reportBuilder.buildReport();
	}

	public String stringReport() {
		return ReportPrinter.stringReport(report);
	}

	public String exportReport() throws IOException {
		File file = ReportXlsExporter.exportToXls(report);
		this.reportFilePath = file.getCanonicalPath();
		return reportFilePath;
	}

	public void openReport() throws IOException {
		File file = new File(reportFilePath);
		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) {
			desktop.open(file);
		}
	}

	public void readEmployeesData(String path) throws InvalidFormatException, IOException {
		FilesScanner fileScanner = new FilesScanner();
		employees = fileScanner.scanFiles(path);
	}

	public void setReportType(String userOption) {
		this.reportBuilder = ReportBuilderFactory.getReportBuilder(userOption);
		reportBuilder.setEmployees(employees);
	}

	public int getNumberOfEmployees() {
		return employees.size();
	}

	public int getNumberOfFilters() {
		return reportBuilder.getNumberOfFilters();
	}

	public String getInputParamName(int filterIndex) {
		return reportBuilder.getFilterParamName(filterIndex);
	}

	public Set<String> getPossibleFilterData(int filterIndex) {
		return reportBuilder.getPossibleFilterData(employees, filterIndex);
	}

	public void addFilterParam(int filterIndex, String filterParameter) {
		reportBuilder.addInputParam(filterIndex, filterParameter);
	}

}
