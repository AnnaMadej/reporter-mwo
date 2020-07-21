package services.reportbuilders;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import services.reportbuilders.Report1Builder;
import services.reportbuilders.Report2Builder;
import services.reportbuilders.Report3Builder;
import services.reportbuilders.Report4Builder;
import services.reportbuilders.Report5Builder;
import services.reportbuilders.ReportBuilder;
import services.reportbuilders.ReportBuilderFactory;

public class ReportBuilderFactoryTests {

    @Test
    public void returnsReport1Builder() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("1");
        Assert.assertTrue(builder instanceof Report1Builder);
    }
    
    @Test
    public void returnsReport2Builder() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("2");
        Assert.assertTrue(builder instanceof Report2Builder);
    }
    
    @Test
    public void returnsReport3Builder() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("3");
        Assert.assertTrue(builder instanceof Report3Builder);
    }
    
    @Test
    public void returnsReport4Builder() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("4");
        Assert.assertTrue(builder instanceof Report4Builder);
    }
    
    @Test
    public void returnsReport5Builder() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("5");
        Assert.assertTrue(builder instanceof Report5Builder);
    }
    
    @Test
    public void returnsNullIfNullString() {
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder(null);
        Assert.assertEquals(builder, null);
    }
    
    @Test
    public void returnsNullIfNotKnownString() {
        ReportBuilderFactory rbf = new ReportBuilderFactory();
        ReportBuilder builder = ReportBuilderFactory.getReportBuilder("123");
        Assert.assertEquals(builder, null);
    }
}
