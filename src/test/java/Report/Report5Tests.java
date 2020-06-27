//package Report;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import Model.Employee;
//import Model.Task;
//
//public class Report5Tests {
//
//	@Test
//	public void testAddsProperEmployee() {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Date date = new Date();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		Employee employee2 = new Employee("Paweł", "Kwiatkowski");
//		Task task2 = new Task(date, "jakisProjekt", "jakies zadanie2", 7);
//		employee2.addTask(task2);
//		employees.add(employee1);
//		employees.add(employee2);
//		ReportBuilder rBuilder = new Report5Builder(employees);
//		Report report = rBuilder.buildReport("jakisProjekt");
//	
//		Assert.assertTrue(report.getRows().stream().anyMatch(row -> row.get(1).equals("Paweł Kwiatkowski")));
//		Assert.assertTrue(report.getRows().stream().anyMatch(row -> row.get(1).equals("Jan Nowak")));
//	}
//
//	@Test
//	public void testNotFilteringMasterEmployeesData() throws IOException {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employees.add(employee1);
//		ReportBuilder rBuilder = new Report5Builder(employees);
//		rBuilder.buildReport("Projekt2");
//		ReportBuilder rBuilder2 = new Report5Builder(employees);
//		Report report = rBuilder2.buildReport( "jakisProjekt");
//
//		Assert.assertTrue((report.getRows().size() == 1));
//
//	}
//
//	@Test
//	public void testNotCountingHoursFromDifferentProject() throws IOException {
//		List<Employee> employees = new ArrayList<Employee>();
//		
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		Task task1 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employee1.addTask(task1);
//		employees.add(employee1);
//		ReportBuilder rBuilder2 = new Report5Builder(employees);
//		Report report = rBuilder2.buildReport("jakisProjekt");
//		Assert.assertTrue((report.getRows().size() == 1));
//		Assert.assertEquals("3.0", report.getRows().get(0).get(3));
//	}
//
//	@Test
//	public void testCountingHours() throws IOException {
//		List<Employee> employees = new ArrayList<Employee>();
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		Task task1 = new Task(date, "jakisProjekt", "jakies zadanie", 8);
//		employee1.addTask(task);
//		employee1.addTask(task1);
//		employees.add(employee1);
//		ReportBuilder rBuilder2 = new Report5Builder(employees);
//		Report report = rBuilder2.buildReport("jakisProjekt");
//		Assert.assertTrue((report.getRows().size() == 1));
//		Assert.assertEquals("11.0", report.getRows().get(0).get(3));
//	}
//	
//	@Test
//	public void testReport5() {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Date date = new Date();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		Employee employee2 = new Employee("Paweł", "Kwiatkowski");
//		Task task2 = new Task(date, "jakisProjekt", "jakies zadanie2", 7);
//		Task task3 = new Task(date, "jakisProjekt3", "jakies zadanie2", 7);
//		employee2.addTask(task2);
//		employee2.addTask(task3);
//		employees.add(employee1);
//		employees.add(employee2);
//		ReportBuilder rBuilder = new Report5Builder(employees);
//		Report report = rBuilder.buildReport("jakisProjekt");
//		Assert.assertEquals(4, report.getColumnNames().size());
//		Assert.assertEquals(2, report.getRows().size());
//		Assert.assertEquals("3.0", report.getRows().get(0).get(3));
//		Assert.assertEquals("7.0", report.getRows().get(1).get(3));
//	}
//	
//	@Test
//	public void testNoRowsIfNoEmployeesData() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		ReportBuilder rBuilder = new Report5Builder(employees);
//		Report report = rBuilder.buildReport("projekt");
//
//		Assert.assertEquals(0, report.getRows().size());
//	}
//	
//	@Test
//	public void testEmptyReportIfNotExistingProject() throws IOException {
//		List<Employee> employees = new ArrayList<Employee>();
//
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		Task task1 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employee1.addTask(task1);
//		employees.add(employee1);
//		ReportBuilder rBuilder2 = new Report5Builder(employees);
//		Report report = rBuilder2.buildReport("Nieistniejący projekt");
//		Assert.assertEquals(0, report.getRows().size());
//	}
//	
//
//}
