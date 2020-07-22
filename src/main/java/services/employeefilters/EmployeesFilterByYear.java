package services.employeefilters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Employee;
import model.Task;
import services.possibledataretrievers.PossibleYearRetriever;

public class EmployeesFilterByYear extends EmployeesFilter {

    public EmployeesFilterByYear() {
        this.setFilterParameterName("rok");
        this.setPossibleDataRetriever(new PossibleYearRetriever());
    }

    @Override
    public List<Employee> filterEmployees(List<Employee> employees) {
        List<model.Employee> filteredEmployees = new ArrayList<Employee>();
        if (this.getFilterParameter() != null) {
            for (model.Employee employee : employees) {

                List<Task> filteredTasks = new ArrayList<Task>();
                for (Task task : employee.getTaskList()) {
                    Date date = task.getTaskDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    if (calendar.get(Calendar.YEAR) == Integer
                            .parseInt(this.getFilterParameter())) {
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
