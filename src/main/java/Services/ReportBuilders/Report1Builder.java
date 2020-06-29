package Services.ReportBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;
import Services.EmployeeFilters.EmployeesFilter;
import Services.EmployeeFilters.EmployeesFilterByYear;
import Services.PossibleDataRetrievers.PossibleYearRetriever;


public class Report1Builder extends ReportBuilder {

	public Report1Builder() {
		super();
		this.addEmployeesFilter(new EmployeesFilterByYear());
	}
	


	@Override
	void setReportTitle() {
		report.setTitle("Sumaryczna liczba godzin za rok ");
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

	
	


}
