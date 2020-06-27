package Report;

import java.util.List;

import Model.Employee;
import Model.Model;

public interface ReportBuilder {
	public Report buildReport(List<Employee> employees);
}
