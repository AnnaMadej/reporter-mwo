package services.possibledataretrievers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import model.Employee;
import model.Task;

public class PossibleYearRetrieverTest {

    private PossibleDataRetriever dataRetriever = new PossibleYearRetriever();

    @Test
    public final void testRetrievesCorrectData() {
        Employee employee1 = new Employee("Jan", "Nowak");
        Calendar calendar = Calendar.getInstance();

        calendar.set(2012, 01, 01);
        Task task1 = new Task(calendar.getTime(), "projekt", "zadanie", 2);

        calendar.set(2013, 01, 01);
        Task task2 = new Task(calendar.getTime(), "projekt", "zadanie", 2);

        employee1.addTask(task1);
        employee1.addTask(task2);

        Employee employee2 = new Employee("Kornel", "Makuszyński");

        calendar.set(2014, 01, 01);
        Task task3 = new Task(calendar.getTime(), "projekt", "zadanie", 2);

        calendar.set(2012, 01, 01);
        Task task4 = new Task(calendar.getTime(), "projekt", "zadanie", 2);

        employee2.addTask(task3);
        employee2.addTask(task4);

        List<Employee> employees = new ArrayList<Employee>();

        employees.add(employee1);
        employees.add(employee2);

        Set<String> possibleData = dataRetriever.getPossibleData(employees);
        Assert.assertTrue(possibleData.contains("2012"));
        Assert.assertTrue(possibleData.contains("2013"));
        Assert.assertTrue(possibleData.contains("2014"));

        Assert.assertEquals(3, possibleData.size());
    }

    @Test
    public final void testReturnsEmptyIfNullEmployeesList() {

        Set<String> possibleData = dataRetriever.getPossibleData(null);
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
