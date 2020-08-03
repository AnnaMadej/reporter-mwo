package services.reportbuilders;

import org.junit.Assert;
import org.junit.Test;

public class ReportBuilderFactoryTests {

    private final ReportBuilderFactory rbFactory = new ReportBuilderFactory();

    @Test
    public void returnsNullIfNotKnownString() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("123");
        Assert.assertEquals(builder, null);
    }

    @Test
    public void returnsNullIfNullString() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder(null);
        Assert.assertEquals(builder, null);
    }

    @Test
    public void returnsReport1Builder() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("1");
        Assert.assertTrue(builder instanceof Report1Builder);
    }

    @Test
    public void returnsReport2Builder() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("2");
        Assert.assertTrue(builder instanceof Report2Builder);
    }

    @Test
    public void returnsReport3Builder() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("3");
        Assert.assertTrue(builder instanceof Report3Builder);
    }

    @Test
    public void returnsReport4Builder() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("4");
        Assert.assertTrue(builder instanceof Report4Builder);
    }

    @Test
    public void returnsReport5Builder() {
        final ReportBuilder builder = this.rbFactory.getReportBuilder("5");
        Assert.assertTrue(builder instanceof Report5Builder);
    }
}
