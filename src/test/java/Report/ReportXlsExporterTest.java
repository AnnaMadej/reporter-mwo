//package Report;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import Model.Employee;
//import Model.Task;
//
//public class ReportXlsExporterTest {
//
//	@Test
//	public void test() throws IOException, EncryptedDocumentException, InvalidFormatException {
//		List<Employee> employees = new ArrayList<Employee>();
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
//		Report report = Mockito.mock(Report.class);
//		String reportTitle = "Tytuł raportu";
//		List<String> columnNames = new ArrayList<String>();
//		columnNames.add("Kolumna1");
//		columnNames.add("Kolumna2");
//		columnNames.add("Kolumna3");
//		List<List<String>> rows = new ArrayList<List<String>>();
//		List<String> row1 = new ArrayList<String>();
//		row1.add("1");
//		row1.add("2");
//		row1.add("3");
//		List<String> row2 = new ArrayList<String>();
//		row2.add("4");
//		row2.add("5");
//		row2.add("6");
//		List<String> row3 = new ArrayList<String>();
//		row3.add("7");
//		row3.add("8");
//		row3.add("9");
//		rows.add(row1);
//		rows.add(row2);
//		rows.add(row3);
//
//		Mockito.when(report.getColumnNames()).thenReturn(columnNames);
//		Mockito.when(report.getTitle()).thenReturn(reportTitle);
//		Mockito.when(report.getRows()).thenReturn(rows);
//
//		ReportXlsExporter exp = new ReportXlsExporter();
//
//		File file = ReportXlsExporter.exportToXls(report);
//		Assert.assertTrue(file.exists());
//
//		Workbook wb = WorkbookFactory.create(file);
//
//		Sheet sheet = wb.getSheetAt(0);
//
//		Assert.assertEquals("Tytuł raportu", sheet.getRow(3).getCell(0).getStringCellValue());
//
//		Row columnNamesRow = sheet.getRow(5);
//
//		for (int i = 0; i < report.getColumnNames().size(); i++) {
//			Assert.assertEquals(columnNames.get(i), columnNamesRow.getCell(i).getStringCellValue());
//		}
//
//		for (int i = 0; i < report.getRows().size(); i++) {
//			List<String> row = report.getRows().get(i);
//			for (int j = 0; j < row.size(); j++) {
//				Row currentRow = sheet.getRow(6 + i);
//				Assert.assertEquals(rows.get(i).get(j), currentRow.getCell(j).getStringCellValue());
//			}
//		}
//
//	}
//
//}
