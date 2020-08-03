package services.possibledataretrievers;

import java.util.List;
import java.util.Set;

import model.Employee;

public interface PossibleDataRetriever {

    Set<String> getPossibleData(List<Employee> employees);

}
