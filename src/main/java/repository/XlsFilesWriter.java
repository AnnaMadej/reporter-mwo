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
    private String generatedReportsPath = "generated-reports";

    private File destinationFile;

    public File writeToFile(Object object) throws FileNotFoundException, IOException {

        if (!(object instanceof Workbook)) {
            return null;
        }
        Workbook wb = (Workbook) object;

        checkReportsPath();
        setDestinationFilePath();
        saveFile(wb);

        return destinationFile;
    }

    protected void saveFile(Workbook wb) throws IOException, FileNotFoundException {
        try (OutputStream fileOut = new FileOutputStream(destinationFile.getAbsolutePath())) {
            wb.write(fileOut);
        }
    }

    protected void checkReportsPath() {

        if (!reportsDirExists()) {
            createReportsDir();
        }
    }

    void createReportsDir() {
        File file = new File(generatedReportsPath);
        file.mkdir();
    }

    protected void setDestinationFilePath() {
        String filePath;
        Integer filesOfSameNameCounter = 0;

        do {
            String fileName = findFilename(filesOfSameNameCounter);
            filePath = generatedReportsPath + "/" + fileName + ".xls";
            filesOfSameNameCounter++;
        } while (fileExists(filePath));

        destinationFile = new File(filePath);
    }

    protected boolean reportsDirExists() {
        return fileExists(generatedReportsPath);
    }

    protected boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public String findFilename(Integer filesOfSameNameCounter) {
        String filesOfSameNameAddedNumber = (filesOfSameNameCounter == 0) ? ""
                : "(" + String.valueOf(filesOfSameNameCounter) + ")";
        String fileName = "report" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date())
                + filesOfSameNameAddedNumber;
        return fileName;
    }

    protected File getDestinationFile() {
        return destinationFile;
    }

    protected void setDestinationFile(File destinationFile) {
        this.destinationFile = destinationFile;
    }





}
