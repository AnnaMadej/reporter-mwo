package App;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Model.*;
import Reader.FilesScanner;
import Reader.ScanErrorsHolder;
import Report.*;


public class UserControl {

    private Scanner sc = new Scanner(System.in);
    private String userOption;
    private String path;
    private ReportBuilder reportBuilder;
    private Report report;
    private ReportXlsExporter reportToXls;

    private List<Employee> employeeList = new ArrayList<Employee>();
    
    public UserControl(String path) {
        this.path = path;
        reportToXls = new ReportXlsExporter();
        
        try {
            FilesScanner fileScanner = new FilesScanner();
            employeeList = fileScanner.scanFiles(path);
        } catch (IOException e){
            System.err.println("Nie znaleziono pliku");
        } catch ( InvalidFormatException e ){
            System.err.println("Bład odczytu pliku");
        }
    }

    public void controlLoop() {

        do {
            clearConsole();
            appHeaders();
            printOptions();
            String userOption = inputUserOption();
            switch (userOption) {
                case "1":
                    generateReport1();
                    break;
                case "2":
                    generateReport2();
                    break;
                case "3":
                    generateReport3();
                    break;
                case "4":
                    generateReport4();
                    break;
                case "5":
                    generateReport5();
                    break;
                case "6":
                    generateReport6();
                    break;
                case "7":
                    generateReport7();
                    break;
                case "8":
                    generateErrorsLog();
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.err.println("Nie znam takiej opcji");
            }

            if (!userOption.equals("0")) {
                System.out.println("Naciśnij Enter aby kontynuować...");
                String pause = sc.nextLine();
            }

        } while (!userOption.equals("0"));

    }

    public String generateOption(){
        StringBuilder option = new StringBuilder();

        option.append("WYBIERZ OPCJE:\n");
        option.append("1. Generuj raport godzin pracowników w podanym roku\n");
        option.append("2. Generuj raport godzin projektów w podanym roku\n");
        option.append("3. Generuj raport godzin przepracowanych miesięcznie przez pracownika w podanym roku\n");
        option.append("4. Generuj procentowy udział projektów w pracy osob dla podanego roku\n");
        option.append("5. Generuj raport ilości godzin pracowników w podanym projekcie\n");
        option.append("6. Wyświetl wykres słupkowy godzin projektów w podanym roku\n");
        option.append("7. Wyświetl wykres kołowy procentowego udziału projektów dla pracowników w podanym roku\n");
        option.append("8. Pokaż logi z odczytu pliku\n");
        option.append("0. Zakończ pracę z programem\n");

        return option.toString();
    }

    private void generateReport1() {
        try {
            List<String> dateList = dateRangeGenerator();
            dateRangePrinter(dateList);
            Integer reportYear;
            printAskForYear();
            reportYear = sc.nextInt();
            sc.nextLine();
            String year = reportYear.toString();
            if (dateList.contains(year)){
                reportBuilder = new Report1Builder(reportYear);
                report = reportBuilder.buildReport(employeeList);
                ReportPrinter.printReport(report);
                saveReportToFile(report);
            } else {
                printErrorYear();
            }

        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }
    }

    private void generateReport2() {
        try {
            List<String> dateList = dateRangeGenerator();
            dateRangePrinter(dateList);
            printAskForYear();
            Integer reportYear = sc.nextInt();
            sc.nextLine();
            String year = reportYear.toString();
            if (dateList.contains(year)) {
                System.out.println();
                reportBuilder = new Report2Builder(reportYear);
                report = reportBuilder.buildReport(employeeList);
                ReportPrinter.printReport(report);
                saveReportToFile(report);
                System.out.println();
            } else {
                printErrorYear();
            }
        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }

    }

