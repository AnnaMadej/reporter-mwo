package controller;

import static org.junit.Assert.*;

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
import org.mockito.Mock;
import org.mockito.Mockito;

import model.Employee;
import model.Report;
import services.ReportStringer;
import services.XlsReportExporter;
import services.errors.ReadErrorsHolder;
import services.readers.FilesReader;
import services.readers.XlsFilesReader;
import services.reportbuilders.Report1Builder;
import services.reportbuilders.ReportBuilder;
import services.reportbuilders.ReportBuilderFactory;

public class ControllerTests {
    
    private ReportBuilder reportBuilder = Mockito.mock(ReportBuilder.class);
    private XlsReportExporter exporter = Mockito.mock(XlsReportExporter.class);
    
    private Controller controller = new Controller();

    @Before
    public final void init() {
        controller.setReportBuilder(reportBuilder);
    }
    
    @Test
    public final void testAddsParameterToReportBuilderFilter() {
        String filterParameter = "2012";
        int filterIndex = 0;
        Mockito.doNothing().when(reportBuilder).setFilterParameter(filterParameter, filterIndex);
        controller.addFilterParam(filterIndex, filterParameter);
        Mockito.verify(reportBuilder, Mockito.times(1)).setFilterParameter(filterParameter, filterIndex);
    }
    
    @Test
    public final void testsCallsReportBuilderToBuildReport() {
        Mockito.when(reportBuilder.buildReport()).thenReturn(new Report());
        controller.buildReport();
        Mockito.verify(reportBuilder, Mockito.times(1)).buildReport();
    }
    
    @Test
    public final void testsCallsExporterToExportReport() throws IOException {
        Report report = new Report();
        File file = new File("aaa");
        controller.setReportExporter(exporter);
        controller.setReport(report);
        Mockito.when(exporter.exportToXls(report)).thenReturn(file);
        String reportPath = controller.exportReport();
        Mockito.verify(exporter, Mockito.times(1)).exportToXls(report);
        Assert.assertEquals(file.getCanonicalPath(), reportPath);
    }
    
    @Test
    public final void textCallsReportBuilderToGetParamNameOfFilter() {
       Mockito.when(reportBuilder.getFilterParamName(0)).thenReturn("rok");
       String paramName = controller.getInputParamName(0);
       Mockito.verify(reportBuilder, Mockito.times(1)).getFilterParamName(0);
       Assert.assertEquals("rok", paramName);
    }
    
    @Test
    public final void testCallsReportBuilderToGetNumberOfFilters() {
        Mockito.when(reportBuilder.getNumberOfFilters()).thenReturn(2);
        int numberOfFilters = controller.getNumberOfFilters();
        Mockito.verify(reportBuilder, Mockito.times(1)).getNumberOfFilters();
        Assert.assertEquals(2, numberOfFilters);
    }
    
    @Test
    public final void testCallsReportBuilderToGetPossibleFilterData() {
        Set<String> possibleFilterData = new TreeSet<String>();
        Set<String> possibleFilterData2 = new TreeSet<String>();
        Mockito.when(reportBuilder.getPossibleFilterData(0)).thenReturn(possibleFilterData);
        Mockito.when(reportBuilder.getPossibleFilterData(1)).thenReturn(possibleFilterData2);
        Set<String> pfd0 = controller.getPossibleFilterData(0);
        Set<String> pfd1 = controller.getPossibleFilterData(1);
        Mockito.verify(reportBuilder, Mockito.times(1)).getPossibleFilterData(0);
        Mockito.verify(reportBuilder, Mockito.times(1)).getPossibleFilterData(1);
        Assert.assertEquals(possibleFilterData, pfd0);
        Assert.assertEquals(possibleFilterData2, pfd1);
    }
    
    @Test
    public final void tetOpensReport() throws IOException {
        File file = Mockito.mock(File.class);
        Desktop desktop = Mockito.mock(Desktop.class);
        controller.setDesktop(desktop);
        controller.setReportFile(file);
        Mockito.doNothing().when(desktop).open(file);
        Mockito.doReturn(true).when(file).exists();
        controller.openReport();
        Mockito.verify(desktop, Mockito.times(1)).open(file);
        
    }
    
    @Test
    public final void tetDoesNotOpenReport() throws IOException {
        File file = Mockito.mock(File.class);
        Desktop desktop = Mockito.mock(Desktop.class);
        controller.setDesktop(desktop);
        controller.setReportFile(file);
        Mockito.doNothing().when(desktop).open(file);
        Mockito.doReturn(false).when(file).exists();
        controller.openReport();
        Mockito.verify(desktop, Mockito.times(0)).open(file);      
    }

    @Test
    public final void testAsksFilesReaderToReadEmployeesData() throws InvalidFormatException, IOException {
        List<Employee> employees = new ArrayList<Employee>();
        String path = "path";
        FilesReader fr = Mockito.mock(FilesReader.class);
        Mockito.doReturn(employees).when(fr).readFiles(path);
        controller.setFilesReader(fr);
        controller.readEmployeesData(path);
        List<Employee> returnedEmployees = controller.getEmployees();
        Mockito.verify(fr, Mockito.times(1)).readFiles(path);
        
        Assert.assertEquals(employees, returnedEmployees);
        
    }
    
    @Test
    public final void setsProperReportBuilderAndSetsItsEmployees() {
        ReportBuilder builder = Mockito.mock(ReportBuilder.class);
        ReportBuilderFactory rbf = Mockito.mock(ReportBuilderFactory.class);
        Mockito.doReturn(builder).when(rbf).getReportBuilder(Mockito.anyString());
        controller.setRbFactory(rbf);
        
        controller.setReportType("1");
        
        Mockito.verify(rbf, Mockito.times(1)).getReportBuilder("1");
        Mockito.verify(builder, Mockito.times(1)).setEmployees(controller.getEmployees());
        
        Assert.assertEquals(builder, controller.getReportBuilder());  
    }
    
    @Test
    public final void asksReportBuilderForChart() {
        controller.showChart();
        Mockito.verify(reportBuilder, Mockito.times(1)).makeChart();
    }
    
    @Test
    public final void asksReportStringerForReportString() {
        Report report = new Report();
        ReportStringer reportStringer = Mockito.mock(ReportStringer.class);
        Mockito.doReturn("report string").when(reportStringer).stringReport(report);
        controller.setReportStringer(reportStringer);
        controller.setReport(report);
        String reportString = controller.stringReport();
        Mockito.verify(reportStringer, Mockito.times(1)).stringReport(report);
        
        Assert.assertEquals("report string", reportString);
        
    }
    
    @Test
    public final void asksReadErrorsHolderForScanErrorsReport() {
        Report report = new Report();
        ReadErrorsHolder readErrorsHolder = Mockito.mock(ReadErrorsHolder.class);
        Mockito.doReturn(report).when(readErrorsHolder).getErrorsReport();
        controller.setReadErrorsHolder(readErrorsHolder);
        controller.createScanErrorsReport();
        Mockito.verify(readErrorsHolder, Mockito.times(1)).getErrorsReport();
        
        Assert.assertEquals(report, controller.getReport());
        
        
    }

}
