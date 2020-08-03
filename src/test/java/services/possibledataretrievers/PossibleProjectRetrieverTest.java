package services.possibledataretrievers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import model.Employee;
import model.Task;

public class PossibleProjectRetrieverTest {

    private PossibleDataRetriever dataRetriever = new PossibleProjectRetriever();

    @Test
    public final void testRetrievesCorrectData() {
        Employee employee1 = new Employee("Jan", "Nowak");
        Task task1 = new Task(new Date(), "Projekt1", "zadanie", 2);
        Task task2 = new Task(new Date(), "Projekt2", "zadanie", 2);

        employee1.addTask(task1);
        employee1.addTask(task2);

        Employee employee2 = new Employee("Kornel", "Makuszyński");
        Task task3 = new Task(new Date(), "Projekt3", "zadanie", 2);
        Task task4 = new Task(new Date(), "Projekt2", "zadanie", 2);

        employee2.addTask(task3);
        employee2.addTask(task4);

        List<Employee> employees = new ArrayList<Employee>();

        employees.add(employee1);
        employees.add(employee2);

        Set<String> possibleData = dataRetriever.getPossibleData(employees);
        Assert.assertTrue(possibleData.contains("Projekt1"));
        Assert.assertTrue(possibleData.contains("Projekt2"));
        Assert.assertTrue(possibleData.contains("Projekt3"));

        Assert.assertEquals(3, possibleData.size());
    }

    @Test
    public final void testReturnsEmptyIfNullEmployeesList() {

        Set<String> possibleData = dataRetriever.getPossibleData(null);
        ;

        Assert.assertEquals(0, possibleData.size());

    }

    @Test
    public final void testReturnsEmptyIfEmptyEmployeesList() {
        List<Employee> employees = new ArrayList<Employee>();
        Set<String> possibleData = dataRetriever.getPossibleData(employees);
        ;

        Assert.assertEquals(0, possibleData.size());

    }

}
