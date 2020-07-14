package services.readers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
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

    @BeforeClass
    public static final void init() {
        readErrorsChecker = Mockito.mock(XlsReadErrorsChecker.class);
        filesFinder = Mockito.mock(FilesFinder.class);
        readErrorsHolder = Mockito.mock(ReadErrorsHolder.class);
        
        filesReader.setFilesFinder(filesFinder);
        filesReader.setReadErrorsChecker(readErrorsChecker);
        filesReader.setReadErrorsHolder(readErrorsHolder);
    }
    @Test
    public final void testReadFilesWithInvalidFilename() throws IOException, InvalidFormatException {
        File file1 = new File("C:/2012/01");
        List<File> filesList = new ArrayList<>();    
        filesList.add(file1);
        
        Mockito.when(readErrorsChecker.filenameIsValid(file1)).thenReturn(false);
        String path = "somePath";
        Mockito.when(filesFinder.findFiles(path)).thenReturn(filesList);
        
        List<Employee> employees = filesReader.readFiles(path);
        
        Mockito.verify(readErrorsHolder, Mockito.times(1)).addScanError(
                new ScanError(file1.getCanonicalPath(), "", "", "zła nazwa pliku!"));
        
        Assert.assertEquals(0, employees.size());
      
    }

}
