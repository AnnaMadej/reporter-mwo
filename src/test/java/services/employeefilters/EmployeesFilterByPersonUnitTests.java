package services.employeefilters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.Employee;

public class EmployeesFilterByPersonUnitTests {

    private EmployeesFilter filter = new EmployeesFilterByPerson();
    
    @Test
    public final void testsFiltersData() {
        filter.setFilterParameter("Jan Nowak");
        Employee employee1 = new Employee("Jan", "Nowak");
        Employee employee2 = new Employee("Paweł",  "Kowalski");
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);
        
        employees = filter.filterEmployees(employees);
        
        Assert.assertFalse(employees.contains(employee2));
        Assert.assertTrue(employees.contains(employee1));
        
        Assert.assertEquals(1, employees.size());
    }
    
    @Test
    public final void testDoesntFilterIfNullParameter() {
        Employee employee1 = new Employee("Jan", "Nowak");
        Employee employee2 = new Employee("Paweł",  "Kowalski");
        
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);
        
        employees = filter.filterEmployees(employees);
        
        Assert.assertTrue(employees.contains(employee2));
        Assert.assertTrue(employees.contains(employee1));
        
        Assert.assertEquals(2, employees.size());
    }

    
    
   
}
