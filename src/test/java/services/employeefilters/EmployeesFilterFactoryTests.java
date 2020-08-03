package services.employeefilters;

import org.junit.Assert;
import org.junit.Test;

public class EmployeesFilterFactoryTests {

    @Test
    public final void testReturnsDefaultFilter() {
        Assert.assertTrue(EmployeesFilterFactory
                        .getEmployeesFilter("whatever") instanceof EmployeesFilterByYear);
    }

    @Test
    public final void testReturnsPersonFilter() {
        Assert.assertTrue(EmployeesFilterFactory
                        .getEmployeesFilter("person") instanceof EmployeesFilterByPerson);
    }

    @Test
    public final void testReturnsProjectnFilter() {
        Assert.assertTrue(EmployeesFilterFactory.getEmployeesFilter(
                        "project") instanceof EmployeesFilterByProjectName);
    }

    @Test
    public final void testReturnsYearFilter() {
        Assert.assertTrue(EmployeesFilterFactory
                        .getEmployeesFilter("year") instanceof EmployeesFilterByYear);
    }

}
