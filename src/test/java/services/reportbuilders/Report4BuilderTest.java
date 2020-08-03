package services.reportbuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Employee;
import model.Report;
import model.Task;

public class Report4BuilderTest {

    private ReportBuilder reportBuilder;
    private Employee employee1;
    private Employee employee2;

    @Before
    public void init() {
        new ArrayList<Employee>();
        this.reportBuilder = new Report4Builder();
        this.employee1 = new Employee("Adam", "Nowak");
        this.employee2 = new Employee("Zofia", "Dzięgiel");
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task1 = new Task(date1, "projekt1", "task1", 1);
        Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
        Task task2 = new Task(date2, "projekt2", "task1", 7);
        this.employee1.addTask(task1);
        this.employee2.addTask(task2);
    }

    @Test
    public final void testCorrectReportTitle() {
        this.reportBuilder.setFilterParameter("2020", 0);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals("Procentowy udział projektów w pracy osób w roku: 2020",
                        report.getTitle());
    }

    @Test
    public final void testCorrectReportTitleWhenNoYear() {
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals("Procentowy udział projektów w pracy osób ",
                        report.getTitle());
    }

    @Test
    public final void testEmptyReportIfEmployeeWithNoTasks() {
        ReportBuilder builder = new Report4Builder();
        Employee employee5 = new Employee("Adam", "Nowak");
        List<Employee> employees3 = new ArrayList<Employee>();
        employees3.add(employee5);
        builder.setEmployees(employees3);
        Report report = builder.buildReport();

        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testEmptyReportIfNoEmployeesAdded() {
        ReportBuilder builder = new Report4Builder();
        Report report = builder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testNoRowsIfAllZeroPercents() {

        Employee employee3 = new Employee("Zdzisław", "Żur");
        List<Employee> employees2 = new ArrayList<Employee>();
        Date date5 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task5 = new Task(date5, "projekt1", "task1", 0);
        new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
        Task task6 = new Task(date5, "projekt2", "task1", 0);
        employee3.addTask(task5);
        employee3.addTask(task6);
        employees2.add(employee3);
        this.reportBuilder.setEmployees(employees2);
        this.reportBuilder.setFilterParameter("2012", 0);
        Report report = this.reportBuilder.buildReport();

        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testNoRowsIfFilterParamNotExistsInData() {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(this.employee1);
        employees.add(this.employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2028", 0);
        Report report = this.reportBuilder.buildReport();
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public final void testPercentsSumIsAlways100() {

        List<Employee> employees = new ArrayList<Employee>();

        Employee employee1 = new Employee("Jan", "Nowak");

        Random rand = new Random();
        Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
        Date date = myCalendar.getTime();
        Task task = new Task(date, "jakisProjekt", "jakies zadanie",
                        rand.nextDouble() + rand.nextInt(12));
        employee1.addTask(task);
        Employee employee2 = new Employee("Paweł", "Kwiatkowski");
        Task task2 = new Task(date, "jakisProjekt", "jakies zadanie2",
                        rand.nextDouble() + rand.nextInt(12));
        Task task3 = new Task(date, "jakisProjekt3", "jakies zadanie2",
                        rand.nextDouble() + rand.nextInt(12));
        employee2.addTask(task2);
        employee2.addTask(task3);

        employees.add(employee1);
        employees.add(employee2);

        ReportBuilder rBuilder = new Report4Builder();

        rBuilder.setEmployees(employees);
        Report report = rBuilder.buildReport();

        for (List<String> row : report.getRows()) {
            String proj1Percents = row.get(2);

            String proj2Percents = row.get(3);

            Double proj1PercentsDouble = Double.parseDouble(
                            proj1Percents.substring(0, proj1Percents.indexOf("%")));
            Double proj2PercentsDouble = Double.parseDouble(
                            proj2Percents.substring(0, proj2Percents.indexOf("%")));

            Assert.assertTrue(proj1PercentsDouble + proj2PercentsDouble == 100);
        }
    }

    @Test
    public final void testProperColumnNames() {
        List<Employee> employees = new ArrayList<Employee>();

        employees.add(this.employee1);
        employees.add(this.employee2);
        this.reportBuilder.setEmployees(employees);
        this.reportBuilder.setFilterParameter("2012", 0);
        Report report = this.reportBuilder.buildReport();

        List<String> properColumnNames = new ArrayList<String>();
        properColumnNames.add("L.p");
        properColumnNames.add("Imię i nazwisko");
        properColumnNames.add("projekt1");
        properColumnNames.add("projekt2");
        Assert.assertEquals(properColumnNames, report.getColumnNames());
    }
}
