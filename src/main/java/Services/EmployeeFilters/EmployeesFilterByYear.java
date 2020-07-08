package Services.EmployeeFilters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Task;
import Services.PossibleDataRetrievers.PossibleYearRetriever;

public class EmployeesFilterByYear extends EmployeesFilter {

	public EmployeesFilterByYear() {
		this.filterParameterName = "rok";
		this.possibleDataRetriever = new PossibleYearRetriever();
	}

	@Override
	public List<Employee> filterEmployees(List<Employee> employees) {
		List<Model.Employee> filteredEmployees = new ArrayList<Employee>();
		if (this.filterParameter != null) {
			for (Model.Employee employee : employees) {

				List<Task> filteredTasks = new ArrayList<Task>();
				for (Task task : employee.getTaskList()) {
					Date date = task.getTaskDate();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					if (calendar.get(Calendar.YEAR) == Integer.parseInt(this.filterParameter)) {
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
		}
		return employees;
	}

}
