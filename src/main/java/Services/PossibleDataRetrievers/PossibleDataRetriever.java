package Services.PossibleDataRetrievers;

import java.util.List;
import java.util.Set;

import Model.Employee;

public interface PossibleDataRetriever {

	public Set<String> getPossibleData(List<Employee> employees);

}
