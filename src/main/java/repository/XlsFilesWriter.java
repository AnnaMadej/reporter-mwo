package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsFilesWriter  implements FilesWriter {
    private String generatedReportsPath = "generated-reports";
    
    public File writeToFile(Object object) throws FileNotFoundException, IOException {
        
        if (!(object instanceof Workbook)) {
            return null;
        }
        Workbook wb = (Workbook) object;
        File file = new File(generatedReportsPath);
        if (!file.exists()) {
            new File(generatedReportsPath).mkdir();
        }
        
        String filePath;
        int filesOfSameNameCounter = 0;
        do {
            filesOfSameNameCounter++;
            String reportName = "report"
                    + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "("
                    + filesOfSameNameCounter + ")";
            filePath = generatedReportsPath + "/" + reportName + ".xls";
            file = new File(filePath);
        } while (file.exists());

        try (OutputStream fileOut = new FileOutputStream(filePath)) {
            wb.write(fileOut);
        }
        return file;
    }
}
