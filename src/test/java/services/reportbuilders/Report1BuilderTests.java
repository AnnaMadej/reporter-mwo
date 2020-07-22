package services.reportbuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Employee;
import model.Report;
import model.Task;
import services.reportbuilders.Report1Builder;
import services.reportbuilders.ReportBuilder;

public class Report1BuilderTests {

        private ReportBuilder reportBuilder;
        private List<Employee> employees = new ArrayList<Employee>();
        private Employee employee1;
        private Employee employee2;

        @Before
        public void init() {
                this.reportBuilder = new Report1Builder();
                this.employee1 = new Employee("Adam", "Nowak");
                this.employee2 = new Employee("Zofia", "Dzięgiel");
                Date date1 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
                Task task1 = new Task(date1, "projekt1", "task1", 1);
                Date date2 = new GregorianCalendar(2012, Calendar.JULY, 24).getTime();
                Task task2 = new Task(date2, "projekt1", "task1", 7);
                this.employee1.addTask(task1);
                this.employee2.addTask(task2);
        }

        @Test
        public final void testAsManyRowsAsEmployees() {
                this.employees.add(this.employee1);
                this.employees.add(this.employee2);
                this.reportBuilder.setEmployees(this.employees);
                Report report = this.reportBuilder.buildReport();
                Assert.assertEquals(report.getRows().size(), 2);

        }

        @Test
        public final void testCorrectReportTitle() {
                this.employees.add(this.employee1);
                this.employees.add(this.employee2);
                this.reportBuilder.setEmployees(this.employees);
                this.reportBuilder.setInputParam(0, "2012");
                Report report = this.reportBuilder.buildReport();
                Assert.assertEquals("Raport godzin pracowników w roku: 2012", report.getTitle());
        }

        @Test
        public final void testCountsHoursCorrectly() {
                Date date3 = new GregorianCalendar(2012, Calendar.JUNE, 28).getTime();
                Task task3 = new Task(date3, "projekt3", "task3", 2);
                this.employee1.addTask(task3);

                Date date4 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
                Task task4 = new Task(date4, "projekt3", "task3", 11);
                this.employee2.addTask(task4);
                this.employees.add(this.employee1);
                this.employees.add(this.employee2);

                this.reportBuilder.setEmployees(this.employees);
                Report report = this.reportBuilder.buildReport();
                Assert.assertTrue(
                                report.getRows().stream().anyMatch(r -> (r.get(1).equals("Adam Nowak") && r.get(2).equals("3.0"))));
                Assert.assertTrue(report.getRows().stream()
                                .anyMatch(r -> (r.get(1).equals("Zofia Dzięgiel") && r.get(2).equals("18.0"))));
        }

        @Test
        public final void testEmptyReportIfEmployeeWithNoTasks() {
                ReportBuilder builder = new Report1Builder();
                Employee employee5 = new Employee("Adam", "Nowak");
                List<Employee> employees3 = new ArrayList<Employee>();
                employees3.add(employee5);
                this.reportBuilder.setEmployees(employees3);
                Report report = builder.buildReport();
                Assert.assertEquals(0, report.getRows().size());
        }

        @Test
        public final void testEmptyReportIfNoEmployeesAdded() {
                ReportBuilder builder = new Report1Builder();
                Report report = builder.buildReport();
                Assert.assertEquals(0, report.getRows().size());
        }

        @Test
        public final void testNoRowsIfFilterParamNotExists() {
                this.employees.add(this.employee1);
                this.employees.add(this.employee2);
                this.reportBuilder.setEmployees(this.employees);
                this.reportBuilder.setInputParam(0, "2011");
                Report report = this.reportBuilder.buildReport();
                Assert.assertEquals(0, report.getRows().size());
        }

        @Test
        public final void testNoRowsIfZeroHours() {

                Employee employee3 = new Employee("Zdzisław", "Żur");
                List<Employee> employees2 = new ArrayList<Employee>();
                Date date5 = new GregorianCalendar(2012, Calendar.JUNE, 25).getTime();
                Task task5 = new Task(date5, "projekt1", "task1", 0);
                employee3.addTask(task5);
                employees2.add(employee3);

                this.reportBuilder.setEmployees(employees2);
                this.reportBuilder.setInputParam(0, "2012");
                Report report = this.reportBuilder.buildReport();
                Assert.assertEquals(0, report.getRows().size());
        }

        @Test
        public final void testNoYearInTitleIfNoFilterParamSet() {
                this.employees.add(this.employee1);
                this.employees.add(this.employee2);
                this.reportBuilder.setEmployees(this.employees);
                Report report = this.reportBuilder.buildReport();
                Assert.assertEquals("Raport godzin pracowników ", report.getTitle());
        }

        @Test
        public final void testProperColumnNames() {
                this.reportBuilder.setEmployees(this.employees);
                this.reportBuilder.setInputParam(0, "2012");
                Report report = this.reportBuilder.buildReport();

                List<String> properColumnNames = new ArrayList<String>();
                properColumnNames.add("L.p");
                properColumnNames.add("Imię i nazwisko");
                properColumnNames.add("Liczba godzin");
                Assert.assertEquals(properColumnNames, report.getColumnNames());
        }
}