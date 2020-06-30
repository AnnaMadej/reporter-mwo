package Services.EmployeeFilters;

import java.util.ArrayList;
import java.util.List;

import Model.Employee;
import Services.PossibleDataRetrievers.PossiblePersonRetriever;

public class EmployeesFilterByPerson extends EmployeesFilter {

	public EmployeesFilterByPerson() {
		this.filterParameterName = "imię i nazwisko";
		this.possibleDataRetriever = new PossiblePersonRetriever();
	}

	@Override
	public List<Employee> filterEmployees(List<Employee> employees) {
		List<Employee> filteredEmployees = new ArrayList<Employee>();

		for (Employee employee : employees) {
			if (employee.getNameAndSurname().equals(this.filterParameter)) {
				filteredEmployees.add(employee);
			}
		}
		return filteredEmployees;
	}

}
