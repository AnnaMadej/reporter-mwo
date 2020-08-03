package repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FilesFinder {

    String[] extensions;

    public FilesFinder(String... extensions) {
        this.extensions = extensions;
    }

    public List<File> findFiles(String path) throws IOException {
        final File masterDirectory = new File(path);
        masterDirectory.getCanonicalPath();

        return (List<File>) FileUtils.listFiles(masterDirectory, this.extensions, true);
    }

}