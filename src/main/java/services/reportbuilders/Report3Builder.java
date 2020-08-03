package services.reportbuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.Task;
import services.employeefilters.EmployeesFilter;
import services.employeefilters.EmployeesFilterFactory;

public class Report3Builder extends ReportBuilder {

    public Report3Builder() {
        super();
        addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("year"));
        addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("person"));
    }

    private Double countHoursSum(List<Task> tasks, int monthIndex, String project) {
        Double hoursSum = 0.0;
        for (final Task task : tasks) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getTaskDate());
            if (task.getProjectName().equals(project)
                    && calendar.get(Calendar.MONTH) == monthIndex) {
                hoursSum += task.getHours();
            }
        }
        return hoursSum;
    }

    @Override
    protected void setReportCollumnNames() {
        final List<String> columnNames = new ArrayList<String>();
        columnNames.add("L.p");
        columnNames.add("Miesiąc");
        columnNames.add("Projekt");
        columnNames.add("Liczba godzin");
        this.report.setColumnNames(columnNames);
    }

    @Override
    protected void setReportRows() {

        final List<List<String>> rows = new ArrayList<List<String>>();
        Integer rowsCounter = 1;

        final String[] polishMonths = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj",
        "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Pażdziernik", "Listopad",
        "Grudzień" };

        if (this.employees.size() > 0) {

            final Set<String> projects = new TreeSet<String>();
            final List<Task> tasks = new ArrayList<Task>();

            this.employees.stream().forEach(emp -> {
                projects.addAll(emp.getProjects());
                tasks.addAll(emp.getTaskList());
            });

            final int numberOfMonths = 12;
            for (int monthIndex = 0; monthIndex < numberOfMonths; monthIndex++) {
                for (final String project : projects) {
                    final Double hoursSum = countHoursSum(tasks, monthIndex, project);
                    if (hoursSum != 0.0) {
                        final List<String> newRow = new ArrayList<String>();
                        newRow.add(rowsCounter.toString());
                        newRow.add(polishMonths[monthIndex]);
                        newRow.add(project);
                        newRow.add(hoursSum.toString());
                        rows.add(newRow);
                        rowsCounter++;
                    }
                }
            }

        }
        this.report.setRows(rows);
    }

    @Override
    protected void setReportTitle() {
        String title = "Raport godzin przepracowanych miesięcznie";

        if (this.filters.size() > 0) {
            if (this.filters.size() > 1) {
                final EmployeesFilter filter2 = this.filters.get(1);
                if (filter2.getFilterParameter() != null) {
                    title += " przez: " + this.filters.get(1).getFilterParameter();
                }
            }
            final EmployeesFilter filter1 = this.filters.get(0);
            if (filter1.getFilterParameter() != null) {
                title += ", w roku: " + this.filters.get(0).getFilterParameter();
            }
        }

        this.report.setTitle(title);
    }

}
