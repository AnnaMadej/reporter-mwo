package services.employeefilters;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import model.Employee;
import services.possibledataretrievers.PossibleDataRetriever;
import services.possibledataretrievers.PossibleYearRetriever;

public class EmployeesFilterUnitTests {

    private EmployeesFilter filter = new EmployeesFilterByYear();

    @Test
    public final void testsAsksForPossibleData() {

        PossibleDataRetriever retriever = Mockito.mock(PossibleYearRetriever.class);
        Mockito.when(retriever.getPossibleData(Mockito.anyList()))
                        .thenReturn(new TreeSet<String>());
        filter.setPossibleDataRetriever(retriever);

        filter.getPossibleData(new ArrayList<Employee>());

        Mockito.verify(retriever, Mockito.times(1)).getPossibleData(Mockito.anyList());
    }

    @Test
    public final void testReturnsFilterParameterName() {
        filter.setFilterParameterName("nazwa parametru");
        Assert.assertEquals("nazwa parametru", filter.getFilterParameterName());
    }

    @Test
    public final void testReturnsPossibleDataRetriever() {
        filter.setPossibleDataRetriever(new PossibleYearRetriever());
        Assert.assertTrue(filter
                        .getPossibleDataRetriever() instanceof PossibleYearRetriever);
    }

}
