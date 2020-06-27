package Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Model.Employee;
import Model.Task;

public class Report2Builder implements ReportBuilder {

	private int year;

	public Report2Builder(int year) {
		this.year = year;
	}

	@Override
	public Report buildReport(List<Employee> employees) {

		Report report = new Report();

		report.setTitle("Raport godzin projektów w roku: " + year);

		List<String> columnNames = new ArrayList<String>();
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		columnNames.add("L.p.");
		columnNames.add("Projekt");
		columnNames.add("Ilość godzin");

		TreeMap<String, Double> projectsMap = new TreeMap<>();

		for (Employee employee : employees) {
			for (Task task : employee.getTaskList()) {
				Date date = task.getTaskDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.YEAR) == year) {
					String projectName = task.getProjectName();
					if (projectsMap.containsKey(projectName)) {
						projectsMap.replace(projectName, projectsMap.get(projectName) + task.getHours());
					} else {
						projectsMap.put(projectName, task.getHours());
					}
				}
			}
		}

		for (Map.Entry project : projectsMap.entrySet()) {
			List<String> newRow = new ArrayList<>();
			newRow.add(rowsCounter.toString());
			newRow.add(project.getKey().toString());
			newRow.add(project.getValue().toString());
			rows.add(newRow);
			rowsCounter++;
		}


		report.setColumnNames(columnNames);
		report.setRows(rows);
		return report;
	}

}
