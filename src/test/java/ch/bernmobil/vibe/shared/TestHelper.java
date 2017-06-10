package ch.bernmobil.vibe.shared;

import org.junit.Assert;
import java.sql.Timestamp;

public class TestHelper {

    private final MockProvider.QueryCollector queryCollector;

    public TestHelper(MockProvider.QueryCollector queryCollector) {
        this.queryCollector = queryCollector;
    }

    void assertBindings(Object[][] expectedBindings) {
        assertBindings(expectedBindings, 1000);
    }

    void assertBindings(Object[][] expectedBindings, long timestampDelta) {
        Assert.assertEquals(expectedBindings.length, queryCollector.getBindings().size());

        for(int i = 0; i < expectedBindings.length; i++) {
            Object[] queryBindings = expectedBindings[i];
            Assert.assertEquals(expectedBindings[i].length, queryCollector.getBindings().get(i).size());
            for(int j = 0; j < queryBindings.length; j++) {
                if(queryBindings[j] instanceof Timestamp) {
                    Assert.assertEquals(((Timestamp)expectedBindings[i][j]).getTime(),
                            ((Timestamp)queryCollector.getBindings().get(i).get(j)).getTime(), timestampDelta);
                } else {
                    Assert.assertEquals(expectedBindings[i][j], queryBindings[j]);
                }
            }
        }
    }

    void assertQueries(String[] expectedQueries) {
        Assert.assertEquals(expectedQueries.length, queryCollector.getQueries().size());
        Assert.assertArrayEquals(expectedQueries,
                queryCollector.getQueries().toArray(new String[queryCollector.getQueries().size()]));
    }
}
