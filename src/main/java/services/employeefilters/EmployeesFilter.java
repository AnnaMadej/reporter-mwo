package services.employeefilters;

import java.util.List;
import java.util.Set;
import model.Employee;
import services.possibledataretrievers.PossibleDataRetriever;

public abstract class EmployeesFilter {
    protected PossibleDataRetriever possibleDataRetriever;
    protected String filterParameterName;
    protected String filterParameter;

    public abstract List<Employee> filterEmployees(List<Employee> employees);

    public String getFilterParameter() {
        return this.filterParameter;
    }

    public String getFilterParameterName() {
        return this.filterParameterName;
    }

    public Set<String> getPossibleData(List<Employee> employees) {
        return this.possibleDataRetriever.getPossibleData(employees);
    }

    public void setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
    }

}
