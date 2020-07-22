package services.reportbuilders;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Employee;
import model.Report;
import services.chartmakers.Report2BarChartMaker;
import services.chartmakers.ReportChartMaker;
import services.employeefilters.EmployeesFilter;
import services.reportbuilders.Report1Builder;
import services.reportbuilders.ReportBuilder;

public class ReportBuilderTests {
    
    List<Employee> employees = new ArrayList<Employee>();
    ReportBuilder builder = new Report1Builder();
    EmployeesFilter filter1 = Mockito.mock(EmployeesFilter.class);
    EmployeesFilter filter2 = Mockito.mock(EmployeesFilter.class);
    ReportChartMaker chartMaker = Mockito.mock(Report2BarChartMaker.class);

    @Before
    public final void init() {
        Mockito.when(filter1.filterEmployees(employees)).thenReturn(employees);
        Mockito.when(filter2.filterEmployees(employees)).thenReturn(employees);
        Mockito.when(filter1.getFilterParameter()).thenReturn("parameter");
        Mockito.when(filter2.getFilterParameter()).thenReturn("parameter");
        
        Mockito.when(filter1.getFilterParameterName()).thenReturn("parameterName");
        Mockito.when(filter2.getFilterParameterName()).thenReturn("parameterName");
        
        Mockito.when(filter1.getPossibleData(employees)).thenReturn(new TreeSet<String>());
        Mockito.when(filter2.getPossibleData(employees)).thenReturn(new TreeSet<String>());
        
        Mockito.doNothing().when(filter1).setFilterParameter(Mockito.any(String.class));
        Mockito.doNothing().when(filter2).setFilterParameter(Mockito.any(String.class));
        
        Mockito.doNothing().when(chartMaker).makeChart(Mockito.any(Report.class));
      
        builder.setReportChartMaker(chartMaker);
        builder.removeFilters();
        builder.addEmployeesFilter(filter1);
        builder.addEmployeesFilter(filter2);
        builder.setEmployees(employees);
        builder.filterEmployees();
        
    }
    @Test
    public final void testUsesFilters() {
       Mockito.verify(filter1, Mockito.times(1)).filterEmployees(employees);
       Mockito.verify(filter2, Mockito.times(1)).filterEmployees(employees);
    }
    
    @Test
    public final void testReturnsEmployees() {
        List<Employee> employeesFromBuilder = builder.getEmployees();
        Assert.assertEquals(employeesFromBuilder, this.employees);
    }
    
    @Test
    public final void testUsesFiltersToGetFilterParamName() {
        builder.getFilterParamName(0);
        builder.getFilterParamName(1);
        
        Mockito.verify(filter1, Mockito.times(1)).getFilterParameterName();
        Mockito.verify(filter2, Mockito.times(1)).getFilterParameterName();
    }

    @Test
    public final void testReturnsFiltersAndNumberOfFilters() {
        List<EmployeesFilter> filters = builder.getFilters();
        int numberOfFilters = builder.getNumberOfFilters();
        
        Assert.assertTrue(filters.contains(filter1));
        Assert.assertTrue(filters.contains(filter2));
        
        Assert.assertEquals(2, filters.size());
        
        Assert.assertEquals(2, numberOfFilters);
    }
    
    @Test 
    public final void testAsksFiltersForPossibleData() {
        builder.getPossibleFilterData(0);
        builder.getPossibleFilterData(1);
        
        Mockito.verify(filter1, Mockito.times(1)).getPossibleData(employees);
        Mockito.verify(filter2, Mockito.times(1)).getPossibleData(employees);
    }
    
    @Test 
    public final void testDoesntAskChartMakerToMakeChartIfZeroRows() {
        builder.makeChart();     
        Mockito.verify(chartMaker, Mockito.times(0)).makeChart(Mockito.any(Report.class));
    }
    
    @Test 
    public final void testDoesntAskChartMakerToMakeChartIfNoChartMaker() {
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> row = new ArrayList<String>();
        rows.add(row);
        
        Report report = Mockito.mock(Report.class);
        Mockito.when(report.getRows()).thenReturn(rows);
        builder.setReport(report); 
        builder.setReportChartMaker(null);
        builder.makeChart();    
        Mockito.verify(chartMaker, Mockito.times(0)).makeChart(Mockito.any(Report.class));
    }
    
    @Test 
    public final void testDoesntAskChartMakerToMakeChartIfNoChartMakerAndZeroRows() {   
        builder.setReportChartMaker(null);
        builder.makeChart();  
        Mockito.verify(chartMaker, Mockito.times(0)).makeChart(Mockito.any(Report.class));
    }
    
    @Test 
    public final void testAsksChartMakerToMakeChart() {
        
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> row = new ArrayList<String>();
        rows.add(row);
        
        Report report = Mockito.mock(Report.class);
        Mockito.when(report.getRows()).thenReturn(rows);
        builder.setReport(report);
        builder.makeChart();     
        Mockito.verify(chartMaker, Mockito.times(1)).makeChart(Mockito.any(Report.class));
    }
    
    @Test
    public final void testAsksFiltersToSetParams() {
        builder.setFilterParameter("param1", 0);
        builder.setFilterParameter("param2", 1);
        
        Mockito.verify(filter1, Mockito.times(1)).setFilterParameter("param1");
        Mockito.verify(filter2, Mockito.times(1)).setFilterParameter("param2");
    }
    
    @Test
    public final void callsMethodsToBuildReport() {
        ReportBuilder reportBuilder = Mockito.spy(Report1Builder.class);
        Mockito.doNothing().when(reportBuilder).setReportTitle();
        Mockito.doNothing().when(reportBuilder).setReportCollumnNames();
        Mockito.doNothing().when(reportBuilder).setReportRows();
        
        reportBuilder.buildReport();
        Mockito.verify(reportBuilder, Mockito.times(1)).setReportTitle();
        Mockito.verify(reportBuilder, Mockito.times(1)).setReportCollumnNames();
        Mockito.verify(reportBuilder, Mockito.times(1)).setReportRows();
    }
    
  

}
