package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;
import Services.PossibleYearRetriever;


public class Report1Builder extends ReportBuilder {

	public Report1Builder() {
		super();
		this.inputParamsNames.add("rok");
	}
	
	@Override
	void filterEmployees() {

		List<Model.Employee> filteredEmployees = new ArrayList<Employee>();

		for (Model.Employee employee : employees) {
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
		Integer year = Integer.parseInt(this.inputParams.get(0));
		report.setTitle("Sumaryczna liczba godzin za rok " + String.valueOf(year));
	}

	@Override
	void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Imię i nazwisko");
		columnNames.add("Liczba godzin");
		report.setColumnNames(columnNames);

	}

	@Override
	void setReportRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;
		for (Employee employee : employees) {
			List<String> newRow = new ArrayList();
			newRow.add(rowsCounter.toString());
			newRow.add(employee.getNameAndSurname());
			newRow.add(String.valueOf(employee.getTotalHours()));
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
