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

public class UserInterface {

	private Scanner sc = new Scanner(System.in);
	private ReportBuilder reportBuilder;
	private Report report;

	Set<String> possibleInputParams = new TreeSet<String>();
	List<String> reportOptions = Arrays.asList(new String[] { "1", "2", "3", "4", "5" });
	List<String> chartOptions = Arrays.asList(new String[] { "6", "7" });
	Controller controller = new Controller();
	private List<Employee> employees = new ArrayList<Employee>();

	public UserInterface(String path) {
		this.employees = controller.getEmployeesFromFiles(path);
	}

	public void controlLoop() {
		String userOption;
		do {

			showHeaders();
			userOption = takeUserInput(showOptions());

			if (reportOptions.contains(userOption)) {
				generateReport(userOption);
			} else if ("9".equals(userOption)) {
				showErrorLogs();
			}
		} while (!userOption.equals("0"));
		exit();

	}

	private void generateReport(String userOption) {
		this.reportBuilder = ReportBuilderFactory.getReportBuilder(userOption);
		reportBuilder.setEmployees(employees);
		boolean reportReady;
		if (reportBuilder != null) {
			reportReady = false;
			for (int i = 0; i < reportBuilder.getInputParamsNames().size(); i++) {
				String inputParamsName = reportBuilder.getInputParamsNames().get(i);
				this.possibleInputParams = reportBuilder.getPossibleInputParams().get(i);
				String question = showPossibleInputParams(inputParamsName);

				String inputParam = takeUserInput(question);
				if (possibleInputParams.contains(inputParam)) {
					reportBuilder.addInputParam(inputParam);
					reportReady = true;
				} else {
					System.out.println("Nie podałeś parametru z wyświetlonej listy!");
					reportReady = false;
					break;
				}
			}
			if (reportReady) {
				report = reportBuilder.buildReport();
				ReportPrinter.printReport(report);
				String answer = takeUserInput("Czy chcesz wyeksportować raport do pliku xls? [t/n]");
				if (answer.toLowerCase().equals("t")) {
					try {
						File file = ReportXlsExporter.exportToXls(report);
						System.out.println("Plik poprawnie wyeksportowany: " + file.getCanonicalPath());
						answer = takeUserInput("Czy chcesz otworzyć plik xls? [t/n]");
						if (answer.toLowerCase().equals("t")) {
							openFile(file);
						}
					} catch (IOException e) {
						System.out.println("Nie udało się wyeksportowa pliku!");
					}
				}
			}

		}
	}

	private String showPossibleInputParams(String inputParamsName) {
		StringBuilder question = new StringBuilder();
		question.append("Podaj parametr: " + inputParamsName + "\n");
		question.append("Możliwe wartości: \n");
		for (String possibleParam : possibleInputParams) {
			question.append("[ " + possibleParam + " ]");
		}
		question.append("\n");
		return question.toString();
	}

	public String showOptions() {
		StringBuilder option = new StringBuilder();

		option.append("WYBIERZ OPCJE:\n");
		option.append("1. Generuj raport godzin pracowników w podanym roku\n");
		option.append("2. Generuj raport godzin projektów w podanym roku\n");
		option.append("3. Generuj raport godzin przepracowanych miesięcznie przez pracownika w podanym roku\n");
		option.append("4. Generuj procentowy udział projektów w pracy osob dla podanego roku\n");
		option.append("5. Generuj raport ilości godzin pracowników w podanym projekcie\n");
		option.append("6. Wyświetl wykres słupkowy godzin projektów w podanym roku\n");
		option.append("7. Wyświetl wykres kołowy procentowego udziału projektów dla pracowników w podanym roku\n");
		option.append("8. Pokaż logi z odczytu plików\n");
		option.append("0. Zakończ pracę z programem\n");

		return option.toString();
	}

	private void showErrorLogs() {
		ScanErrorsHolder.showScanErrors();
	}

	private void openFile(File generatedReport) throws IOException {
		try {
			Desktop desktop = Desktop.getDesktop();
			if (generatedReport.exists()) {
				desktop.open(generatedReport);
			}
		} catch (UnsupportedOperationException e) {
		}
	}

	private void showHeaders() {
		System.out.println("______                _____  _                    _____                                \n"
				+ "| ___ \\              |_   _|(_)                  |_   _|                               \n"
				+ "| |_/ / _   _  _ __    | |   _  _ __ ___    ___    | |    ___  _ __  _ __   ___   _ __ \n"
				+ "|    / | | | || '_ \\   | |  | || '_ ` _ \\  / _ \\   | |   / _ \\| '__|| '__| / _ \\ | '__|\n"
				+ "| |\\ \\ | |_| || | | |  | |  | || | | | | ||  __/   | |  |  __/| |   | |   | (_) || |   \n"
				+ "\\_| \\_| \\__,_||_| |_|  \\_/  |_||_| |_| |_| \\___|   \\_/   \\___||_|   |_|    \\___/ |_|   \n"
				+ "                                                                                       \nversion 1.0.0");
		System.out.println("----------------------------\n");
	}

	private void exit() {
		System.out.println("Copyright © 2020 RunTime Terror, All Rights Reserved. ");
		sc.close();
	}

	public void printOptions() {
		String option = showOptions();
		System.out.println(option);
	}

	private String takeUserInput(String question) {
		System.out.println(question);
		return sc.nextLine();
	}
}
