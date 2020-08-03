package services.possibledataretrievers;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Employee;
import model.Task;

public class PossibleProjectRetriever implements PossibleDataRetriever {

    @Override
    public Set<String> getPossibleData(List<Employee> employees) {
        Set<String> possibleData = new TreeSet<String>();
        if (employees != null) {
            for (Employee employee : employees) {
                for (Task task : employee.getTaskList()) {
                    possibleData.add(task.getProjectName());
                }
            }
        }
        return possibleData;
    }

}
