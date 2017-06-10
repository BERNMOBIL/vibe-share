package ch.bernmobil.vibe.shared;

import java.sql.Timestamp;
import org.junit.Assert;

public class TestHelper {

    private final MockProvider.QueryCollector queryCollector;

    public TestHelper(MockProvider.QueryCollector queryCollector) {
        this.queryCollector = queryCollector;
    }

    void assertBindings(Object[][] expectedBindings) {
        assertBindings(expectedBindings, 1000);
    }

    void assertBindings(Object[][] expectedBindings, long timestampDelta) {
        Assert.assertEquals(expectedBindings.length, queryCollector.bindings.size());

        for(int i = 0; i < expectedBindings.length; i++) {
            Object[] queryBindings = expectedBindings[i];
            Assert.assertEquals(expectedBindings[i].length, queryCollector.bindings.get(i).size());
            for(int j = 0; j < queryBindings.length; j++) {
                if(queryBindings[j] instanceof Timestamp) {
                    Assert.assertEquals(((Timestamp)expectedBindings[i][j]).getTime(),((Timestamp)queryCollector.bindings.get(i).get(j)).getTime(), timestampDelta);
                } else {
                    Assert.assertEquals(expectedBindings[i][j], queryBindings[j]);
                }
            }
        }
    }

    void assertQueries(String[] expectedQueries) {
        Assert.assertEquals(expectedQueries.length, queryCollector.queries.size());
        Assert.assertArrayEquals(expectedQueries, queryCollector.queries.toArray(new String[queryCollector.queries.size()]));
    }
}
