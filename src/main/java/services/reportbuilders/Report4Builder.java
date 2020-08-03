package services.reportbuilders;

import java.util.ArrayList;
import java.util.List;

import model.Employee;
import services.chartmakers.Report4PieChartMaker;
import services.employeefilters.EmployeesFilterFactory;

public class Report4Builder extends ReportBuilder {

    public Report4Builder() {
        super();
        this.addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("year"));
        setReportChartMaker(new Report4PieChartMaker());
    }

    @Override
    protected void setReportCollumnNames() {
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("L.p");
        columnNames.add("Imię i nazwisko");
        this.report.setColumnNames(columnNames);

        for (Employee employee : this.employees) {
            for (String project : employee.getProjects()) {
                if (!columnNames.contains(project)) {
                    columnNames.add(project);
                }
            }
        }
    }

    @Override
    protected void setReportRows() {
        if (report.getColumnNames().size() == 0) {
            return;
        }
        List<List<String>> rows = new ArrayList<List<String>>();
        Integer rowsCounter = 1;

        for (Employee employee : this.employees) {
            Double totalHours = employee.getTotalHours();
            if (totalHours > 0) {

                for (String project : employee.getProjects()) {
                    List<String> rowToAdd = new ArrayList<String>();
                    for (int i = 0; i < this.report.getColumnNames().size(); i++) {
                        rowToAdd.add("0.0%");
                    }
                    String employeeName = employee.getNameAndSurname();

                    boolean rowExists = false;
                    for (List<String> row : rows) {
                        if (row.get(1).equals(employeeName)) {
                            rowToAdd = row;
                            rowExists = true;
                        }
                    }
                    if (!rowExists) {
                        rowToAdd.set(0, rowsCounter.toString());
                        rowsCounter++;
                        rowToAdd.set(1, employee.getNameAndSurname());
                    }

                    Double projectHours = employee.getProjectHours(project);
                    final int maxPercents = 100;
                    Double percentHours = projectHours * maxPercents / totalHours;

                    percentHours = percentHours * maxPercents;
                    percentHours = (double) Math.round(percentHours);
                    percentHours = percentHours / maxPercents;

                    Integer indexOfProject = this.report.getColumnNames().indexOf(project);
                    rowToAdd.set(indexOfProject, percentHours.toString() + "%");
                    if (!rowExists) {
                        rows.add(rowToAdd);
                    }
                }

            }
        }

        this.report.setRows(rows);
    }

    @Override
    protected void setReportTitle() {
        String title = "Procentowy udział projektów w pracy osób ";
        if (this.filters.size() > 0) {
            if (this.filters.get(0).getFilterParameter() != null) {
                title += "w roku: " + this.filters.get(0).getFilterParameter();
            }
        }

        this.report.setTitle(title);
    }

}
