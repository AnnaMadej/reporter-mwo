package Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Model.Employee;
import Model.Task;
import services.PossibleYearRetriever;

public class Report1Builder extends ReportBuilder {

	private int year;

	public Report1Builder() {
		super();
		this.inputParamsNames.add("rok");
	}

	@Override
	public Report buildReport() {

		Report report = new Report();

		this.year = Integer.valueOf(this.inputParams.get(0));
		report.setTitle("Raport godzin pracowników w roku: " + year);

		List<String> columnNames = new ArrayList<String>();
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		columnNames.add("L.p.");
		columnNames.add("Imię i nazwisko");
		columnNames.add("Liczba godzin");

		List<Employee> employeeList = employees;
		employeeList.sort(Comparator.comparing(Employee::getSurname));

		for (Employee employee : employeeList) {
			if (getTotalHours(employee, year) != 0) {
				List<String> newRow = new ArrayList();
				newRow.add(rowsCounter.toString());
				newRow.add(employee.getNameAndSurname());
				newRow.add(String.valueOf(getTotalHours(employee, year)));
				rows.add(newRow);
				rowsCounter++;
			}
		}

		report.setColumnNames(columnNames);
		report.setRows(rows);

		return report;
	}

	public double getTotalHours(Employee employee, int year) {
		List<Task> taskList = employee.getTaskList();
		double sum = 0;
		for (Task task : taskList) {
			Date date = task.getTaskDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			if (calendar.get(Calendar.YEAR) == year) {
				sum += task.getHours();
			}
		}
		return sum;
	}

	@Override
	public void retrievePossibleInputData() {
		possibleDataRetriever = new PossibleYearRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));
	}

}
