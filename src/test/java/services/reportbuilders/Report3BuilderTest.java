package services.reportbuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.Employee;
import model.Report;
import model.Task;

public class Report3BuilderTest {

    private ReportBuilder reportBuilder = new Report3Builder();

    @Test
    public final void testCorrectReportTitle() {
        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);
        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);
        this.reportBuilder.setFilterParameter("Adam Nowak", 1);
        Report report = this.reportBuilder.buildReport();

        Assert.assertEquals(
                        "Raport godzin przepracowanych miesięcznie przez: Adam Nowak, w roku: 2012",
                        report.getTitle());
    }

    @Test
    public final void testCountsHoursCorrectly() {

        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);
        employee1.addTask(task1);
        employee2.addTask(task2);

        Date date3 = new GregorianCalendar(2012, Calendar.OCTOBER, 28).getTime();
        Task task3 = new Task(date3, "projekt3", "task3", 2);
        employee1.addTask(task3);

        Date date4 = new GregorianCalendar(2012, Calendar.OCTOBER, 25).getTime();
        Task task4 = new Task(date4, "projekt2", "task3", 11);

        Date date5 = new GregorianCalendar(2012, Calendar.OCTOBER, 22).getTime();
        Task task5 = new Task(date5, "projekt2", "task3", 9);
        employee1.addTask(task5);
        employee1.addTask(task4);
        employees.add(employee1);
        employees.add(employee2);

        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);
        this.reportBuilder.setFilterParameter("Adam Nowak", 1);
        Report report = this.reportBuilder.buildReport();

        Assert.assertEquals(3, report.getRows().size());
        Assert.assertTrue(report.getRows().stream()
                        .anyMatch(r -> (r.get(1).equals("Czerwiec")
                                        && r.get(2).equals("projekt1")
                                        && r.get(3).equals("1.0"))));
        Assert.assertTrue(report.getRows().stream()
                        .anyMatch(r -> (r.get(1).equals("Pażdziernik")
                                        && r.get(2).equals("projekt2")
                                        && r.get(3).equals("20.0"))));
        Assert.assertTrue(report.getRows().stream()
                        .anyMatch(r -> (r.get(1).equals("Pażdziernik")
                                        && r.get(2).equals("projekt3")
                                        && r.get(3).equals("2.0"))));
    }

    @Test
    public final void testEmptyReportIfEmployeeWithNoTasks() {
        ReportBuilder builder = new Report3Builder();
        Employee employee5 = new Employee("Adam", "Nowak");
        List<Employee> employees3 = new ArrayList<Employee>();
        employees3.add(employee5);
        builder.setEmployees(employees3);
        Report report = builder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testEmptyReportIfNoEmployeesAdded() {
        ReportBuilder builder = new Report3Builder();
        Report report = builder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testNoRowsIfYearNotExists() {

        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);
        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2011", 0);
        this.reportBuilder.setFilterParameter("Adam Nowak", 1);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testNoRowsIfZeroHours() {

        Employee employee3 = new Employee("Zdzislaw", "Zur");
        List<Employee> employees2 = new ArrayList<Employee>();
        Date date5 = new GregorianCalendar(2020, Calendar.JUNE, 25).getTime();
        Task task5 = new Task(date5, "projekt1", "task1", 0);
        employee3.addTask(task5);
        employees2.add(employee3);
        this.reportBuilder.setEmployees(employees2);
        this.reportBuilder.setFilterParameter("2020", 0);
        this.reportBuilder.setFilterParameter("Zdzislaw Zur", 1);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testNoYearAndNoPersonInTitleIfNoFilterParamSet() {

        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);

        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie",
                        report.getTitle());
    }

    @Test
    public final void testOnlyPersonInTittleIfNoYearFiltered() {
        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);

        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("Adam Nowak", 1);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie przez: Adam Nowak",
                        report.getTitle());
    }

    @Test
    public final void testOnlyYearInTittleIfNoPersonFiltered() {
        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);

        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals("Raport godzin przepracowanych miesięcznie, w roku: 2012",
                        report.getTitle());
    }

    @Test
    public final void testPresentRowsIfFilterYearExistsAndPersonNotSpecified() {
        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);
        employee1.addTask(task1);
        employee2.addTask(task2);

        Date date6 = new GregorianCalendar(2020, Calendar.JANUARY, 5).getTime();
        Task task6 = new Task(date6, "projekt1", "task1", 1);
        employee1.addTask(task6);

        employees.add(employee1);
        employees.add(employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);

        Report report = this.reportBuilder.buildReport();

        Assert.assertEquals(2, report.getRows().size());
    }

    @Test
    public final void testProperColumnNames() {
        List<Employee> employees = new ArrayList<Employee>();

        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);
        Report report = this.reportBuilder.buildReport();

        List<String> properColumnNames = new ArrayList<String>();
        properColumnNames.add("L.p");
        properColumnNames.add("Miesiąc");
        properColumnNames.add("Projekt");
        properColumnNames.add("Liczba godzin");
        Assert.assertEquals(properColumnNames, report.getColumnNames());
    }

}
