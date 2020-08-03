package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FilesWriter {
    public File writeToFile(Object object) throws FileNotFoundException, IOException;

}
