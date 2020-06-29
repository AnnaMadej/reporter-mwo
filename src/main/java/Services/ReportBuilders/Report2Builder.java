package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Model.Employee;
import Model.Report;
import Model.Task;
import Services.PossibleYearRetriever;


public class Report2Builder extends ReportBuilder {

	public Report2Builder() {
		super();
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
				if (calendar.get(Calendar.YEAR) == Integer.parseInt(inputParams.get(0))) {
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
		Report report = new Report();
		report.setTitle("Raport listy projektów za podany rok " + inputParams.get(0));
	}

	@Override
	void setReportCollumnNames() {

		List<String> columnNames = new ArrayList<String>();

		columnNames.add("L.p");
		columnNames.add("Projekt");
		columnNames.add("Ilość godzin");
		report.setColumnNames(columnNames);

	}

	@Override
	void setReportRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		TreeMap<String, Double> projectsMap = new TreeMap<>();

		for (Employee employee : employees) {
			for (Task task : employee.getTaskList()) {
				String projectName = task.getProjectName();
				if (projectsMap.containsKey(projectName)) {
					projectsMap.replace(projectName, projectsMap.get(projectName) + task.getHours());
				} else {
					projectsMap.put(projectName, task.getHours());
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

		report.setRows(rows);
	}
	
	@Override
	public void retrievePossibleInputData() {
		possibleDataRetriever = new PossibleYearRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));

	}

}
