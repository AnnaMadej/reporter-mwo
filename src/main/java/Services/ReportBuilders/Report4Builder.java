package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Services.ChartMakers.Report4PieChartMaker;
import Services.EmployeeFilters.EmployeesFilterByYear;

public class Report4Builder extends ReportBuilder {

	public Report4Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByYear());
		this.reportChartMaker = new Report4PieChartMaker();
	}

	@Override
	protected void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Imię i nazwisko");
		this.report.setColumnNames(columnNames);
		this.report.setColumnNames(columnNames);

		for (Employee employee : this.employees) {
			for (String project : employee.getProjects()) {
				if (!columnNames.contains(project)) {
					columnNames.add(project);
				}
			}
		}
	}

	@Override
	protected void setReportRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;

		for (Employee employee : this.employees) {
			Double totalHours = employee.getTotalHours();
			for (String project : employee.getProjects()) {
				List<String> rowToAdd = new ArrayList<String>();
				for (int i = 0; i < this.report.getColumnNames().size(); i++) {
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
				Integer indexOfProject = this.report.getColumnNames().indexOf(project);

				Double projectHours = employee.getProjectHours(project);
				Double percentHours = projectHours * 100 / totalHours;

				percentHours = percentHours * 100;
				percentHours = (double) Math.round(percentHours);
				percentHours = percentHours / 100;

				rowToAdd.set(indexOfProject, percentHours.toString() + "%");
				if (!rowExists) {
					rows.add(rowToAdd);
				}
			}
		}

		this.report.setRows(rows);
	}

	@Override
	protected void setReportTitle() {
		this.report.setTitle(
				"Procentowy udział projektów w pracy osob w roku: " + this.filters.get(0).getFilterParameter());
	}

}
