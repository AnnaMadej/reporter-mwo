package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Model.Employee;
import Model.Task;

public class PossiblePersonRetriever implements PossibleDataRetriever {

	@Override
	public Set<String> getPossibleData(List<Employee> employees) {
		Set<String> possibleData= new TreeSet<String>();
		

		for (Employee employee : employees) {
			possibleData.add(employee.getNameAndSurname());
		}
		return possibleData;
	}

}
