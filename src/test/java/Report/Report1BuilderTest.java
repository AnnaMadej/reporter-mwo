package Report;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import Model.Employee;
import Model.Task;

public class Report1BuilderTest {

	@Test
	public void test() {
		List<Employee> employees = new ArrayList<Employee>();

		Employee employee1 = new Employee("Jan", "Nowak");

		Date date = new Date();
		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
		employee1.addTask(task);
		Employee employee2 = new Employee("Paweł", "Kwiatkowski");
		Task task2 = new Task(date, "jakisProjekt", "jakies zadanie2", 7);
		Task task3 = new Task(date, "jakisProjekt3", "jakies zadanie2", 7);
		employee2.addTask(task2);
		employee2.addTask(task3);

		employees.add(employee1);
		employees.add(employee2);

		ReportBuilder rBuilder = new Report1Builder(2020);

		Report report = rBuilder.buildReport(employees);
		ReportPrinter.printReport(report);

	}
	

}
