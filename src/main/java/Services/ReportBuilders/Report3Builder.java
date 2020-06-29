package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Employee;
import Model.Task;
import Services.EmployeesFilter;
import Services.EmployeesFilterByPerson;
import Services.EmployeesFilterByYear;
import Services.PossiblePersonRetriever;
import Services.PossibleYearRetriever;

public class Report3Builder extends ReportBuilder {

	public Report3Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByYear());
		this.addEmployeesFilter(new EmployeesFilterByPerson());
	}

	@Override
	void filterEmployees() {

		for (EmployeesFilter filter : filters) {
			this.employees = filter.filterEmployees(employees);
		}
	}

	@Override
	void setReportTitle() {
		report.setTitle("Rok:  Imię i nazwisko: ");
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
					Double hoursSum = countHoursSum(foundEmployee, monthIndex, project);
					if (hoursSum != 0.0) {
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

		}
		report.setRows(rows);
	}

	private Double countHoursSum(Employee foundEmployee, int monthIndex, String project) {
		Double hoursSum = 0.0;
		for (Task task : foundEmployee.getTaskList()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(task.getTaskDate());
			if (task.getProjectName().equals(project) && calendar.get(Calendar.MONTH) == monthIndex) {
				hoursSum += task.getHours();
			}
		}
		return hoursSum;
	}


}
