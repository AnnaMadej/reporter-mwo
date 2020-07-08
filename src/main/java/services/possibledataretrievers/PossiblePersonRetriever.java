package services.possibledataretrievers;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import model.Employee;

public class PossiblePersonRetriever implements PossibleDataRetriever {

    @Override
    public Set<String> getPossibleData(List<Employee> employees) {
        Set<String> possibleData = new TreeSet<String>();

        for (Employee employee : employees) {
            possibleData.add(employee.getNameAndSurname());
        }
        return possibleData;
    }

}
