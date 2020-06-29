package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Employee;
import Model.Task;
import Services.PossiblePersonRetriever;
import Services.PossibleYearRetriever;

public class Report3Builder extends ReportBuilder {

	public Report3Builder() {
		super();
		this.inputParamsNames.add("imię i nazwisko");
		this.inputParamsNames.add("rok");
	}

	@Override
	void filterEmployees() {
		List<Employee> filteredEmployees = new ArrayList<Employee>();

		for (Employee employee : employees) {
			List<Task> filteredTasks = new ArrayList<Task>();
			for (Task task : employee.getTaskList()) {
				Date date = task.getTaskDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.YEAR) == Integer.parseInt(inputParams.get(1))
						&& ((String) inputParams.get(0)).toLowerCase().contains(employee.getName().toLowerCase())
						&& ((String) inputParams.get(0)).toLowerCase().contains(employee.getSurname().toLowerCase())) {
					filteredTasks.add(task);
				}
			}
			if (filteredTasks.size() > 0) {
				Employee employeeCopy = (Employee) employee.clone();
				employeeCopy.setTaskList(filteredTasks);
				filteredEmployees.add(employeeCopy);
			}
		}

		employees = filteredEmployees;

	}

	@Override
	void setReportTitle() {
		report.setTitle("Rok: " + inputParams.get(1) + "; Imię i nazwisko: " + (String) inputParams.get(0));
	}

	@Override
	void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Miesiąc");
		columnNames.add("Projekt");
		columnNames.add("Liczba godzin");
		report.setColumnNames(columnNames);
	}

	@Override
	void setReportRows() {

		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		String[] polishMonths = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
				"Wrzesień", "Pażdziernik", "Listopad", "Grudzień" };

		if (employees.size() > 0) {

			Employee foundEmployee = employees.get(0);

			for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
				for (String project : foundEmployee.getProjects()) {
					Double hoursSum = 0.0;
					for (Task task : foundEmployee.getTaskList()) {

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(task.getTaskDate());
						if (task.getProjectName().equals(project) && calendar.get(Calendar.MONTH) == monthIndex
								&& calendar.get(Calendar.YEAR) == Integer.parseInt(inputParams.get(1))) {
							hoursSum += task.getHours();
						}
					}
					List<String> newRow = new ArrayList();
					newRow.add(rowsCounter.toString());
					newRow.add(polishMonths[monthIndex]);
					newRow.add(project);
					newRow.add(hoursSum.toString());

					rows.add(newRow);
					rowsCounter++;
				}
			}

		}
		report.setRows(rows);
	}

	@Override
	public void retrievePossibleInputData() {
		possibleDataRetriever = new PossiblePersonRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

		possibleDataRetriever = new PossibleYearRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

	}
}
