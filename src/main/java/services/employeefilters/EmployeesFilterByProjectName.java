package services.employeefilters;

import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Task;
import services.possibledataretrievers.PossibleProjectRetriever;

public class EmployeesFilterByProjectName extends EmployeesFilter {

    public EmployeesFilterByProjectName() {
        this.setFilterParameterName("nazwa projektu");
        this.setPossibleDataRetriever(new PossibleProjectRetriever());
    }

    @Override
    public List<Employee> filterEmployees(List<Employee> employees) {

        if (this.getFilterParameter() == null) {
            return employees;
        }

        List<model.Employee> filteredEmployees = new ArrayList<Employee>();

        for (model.Employee employee : employees) {

            List<Task> filteredTasks = new ArrayList<Task>();
            for (Task task : employee.getTaskList()) {

                if (task.getProjectName().toLowerCase()
                        .equals(this.getFilterParameter().toLowerCase())) {
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

}
