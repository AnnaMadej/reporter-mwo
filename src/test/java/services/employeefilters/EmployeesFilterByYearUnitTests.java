package services.employeefilters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Employee;
import model.Task;

public class EmployeesFilterByYearUnitTests {

    private EmployeesFilter filter;
    private Employee employee1 = new Employee("Jan", "Nowak");
    private Employee employee2 = new Employee("Paweł", "Kowalski");
    private List<Employee> employees;

    @Before
    public void init() {
        filter = new EmployeesFilterByYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 01, 01);
        Task task1 = new Task(calendar.getTime(), "Projekt1", "zadanie", 4);
        employee1.addTask(task1);

        calendar.set(2013, 01, 01);
        Task task2 = new Task(calendar.getTime(), "Projekt2", "zadanie", 4);
        employee2.addTask(task2);

        employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);
    }

    @Test
    public final void testsFiltersData() {
        filter.setFilterParameter("2012");

        employees = filter.filterEmployees(employees);

        Assert.assertFalse(employees.contains(employee2));
        Assert.assertTrue(employees.contains(employee1));

        Assert.assertEquals(1, employees.size());
    }

    @Test
    public final void testDoesntFilterIfNullParameter() {
        employees = filter.filterEmployees(employees);

        Assert.assertTrue(employees.contains(employee2));
        Assert.assertTrue(employees.contains(employee1));

        Assert.assertEquals(2, employees.size());
    }

}
