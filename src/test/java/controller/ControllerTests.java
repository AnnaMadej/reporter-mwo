package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Employee;
import model.Report;
import services.ReportStringer;
import services.XlsReportExporter;
import services.errors.ReadErrorsHolder;
import services.readers.FilesReader;
import services.reportbuilders.ReportBuilder;
import services.reportbuilders.ReportBuilderFactory;

public class ControllerTests {

    private final ReportBuilder reportBuilder = Mockito.mock(ReportBuilder.class);
    private final XlsReportExporter exporter = Mockito.mock(XlsReportExporter.class);

    private final Controller controller = new Controller();

    @Test
    public final void asksReadErrorsHolderForScanErrorsReport() {
        final Report report = new Report();
        final ReadErrorsHolder readErrorsHolder = Mockito.mock(ReadErrorsHolder.class);
        Mockito.doReturn(report).when(readErrorsHolder).getErrorsReport();
        this.controller.setReadErrorsHolder(readErrorsHolder);
        this.controller.createScanErrorsReport();
        Mockito.verify(readErrorsHolder, Mockito.times(1)).getErrorsReport();

        Assert.assertEquals(report, this.controller.getReport());

    }

    @Test
    public final void asksReportBuilderForChart() {
        this.controller.showChart();
        Mockito.verify(this.reportBuilder, Mockito.times(1)).makeChart();
    }

    @Test
    public final void asksReportStringerForReportString() {
        final Report report = new Report();
        final ReportStringer reportStringer = Mockito.mock(ReportStringer.class);
        Mockito.doReturn("report string").when(reportStringer).stringReport(report);
        this.controller.setReportStringer(reportStringer);
        this.controller.setReport(report);
        final String reportString = this.controller.stringReport();
        Mockito.verify(reportStringer, Mockito.times(1)).stringReport(report);

        Assert.assertEquals("report string", reportString);

    }

    @Before
    public final void init() {
        this.controller.setReportBuilder(this.reportBuilder);
    }

    @Test
    public final void setsProperReportBuilderAndSetsItsEmployees() {
        final ReportBuilder builder = Mockito.mock(ReportBuilder.class);
        final ReportBuilderFactory rbf = Mockito.mock(ReportBuilderFactory.class);
        Mockito.doReturn(builder).when(rbf).getReportBuilder(Mockito.anyString());
        this.controller.setRbFactory(rbf);

        this.controller.setReportType("1");

        Mockito.verify(rbf, Mockito.times(1)).getReportBuilder("1");
        Mockito.verify(builder, Mockito.times(1))
                        .setEmployees(this.controller.getEmployees());

        Assert.assertEquals(builder, this.controller.getReportBuilder());
    }

    @Test
    public final void testAddsParameterToReportBuilderFilter() {
        final String filterParameter = "2012";
        final int filterIndex = 0;
        Mockito.doNothing().when(this.reportBuilder).setFilterParameter(filterParameter,
                        filterIndex);
        this.controller.addFilterParam(filterIndex, filterParameter);
        Mockito.verify(this.reportBuilder, Mockito.times(1))
                        .setFilterParameter(filterParameter, filterIndex);
    }

    @Test
    public final void testAsksFilesReaderToReadEmployeesData()
                    throws InvalidFormatException, IOException {
        final List<Employee> employees = new ArrayList<Employee>();
        final String path = "path";
        final FilesReader fr = Mockito.mock(FilesReader.class);
        Mockito.doReturn(employees).when(fr).readFiles(path);
        this.controller.setFilesReader(fr);
        this.controller.readEmployeesData(path);
        final List<Employee> returnedEmployees = this.controller.getEmployees();
        Mockito.verify(fr, Mockito.times(1)).readFiles(path);

        Assert.assertEquals(employees, returnedEmployees);

    }

    @Test
    public final void testCallsReportBuilderToGetNumberOfFilters() {
        Mockito.when(this.reportBuilder.getNumberOfFilters()).thenReturn(2);
        final int numberOfFilters = this.controller.getNumberOfFilters();
        Mockito.verify(this.reportBuilder, Mockito.times(1)).getNumberOfFilters();
        Assert.assertEquals(2, numberOfFilters);
    }

    @Test
    public final void testCallsReportBuilderToGetPossibleFilterData() {
        final Set<String> possibleFilterData = new TreeSet<String>();
        final Set<String> possibleFilterData2 = new TreeSet<String>();
        Mockito.when(this.reportBuilder.getPossibleFilterData(0))
                        .thenReturn(possibleFilterData);
        Mockito.when(this.reportBuilder.getPossibleFilterData(1))
                        .thenReturn(possibleFilterData2);
        final Set<String> pfd0 = this.controller.getPossibleFilterData(0);
        final Set<String> pfd1 = this.controller.getPossibleFilterData(1);
        Mockito.verify(this.reportBuilder, Mockito.times(1)).getPossibleFilterData(0);
        Mockito.verify(this.reportBuilder, Mockito.times(1)).getPossibleFilterData(1);
        Assert.assertEquals(possibleFilterData, pfd0);
        Assert.assertEquals(possibleFilterData2, pfd1);
    }

    @Test
    public final void testsCallsExporterToExportReport() throws IOException {
        final Report report = new Report();
        final File file = new File("aaa");
        this.controller.setReportExporter(this.exporter);
        this.controller.setReport(report);
        Mockito.when(this.exporter.exportToXls(report)).thenReturn(file);
        final String reportPath = this.controller.exportReport();
        Mockito.verify(this.exporter, Mockito.times(1)).exportToXls(report);
        Assert.assertEquals(file.getCanonicalPath(), reportPath);
    }

    @Test
    public final void testsCallsReportBuilderToBuildReport() {
        Mockito.when(this.reportBuilder.buildReport()).thenReturn(new Report());
        this.controller.buildReport();
        Mockito.verify(this.reportBuilder, Mockito.times(1)).buildReport();
    }

    @Test
    public final void tetDoesNotOpenReport() throws IOException {
        final File file = Mockito.mock(File.class);
        final Desktop desktop = Mockito.mock(Desktop.class);
        this.controller.setDesktop(desktop);
        this.controller.setReportFile(file);
        Mockito.doNothing().when(desktop).open(file);
        Mockito.doReturn(false).when(file).exists();
        this.controller.openReport();
        Mockito.verify(desktop, Mockito.times(0)).open(file);
    }

    @Test
    public final void tetOpensReport() throws IOException {
        final File file = Mockito.mock(File.class);
        final Desktop desktop = Mockito.mock(Desktop.class);
        this.controller.setDesktop(desktop);
        this.controller.setReportFile(file);
        Mockito.doNothing().when(desktop).open(file);
        Mockito.doReturn(true).when(file).exists();
        this.controller.openReport();
        Mockito.verify(desktop, Mockito.times(1)).open(file);

    }

    @Test
    public final void textCallsReportBuilderToGetParamNameOfFilter() {
        Mockito.when(this.reportBuilder.getFilterParamName(0)).thenReturn("rok");
        final String paramName = this.controller.getInputParamName(0);
        Mockito.verify(this.reportBuilder, Mockito.times(1)).getFilterParamName(0);
        Assert.assertEquals("rok", paramName);
    }

}
