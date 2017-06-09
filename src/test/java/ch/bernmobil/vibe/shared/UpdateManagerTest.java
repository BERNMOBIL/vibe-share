package ch.bernmobil.vibe.shared;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("testConfiguration")
public class UpdateManagerTest {
    private MockProvider mockProvider;
    private QueryCollector queryCollector;
    private UpdateManager updateManager;
    private UpdateTimestampManager updateTimestampManager;

    @Before
    public void beforeTest() {
        mockProvider.actLikeUpdateHistoryIsEmpty = false;
        mockProvider.actLikeUpdateHistoryhasInvalidCollision = false;
        mockProvider.actLikeUpdateHistoryhasValidCollision = false;
        queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
    }

    private void assertBindings(Object[][] expectedBindings) {
        assertBindings(expectedBindings, 1000);
    }

    private void assertBindings(Object[][] expectedBindings, long timestampDelta) {
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

    private void assertQueries(String[] expectedQueries) {
        Assert.assertEquals(expectedQueries.length, queryCollector.queries.size());
        Assert.assertArrayEquals(expectedQueries, queryCollector.queries.toArray(new String[queryCollector.queries.size()]));
    }

    @Test
    public void findUpdateHistoryEntryByTimestampTest() {
        final Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = { "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)" };
        final Object[][] expectedBindings = {
                { expectedTimestamp, UpdateManager.Status.IN_PROGRESS.toString() }
        };

        Timestamp resultingTimestamp = updateManager.prepareUpdate();

        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
        Assert.assertEquals(expectedTimestamp.getTime(), resultingTimestamp.getTime(), 1000);
    }


    @Test
    public void startUpdateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        updateManager.startUpdate(timestamp);
        Assert.assertEquals(timestamp, updateTimestampManager.getActiveUpdateTimestamp());
    }

    @Test
    public void cleanOldDataTest() {
        final Timestamp[] validTimestamps = { Timestamp.valueOf("2017-06-04 15:48:05.0"), Timestamp.valueOf("2017-06-03 15:48:05.0")};
        final String[] expectedQueries =  {
                "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?",
                "TRUNCATE TABLE SCHEDULE_UPDATE",
                "DELETE FROM SCHEDULE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM CALENDAR_DATE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM CALENDAR_EXCEPTION WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM JOURNEY WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM ROUTE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM STOP WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM AREA WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM AREA_MAPPER WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM CALENDAR_DATE_MAPPER WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM JOURNEY_MAPPER WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM ROUTE_MAPPER WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM STOP_MAPPER WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
        };
        final Object[][] expectedBindings = {
                {2},
                {},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
                {validTimestamps, validTimestamps},
        };

        updateManager.cleanOldData();

        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
    }

    @Test
    public void repairFailedUpdateTest() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = {
                "DELETE FROM SCHEDULE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_DATE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_EXCEPTION WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM JOURNEY WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM ROUTE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM STOP WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM AREA WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM AREA_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_DATE_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM JOURNEY_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM ROUTE_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM STOP_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
        };
        final Object[][] expectedBindings = {
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
                {now},
        };


        updateTimestampManager.setActiveUpdateTimestamp(now);
        updateManager.repairFailedUpdate();

        assertQueries(expectedQueries);
        assertBindings(expectedBindings);

    }

    @Test
    public void hasUpdateCollisionWithoutCollisionTest() {
        final String[] expectedQueries = { "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?" };
        final Object[][] expectedBindings = {
                {1}
        };

        boolean result = updateManager.hasUpdateCollision();
        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
        Assert.assertFalse(result);
    }


    @Test
    public void hasUpdateCollisionWithValidCollisionTest() {
        final String[] expectedQueries = {"SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?" };
        final Object[][] expectedBindings = {
                {1}
        };
        mockProvider.actLikeUpdateHistoryhasValidCollision = true;
        boolean result = updateManager.hasUpdateCollision();
        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
        Assert.assertTrue(result);
    }


    @Test
    public void hasUpdateCollisionWithInvalidCollisionTest() {
        final Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis() - 5000000);
        final String[] expectedQueries = {
                "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?",
                "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)",
                "DELETE FROM SCHEDULE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_DATE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_EXCEPTION WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM JOURNEY WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM ROUTE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM STOP WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM AREA WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM AREA_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM CALENDAR_DATE_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM JOURNEY_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM ROUTE_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM STOP_MAPPER WHERE UPDATE = CAST(? AS TIMESTAMP)",
        };
        final Object[][] expectedBindings = {
                {1},
                {"FAILED", expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
                {expectedTimestamp},
        };
        mockProvider.actLikeUpdateHistoryhasInvalidCollision = true;
        boolean result = updateManager.hasUpdateCollision();
        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
        Assert.assertFalse(result);
    }

    @Test
    public void setStatusTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = {
                "SELECT * FROM UPDATE_HISTORY WHERE TIME = CAST(? AS VARCHAR) LIMIT ?",
                "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)",
        };
        final Object[][] expectedBindings = {
                { timestamp, 1 },
                { "FAILED", timestamp }
        };

        updateManager.setStatus(UpdateManager.Status.FAILED);

        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
    }

    @Test
    public void removeUpdateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = { "DELETE FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP)" };
        final Object[][] expectedBindings = {
                {timestamp}
        };
        updateManager.removeUpdateByTimestamp(timestamp);
        assertQueries(expectedQueries);
        assertBindings(expectedBindings);
    }


    @Autowired
    private void setUpdateManager(UpdateManager updateManager) {
        this.updateManager = updateManager;
    }
    @Autowired
    private void setMockProvider(MockProvider mockProvider) {
        this.mockProvider = mockProvider;
    }

    @Autowired
    private void setUpdateTimestampManager(UpdateTimestampManager updateTimestampManager) {
        this.updateTimestampManager = updateTimestampManager;
    }

}
