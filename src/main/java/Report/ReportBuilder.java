package Report;

import java.util.List;

import Model.Employee;

public interface ReportBuilder {
	public Report buildReport(List<Employee> employees);
}
