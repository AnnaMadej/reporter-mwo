package services.reportbuilders;

import java.util.ArrayList;
import java.util.List;
import model.Employee;
import model.Task;
import services.employeefilters.EmployeesFilter;
import services.employeefilters.EmployeesFilterFactory;

public class Report5Builder extends ReportBuilder {

    public Report5Builder() {
        super();
        this.addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("project"));
    }

    @Override
    protected void setReportCollumnNames() {
        List<String> columnNames = new ArrayList<String>();

        columnNames.add("L.p");
        columnNames.add("Imię i nazwisko");
        columnNames.add("Projekt");
        columnNames.add("Ilość godzin");

        this.report.setColumnNames(columnNames);
    }

    @Override
    protected void setReportRows() {
        List<List<String>> rows = new ArrayList<List<String>>();
        Integer rowsCounter = 1;

        for (Employee employee : this.employees) {
            for (Task task : employee.getTaskList()) {

                Integer indexOfRowToChange = null;
                for (List<String> row : rows) {
                    String employeeInRow = row.get(1);
                    String projectInRow = row.get(2);

                    if (employeeInRow.equals(employee.getNameAndSurname())
                            && projectInRow.equals(task.getProjectName())) {
                        indexOfRowToChange = rows.indexOf(row);
                    }
                }

                if (indexOfRowToChange != null) {
                    List<String> rowToChange = rows.get(indexOfRowToChange);
                    final int hoursCellIndex = 3;
                    Double hoursToChange = Double.valueOf(rowToChange.get(hoursCellIndex));
                    Double newHours = hoursToChange + task.getHours();
                    rowToChange.set(hoursCellIndex, newHours.toString());
                } else {
                    if (task.getHours() > 0) {
                        List<String> newRow = new ArrayList<String>();
                        newRow.add(rowsCounter.toString());
                        newRow.add(employee.getNameAndSurname());
                        newRow.add(task.getProjectName());
                        newRow.add(task.getHours().toString());
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
        String title = "Raport ilości przepracowanych godzin pracowników ";
        
        if (this.filters.size() > 0) {
            EmployeesFilter filter = this.filters.get(0);
            if (filter.getFilterParameter() != null) {
                title += "w projekcie: " + filter.getFilterParameter();
            }
        }
        
        this.report.setTitle(title);
    }

}
