package Report;

import Model.Employee;
import Model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class Report3Tests {

    private List<Employee> employees;
    private Employee employee1;
    private Employee employee2;

    @Before
    public void init() {
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
     
        ReportBuilder reportBuilder = new Report3Builder(2012, "Jan Turnia");
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals(0, report.getRows().size());
    }

    @Test
    public void testDisplaysHoursForASelectedEmployee() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Date date2 = new GregorianCalendar(2012, Calendar.APRIL,24).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 2);
        Task task2 = new Task(date2,"project2", "seminar", 3);
        Task task3 = new Task(date2,"project3", "data analysis", 5);
        employee1.addTask(task1);
        employee2.addTask(task2);
        employee2.addTask(task3);
        employees.add(employee1);
        employees.add(employee2);

        ReportBuilder reportBuilder = new Report3Builder(2012, "Jan Turnia");
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals("Czerwiec", report.getRows().get(0).get(1));
        Assert.assertEquals("2.0", report.getRows().get(0).get(3));
    }

    @Test
    public void testListsEachProjectPerMonthPerRow() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Date date2 = new GregorianCalendar(2012, Calendar.APRIL,24).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 50);
        Task task2 = new Task(date2,"project1", "seminar", 80);
        Task task3 = new Task(date2,"project2", "data analysis", 90);
        employee1.addTask(task1);
        employee1.addTask(task2);
        employee1.addTask(task3);
        employees.add(employee1);
        
        ReportBuilder reportBuilder = new Report3Builder(2012, "Jan Turnia");
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals("Kwiecień", report.getRows().get(0).get(1));
        Assert.assertEquals("90.0", report.getRows().get(0).get(3));
        Assert.assertEquals("Kwiecień", report.getRows().get(1).get(1));
        Assert.assertEquals("80.0", report.getRows().get(1).get(3));
        Assert.assertEquals("Czerwiec", report.getRows().get(2).get(1));
        Assert.assertEquals("50.0", report.getRows().get(2).get(3));
    }

    @Test
    public void testListsMonthsInChronologicalOrder() {
        Date date1 = new GregorianCalendar(2012, Calendar.JUNE,25).getTime();
        Date date2 = new GregorianCalendar(2012, Calendar.APRIL,24).getTime();
        Date date3 = new GregorianCalendar(2012, Calendar.NOVEMBER,7).getTime();
        Task task1 = new Task(date1,"project1", "meeting", 50);
        Task task2 = new Task(date2,"project1", "seminar", 80);
        Task task3 = new Task(date3,"project2", "data analysis", 90);
        employee1.addTask(task1);
        employee1.addTask(task2);
        employee1.addTask(task3);
        employees.add(employee1);


        ReportBuilder reportBuilder = new Report3Builder(2012, "Jan Turnia");
        Report report = reportBuilder.buildReport(employees);

        Assert.assertEquals("Listopad", report.getRows().get(report.getRows().size()-1).get(1));
    }
}
