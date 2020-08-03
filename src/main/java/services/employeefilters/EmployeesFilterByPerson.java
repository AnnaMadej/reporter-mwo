package services.employeefilters;

import java.util.ArrayList;
import java.util.List;

import model.Employee;
import services.possibledataretrievers.PossiblePersonRetriever;

public class EmployeesFilterByPerson extends EmployeesFilter {

    public EmployeesFilterByPerson() {
        this.setFilterParameterName("imię i nazwisko");
        this.setPossibleDataRetriever(new PossiblePersonRetriever());
    }

    @Override
    public List<Employee> filterEmployees(List<Employee> employees) {
        if (this.getFilterParameter() != null) {
            List<Employee> filteredEmployees = new ArrayList<Employee>();

            for (Employee employee : employees) {
                if (employee.getNameAndSurname().toLowerCase()
                        .equals(this.getFilterParameter().toLowerCase())) {
                    filteredEmployees.add(employee);
                }
            }
            return filteredEmployees;
        }
        return employees;
    }

}
