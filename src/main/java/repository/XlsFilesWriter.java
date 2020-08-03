package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Workbook;

public class XlsFilesWriter implements FilesWriter {
    private final String generatedReportsPath = "generated-reports";

    private File destinationFile;

    protected void checkReportsPath() {

        if (!reportsDirExists()) {
            createReportsDir();
        }
    }

    void createReportsDir() {
        final File file = new File(this.generatedReportsPath);
        file.mkdir();
    }

    protected boolean fileExists(String filePath) {
        final File file = new File(filePath);
        return file.exists();
    }

    public String findFilename(Integer filesOfSameNameCounter) {
        final String filesOfSameNameAddedNumber = filesOfSameNameCounter == 0 ? ""
                : "(" + String.valueOf(filesOfSameNameCounter) + ")";
        final String fileName = "report"
                + new SimpleDateFormat("yyyyMMddHHmm").format(new Date())
                + filesOfSameNameAddedNumber;
        return fileName;
    }

    protected File getDestinationFile() {
        return this.destinationFile;
    }

    protected boolean reportsDirExists() {
        return fileExists(this.generatedReportsPath);
    }

    protected void saveFile(Workbook wb) throws IOException, FileNotFoundException {
        try (OutputStream fileOut = new FileOutputStream(
                this.destinationFile.getAbsolutePath())) {
            wb.write(fileOut);
        }
    }

    protected void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    protected void setDestinationFilePath() {
        String filePath;
        Integer filesOfSameNameCounter = 0;

        do {
            final String fileName = findFilename(filesOfSameNameCounter);
            filePath = this.generatedReportsPath + "/" + fileName + ".xls";
            filesOfSameNameCounter++;
        } while (fileExists(filePath));

        this.destinationFile = new File(filePath);
    }

    @Override
    public File writeToFile(Object object) throws FileNotFoundException, IOException {

        if (!(object instanceof Workbook)) {
            return null;
        }
        final Workbook wb = (Workbook) object;

        checkReportsPath();
        setDestinationFilePath();
        saveFile(wb);

        return this.destinationFile;
    }

}
