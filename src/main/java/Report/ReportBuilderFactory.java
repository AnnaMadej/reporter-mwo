package Report;

import java.util.List;

import Model.Employee;

public class ReportBuilderFactory {
	public static ReportBuilder getReportBuilder(String number, List<Employee> employees) {
		switch (number) {
		case "1":
			return new Report1Builder(employees);
		case "2":
			return new Report2Builder(employees);

		case "3":
			return new Report3Builder(employees);

		case "4":
			return new Report4Builder(employees);

		case "5":
			return new Report5Builder(employees);

		default:
			return null;
		}

	}

}
