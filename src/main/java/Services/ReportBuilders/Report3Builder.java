package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.Employee;
import Model.Task;
import Services.EmployeeFilters.EmployeesFilterByPerson;
import Services.EmployeeFilters.EmployeesFilterByYear;

public class Report3Builder extends ReportBuilder {

	public Report3Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByYear());
		this.addEmployeesFilter(new EmployeesFilterByPerson());
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

	@Override
	void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Miesiąc");
		columnNames.add("Projekt");
		columnNames.add("Liczba godzin");
		this.report.setColumnNames(columnNames);
	}

	@Override
	void setReportRows() {

		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		String[] polishMonths = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
				"Wrzesień", "Pażdziernik", "Listopad", "Grudzień" };

		if (this.employees.size() > 0) {

			Employee foundEmployee = this.employees.get(0);

			for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
				for (String project : foundEmployee.getProjects()) {
					Double hoursSum = this.countHoursSum(foundEmployee, monthIndex, project);
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
		this.report.setRows(rows);
	}

	@Override
	void setReportTitle() {
		this.report.setTitle("Raport godzin przepracowanych miesięcznie przez: "
				+ this.filters.get(0).getFilterParameter() + ", w roku: " + this.filters.get(0).getFilterParameter());
	}

}
