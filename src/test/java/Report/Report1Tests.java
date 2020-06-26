package Report;

import Model.Model;
import Model.Employee;
import Model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

public class Report1Tests {

    private Model model;
    private List<Employee> employees;
    private Employee employee1;
    private Employee employee2;

    @Before
    public void init() {
        model = Mockito.mock(Model.class);
        employees = new ArrayList<Employee>();
        employee1 = new Employee("Jan", "Turnia");
        employee2 = new Employee("Janusz", "Wierch");
    }

    @Test
    public void testRowsEmptyIfZeroHours() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 0);
        employee1.addTask(task1);
        employees.add(employee1);
        Mockito.when(model.getEmployeeList()).thenReturn(employees);

        ReportBuilder reportBuilder = new Report1Builder(2012);
        Report report = reportBuilder.buildReport(model);

        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public void testDisplaysAsManyRowsAsEmployees() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Date date2 = new GregorianCalendar(2012, Calendar.JULY,24).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 2);
        Task task2 = new Task(date2,"project3", "seminar", 3);
        Task task3 = new Task(date2,"project3", "data analysis", 5);
        employee1.addTask(task1);
        employee2.addTask(task2);
        employee2.addTask(task3);
        employees.add(employee1);
        employees.add(employee2);

        Mockito.when(model.getEmployeeList()).thenReturn(employees);

        ReportBuilder reportBuilder = new Report1Builder(2012);
        Report report = reportBuilder.buildReport(model);

        Assert.assertEquals(2, report.getRows().size());
    }

    @Test
    public void testDisplaysCorrectSumOfHoursHours() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Date date2 = new GregorianCalendar(2012, Calendar.JULY,24).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 2);
        Task task2 = new Task(date2,"project3", "seminar", 3.5);
        employee1.addTask(task1);
        employee1.addTask(task2);
        employees.add(employee1);

        Mockito.when(model.getEmployeeList()).thenReturn(employees);

        ReportBuilder reportBuilder = new Report1Builder(2012);
        Report report = reportBuilder.buildReport(model);

        Assert.assertEquals("5.5", report.getRows().get(0).get(2));
    }
}
