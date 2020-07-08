package services.reportbuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Employee;
import model.Task;
import services.chartmakers.Report2BarChartMaker;
import services.employeefilters.EmployeesFilterFactory;

public class Report2Builder extends ReportBuilder {

    public Report2Builder() {
        super();
        this.addEmployeesFilter(EmployeesFilterFactory.getEmployeesFilter("year"));

    }

    @Override
    protected void setReportCollumnNames() {

        List<String> columnNames = new ArrayList<String>();

        columnNames.add("L.p");
        columnNames.add("Projekt");
        columnNames.add("Ilość godzin");
        this.report.setColumnNames(columnNames);
        this.reportChartMaker = new Report2BarChartMaker();

    }

    @Override
    protected void setReportRows() {
        List<List<String>> rows = new ArrayList<List<String>>();
        Integer rowsCounter = 1;

        TreeMap<String, Double> projectsMap = new TreeMap<>();

        for (Employee employee : this.employees) {
            for (Task task : employee.getTaskList()) {
                String projectName = task.getProjectName();
                if (projectsMap.containsKey(projectName)) {
                    projectsMap.replace(projectName,
                            projectsMap.get(projectName) + task.getHours());
                } else {
                    projectsMap.put(projectName, task.getHours());
                }

            }
        }

        for (Map.Entry<String, Double> project : projectsMap.entrySet()) {
            List<String> newRow = new ArrayList<>();
            newRow.add(rowsCounter.toString());
            newRow.add(project.getKey().toString());
            newRow.add(project.getValue().toString());
            if (project.getValue() > 0.0) {
                rows.add(newRow);
                rowsCounter++;
            }

        }

        this.report.setRows(rows);
    }

    @Override
    protected void setReportTitle() {
        String title = "Raport godzin projektów ";
        if (this.filters.get(0).getFilterParameter() != null) {
            title += "w roku: " + this.filters.get(0).getFilterParameter();
        }
        this.report.setTitle(title);
    }

}
