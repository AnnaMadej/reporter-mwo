package view;

import controller.Controller;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import services.ReadErrorsHolder;

public class UserInterface {

    private Scanner sc = new Scanner(System.in); 
    private Controller controller = new Controller();
    private List<String> reportOptions = Arrays
            .asList(new String[] { "1", "2", "3", "4", "5" });
    private List<String> chartOptions = Arrays.asList(new String[] { "6", "7" });

    private Set<String> possibleInputParams = new TreeSet<String>();

    public UserInterface(String path) {
        try {
            this.controller.readEmployeesData(path);
        } catch (IOException | InvalidFormatException e) {
            System.out.println("Błąd odczytu plików! \n"
                    + "Sprawdz czy nie są uszkodzone lub otwarte w innym programie.");
        } catch (IllegalArgumentException e) {
            System.out.println("Podałeś nieprawidłową ścieżkę do katalogów z raportami!");
        }
    }

    private String askForOption() {
        StringBuilder option = new StringBuilder();

        option.append("WYBIERZ OPCJE:\n");
        option.append("1. Generuj raport godzin pracowników w podanym roku\n");
        option.append("2. Generuj raport godzin projektów w podanym roku\n");
        option.append("3. Generuj raport godzin przepracowanych miesięcznie "
                + "przez pracownika w podanym roku\n");
        option.append(
                "4. Generuj procentowy udział projektów w pracy osob dla podanego roku\n");
        option.append("5. Generuj raport ilości godzin pracowników w podanym projekcie\n");
        option.append("6. Wyświetl wykres słupkowy godzin projektów w podanym roku\n");
        option.append("7. Wyświetl wykres kołowy procentowego udziału "
                + "projektów dla pracowników w podanym roku\n");
        option.append("8. Pokaż logi z odczytu plików\n");
        option.append("0. Zakończ pracę z programem\n");

        return option.toString();
    }

    private String askForParam(String inputParamsName) {
        StringBuilder question = new StringBuilder();
        question.append("Podaj parametr: " + inputParamsName + "\n");
        question.append("Możliwe wartości: \n");
        for (String possibleParam : this.possibleInputParams) {
            question.append("\t\t\t [ " + possibleParam + " ]  \n");
        }
        question.append("\n");
        return question.toString();
    }

    private String askForXlsCreation() {
        return "Czy chcesz wyeksportować raport do pliku xls? [t/n]";
    }

    private String askForXlsOpening() {
        return "Czy chcesz otworzyć plik xls? [t/n]";
    }

    private void exit() {
        System.out.println("Copyright © 2020 RunTime Terror, All Rights Reserved. ");
        this.sc.close();
    }

    private void exportToXls() {
        String answer;
        String filePath = "";
        try {
            filePath = this.controller.exportReport();
        } catch (IOException e) {
            System.out.println("Nie udało się wyeksportowac pliku xls!");
        }
        System.out.println("Plik poprawnie wyeksportowany: " + filePath);
        answer = this.takeUserInput(this.askForXlsOpening());
        if (answer.toLowerCase().equals("t")) {
            try {
                this.controller.openReport();
            } catch (IOException e) {
                System.out.println("Nie udało się otworzyc pliku xls!");
            }
        }
    }

    private boolean filterData() {
        boolean reportReady = false;

        int numberOfFilters = this.controller.getNumberOfFilters();
        for (int filterIndex = 0; filterIndex < numberOfFilters; filterIndex++) {
            String inputParamName = this.controller.getInputParamName(filterIndex);
            this.possibleInputParams = this.controller.getPossibleFilterData(filterIndex);
            String question = this.askForParam(inputParamName);
            String inputParam = this.takeUserInput(question);
            if (this.possibleInputParams.stream()
                    .anyMatch(param -> param.toLowerCase().equals(inputParam.toLowerCase()))) {
                this.controller.addFilterParam(filterIndex, inputParam);
                reportReady = true;
            } else {
                System.out.println("Nie podałeś parametru z wyświetlonej listy!");
                reportReady = false;
                break;
            }
        }
        return reportReady;
    }

    private void showChart(String userOption) {
        if (userOption.equals("6")) {
            userOption = "2";
        }
        if (userOption.equals("7")) {
            userOption = "4";
        }
        this.controller.setReportType(userOption);
        boolean reportReady = this.filterData();
        if (reportReady) {
            this.controller.buildReport();
            this.controller.showChart();
        }
    }

    private void showErrorLogs() {
        System.out.println(controller.showScanErrors());
    }

    private void showHeaders() {
        System.out.println("______                _____  _             "
                + "       _____                                \n"
                + "| ___ \\              |_   _|(_)       "
                + "           |_   _|                       " + "        \n"
                + "| |_/ / _   _  _ __    | |   _  _ __ ___  "
                + "  ___    | |    ___  _ __  _ __   ___   _ __ \n"
                + "|    / | | | || '_ \\   | |  | || '_ ` _ \\ "
                + " / _ \\   | |   / _ \\| '__|| '__| / _ \\ | '__|\n"
                + "| |\\ \\ | |_| || | | |  | |  | || | | | | ||"
                + "  __/   | |  |  __/| |   | |   | (_) || |   \n"
                + "\\_| \\_| \\__,_||_| |_|  \\_/  |_||_| |_| |_"
                + "| \\___|   \\_/   \\___||_|   |_|    \\___/ |" + "_|   \n"
                + "                                             "
                + "                                          \nversion 1.0.0");
        System.out.println("----------------------------\n");
    }

    public void showMenu() {

        if (this.controller.getNumberOfEmployees() != 0) {
            loopUserInput();
        } else {
            System.out.println("Podana ścieżka nie zawiera żadnych danych!");
        }
        this.exit();

    }

    private void loopUserInput() {
        String userOption;
        do {
            this.showHeaders();
            userOption = this.takeUserInput(this.askForOption());
            if (this.reportOptions.contains(userOption)) {
                this.showReport(userOption);
            } else if (this.chartOptions.contains(userOption)) {
                this.showChart(userOption);
            } else if ("8".equals(userOption)) {

                this.showErrorLogs();
            }
        } while (!userOption.equals("0"));
    }

    private void showReport(String userOption) {
        this.controller.setReportType(userOption);
        boolean reportReady = this.filterData();
        if (reportReady) {
            this.controller.buildReport();
            System.out.println(this.controller.stringReport());
            String answer = this.takeUserInput(this.askForXlsCreation());
            if (answer.toLowerCase().equals("t")) {
                this.exportToXls();
            }
        }
    }

    private String takeUserInput(String question) {
        System.out.println(question);
        return this.sc.nextLine();
    }
}
