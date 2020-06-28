package Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Model.Employee;
import Model.Task;

public class DataReaderTests {

	@Before
	public void cleanScanErrors() {
		ScanErrorsHolder.clearScanErrors();
	}

	@Test
	public void testContentOfTasksIsCorrect() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		List<Employee> employees = filesScanner.scanFiles("src/test/testing-data/reporter-dane");

		Optional<Employee> firstEmployee = employees.stream()
				.filter(emp -> emp.getNameAndSurname().equals("Jan Kowalski")).findFirst();

		List<Task> janTasks = new ArrayList<Task>();
		Calendar calendar = new GregorianCalendar(2012, 0, 5);
		Date date = calendar.getTime();
		Task task1 = new Task(date, "Projekt1", "Specyfikacja wymagan niefuncjonalnych dot. technologii", 3);

		calendar = new GregorianCalendar(2012, 0, 21);
		date = calendar.getTime();
		Task task2 = new Task(date, "Projekt2", "Analiza wymagań", 7);

		calendar = new GregorianCalendar(2012, 0, 21);
		date = calendar.getTime();
		Task task3 = new Task(date, "Projekt2", "Projekt UML", 7);
		date = calendar.getTime();
		calendar = new GregorianCalendar(2012, 1, 8);
		date = calendar.getTime();
		Task task4 = new Task(date, "Projekt1", "Testy prototypu", 3);

		calendar = new GregorianCalendar(2012, 1, 8);
		date = calendar.getTime();
		Task task5 = new Task(date, "Projekt1", "Przygotowanie pierwszej wersji dokumentacji użytkownika", 4);

		calendar = new GregorianCalendar(2012, 1, 1);
		date = calendar.getTime();
		Task task6 = new Task(date, "Projekt2", "Wizyta u klienta, dopytanie o dane", 7.25);

		janTasks.add(task1);
		janTasks.add(task2);
		janTasks.add(task3);
		janTasks.add(task4);
		janTasks.add(task5);
		janTasks.add(task6);

		Assert.assertEquals(janTasks.size(), firstEmployee.get().getTaskList().size());
		Assert.assertTrue(firstEmployee.get().getTaskList().containsAll(janTasks));

		Optional<Employee> secondEmployee = employees.stream()
				.filter(emp -> emp.getNameAndSurname().equals("Piotr Nowak")).findFirst();

		List<Task> piotrTasks = new ArrayList<Task>();

		calendar = new GregorianCalendar(2012, 0, 13);
		date = calendar.getTime();
		Task task7 = new Task(date, "Projekt2", "Wizyta u klienta", 3);

		calendar = new GregorianCalendar(2012, 0, 14);
		date = calendar.getTime();
		Task task8 = new Task(date, "Projekt2", "Podsumowanie spotkania", 1);

		piotrTasks.add(task7);
		piotrTasks.add(task8);

