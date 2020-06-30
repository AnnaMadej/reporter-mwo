package Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import Model.Employee;
import Services.DataReader;

public class FilesScanner {

	DataReader dataReader = new DataReader();

	private List<File> findFiles(String path) throws IOException {
		File masterDirectory = new File(path);
		masterDirectory.getCanonicalPath();

		return (List<File>) FileUtils.listFiles(masterDirectory, new String[] { "xls", "xlsx" }, true);
	}

	public List<Employee> scanFiles(String path) throws InvalidFormatException, IOException {

		List<File> files = this.findFiles(path);

		List<Employee> employees = new ArrayList();

		for (File file : files) {
			Employee employee = new Employee();
			employee = this.dataReader.readFile(file);

			if (employee != null) {
				if (employees.contains(employee)) {
					employees.get(employees.indexOf(employee)).addTasks(employee.getTaskList());
				} else {
					employees.add(employee);
				}

			}

		}
		employees.sort(Comparator.comparing(Employee::getSurname));
		return employees;
	}

}