package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import Model.Employee;
import Model.Report;
import Model.Task;
import Services.PossiblePersonRetriever;
import Services.PossibleYearRetriever;

public class Report3Builder extends ReportBuilder {

	private int year;

	private String id;
	public Report3Builder() {
		super();
		this.inputParamsNames.add("imię i nazwisko");
		this.inputParamsNames.add("rok");
	}

	@Override
	public Report buildReport() {

		this.id = this.inputParams.get(0);
		this.year = Integer.valueOf(this.inputParams.get(1));

		Report report = new Report();
		List<String> columnNames = new ArrayList<String>();
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		report.setTitle("Raport godzin przepracowanych przez: " + id + " w roku: " + year);

		columnNames.add("L.p.");
		columnNames.add("Miesiąc");
		columnNames.add("Projekt");
		columnNames.add("Liczba godzin");

		String[] polishMonths = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
				"Wrzesień", "Pażdziernik", "Listopad", "Grudzień" };

		Employee foundEmployee = findEmployee(employees, id);

		if (foundEmployee != null) {
			for (int i = 0; i < 12; i++) {
				HashMap<String, Double> hours = getHoursByProject(foundEmployee, year, i);
				for (String project : hours.keySet()) {
					if (hours.get(project) != 0) {
						List<String> newRow = new ArrayList();
						newRow.add(rowsCounter.toString());
						newRow.add(polishMonths[i]);
						newRow.add(project);
						newRow.add(String.valueOf(hours.get(project)));
						rows.add(newRow);
						rowsCounter++;
					}
				}
			}
		} else {
			System.out.println("Pracownik nie istnieje w bazie lub w tym roku nie wykonywał prac");
		}

		report.setColumnNames(columnNames);
		report.setRows(rows);
		return report;
	}

	public Employee findEmployee(List<Employee> employeeList, String id) {
		String[] listOfWords = id.toLowerCase().trim().split(" +");

		for (Employee employee : employeeList) {
			if ((listOfWords[0].equals(employee.getName().toLowerCase())
					&& listOfWords[1].equals(employee.getSurname().toLowerCase()))
					|| (listOfWords[1].equals(employee.getName().toLowerCase())
							&& listOfWords[0].equals(employee.getSurname().toLowerCase()))) {
				return employee;
			}
		}
		return null;
	}

	public HashMap<String, Double> getHoursByProject(Employee employee, int year, int month) {
		List<Task> taskList = employee.getTaskList();
		HashMap<String, Double> projectsHours = new HashMap<>();
		for (Task task : taskList) {
			Date date = task.getTaskDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);

			if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month) {
				String project = task.getProjectName();
				if (projectsHours.containsKey(project)) {
					Double d = projectsHours.get(project);
					projectsHours.put(project, task.getHours() + d);
				} else {
					projectsHours.put(project, task.getHours());
				}
			}
		}
		return projectsHours;
	}

	@Override
	public void retrievePossibleInputData() {
		possibleDataRetriever = new PossiblePersonRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

		possibleDataRetriever = new PossibleYearRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

	}
}
