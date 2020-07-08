package Services.EmployeeFilters;

public class EmployeesFilterFactory {
	public static EmployeesFilter getEmployeesFilter(String type) {
		switch (type.toLowerCase()) {
		case "person":
			return new EmployeesFilterByPerson();
		case "project":
			return new EmployeesFilterByProjectName();
		default:
			return new EmployeesFilterByYear();
		}
	}
}
