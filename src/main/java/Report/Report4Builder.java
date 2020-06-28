package Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;
import services.PossibleYearRetriever;

public class Report4Builder extends ReportBuilder {

	private int year;

	public Report4Builder() {
		super();
		this.inputParamsNames.add("rok");
	}

	@Override
	public Report buildReport() {

		Report report = new Report();
		List<Employee> modelEmployees = employees;
		List<Employee> filteredEmployees = new ArrayList<Employee>();
		this.year = Integer.valueOf(inputParams.get(0));

		for (Employee employee : modelEmployees) {
			List<Task> filteredTasks = new ArrayList<Task>();
			for (Task task : employee.getTaskList()) {
				Date date = task.getTaskDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.YEAR) == this.year) {
					filteredTasks.add(task);
				}
			}
			if (filteredTasks.size() > 0) {
				Employee employeeCopy = (Employee) employee.clone();
				employeeCopy.setTaskList(filteredTasks);
				filteredEmployees.add(employeeCopy);
			}
		}

		report.setTitle("Procentowy udział projektów w pracy osób dla roku: " + year);

		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p.");
		columnNames.add("Imię i nazwisko");
		report.setColumnNames(columnNames);
		report.setColumnNames(columnNames);

		for (Employee employee : filteredEmployees) {
			for (String project : employee.getProjects()) {
				if (!columnNames.contains(project)) {
					columnNames.add(project);
				}
			}
		}

		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		for (Employee employee : filteredEmployees) {
			Double totalHours = employee.getTotalHours();
			for (String project : employee.getProjects()) {
				List<String> rowToAdd = new ArrayList<String>();
				for (int i = 0; i < report.getColumnNames().size(); i++) {
					rowToAdd.add("");
				}
				String employeeName = employee.getNameAndSurname();

				boolean rowExists = false;
				for (List<String> row : rows) {
					if (row.get(1).contains(employeeName)) {
						rowToAdd = row;
						rowExists = true;
					}
				}
				if (!rowExists) {
					rowToAdd.set(0, rowsCounter.toString());
					rowsCounter++;
					rowToAdd.set(1, employee.getNameAndSurname());
				}
				Integer indexOfProject = report.getColumnNames().indexOf(project);

				Double projectHours = employee.getProjectHours(project);
				Double percentHours = (projectHours * 100) / totalHours;

				percentHours = percentHours * 100;
				percentHours = (double) Math.round(percentHours);
				percentHours = percentHours / 100;

				rowToAdd.set(indexOfProject, percentHours.toString() + "%");
				if (!rowExists) {
					rows.add(rowToAdd);
				}
			}
		}

		report.setRows(rows);

		return report;
	}

	@Override
	public void retrievePossibleInputData() {
		possibleDataRetriever = new PossibleYearRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

	}
}
