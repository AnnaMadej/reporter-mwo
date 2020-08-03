package services.possibledataretrievers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import model.Employee;

public class PossiblePersonRetrieverTest {

    private PossibleDataRetriever possibleDataRetriever = new PossiblePersonRetriever();

    @Test
    public final void testRetrievesCorrectPeopleData() {
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee1 = new Employee("Adam", "Nowak");
        Employee employee2 = new Employee("Jan", "Nowak");
        Employee employee3 = new Employee("Adam", "Kowalski");
        Employee employee4 = new Employee("Jan", "Kowalski");

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);

        Set<String> possibleData = possibleDataRetriever.getPossibleData(employees);
        Assert.assertTrue(possibleData.contains("Adam Nowak"));
        Assert.assertTrue(possibleData.contains("Jan Nowak"));
        Assert.assertTrue(possibleData.contains("Adam Kowalski"));
        Assert.assertTrue(possibleData.contains("Jan Kowalski"));

        Assert.assertEquals(4, possibleData.size());

    }

    @Test
    public final void testReturnsEmptyIfNullEmployeesList() {

        Set<String> possibleData = possibleDataRetriever.getPossibleData(null);
        ;

        Assert.assertEquals(0, possibleData.size());

    }

    @Test
    public final void testReturnsEmptyIfEmptyEmployeesList() {
        List<Employee> employees = new ArrayList<Employee>();
        Set<String> possibleData = possibleDataRetriever.getPossibleData(employees);
        ;

        Assert.assertEquals(0, possibleData.size());

    }

}
