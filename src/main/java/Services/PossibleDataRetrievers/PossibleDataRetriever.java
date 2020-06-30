package Services.PossibleDataRetrievers;

import java.util.List;
import java.util.Set;

import Model.Employee;

public interface PossibleDataRetriever {

	Set<String> getPossibleData(List<Employee> employees);

}
