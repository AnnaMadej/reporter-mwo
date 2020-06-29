package Services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;

public class EmployeesFilterByProjectName extends EmployeesFilter {
	
	public EmployeesFilterByProjectName() {
		this.filterParameterName = "nazwa projektu";
		this.possibleDataRetriever = new PossibleProjectRetriever();
	}

	public List<Employee> filterEmployees(List<Employee> employees){
		List<Model.Employee> filteredEmployees = new ArrayList<Employee>();

		for (Model.Employee employee : employees) {
			List<Task> filteredTasks = new ArrayList<Task>();
			for (Task task : employee.getTaskList()) {
				
				if (task.getProjectName().equals(this.filterParameter)) {
					filteredTasks.add(task);
				}
			}
			if (filteredTasks.size() > 0) {
				Employee employeeCopy = (Employee) employee.clone();
				employeeCopy.setTaskList(filteredTasks);
				filteredEmployees.add(employeeCopy);
			}
		}

		return filteredEmployees;
	};
	

}