		Assert.assertEquals(piotrTasks.size(), secondEmployee.get().getTaskList().size());
		Assert.assertTrue(secondEmployee.get().getTaskList().containsAll(piotrTasks));
	}

	@Test
	public void testEachTypeOfScanErrorOneTime() throws InvalidFormatException, IOException {

		FilesScanner filesScanner = new FilesScanner();
		filesScanner.scanFiles("src/test/testing-data/reporter-dane-z-bledami");
		List<ScanError> myScanErrors = new ArrayList<ScanError>();
		ScanError scanError1 = new ScanError("src\\test\\testing-data\\reporter-dane-z-bledami\\plik-o-zlej-nazwie.xls",
				"", "", "zła nazwa pliku!");
		myScanErrors.add(scanError1);

		ScanError scanError2 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\ZLY-KATALOG\\Wisniewski_Marek.xls", "", "",
				"zła lokalizacja pliku!");
		myScanErrors.add(scanError2);

		ScanError scanError3 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Kowalski_Jan.xls", "Projekt2",
				"Arkusz nie zawiera odpowiednich kolumn");
		myScanErrors.add(scanError3);

		ScanError scanError4 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2012\\03\\Nowak_Piotr.xls", "Projekt1", 11,
				"pusty wiersz!");
		myScanErrors.add(scanError4);
		ScanErrorsHolder.showScanErrors();

		ScanError scanerror5 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2012\\03\\Wisniewski_Marek.xls", "Projekt1", 11,
				"OPIS", "pusta komórka!");
		myScanErrors.add(scanerror5);

		ScanError scanerror6 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2012\\03\\Wisniewski_Marek.xls", "Projekt2", 10,
				"CZAS", "pusta komórka!");
		myScanErrors.add(scanerror6);

		ScanError scanerror7 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2012\\03\\Wisniewski_Marek.xls", "Projekt2", 19,
				"OPIS", "pusty opis w komórce!");
		myScanErrors.add(scanerror7);

		ScanError scanerror8 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt2", 18,
				"DATA", "pusta komórka!");
		myScanErrors.add(scanerror8);

		ScanError scanerror9 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt2", 23,
				"DATA", "komórka nie zawiera wartości numerycznej!");
		myScanErrors.add(scanerror9);

		ScanError scanerror10 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2012\\03\\Wojcik_Dawid.xls", "Projekt1", 21, "DATA",
				"zły rok w dacie!");
		myScanErrors.add(scanerror10);

		ScanError scanerror11 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt1", 20,
				"DATA", "komórka nie zawiera wartości numerycznej!");
		myScanErrors.add(scanerror11);

		ScanError scanerror12 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt1", 25,
				"DATA", "zły miesiąc w dacie!");
		myScanErrors.add(scanerror12);

		ScanError scanerror13 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt2", 12,
				"CZAS", "komórka nie zawiera wartości numerycznej!");
		myScanErrors.add(scanerror13);

		ScanError scanerror14 = new ScanError(
				"src\\test\\testing-data\\reporter-dane-z-bledami\\2014\\05\\Wisniewski_Marek.xls", "Projekt2", 22,
				"DATA", "komórka nie zawiera wartości numerycznej!");
		myScanErrors.add(scanerror14);

		Assert.assertEquals(ScanErrorsHolder.getScanErrors().size(), 14);
		myScanErrors.removeAll(ScanErrorsHolder.getScanErrors());
		myScanErrors.removeAll(ScanErrorsHolder.getScanErrors());
		Assert.assertTrue(ScanErrorsHolder.getScanErrors().containsAll(myScanErrors));

	}

	@Test
	public void testFilesWithNoErrors() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		filesScanner.scanFiles("src/test/testing-data/reporter-dane");
		ScanErrorsHolder.showScanErrors();

		Assert.assertEquals(ScanErrorsHolder.getScanErrors().size(), 0);

	}

	@Test
	public void testManyScanErrorsInManyFiles() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		filesScanner.scanFiles("src/test/testing-data/reporter-dane-z-wieloma-bledami");
		Assert.assertEquals(ScanErrorsHolder.getScanErrors().size(), 61);
	}

	@Test
	public void testNumberOfEmployeesIsCorrect() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		List<Employee> employees = filesScanner.scanFiles("src/test/testing-data/reporter-dane");
		Assert.assertEquals(employees.size(), 2);
	}

	@Test
	public void testNumberOfProjectsIsCorrect() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		List<Employee> employees = filesScanner.scanFiles("src/test/testing-data/reporter-dane");

		Optional<Employee> firstEmployee = employees.stream()
				.filter(emp -> emp.getNameAndSurname().equals("Jan Kowalski")).findFirst();
		Assert.assertEquals(firstEmployee.get().getProjects().size(), 2);

		Optional<Employee> secondEmployee = employees.stream()
				.filter(emp -> emp.getNameAndSurname().equals("Piotr Nowak")).findFirst();
		Assert.assertEquals(secondEmployee.get().getProjects().size(), 1);
	}

	@Test
	public void testNumberOfTasksIsCorrect() throws InvalidFormatException, IOException {
		FilesScanner filesScanner = new FilesScanner();
		List<Employee> employees = filesScanner.scanFiles("src/test/testing-data/reporter-dane");

		List<Task> tasks = new ArrayList<Task>();

		for (Employee employee : employees) {
			tasks.addAll(employee.getTaskList());
		}
		Assert.assertEquals(tasks.size(), 8);
	}

}
