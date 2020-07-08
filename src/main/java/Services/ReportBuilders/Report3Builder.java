package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Model.Task;
import Services.EmployeeFilters.EmployeesFilterFactory;

public class Report3Builder extends ReportBuilder {

	public Report3Builder() {
		super();
		this.addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("year"));
		this.addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("person"));
	}

	private Double countHoursSum(List<Task> tasks, int monthIndex, String project) {
		Double hoursSum = 0.0;
		for (Task task : tasks) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(task.getTaskDate());
			if (task.getProjectName().equals(project) && calendar.get(Calendar.MONTH) == monthIndex) {
				hoursSum += task.getHours();
			}
		}
		return hoursSum;
	}

	@Override
	protected void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Miesiąc");
		columnNames.add("Projekt");
		columnNames.add("Liczba godzin");
		this.report.setColumnNames(columnNames);
	}

	@Override
	protected void setReportRows() {

		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		String[] polishMonths = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień",
				"Wrzesień", "Pażdziernik", "Listopad", "Grudzień" };

		if (this.employees.size() > 0) {

			Set<String> projects = new TreeSet<String>();
			List<Task> tasks = new ArrayList<Task>();

			this.employees.stream().forEach(emp -> {
				projects.addAll(emp.getProjects());
				tasks.addAll(emp.getTaskList());
			});

			for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
				for (String project : projects) {
					Double hoursSum = this.countHoursSum(tasks, monthIndex, project);
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
	protected void setReportTitle() {
		String title = "Raport godzin przepracowanych miesięcznie";
		if (this.filters.get(1).getFilterParameter() != null) {
			title += " przez: " + this.filters.get(1).getFilterParameter();
		}
		if (this.filters.get(0).getFilterParameter() != null) {
			title += ", w roku: " + this.filters.get(0).getFilterParameter();
		}
		this.report.setTitle(title);

//		this.report.setTitle("Raport godzin przepracowanych miesięcznie przez: "
//				+ this.filters.get(1).getFilterParameter() + ", w roku: " + this.filters.get(0).getFilterParameter());
	}

}
