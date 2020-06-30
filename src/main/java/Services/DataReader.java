package Services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Model.Employee;
import Model.ScanError;
import Model.Task;

public class DataReader {

	private String extractEmployeeName(String fileName) {
		return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
	}

	private String extractEmployeeSurname(String fileName) {
		return fileName.substring(0, fileName.indexOf("_"));
	}

	public Employee readFile(File file) throws IOException, InvalidFormatException {

		String fileLocation = file.getParent();
		Integer fileMonthFromLocation;
		Integer fileYearFromLocation;

		String filename = file.getName().substring(0, file.getName().indexOf("."));
		if (!filename.matches("[A-z]+_[A-z]+")) {
			ScanErrorsHolder.addScanError(new ScanError(file.getPath(), "", "", "zła nazwa pliku!"));
			return null;
		}

		try {
			fileMonthFromLocation = Integer.valueOf(fileLocation.substring(fileLocation.length() - 2));
			fileYearFromLocation = Integer
					.valueOf(fileLocation.substring(fileLocation.length() - 7, fileLocation.length() - 3));
		} catch (NumberFormatException e) {
			ScanErrorsHolder.addScanError(new ScanError(file.getPath(), "", "", "zła lokalizacja pliku!"));
			return null;
		}

		if (fileMonthFromLocation < 1 || fileMonthFromLocation > 12) {
			ScanErrorsHolder.addScanError(new ScanError(file.getPath(), "", "", "zła lokalizacja pliku!"));
			return null;
		}

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int currentYear = calendar.get(Calendar.YEAR);
		if (fileYearFromLocation < currentYear - 15 || fileYearFromLocation > currentYear + 15) {
			ScanErrorsHolder.addScanError(new ScanError(file.getPath(), "", "", "zła lokalizacja pliku!"));
			return null;
		}

		String fileName = file.getName();
		String employeeName = this.extractEmployeeName(fileName);
		String employeeSurname = this.extractEmployeeSurname(fileName);
		Employee employee = new Employee(employeeName, employeeSurname);
		new ArrayList<Task>();
		Workbook wb = WorkbookFactory.create(file);

		String project;
		String description;
		double time;

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (sheet.getRow(0).getCell(0) == null || sheet.getRow(0).getCell(1) == null
					|| sheet.getRow(0).getCell(2) == null
					|| !sheet.getRow(0).getCell(0).getCellTypeEnum().equals(CellType.STRING)
					|| !sheet.getRow(0).getCell(1).getCellTypeEnum().equals(CellType.STRING)
					|| !sheet.getRow(0).getCell(2).getCellTypeEnum().equals(CellType.STRING)
					|| !sheet.getRow(0).getCell(0).getStringCellValue().equals("Data")
					|| !sheet.getRow(0).getCell(1).getStringCellValue().equals("Zadanie")
					|| !sheet.getRow(0).getCell(2).getStringCellValue().equals("Czas [h]")) {
				ScanErrorsHolder.addScanError(
						new ScanError(file.getPath(), sheet.getSheetName(), "Arkusz nie zawiera odpowiednich kolumn"));
				continue;
			}
			project = sheet.getSheetName();
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {

				if (sheet.getRow(j) == null) {
					ScanErrorsHolder
							.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "pusty wiersz!"));
					continue;
				}

				Row row = sheet.getRow(j);

				if (row.getCell(0) == null || row.getCell(0).getCellTypeEnum() == CellType.BLANK) {
					ScanErrorsHolder.addScanError(
							new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "DATA", "pusta komórka!"));
					continue;
				}
				if (!row.getCell(0).getCellTypeEnum().equals(CellType.NUMERIC)) {
					ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "DATA",
							"komórka nie zawiera wartości numerycznej!"));
					continue;
				}

				if (row.getCell(1) == null || row.getCell(1).getCellTypeEnum() == CellType.BLANK) {
					ScanErrorsHolder.addScanError(
							new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "OPIS", "pusta komórka!"));
					continue;
				}
				if (row.getCell(1).getCellTypeEnum() != CellType.STRING) {
					ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "OPIS",
							"komórka nie zawiera wartości numerycznej!"));
					continue;
				}

				if (row.getCell(2) == null || row.getCell(2).getCellTypeEnum() == CellType.BLANK) {
					ScanErrorsHolder.addScanError(
							new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "CZAS", "pusta komórka!"));
					continue;

				}
				if (row.getCell(2).getCellTypeEnum() != CellType.NUMERIC) {
					ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "CZAS",
							"komórka nie zawiera wartości numerycznej!"));
					continue;
				}
				if (row.getCell(2).getNumericCellValue() > 24) {
					ScanErrorsHolder.addScanError(
							new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "CZAS", "nieodpowiednie dane!"));
					continue;
				}

				if (row.getCell(0) != null) {
					Cell dateCell = row.getCell(0);
					Cell descriptionCell = row.getCell(1);
					Cell timeCell = row.getCell(2);

					if (descriptionCell.getStringCellValue().trim().equals("")) {
						ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "OPIS",
								"pusty opis w komórce!"));
						continue;
					}

					try {
						date = dateCell.getDateCellValue();
						calendar.setTime(date);
					} catch (NullPointerException e) {
						ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "DATA",
								"zle wpisana data!"));
						continue;
					}

					if (calendar.get(Calendar.YEAR) != fileYearFromLocation) {
						ScanErrorsHolder.addScanError(
								new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "DATA", "zły rok w dacie!"));
						continue;
					}

					if (calendar.get(Calendar.MONTH) + 1 != fileMonthFromLocation) {
						ScanErrorsHolder.addScanError(new ScanError(file.getPath(), sheet.getSheetName(), j + 1, "DATA",
								"zły miesiąc w dacie!"));
						continue;
					}

					description = descriptionCell.getStringCellValue();
					time = timeCell.getNumericCellValue();
					Task task = new Task(date, project, description, time);
					employee.addTask(task);
				}

			}

		}
		wb.close();
		return employee;
	}

}