    private void generateReport3() {
        try {
            List<String> strings = employeeRangeGenerator();
            employeeRangePrinter(strings);
            printAskForEmployee();
            String name = sc.nextLine();
            if (strings.contains(name)) {
                List<String> dateList = dateRangeGenerator();
                dateRangePrinter(dateList);
                printAskForYear();
                Integer reportYear = sc.nextInt();
                sc.nextLine();
                String year = reportYear.toString();
                if (dateList.contains(year)) {
                    System.out.println();
                    reportBuilder = new Report3Builder(reportYear, name);
                    report = reportBuilder.buildReport(employeeList);
                    ReportPrinter.printReport(report);
                    saveReportToFile(report);
                    System.out.println();
                } else {
                    printErrorYear();
                }
            } else {
                printErrorIncorrectNameSurname();
            }
        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }
    }

    private void generateReport4() {
        try {
            List<String> dateList = dateRangeGenerator();
            dateRangePrinter(dateList);
            printAskForYear();
            Integer reportYear = sc.nextInt();
            sc.nextLine();
            String year = reportYear.toString();
            if (dateList.contains(year)) {
                reportBuilder = new Report4Builder(reportYear);
                report = reportBuilder.buildReport(employeeList);
                ReportPrinter.printReport(report);
                saveReportToFile(report);
                System.out.println();
            } else {
                printErrorYear();
            }
        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }
    }

    private void generateReport5() {
        List<String> projectsList = projectsRangeGenerator();
        projectRangePrinter(projectsList);
        printAskForProjectName();
        String projectName = sc.nextLine();
        if (projectsList.contains(projectName)) {
            reportBuilder = new Report5Builder(projectName);
            report = reportBuilder.buildReport(employeeList);
            ReportPrinter.printReport(report);
            saveReportToFile(report);
            System.out.println();
        } else {
            printErrorIncorrectProject();
        }
    }

    private void generateReport6() {
        try {
            List<String> dateList = dateRangeGenerator();
            dateRangePrinter(dateList);
            Integer reportYear;
            printAskForYear();
            reportYear = sc.nextInt();
            sc.nextLine();
            String year = reportYear.toString();
            if (dateList.contains(year)) {
                System.out.println();
                reportBuilder = new Report2Builder(reportYear);
                report = reportBuilder.buildReport(employeeList);
                Report6Builder barChartReport = new Report6Builder();
                barChartReport.plotBarChart(report, reportYear);
                System.out.println();
            } else {
                printErrorYear();
            }
        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }
    }

    private void generateReport7() {
        try {
            List<String> empList = employeeRangeGenerator();
            employeeRangePrinter(empList);
            printAskForEmployee();
            String name = sc.nextLine();
            if (empList.contains(name)) {
                List<String> dateList = dateRangeGenerator();
                dateRangePrinter(dateList);
                printAskForYear();
                Integer reportYear = sc.nextInt();
                sc.nextLine();
                String year = reportYear.toString();
                if (dateList.contains(year)) {
                    reportBuilder = new Report4Builder(reportYear);
                    report = reportBuilder.buildReport(employeeList);
                    Report7Builder report7 = new Report7Builder();
                    report7.plotChart(report, name, reportYear);
                    System.out.println();
                } else {
                    printErrorYear();
                }
            } else {
                printErrorIncorrectNameSurname();
            }
        } catch (InputMismatchException e) {
            printErrorIncorrectData();
        }
    }

    private void generateErrorsLog() {
        ScanErrorsHolder.printScanErrors();
        System.out.println();
    }

    private void saveReportToFile(Report report) {
        System.out.println("\nCzy chcesz zapisać raport do pliku T / N ?");
        try {
            String writeReportOpt = sc.nextLine();
            switch (writeReportOpt.toLowerCase()) {
                case "t": {

                    File generatedReport = reportToXls.exportToXls(report);
                    String reportPath = generatedReport.getCanonicalPath();
                    System.out.println("Poprawnie wygenerowano raport do pliku: " + reportPath);
                    System.out.println("\nCzy chcesz otworzyć plik xls? T / N ?");
                    String showXlsOpt = sc.nextLine();
                    switch (showXlsOpt.toLowerCase()) {
                        case "t": {
                            openGeneratedFile(generatedReport);
                        }
                        default:
                            break;
                    }
                    break;
                }
                default: {
                    System.out.println("Zrezygnowano z zapisu pliku");
                }
            }
        } catch (IOException e) {
            printErrorWriteFile();
        }
    }

