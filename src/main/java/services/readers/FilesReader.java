package services.readers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;

import model.Employee;
import model.ScanError;
import repository.FilesFinder;
import services.errors.ReadErrorsChecker;
import services.errors.ReadErrorsHolder;

public abstract class FilesReader {

    protected ReadErrorsChecker readErrorsChecker;
    protected FilesFinder filesFinder;
    protected ReadErrorsHolder readErrorsHolder;

    protected void setReadErrorsChecker(ReadErrorsChecker readErrorsChecker) {
        this.readErrorsChecker = readErrorsChecker;
    }

    protected void setFilesFinder(FilesFinder filesFinder) {
        this.filesFinder = filesFinder;
    }

    protected void setReadErrorsHolder(ReadErrorsHolder readErrorsHolder) {
        this.readErrorsHolder = readErrorsHolder;
    }

    protected abstract Employee readFile(File file) throws IOException, InvalidFormatException;

    public List<Employee> readFiles(String path) throws InvalidFormatException, IOException {
        List<File> files = filesFinder.findFiles(path);

        List<Employee> employees = new ArrayList<Employee>();

        for (File file : files) {
            Employee employee = new Employee();
            employee = this.readFile(file);

            mergeEmployee(employees, employee);

        }
        employees.sort(Comparator.comparing(Employee::getSurname));
        return employees;
    }

    protected void mergeEmployee(List<Employee> employees, Employee employee) {
        if (employee != null) {
            if (employees.contains(employee)) {
                employees.get(employees.indexOf(employee)).addTasks(employee.getTaskList());
            } else {
                employees.add(employee);
            }
        }
    }

    protected String extractEmployeeName(String fileName) {
        return fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("."));
    }

    protected String extractEmployeeSurname(String fileName) {
        return fileName.substring(0, fileName.indexOf("_"));
    }

    protected String extractFileLocation(File file) {
        String fileLocation = file.getParent();
        fileLocation = fileLocation.replace('\\', '/');
        return fileLocation;
    }

    protected void addMonthLocationError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder
                .addScanError(new ScanError(file.getCanonicalPath(), sheet.getSheetName(),
                        j + 1, "DATA", "miesiąc nie zgadza się z lokalizacją pliku!"));
    }

    protected void addYearLocationError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder
                .addScanError(new ScanError(file.getCanonicalPath(), sheet.getSheetName(),
                        j + 1, "DATA", "rok nie zgadza się z lokalizacją pliku!"));
    }

    protected void addHoursCellError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), j + 1, "CZAS", "błędnie wypełniona komórka!"));
    }

    protected void addDescriptionCellError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), j + 1, "OPIS", "błędnie wypełniona komórka!"));
    }

    protected void addDateCellError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), j + 1, "DATA", "błędnie wypełniona komórka!"));
    }

    protected void addEmptyRowError(File file, Sheet sheet, int j) throws IOException {
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), j + 1, "pusty wiersz!"));
    }

    protected void addColumnsError(File file, Sheet sheet) throws IOException {
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(),
                sheet.getSheetName(), "Arkusz nie zawiera odpowiednich kolumn"));
    }

    protected void addLocationError(File file) throws IOException {
        readErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), "", "Zła lokalizacja pliku!"));
    }

    protected void addHoursError(File file, Date date) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = format.format(date);
        readErrorsHolder.addScanError(new ScanError(file.getCanonicalPath(), "",
                "Niepoprawna suma godzin w dniu: " + stringDate));
    }

    protected void addFilenameError(File file) throws IOException {
        readErrorsHolder.addScanError(
                new ScanError(file.getCanonicalPath(), "", "", "zła nazwa pliku!"));
    }

}
