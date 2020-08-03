package services.reportbuilders;

import java.util.ArrayList;
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
import services.employeefilters.EmployeesFilterByYear;

public class Report1BuilderUnitTests {

    private ReportBuilder reportBuilder = new Report1Builder();
    Report report = reportBuilder.getReport();
    List<Employee> employees = new ArrayList<Employee>();

    @Before
    public void init() {
        Employee employee1 = new Employee("Jan", "Nowak");
        Task task1 = new Task(new Date(), "projekt1", "opis", 4);
        employee1.addTask(task1);

        Employee employee2 = new Employee("Paweł", "Kowalski");
        Task task2 = new Task(new Date(), "projekt1", "opis", 2);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);

        reportBuilder.setEmployees(employees);

    }

    @Test
    public final void testHasProperEmployeeFilter() {
        List<EmployeesFilter> filters = reportBuilder.getFilters();
        Assert.assertEquals(1, filters.size());

        EmployeesFilter filter = filters.get(0);
        Assert.assertTrue(filter instanceof EmployeesFilterByYear);
    }

    @Test
    public final void setsProperColumnNames() {
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("L.p");
        columnNames.add("Imię i nazwisko");
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
        row1.add("Jan Nowak");
        row1.add("4.0");

        List<String> row2 = new ArrayList<String>();
        row2.add("2");
        row2.add("Paweł Kowalski");
        row2.add("2.0");

        rows.add(row1);
        rows.add(row2);

        List<List<String>> reportRows = report.getRows();

        Assert.assertEquals(rows, reportRows);
    }

    @Test
    public final void setsProperRowsWith0Hours() {

        // this employee should not be added to report rows:
        Employee employee3 = new Employee("Halina", "Kwiatkowska");
        Task task3 = new Task(new Date(), "projekt1", "opis", 0);
        employee3.addTask(task3);
        employees.add(employee3);

        reportBuilder.setReportRows();
        List<List<String>> rows = new ArrayList<List<String>>();

        List<String> row1 = new ArrayList<String>();
        row1.add("1");
        row1.add("Jan Nowak");
        row1.add("4.0");

        List<String> row2 = new ArrayList<String>();
        row2.add("2");
        row2.add("Paweł Kowalski");
        row2.add("2.0");

        rows.add(row1);
        rows.add(row2);

        List<List<String>> reportRows = report.getRows();

        Assert.assertEquals(rows, reportRows);
    }

    @Test
    public void setsProperReportTitleWithoutFilter() {
        reportBuilder.removeFilters();
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin pracowników ", report.getTitle());
    }

    @Test
    public void setsProperReportTitleWithFilterParameter() {
        reportBuilder.removeFilters();
        EmployeesFilter filter = Mockito.mock(EmployeesFilterByYear.class);
        reportBuilder.addEmployeesFilter(filter);
        Mockito.when(filter.getFilterParameter()).thenReturn("2012");
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin pracowników w roku: 2012", report.getTitle());
    }

    @Test
    public void setsProperReportTitleWithoutFilterParameter() {
        reportBuilder.removeFilters();
        EmployeesFilter filter = Mockito.mock(EmployeesFilterByYear.class);
        reportBuilder.addEmployeesFilter(filter);
        Mockito.when(filter.getFilterParameter()).thenReturn(null);
        reportBuilder.addEmployeesFilter(filter);
        reportBuilder.setReportTitle();
        Assert.assertEquals("Raport godzin pracowników ", report.getTitle());
    }

}
