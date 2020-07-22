package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import model.Employee;
import model.Report;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import repository.FilesFinder;
import services.ReportStringer;
import services.XlsReportExporter;
import services.errors.ReadErrorsHolder;
import services.readers.FilesReader;
import services.readers.XlsFilesReader;
import services.reportbuilders.ReportBuilder;
import services.reportbuilders.ReportBuilderFactory;

public class Controller {
    private ReadErrorsHolder readErrorsHolder = new ReadErrorsHolder();
    private FilesReader filesReader = new XlsFilesReader(readErrorsHolder);
    private ReportBuilder reportBuilder;
    
    private List<Employee> employees = new ArrayList<Employee>();

    private Report report;
    private String reportFilePath;

    public void addFilterParam(int filterIndex, String filterParameter) {
        this.reportBuilder.setFilterParameter(filterParameter, filterIndex);
    }

    public void buildReport() {
        this.report = this.reportBuilder.buildReport();
    }

    public String exportReport() throws IOException {
        File file = XlsReportExporter.exportToXls(this.report);
        this.reportFilePath = file.getCanonicalPath();
        return this.reportFilePath;
    }

    public String getInputParamName(int filterIndex) {
        return this.reportBuilder.getFilterParamName(filterIndex);
    }

    public int getNumberOfEmployees() {
        return this.employees.size();
    }

    public int getNumberOfFilters() {
        return this.reportBuilder.getNumberOfFilters();
    }

    public Set<String> getPossibleFilterData(int filterIndex) {
        return this.reportBuilder.getPossibleFilterData(filterIndex);
    }

    public void openReport() throws IOException {
        File file = new File(this.reportFilePath);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    public void readEmployeesData(String path) throws InvalidFormatException, IOException {
        this.employees = filesReader.readFiles(path);
    }

    public void setReportType(String userOption) {
        this.reportBuilder = ReportBuilderFactory.getReportBuilder(userOption);
        this.reportBuilder.setEmployees(this.employees);
    }

    public void showChart() {
        this.reportBuilder.makeChart();
    }

    public String stringReport() {
        return ReportStringer.stringReport(this.report);
    }

    public void createScanErrorsReport() {
        report = readErrorsHolder.getErrorsReport();
    }

}
