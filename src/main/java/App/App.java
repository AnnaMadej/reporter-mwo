package App;

public class App {
	public static void main(String[] args) {
		try {
			String folderPath = args[0];
			UserInterface userControl = new UserInterface(folderPath);
			userControl.controlLoop();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Nie wprowadziłeś poprawnej ścieżki do folderu");
		} catch (IllegalArgumentException e) {
			System.out.println("Wprowadziłeś ścieżkę do folderu który nie istnieje lub jest pusty");
		}
	}
}