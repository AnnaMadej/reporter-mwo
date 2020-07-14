package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import model.Employee;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class FilesFinder {
    
    String[] extensions;
    
    public FilesFinder(String... extensions) {
        this.extensions = extensions;
    }
    
    public List<File> findFiles(String path) throws IOException {
        File masterDirectory = new File(path);
        masterDirectory.getCanonicalPath();

        return (List<File>) FileUtils.listFiles(masterDirectory,
               extensions, true);
    }

   

}