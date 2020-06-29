package Services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;

public class EmployeesFilterByPerson extends EmployeesFilter {
	
	public EmployeesFilterByPerson() {
		this.filterParameterName = "imię i nazwisko";
		this.possibleDataRetriever = new PossiblePersonRetriever();
	}

	public List<Employee> filterEmployees(List<Employee> employees){
		List<Employee> filteredEmployees = new ArrayList<Employee>();

		for (Employee employee : employees) {
			if(employee.getNameAndSurname().equals(filterParameter)){
				filteredEmployees.add(employee);
			}
		}
		return filteredEmployees;
	};
	

}
