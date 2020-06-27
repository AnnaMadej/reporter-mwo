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
	private ReportBuilder reportBuilder;
	private Report report;

	private List<Employee> employeeList = new ArrayList<Employee>();

	public UserControl(String path) {
		try {
			FilesScanner fileScanner = new FilesScanner();
			employeeList = fileScanner.scanFiles(path);
		} catch (IOException e) {
			System.err.println("Nie znaleziono pliku");
		} catch (InvalidFormatException e) {
			System.err.println("Bład odczytu pliku");
		}
	}

	public void controlLoop() {

		do {
			clearConsole();
			showHeaders();
			printOptions();
			userOption = sc.nextLine();

			this.reportBuilder = ReportBuilderFactory.getReportBuilder(userOption, employeeList);
			boolean reportReady;
			if (reportBuilder != null) {
				reportReady = false;
				for (int i = 0; i < reportBuilder.getInputParamsNames().size(); i++) {
					String inputParamsName = reportBuilder.getInputParamsNames().get(i);
					System.out.println("Podaj parametr: " + inputParamsName);
					System.out.println("Możliwe wartości: ");
					Set<String> possibleInputParams = reportBuilder.getPossibleInputParams().get(i);
					for (String possibleParam : possibleInputParams) {
						System.out.print("[ " + possibleParam + " ]");
					}
					System.out.println();
					String inputParam = inputParam = sc.nextLine();
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
					System.out.println("Czy chcesz wyeksportować raport do pliku xls? [t/n]");
					String answer = sc.nextLine();
					if (answer.toLowerCase().equals("t")) {
						try {
							File file = ReportXlsExporter.exportToXls(report);
							System.out.println("Plik poprawnie wyeksportowany: " + file.getCanonicalPath());
							System.out.println("Czy chcesz otworzyć plik xls? [t/n]");
							answer = sc.nextLine();
							if (answer.toLowerCase().equals("t")) {
								openFile(file);
							}
						} catch (IOException e) {
							System.out.println("Nie udało się wyeksportowa pliku!");
						}
					}
				}

			}

			if (!userOption.equals("0")) {
				System.out.println("Naciśnij Enter aby kontynuować...");
				String pause = sc.nextLine();
			}

		} while (!userOption.equals("0"));

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
		option.append("8. Pokaż logi z odczytu pliku\n");
		option.append("0. Zakończ pracę z programem\n");

		return option.toString();
	}

	private void showErrorLogs() {
		ScanErrorsHolder.printScanErrors();
		System.out.println();
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


	public void clearConsole() {
		// Clears Screen in java
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {
		}
	}
}
