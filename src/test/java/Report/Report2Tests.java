package Report;

import Model.Employee;
import Model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

public class Report2Tests {

    private List<Employee> employees;

    @Before
    public void createModelAndEmployeeListForTest(){
        employees = new ArrayList<>();
    }

    @Test
    public void testReport2() {

        Employee employee1 = new Employee("Jan", "Kowalski");
        Employee employee2 = new Employee("Kazimierz", "Nowak");

        Date date1 = new GregorianCalendar(2012, Calendar.APRIL,10).getTime();
        Date date2 = new GregorianCalendar(2012,Calendar.MAY,15).getTime();

        Task task1 = new Task(date1,"projekt1", "jakieś zadanie", 5);
        Task task2 = new Task(date2,"projekt1", "inne zadanie", 2);

        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);

        ReportBuilder reportBuilder = new Report2Builder(2012);
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals(3, report.getColumnNames().size());
        Assert.assertEquals(1, report.getRows().size());
        Assert.assertEquals("7.0", report.getRows().get(0).get(2));

    }

    @Test
    public void testReportIsEmptyIfNoSuchYearInDataFiles() {

        Employee employee1 = new Employee("Jan", "Kowalski");
        Employee employee2 = new Employee("Kazimierz", "Nowak");

        Date date = new GregorianCalendar(2012,Calendar.FEBRUARY,10).getTime();

        Task task1 = new Task(date,"pierwszy projekt", "jakieś zadanie", 5);
        Task task2 = new Task(date,"drugi projekt", "inne zadanie", 5);

        employee1.addTask(task1);
        employee2.addTask(task2);

        employees.add(employee1);
        employees.add(employee2);

        ReportBuilder reportBuilder = new Report2Builder(2020);
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals(3, report.getColumnNames().size());
        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public void testNotAddingHoursFromDifferentProject() {

        Employee employee1 = new Employee("Jan", "Kowalski");
        Employee employee2 = new Employee("Kazimierz", "Nowak");

        Date date1 = new GregorianCalendar(2012,Calendar.APRIL,10).getTime();
        Date date2 = new GregorianCalendar(2012,Calendar.MAY,15).getTime();

        Task task1 = new Task(date1,"projekt1", "jakieś zadanie", 5);
        Task task2 = new Task(date2,"projekt1", "inne zadanie", 2.5);
        Task task3 = new Task(date1,"projekt2", "jeszcze inne zadanie", 5);
        Task task4 = new Task(date2,"projekt2", "kolejne zadanie", 4.25);

        employee1.addTask(task1);
        employee2.addTask(task2);
        employee1.addTask(task3);
        employee2.addTask(task4);

        employees.add(employee1);
        employees.add(employee2);

        ReportBuilder reportBuilder = new Report2Builder(2012);
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals("7.5", report.getRows().get(0).get(2));
        Assert.assertEquals("9.25", report.getRows().get(1).get(2));

    }
}
