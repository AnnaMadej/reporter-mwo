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
    private ReportBuilderFactory rbFactory = new ReportBuilderFactory();
    private XlsReportExporter reportExporter = new XlsReportExporter();
    private ReadErrorsHolder readErrorsHolder = new ReadErrorsHolder();
    private FilesReader filesReader = new XlsFilesReader(readErrorsHolder);
    private ReportBuilder reportBuilder;
    private Desktop desktop = Desktop.getDesktop();
    private ReportStringer reportStringer = new ReportStringer();

    private List<Employee> employees = new ArrayList<Employee>();

    private Report report;
    private File reportFile;

    public void addFilterParam(int filterIndex, String filterParameter) {
        this.reportBuilder.setFilterParameter(filterParameter, filterIndex);
    }

    public void buildReport() {
        this.report = this.reportBuilder.buildReport();
    }

    public String exportReport() throws IOException {
        File file = reportExporter.exportToXls(this.report);
        this.reportFile = file;
        return file.getCanonicalPath();
    }

    public String getInputParamName(int filterIndex) {
        return this.reportBuilder.getFilterParamName(filterIndex);
    }

    public int getNumberOfFilters() {
        return this.reportBuilder.getNumberOfFilters();
    }

    public Set<String> getPossibleFilterData(int filterIndex) {
        return this.reportBuilder.getPossibleFilterData(filterIndex);
    }

    public void openReport() throws IOException {
        if (reportFile.exists()) {
            desktop.open(reportFile);
        }
    }

    public void readEmployeesData(String path) throws InvalidFormatException, IOException {
        this.employees = filesReader.readFiles(path);
    }

    public void setReportType(String userOption) {
        this.setReportBuilder(rbFactory.getReportBuilder(userOption));
        this.reportBuilder.setEmployees(this.employees);
    }

    public void showChart() {
        this.reportBuilder.makeChart();
    }

    public String stringReport() {
        return reportStringer.stringReport(this.report);
    }

    public void createScanErrorsReport() {
        report = readErrorsHolder.getErrorsReport();
    }

    public void setReportBuilder(ReportBuilder reportBuilder) {
        this.reportBuilder = reportBuilder;
    }

    public void setReportExporter(XlsReportExporter reportExporter) {
        this.reportExporter = reportExporter;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }

    public void setReportFile(File reportFile) {
        this.reportFile = reportFile;
    }

    public void setFilesReader(FilesReader filesReader) {
        this.filesReader = filesReader;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setRbFactory(ReportBuilderFactory rbFactory) {
        this.rbFactory = rbFactory;
    }

    public ReportBuilder getReportBuilder() {
        return reportBuilder;
    }

    public void setReportStringer(ReportStringer reportStringer) {
        this.reportStringer = reportStringer;
    }

    public void setReadErrorsHolder(ReadErrorsHolder readErrorsHolder) {
        this.readErrorsHolder = readErrorsHolder;
    }

    public Report getReport() {
        return report;
    }

    
}
