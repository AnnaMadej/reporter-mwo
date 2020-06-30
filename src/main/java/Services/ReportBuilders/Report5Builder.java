package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Model.Task;
import Services.EmployeeFilters.EmployeesFilterByProjectName;

public class Report5Builder extends ReportBuilder {

	public Report5Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByProjectName());
	}

	@Override
	protected void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();

		columnNames.add("L.p");
		columnNames.add("Imię i nazwisko");
		columnNames.add("Projekt");
		columnNames.add("Ilość godzin");

		this.report.setColumnNames(columnNames);
	}

	@Override
	protected void setReportRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		for (Employee employee : this.employees) {
			for (Task task : employee.getTaskList()) {

				Integer indexOfRowToChange = null;
				for (List<String> row : rows) {
					String employeeInRow = row.get(1);
					row.get(2);

					if (employeeInRow.equals(employee.getNameAndSurname())) {
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
		this.report.setRows(rows);
	}

	@Override
	protected void setReportTitle() {
		this.report.setTitle("Raport ilości przepracowanych godzin pracowników projekcie: "
				+ this.filters.get(0).getFilterParameter());
	}

}
