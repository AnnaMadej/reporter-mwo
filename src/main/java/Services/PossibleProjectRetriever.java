package Services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Model.Employee;
import Model.Task;

public class PossibleProjectRetriever implements PossibleDataRetriever {

	@Override
	public Set<String> getPossibleData(List<Employee> employees) {
		Set<String> possibleData = new TreeSet<String>();

		for (Employee employee : employees) {
			for (Task task : employee.getTaskList()) {
				possibleData.add(task.getProjectName());
			}
		}
		return possibleData;
	}

}
