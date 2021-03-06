package services.possibledataretrievers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Employee;
import model.Task;

public class PossibleYearRetriever implements PossibleDataRetriever {

    @Override
    public Set<String> getPossibleData(List<Employee> employees) {
        Set<String> possibleData = new TreeSet<String>();

        if (employees != null) {
            Calendar calendar = new GregorianCalendar();
            for (Employee employee : employees) {
                for (Task task : employee.getTaskList()) {
                    Date date = task.getTaskDate();
                    calendar.setTime(date);
                    possibleData.add(String.valueOf(calendar.get(Calendar.YEAR)));
                }
            }
        }

        return possibleData;
    }

}
