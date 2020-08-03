package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class XlsFilesWriterTests {

    private static XlsFilesWriter writer;

    @Before
    public void init() {
        writer = new XlsFilesWriter();
    }

    @Test
    public final void testReturnsNullIfSourceObjectIsNotAWorkbook()
                    throws FileNotFoundException, IOException {
        File result = writer.writeToFile("string");
        Assert.assertNull(result);
    }

    @Test
    public final void testReturnsNullIfSourceObjectINull()
                    throws FileNotFoundException, IOException {
        File result = writer.writeToFile(null);
        Assert.assertNull(result);

    }

    @Test
    public final void filenameEndsWithCounterNumber() {
        String filename = writer.findFilename(5);
        Assert.assertTrue(filename.startsWith("report"));
        Assert.assertTrue(filename.endsWith("(5)"));
    }

    @Test
    public final void filenameDoesntContainBracketsIfCounterIs0() {
        String filename = writer.findFilename(0);
        Assert.assertTrue(filename.startsWith("report"));
        Assert.assertFalse(filename.contains("("));
        Assert.assertFalse(filename.contains(")"));
    }

    @Test
    public final void filenameHasProperLengthIfCounterHigherThan0() {
        String filename = writer.findFilename(5);
        Assert.assertEquals(21, filename.length());
    }

    @Test
    public final void filenameHasProperLengthIfCounterIs0() {
        String filename = writer.findFilename(0);
        Assert.assertEquals(18, filename.length());
    }

    @Test
    public final void testAddsProperDateStringToFilenameWith0Counter() {
        String filename = writer.findFilename(0);
        String currentDateString = new SimpleDateFormat("yyyyMMddHHmm")
                        .format(new Date());
        long currentDateLong = Long.valueOf(currentDateString);

        String dateString = filename.substring(6, 6 + 12);
        long dateLong = Long.valueOf(dateString);

        MatcherAssert.assertThat(dateLong,
                        CoreMatchers.allOf(Matchers.greaterThan(currentDateLong - 2),
                                        Matchers.lessThan(currentDateLong + 2)));
    }

    @Test
    public final void testAddsProperDateStringToFilenameWith5Counter() {
        String filename = writer.findFilename(5);
        String currentDateString = new SimpleDateFormat("yyyyMMddHHmm")
                        .format(new Date());
        long currentDateLong = Long.valueOf(currentDateString);

        String dateString = filename.substring(6, 6 + 12);
        long dateLong = Long.valueOf(dateString);

        MatcherAssert.assertThat(dateLong,
                        CoreMatchers.allOf(Matchers.greaterThan(currentDateLong - 2),
                                        Matchers.lessThan(currentDateLong + 2)));

    }

    @Test
    public final void testCallsProperFileMakingMethods()
                    throws FileNotFoundException, IOException {
        writer = Mockito.spy(XlsFilesWriter.class);
        Mockito.doNothing().when(writer).checkReportsPath();
        Mockito.doNothing().when(writer).setDestinationFilePath();
        Mockito.doNothing().when(writer).saveFile(Mockito.any(Workbook.class));

        File file = new File("path");
        writer.setDestinationFile(file);

        File file2 = writer.writeToFile(new HSSFWorkbook());
        Mockito.verify(writer, Mockito.times(1)).checkReportsPath();
        Mockito.verify(writer, Mockito.times(1)).setDestinationFilePath();
        Mockito.verify(writer, Mockito.times(1)).saveFile(Mockito.any(Workbook.class));

        Assert.assertEquals(file, file2);
    }

    @Test
    public final void testTriesToCreateDirectoryIfNotExists() {

        writer = Mockito.spy(XlsFilesWriter.class);
        Mockito.when(writer.reportsDirExists()).thenReturn(false);
        writer.checkReportsPath();

        Mockito.verify(writer, Mockito.times(2)).reportsDirExists();
        Mockito.verify(writer, Mockito.times(1)).createReportsDir();

    }

    @Test
    public final void testNotTriesToCreateDirectoryIfNotExists() {

        writer = Mockito.spy(XlsFilesWriter.class);
        Mockito.when(writer.reportsDirExists()).thenReturn(true);
        writer.checkReportsPath();

        Mockito.verify(writer, Mockito.times(2)).reportsDirExists();
        Mockito.verify(writer, Mockito.times(0)).createReportsDir();

    }

    @Test
    public final void setsProperDestinationFilePath() {
        writer = Mockito.spy(XlsFilesWriter.class);
        Mockito.when(writer.findFilename(Mockito.any(Integer.class)))
                        .thenReturn("filename");
        writer.setDestinationFilePath();

        File destinationFile = writer.getDestinationFile();
        String filepath = destinationFile.getPath().replace("/", "\\");
        Assert.assertEquals("generated-reports\\filename.xls", filepath);

    }

    @Test
    public final void testWritesFile() throws IOException {
        Workbook wb = Mockito.mock(Workbook.class);
        writer.setDestinationFile(new File("file"));
        Mockito.doNothing().when(wb).write(Mockito.any(OutputStream.class));
        writer.saveFile(wb);
        Mockito.verify(wb, Mockito.times(1)).write(Mockito.any(OutputStream.class));

    }

}
