//package Report;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import Model.Employee;
//import Model.Task;
//
//public class Report4Tests {
//	
//	@Test
//	public void testNumberOfColumnsIsCorrect() {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 2.5);
//		employee1.addTask(task);
//		Task task2 = new Task(date, "jakisProjekt2", "jakies zadanie2",7.5);
//		employee1.addTask(task2);
//
//		employees.add(employee1);
//		
//		ReportBuilder rBuilder = new Report4Builder(employees);
//
//		Report report = rBuilder.buildReport("2012");
//		
//		Assert.assertEquals(report.getColumnNames().size(), 4);
//		
//		
//	}
//	
//	@Test
//	public void testNumberOfRowsIsCorrect() {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 2.5);
//		employee1.addTask(task);
//		Task task2 = new Task(date, "jakisProjekt2", "jakies zadanie2",7.5);
//		employee1.addTask(task2);
//
//		employees.add(employee1);
//		
//		Employee employee2 = new Employee("Patryk", "Taki");
//
//		myCalendar = new GregorianCalendar(2012, 2, 11);
//		date = myCalendar.getTime();
//		Task task3  = new Task(date, "jakisProjekt", "jakies zadanko", 1);
//		employee1.addTask(task);
//		Task task4 = new Task(date, "jakisProjekt2", "zadanie21",2);
//		employee2.addTask(task2);
//
//		employees.add(employee1);
//		employees.add(employee2);
//
//		ReportBuilder rBuilder = new Report4Builder(employees);
//
//		Report report = rBuilder.buildReport("2012");
//
//		
//		Assert.assertEquals(2, report.getRows().size());
//	
//		
//	}
//
//	@Test
//	public void testCountingProperPercents() {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 2.5);
//		employee1.addTask(task);
//		Task task2 = new Task(date, "jakisProjekt2", "jakies zadanie2",7.5);
//		employee1.addTask(task2);
//
//		employees.add(employee1);
//
//
//		ReportBuilder rBuilder = new Report4Builder(employees);
//
//		Report report = rBuilder.buildReport("2012");
//
//		Assert.assertEquals(4, report.getColumnNames().size());
//		Assert.assertEquals(1, report.getRows().size());
//		Assert.assertEquals("25.0%", report.getRows().get(0).get(2));
//		Assert.assertEquals("75.0%", report.getRows().get(0).get(3));
//
//	}
//
//	@Test
//	public void testReport4() {
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		Employee employee2 = new Employee("Paweł", "Kwiatkowski");
//		Task task2 = new Task(date, "jakisProjekt", "jakies zadanie2", 7);
//		Task task3 = new Task(date, "jakisProjekt3", "jakies zadanie2", 7);
//		employee2.addTask(task2);
//		employee2.addTask(task3);
//
//		employees.add(employee1);
//		employees.add(employee2);
//		
//		ReportBuilder rBuilder = new Report4Builder(employees);
//
//		Report report = rBuilder.buildReport("2012");
//		Assert.assertEquals(4, report.getColumnNames().size());
//		Assert.assertEquals(2, report.getRows().size());
//		Assert.assertEquals("100.0%", report.getRows().get(0).get(2));
//		Assert.assertEquals("", report.getRows().get(0).get(3));
//		Assert.assertEquals("50.0%", report.getRows().get(1).get(2));
//		Assert.assertEquals("50.0%", report.getRows().get(1).get(3));
//
//	}
//
//	@Test
//	public void testNotFilteringMasterEmployeesData() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employees.add(employee1);
//		ReportBuilder rBuilder = new Report4Builder(employees);
//		rBuilder.buildReport("2013");
//		ReportBuilder rBuilder2 = new Report4Builder(employees);
//		Report report = rBuilder2.buildReport("2012");
//
//		Assert.assertTrue((report.getRows().size() == 1));
//	}
//
//	@Test
//	public void testNotCountingPercentsFromDifferentYears() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		Task task1 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//
//		myCalendar = new GregorianCalendar(2013, 2, 11);
//		date = myCalendar.getTime();
//		Task task2 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employee1.addTask(task1);
//		employee1.addTask(task2);
//		employees.add(employee1);
//		ReportBuilder rBuilder = new Report4Builder(employees);
//		Report report = rBuilder.buildReport("2012");
//
//		Assert.assertEquals(4, report.getColumnNames().size());
//		Assert.assertEquals(1, report.getRows().size());
//		Assert.assertEquals("50.0%", report.getRows().get(0).get(2));
//		Assert.assertEquals("50.0%", report.getRows().get(0).get(3));
//
//	}
//
//	@Test
//	public void testSumOfPercentsInRowIsAlways100() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//
//		Random random = new Random();
//
//		for (int i = 0; i < 10; i++) {
//			Task task = new Task(date, "jakisProjekt", "jakies zadanie" + i, random.nextDouble() * random.nextInt(120));
//			Task task1 = new Task(date, "innyProjekt", "jakies zadanie" + i, random.nextDouble() * random.nextInt(120));
//			employee1.addTask(task);
//			employee1.addTask(task1);
//		}
//
//		employees.add(employee1);
//		ReportBuilder rBuilder = new Report4Builder(employees);
//		Report report = rBuilder.buildReport("2012");
//
//		String proj1Percents = report.getRows().get(0).get(2);
//		String proj2Percents = report.getRows().get(0).get(3);
//
//		Double proj1PercentsDouble = Double.parseDouble(proj1Percents.substring(0, proj1Percents.indexOf("%")));
//		Double proj2PercentsDouble = Double.parseDouble(proj2Percents.substring(0, proj2Percents.indexOf("%")));
//
//		Assert.assertTrue(proj1PercentsDouble + proj2PercentsDouble == 100);
//	}
//	
//	@Test
//	public void testNoRowsIfNoEmployeesData() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		ReportBuilder rBuilder = new Report4Builder(employees);
//		Report report = rBuilder.buildReport("2012");
//
//		Assert.assertEquals(0, report.getRows().size());
//	}
//
//	@Test
//	public void testEmptyReportIfNotExistingYear() throws IOException {
//
//		List<Employee> employees = new ArrayList<Employee>();
//
//		Employee employee1 = new Employee("Jan", "Nowak");
//		Calendar myCalendar = new GregorianCalendar(2012, 2, 11);
//		Date date = myCalendar.getTime();
//		Task task = new Task(date, "jakisProjekt", "jakies zadanie", 3);
//		Task task1 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//
//		myCalendar = new GregorianCalendar(2013, 2, 11);
//		date = myCalendar.getTime();
//		Task task2 = new Task(date, "innyProjekt", "jakies zadanie", 3);
//		employee1.addTask(task);
//		employee1.addTask(task1);
//		employee1.addTask(task2);
//		employees.add(employee1);
//		ReportBuilder rBuilder = new Report4Builder(employees);
//		Report report = rBuilder.buildReport( "2020");
//
//		Assert.assertEquals(0, report.getRows().size());
//
//	}
//}
