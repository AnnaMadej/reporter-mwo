package App;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Reader.ScanErrorsHolder;

public class UserInterface {

	private Scanner sc = new Scanner(System.in);
	private Controller controller = new Controller();
	private List<String> reportOptions = Arrays.asList(new String[] { "1", "2", "3", "4", "5" });
	private List<String> chartOptions = Arrays.asList(new String[] { "6", "7" });

	private Set<String> possibleInputParams = new TreeSet<String>();
	private List<String> inputParamNames = new ArrayList<String>();

	public UserInterface(String path) {
		try {
			controller.readEmployees(path);
		} catch (InvalidFormatException e) {
			System.out.println("invalidFormat!");
		} catch (IOException e) {
			System.out.println("IO exception!");
		} catch (IllegalArgumentException e) {
			System.out.println("Podałeś nieprawidłową ścieżkę do katalogów z raportami!");
		}
	}

	private String askForOption() {
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

	private String askForParam(String inputParamsName) {
		StringBuilder question = new StringBuilder();
		question.append("Podaj parametr: " + inputParamsName + "\n");
		question.append("Możliwe wartości: \n");
		for (String possibleParam : possibleInputParams) {
			question.append("[ " + possibleParam + " ]");
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

	public void showMenu() {

		if (controller.getNumberOfEmployees() != 0) {
			String userOption;
			do {
				showHeaders();
				userOption = takeUserInput(askForOption());
				if (reportOptions.contains(userOption)) {
					showReport(userOption);
				} else if ("9".equals(userOption)) {
					showErrorLogs();
				}
			} while (!userOption.equals("0"));
		} else {
			System.out.println("Ścieżka nie zawiera żadnych danych do raportów!");
		}
		exit();
	}

	private void exit() {
		System.out.println("Copyright © 2020 RunTime Terror, All Rights Reserved. ");
		sc.close();
	}

	private void exportToXls() {
		String answer;
		String filePath = "";
		try {
			filePath = controller.exportReport();
		} catch (IOException e) {
			System.out.println("Nie udało się wyeksportowac pliku xls!");
		}
		System.out.println("Plik poprawnie wyeksportowany: " + filePath);
		answer = takeUserInput(askForXlsOpening());
		if (answer.toLowerCase().equals("t")) {
			try {
				controller.openReport();
			} catch (IOException e) {
				System.out.println("Nie udało się otworzyc pliku xls!");
			}
		}
	}

	private void showErrorLogs() {
		ScanErrorsHolder.showScanErrors();
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

	private void showReport(String userOption) {
		controller.setReportType(userOption);
		boolean reportReady = false;
		inputParamNames = controller.getInputParamNames();
		for (int i = 0; i < inputParamNames.size(); i++) {
			String inputParamName = inputParamNames.get(i);
			possibleInputParams = controller.getPossibleInputParams().get(i);
			String question = askForParam(inputParamName);
			String inputParam = takeUserInput(question);
			if (possibleInputParams.contains(inputParam)) {
				controller.addReportInputParam(inputParam);
				reportReady = true;
			} else {
				System.out.println("Nie podałeś parametru z wyświetlonej listy!");
				reportReady = false;
				break;
			}
		}
		if (reportReady) {
			controller.buildReport();
			System.out.println(controller.reportString());
			String answer = takeUserInput(askForXlsCreation());
			if (answer.toLowerCase().equals("t")) {
				exportToXls();
			}
		}
	}

	private String takeUserInput(String question) {
		System.out.println(question);
		return sc.nextLine();
	}
}
