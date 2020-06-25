package Report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Model.Employee;
import Model.Model;
import Model.Task;

public class Report4Builder implements ReportBuilder {

	private int year;

	public Report4Builder(int year) {
		super();
		this.year = year;
	}

	@Override
	public Report buildReport(Model model){
		
		Report report = new Report();
		List<Employee> modelEmployees = model.getEmployeeList();
		List<Employee> filteredEmployees = new ArrayList<Employee>();

		for (Employee employee : modelEmployees) {
			List<Task> filteredTasks = new ArrayList<Task>();
			for (Task task : employee.getTaskList()) {
				Date date = task.getTaskDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (calendar.get(Calendar.YEAR) == (Integer) this.year) {
					filteredTasks.add(task);
				}
			}
			if (filteredTasks.size() > 0) {
				Employee employeeCopy = (Employee) employee.clone();
				employeeCopy.setTaskList(filteredTasks);
				filteredEmployees.add(employeeCopy);
			}
		}
		
		report.setTitle("Procentowy udział danego pracownika w projekt za dany rok");
		
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
				
				percentHours = percentHours*100;
				percentHours = (double) Math.round(percentHours);
				percentHours = percentHours/100;
				
				
				rowToAdd.set(indexOfProject, percentHours.toString() + "%");
				if (!rowExists) {
					rows.add(rowToAdd);
				}
			}
		}

		report.setRows(rows);

		return report;
	}
}
