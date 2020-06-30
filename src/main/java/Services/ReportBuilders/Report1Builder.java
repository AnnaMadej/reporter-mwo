package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Services.EmployeeFilters.EmployeesFilterByYear;

public class Report1Builder extends ReportBuilder {

	public Report1Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByYear());
	}

	@Override
	void setReportCollumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("L.p");
		columnNames.add("Imię i nazwisko");
		columnNames.add("Liczba godzin");
		this.report.setColumnNames(columnNames);

	}

	@Override
	void setReportRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		Integer rowsCounter = 1;
		for (Employee employee : this.employees) {
			List<String> newRow = new ArrayList();
			newRow.add(rowsCounter.toString());
			newRow.add(employee.getNameAndSurname());
			newRow.add(String.valueOf(employee.getTotalHours()));
			rows.add(newRow);
			rowsCounter++;
		}
		this.report.setRows(rows);
	}

	@Override
	void setReportTitle() {
		this.report.setTitle("Raport godzin pracowników w roku " + this.filters.get(0).getFilterParameter());
	}

}
