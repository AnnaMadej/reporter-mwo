package services.readers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import model.Employee;
import model.ScanError;
import repository.FilesFinder;
import services.ReadErrorsChecker;
import services.ReadErrorsHolder;
import services.XlsReadErrorsChecker;

public class XlsFilesReaderTest {

    private static ReadErrorsHolder readErrorsHolder;
    private static FilesReader filesReader = new XlsFilesReader(readErrorsHolder);
    private static ReadErrorsChecker readErrorsChecker;
    private static FilesFinder filesFinder;
    private static File file1;
    private static List<File> filesList;
    private static String path = "somePath";
    private static List<Employee> employees;      
    private static String location;

    @Before
    public final void init() throws IOException {
        readErrorsChecker = Mockito.mock(XlsReadErrorsChecker.class);
        filesFinder = Mockito.mock(FilesFinder.class);
        readErrorsHolder = Mockito.mock(ReadErrorsHolder.class);
        
        filesReader.setFilesFinder(filesFinder);
        filesReader.setReadErrorsChecker(readErrorsChecker);
        filesReader.setReadErrorsHolder(readErrorsHolder);
        
        file1 = new File("C:/2012/01/Nowak_Jan.xls");
        filesList = new ArrayList<>();    
        filesList.add(file1);
        location = file1.getParent().replace("\\", "/");
        
        Mockito.when(filesFinder.findFiles(path)).thenReturn(filesList);
    }
    @Test
    public final void testReadFilesWithInvalidFilename() throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(false);
        
       employees = filesReader.readFiles(path);
        
        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(
                new ScanError(file1.getCanonicalPath(), "", "", "zła nazwa pliku!"));
        
        Assert.assertEquals(0, employees.size());
    }
    
    @Test 
    public final void testExtractFileLocation() {
        Assert.assertEquals(location, filesReader.extractFileLocation(file1));
    }
    
    @Test
    public final void testReadFilesWithInvalidMonthInLocation() throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(false);
        
        employees = filesReader.readFiles(path);
        
        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(new ScanError(file1.getCanonicalPath(),
                "", "Zła lokalizacja pliku!"));
        
        Assert.assertEquals(0, employees.size());
    }
    
    @Test
    public final void testReadFilesWithInvalidYearInLocation() throws IOException, InvalidFormatException {
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationMonthIsValid(location)).thenReturn(true);
        Mockito.when(readErrorsChecker.locationYearIsValid(location)).thenReturn(false);
        
        employees = filesReader.readFiles(path);
        
        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(new ScanError(file1.getCanonicalPath(),
                "", "Zła lokalizacja pliku!"));
        
        Assert.assertEquals(0, employees.size());
    }

}
