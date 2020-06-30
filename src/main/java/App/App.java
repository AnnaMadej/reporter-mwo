package App;

import View.UserInterface;

public class App {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Nie podałeś ścieżki do pliku z raportami!");
		} else {
			String folderPath = args[0];
			UserInterface userInterface = new UserInterface(folderPath);
			userInterface.showMenu();
		}

	}
}