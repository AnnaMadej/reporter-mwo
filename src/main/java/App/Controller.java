package App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Model.Employee;
import Reader.FilesScanner;

public class Controller {
	List<Employee> employees = new ArrayList<Employee>();
	
	public List<Employee> getEmployeesFromFiles(String path){
		
		try {
			FilesScanner fileScanner = new FilesScanner();
			employees = fileScanner.scanFiles(path);
		} catch (IOException e) {
			System.err.println("Nie znaleziono pliku");
		} catch (InvalidFormatException e) {
			System.err.println("Bład odczytu pliku");
		}
		return employees;
	}
}
