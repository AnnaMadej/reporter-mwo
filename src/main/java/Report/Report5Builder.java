package Report;

import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Model.Task;
import services.PossibleProjectRetriever;
import services.PossibleYearRetriever;

public class Report5Builder extends ReportBuilder {

	public Report5Builder(List<Employee> employees) {
		super(employees);
		this.inputParamsNames.add("nazwa projektu");
		possibleDataRetriever = new PossibleProjectRetriever();
		this.possibleInputParams.add(possibleDataRetriever.getPossibleData(employees));
		
	}



	private String projectName;

	

	@Override
	public Report buildReport() {
		
		this.projectName = this.inputParams.get(0);
		Report report = new Report();

		report.setTitle("Raport ilości godzin pracowników w  projekcie: " + projectName);

		List<String> columnNames = new ArrayList<String>();

		columnNames.add("L.p.");
		columnNames.add("Imię i nazwisko");
		columnNames.add("Projekt");
		columnNames.add("Ilość godzin");

		report.setColumnNames(columnNames);

		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		for (Employee employee : employees) {
			for (Task task : employee.getTaskList()) {

				if (task.getProjectName().equals(projectName)) {

					Integer indexOfRowToChange = null;
					for (List<String> row : rows) {
						String employeeInRow = row.get(1);
						String projectInRow = row.get(2);

						if (employeeInRow.equals(employee.getNameAndSurname())
								&& projectInRow.equals(task.getProjectName())) {
							indexOfRowToChange = rows.indexOf(row);
						}
					}

					if (indexOfRowToChange != null) {
						List<String> rowToChange = rows.get(indexOfRowToChange);
						Double hoursToChange = Double.valueOf(rowToChange.get(3));
						Double newHours = hoursToChange + task.getHours();
						rowToChange.set(3, newHours.toString());
					} else {
						List<String> newRow = new ArrayList<String>();
						newRow.add(rowsCounter.toString());
						newRow.add(employee.getNameAndSurname());
						newRow.add(task.getProjectName());
						newRow.add(task.getHours().toString());
						rows.add(newRow);
						rowsCounter++;
					}

				}

			}
		}
		
		report.setRows(rows);
		return report;
	}
}