    private void openGeneratedFile(File generatedReport) throws IOException {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (generatedReport.exists()) {
                desktop.open(generatedReport);
            }
        } catch (UnsupportedOperationException e) {
        }
    }

    private List<String> dateRangeGenerator() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        List<String> yearProject = new ArrayList<>();
        for (Employee employee : employeeList) {
            List<Task> taskList = employee.getTaskList();
            for (Task task : taskList) {
                Date allDates = task.getTaskDate();
                String year = simpleDateFormat.format(allDates);
                if (!yearProject.contains(year)) {
                    yearProject.add(year);
                }
            }
        }
        Collections.sort(yearProject);
        return yearProject;
    }

    private List<String> employeeRangeGenerator() {
        List<String> employeeList = new ArrayList<>();
        List<Employee> allEmployeeData = this.employeeList;
        for (Employee employee : allEmployeeData) {
            String nameAndSurname = employee.getNameAndSurname();
            employeeList.add(nameAndSurname);
        }
        return employeeList;
    }

    private List<String> projectsRangeGenerator() {
        List<String> projects = new ArrayList<>();
        List<Employee> allEmployeeData =  this.employeeList;
        for (Employee employee : allEmployeeData) {
            Set<String> allProjects = employee.getProjects();
            for (String oneProject : allProjects) {
                if (!projects.contains(oneProject)) {
                    projects.add(oneProject);
                }
            }
        }
        return projects;
    }

    private void dateRangePrinter(List<String> datesList) {
        System.out.println("\nRaporty są dostępne za lata: " + datesList + "\n");
    }

    private void employeeRangePrinter(List<String> employeesList) {
        System.out.println("\nRaporty są dostępne dla pracowników: " + employeesList + "\n");
    }

    private void projectRangePrinter(List<String> projectsList) {
        System.out.println("\nRaporty są dostępne dla projektów: " + projectsList + "\n");
    }


    private void appHeaders() {
        System.out.println("______                _____  _                    _____                                \n"
                + "| ___ \\              |_   _|(_)                  |_   _|                               \n"
                + "| |_/ / _   _  _ __    | |   _  _ __ ___    ___    | |    ___  _ __  _ __   ___   _ __ \n"
                + "|    / | | | || '_ \\   | |  | || '_ ` _ \\  / _ \\   | |   / _ \\| '__|| '__| / _ \\ | '__|\n"
                + "| |\\ \\ | |_| || | | |  | |  | || | | | | ||  __/   | |  |  __/| |   | |   | (_) || |   \n"
                + "\\_| \\_| \\__,_||_| |_|  \\_/  |_||_| |_| |_| \\___|   \\_/   \\___||_|   |_|    \\___/ |_|   \n"
                + "                                                                                       \nversion 1.0.0");
        System.out.println("----------------------------\n");
    }

    public String inputUserOption() {
        System.out.println("\n______________________");
        System.out.print("Wprowadź wybraną opcję:");
        userOption = sc.nextLine();
        return userOption;
    }

    private void exit() {
        System.out.println("Copyright © 2020 RunTime Terror, All Rights Reserved. ");
        sc.close();
    }

    public void printOptions() {
        String option = generateOption();
        System.out.println(option);
    }

    private void printAskForYear(){
        System.out.println("Podaj za jaki rok mam wygenerować raport");
    }

    private void printAskForEmployee(){
        System.out.println("Podaj imię i nazwisko pracownika");
    }

    private void printAskForProjectName(){
        System.out.println("Podaj nazwę projektu:");
    }


    private void printErrorYear(){
        System.out.println("Wpisałeś rok który nie znajduje się na liście");
    }

    private void printErrorIncorrectData(){
        System.err.println("Wprowadziłeś błędne dane");
    }

    private void printErrorIncorrectNameSurname(){
        System.out.println("Wprowadziłeś błędne Imię i Nazwisko");
    }

    private void printErrorIncorrectProject(){
        System.out.println("Wprowadziłeś błędne Imię i Nazwisko");
    }

    private void printErrorWriteFile(){
        System.err.println("Nie udało się zapisać pliku");
    }

    public void clearConsole() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }
}
