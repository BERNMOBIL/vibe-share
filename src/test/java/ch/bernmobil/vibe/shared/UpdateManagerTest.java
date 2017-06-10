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
    private TestHelper testHelper;

    @Before
    public void beforeTest() {
        queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        mockProvider.cleanFlags();
        testHelper = new TestHelper(queryCollector);
    }

    @Test
    public void prepareUpdateTest() {
        final Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = {
            "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)"
        };
        final Object[][] expectedBindings = {
                { expectedTimestamp, UpdateManager.Status.IN_PROGRESS.toString() }
        };

        Timestamp resultingTimestamp = updateManager.prepareUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
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
                "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?",
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
                {"SUCCESS", 2},
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

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
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

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);

    }

    @Test
    public void hasUpdateCollisionWithoutCollisionTest() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
                {1}
        };

        boolean result = updateManager.hasUpdateCollision();
        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertFalse(result);
    }


    @Test
    public void hasUpdateCollisionWithValidCollisionTest() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
                {1}
        };
        mockProvider.actLikeUpdateHistoryhasValidCollision = true;
        boolean result = updateManager.hasUpdateCollision();
        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertTrue(result);
    }


    @Test
    public void hasUpdateCollisionWithInvalidCollisionTest() {
        final Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis() -  5 * 60 * 60 * 1000);
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
        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertFalse(result);
    }

    @Test
    public void setStatusTest() {
        Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05.0");
        final String[] expectedQueries = {
                "SELECT * FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP) LIMIT ?",
                "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)",
        };
        final Object[][] expectedBindings = {
                { timestamp, 1 },
                { "FAILED", timestamp }
        };

        updateTimestampManager.setActiveUpdateTimestamp(timestamp);
        updateManager.setStatus(UpdateManager.Status.FAILED);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void removeUpdateTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String[] expectedQueries = {
            "DELETE FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP)"
        };
        final Object[][] expectedBindings = {
                {timestamp}
        };
        updateManager.removeUpdateByTimestamp(timestamp);
        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
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
