package services.employeefilters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Employee;
import model.Task;

public class EmployeesFilterByProjectNameUnitTests {

    private EmployeesFilter filter;
    private Employee employee1 = new Employee("Jan", "Nowak");
    private Employee employee2 = new Employee("Paweł", "Kowalski");
    private List<Employee> employees;

    @Before
    public void init() {
        filter = new EmployeesFilterByProjectName();
        Task task1 = new Task(new Date(), "Projekt1", "zadanie", 4);
        employee1.addTask(task1);

        Task task2 = new Task(new Date(), "Projekt2", "zadanie", 4);
        employee2.addTask(task2);

        employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);
    }

    @Test
    public final void testsFiltersData() {
        filter.setFilterParameter("Projekt1");

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
