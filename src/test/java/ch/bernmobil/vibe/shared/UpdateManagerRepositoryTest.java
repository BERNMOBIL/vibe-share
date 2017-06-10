package ch.bernmobil.vibe.shared;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("testConfiguration")
public class UpdateManagerRepositoryTest {
    private UpdateManagerRepository updateManagerRepository;
    private MockProvider mockProvider;
    private MockProvider.QueryCollector queryCollector;
    private TestHelper testHelper;

    @Before
    public void beforeTest() {
        queryCollector = new MockProvider.QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        mockProvider.cleanFlags();
        testHelper = new TestHelper(queryCollector);
    }

    @Test
    public void truncateTest() {
        final String[] expectedQueries = {
            "TRUNCATE TABLE SCHEDULE_UPDATE"
        };
        final Object[][] expectedBindings = {
            {}
        };
        updateManagerRepository.truncate("SCHEDULE_UPDATE");

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void deleteInvalidUpdatesTest() {
        final String[] expectedQueries = {
            "DELETE FROM SCHEDULE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))"
        };
        final Timestamp[] validTimestamps = {
            Timestamp.valueOf("2017-06-02 15:48:05"),
            Timestamp.valueOf("2017-06-03 15:48:05"),
            Timestamp.valueOf("2017-06-04 15:48:05")
        };
        final Object[][] expectedBindings = {
            validTimestamps
        };
        final String tableName = "SCHEDULE";
        updateManagerRepository.deleteUpdatesWithInvalidTimestamp(tableName, Arrays.asList(validTimestamps));

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void deleteInvalidUpdatesInMultipleTablesAndTimestampsTest() {
        final String expectedQueries[] = {
                "DELETE FROM SCHEDULE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM SCHEDULE_UPDATE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM JOURNEY WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))"
        };
        final Timestamp[] validTimestamps = {
            Timestamp.valueOf("2017-06-02 15:48:05"),
            Timestamp.valueOf("2017-06-03 15:48:05"),
            Timestamp.valueOf("2017-06-04 15:48:05")
        };
        final Object[][] expectedBindings = {
            validTimestamps,
            validTimestamps,
            validTimestamps
        };
        final String[] tableNames = {"SCHEDULE", "SCHEDULE_UPDATE", "JOURNEY"};
        updateManagerRepository.deleteUpdatesWithInvalidTimestamp(tableNames, Arrays.asList(validTimestamps));

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void deleteByUpdateTimestampTest() {
        final String[] expectedQueries = {
            "DELETE FROM SCHEDULE WHERE UPDATE = CAST(? AS TIMESTAMP)"
        };
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final String tableName = "SCHEDULE";
        final String timestampColumn = "UPDATE";
        final Object[][] expectedBindings = {
            {timestamp}
        };

        updateManagerRepository.deleteByUpdateTimestamp(tableName, timestamp, timestampColumn);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void deleteMultipleTablesByUpdateTimestampTest() {
        final String[] expectedQueries = {
                "DELETE FROM SCHEDULE WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM JOURNEY WHERE UPDATE = CAST(? AS TIMESTAMP)",
                "DELETE FROM SCHEDULE_UPDATE WHERE UPDATE = CAST(? AS TIMESTAMP)"
        };
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final String[] tableNames = {"SCHEDULE", "JOURNEY", "SCHEDULE_UPDATE"};
        final Object[][] expectedBindings = {
            {timestamp},
            {timestamp},
            {timestamp}
        };

        updateManagerRepository.deleteByUpdateTimestamp(tableNames, timestamp, "update");

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Autowired
    private void setUpdateManagerRepository(UpdateManagerRepository updateManagerRepository) {
        this.updateManagerRepository = updateManagerRepository;
    }
    @Autowired
    private void setMocrovider(MockProvider mockProvider) {
        this.mockProvider = mockProvider;
    }

}
