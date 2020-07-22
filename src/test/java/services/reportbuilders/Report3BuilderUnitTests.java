package services.reportbuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import model.Employee;
import model.Report;
import model.Task;
import services.employeefilters.EmployeesFilter;
import services.employeefilters.EmployeesFilterByPerson;
import services.employeefilters.EmployeesFilterByYear;

public class Report3BuilderUnitTests {

    private ReportBuilder reportBuilder = new Report3Builder();
    Report report = reportBuilder.getReport();
    List<Employee> employees = new ArrayList<Employee>();

    @Before
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012,1,1);
        Employee employee1 = new Employee("Jan", "Nowak");
        Task task1 = new Task(calendar.getTime(), "projekt1", "opis", 4);
        employee1.addTask(task1);

        calendar.set(2012,2,1);
        Employee employee2 = new Employee("Paweł", "Kowalski");
        Task task2 = new Task(calendar.getTime(), "projekt2", "opis", 2);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);

        reportBuilder.setEmployees(employees);

    }

    @Test
    public final void testHasProperEmployeeFilter() {
        List<EmployeesFilter> filters = reportBuilder.getFilters();
        Assert.assertEquals(2, filters.size());

        EmployeesFilter filter1 = filters.get(0);
        Assert.assertTrue(filter1 instanceof EmployeesFilterByYear);
        
        EmployeesFilter filter2 = filters.get(1);
        Assert.assertTrue(filter2 instanceof EmployeesFilterByPerson);
    }

    @Test
    public final void setsProperColumnNames() {
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("L.p");
        columnNames.add("Miesiąc");
        columnNames.add("Projekt");
        columnNames.add("Liczba godzin");

        reportBuilder.setReportCollumnNames();
        List<String> reportColumnNames = report.getColumnNames();
        Assert.assertTrue(reportColumnNames.containsAll(columnNames));
    }

    @Test
    public final void setsProperRows() {
        reportBuilder.setReportRows();
        List<List<String>> rows = new ArrayList<List<String>>();

        List<String> row1 = new ArrayList<String>();
        row1.add("1");
        row1.add("Luty");
        row1.add("projekt1");
        row1.add("4.0");

        List<String> row2 = new ArrayList<String>();
        row2.add("2");
        row2.add("Marzec");
        row2.add("projekt2");
        row2.add("2.0");
        
        rows.add(row1);
        rows.add(row2);

        List<List<String>> reportRows = report.getRows();
        Assert.assertEquals(2, report.getRows().size());
        Assert.assertTrue(rows.containsAll(reportRows));
    }

    @Test
    public final void setsProperRowsWith0Hours() {

        // this should not be added to report rows:
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012,3,1);
        Employee employee2 = new Employee("Adam", "Kot");
        Task task2 = new Task(calendar.getTime(), "projekt2", "opis", 0);
        employee2.addTask(task2);

        reportBuilder.setReportRows();
        List<List<String>> rows = new ArrayList<List<String>>();

        List<String> row1 = new ArrayList<String>();
        row1.add("1");
        row1.add("Luty");
        row1.add("projekt1");
        row1.add("4.0");

        List<String> row2 = new ArrayList<String>();
        row2.add("2");
        row2.add("Marzec");
        row2.add("projekt2");
        row2.add("2.0");
        
        rows.add(row1);
        rows.add(row2);

        List<List<String>> reportRows = report.getRows();
        Assert.assertEquals(2, report.getRows().size());
        Assert.assertTrue(rows.containsAll(reportRows));
    }

    @Test
    public void setsProperReportTitleWithoutFilter() {
        reportBuilder.removeFilters();
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie", report.getTitle());
    }

    @Test
    public void setsProperReportTitleWithOneFilterParameter() {
        reportBuilder.removeFilters();
        EmployeesFilter filter = Mockito.mock(EmployeesFilterByYear.class);
        reportBuilder.addEmployeesFilter(filter);
        Mockito.when(filter.getFilterParameter()).thenReturn("2012");
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie, w roku: 2012", report.getTitle());
    }
    
    @Test
    public void setsProperReportTitleWithTwoFilterParameters() {
        reportBuilder.removeFilters();
        EmployeesFilter filter1 = Mockito.mock(EmployeesFilterByYear.class);
        Mockito.when(filter1.getFilterParameter()).thenReturn("2012");
        EmployeesFilter filter2 = Mockito.mock(EmployeesFilterByPerson.class);
        reportBuilder.addEmployeesFilter(filter2);
        Mockito.when(filter2.getFilterParameter()).thenReturn("Jan Nowak");
        
        reportBuilder.addEmployeesFilter(filter1);
        reportBuilder.addEmployeesFilter(filter2);
        
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie przez: 2012, w roku: Jan Nowak", report.getTitle());
    }

    @Test
    public void setsProperReportTitleWithoutFilterParameters() {
        reportBuilder.removeFilters();
        EmployeesFilter filter = Mockito.mock(EmployeesFilterByYear.class);
        reportBuilder.addEmployeesFilter(filter);
        Mockito.when(filter.getFilterParameter()).thenReturn(null);
        reportBuilder.addEmployeesFilter(filter);
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie", report.getTitle());
    }

}
