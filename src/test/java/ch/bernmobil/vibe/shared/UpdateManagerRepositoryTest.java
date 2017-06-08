package ch.bernmobil.vibe.shared;

import org.jooq.Query;
import org.jooq.tools.jdbc.Mock;
import org.jooq.tools.jdbc.MockDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("testConfiguration")
public class UpdateManagerRepositoryTest {
    private UpdateManagerRepository updateManagerRepository;
    private MockProvider mockProvider;

    @Test
    public void prepareUpdateTest() {
        final String expectedQuery = "TRUNCATE TABLE SCHEDULE_UPDATE";
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateManagerRepository.truncate("SCHEDULE_UPDATE");

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
    }

    @Test
    public void deleteInvalidUpdatesTest() {
        final String expectedQuery = "DELETE FROM SCHEDULE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))";
        List<Timestamp> validTimestamps = new ArrayList<>();
        validTimestamps.add(Timestamp.valueOf("2017-06-02 15:48:05"));
        validTimestamps.add(Timestamp.valueOf("2017-06-03 15:48:05"));
        validTimestamps.add(Timestamp.valueOf("2017-06-04 15:48:05"));
        String tableName = "SCHEDULE";
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateManagerRepository.deleteUpdatesWithInvalidTimestamp(tableName, validTimestamps);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(3, queryCollector.bindings.size());
        Assert.assertEquals(validTimestamps.get(0), queryCollector.bindings.get(0));
        Assert.assertEquals(validTimestamps.get(1), queryCollector.bindings.get(1));
        Assert.assertEquals(validTimestamps.get(2), queryCollector.bindings.get(2));
    }

    @Test
    public void deleteInvalidUpdatesByMultipleTablesTest() {
        final String expectedQueries[] = {
                "DELETE FROM SCHEDULE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM SCHEDULE_UPDATE WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))",
                "DELETE FROM JOURNEY WHERE (UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP) AND UPDATE <> CAST(? AS TIMESTAMP))"
        };
        List<Timestamp> validTimestamps = new ArrayList<>();
        validTimestamps.add(Timestamp.valueOf("2017-06-02 15:48:05"));
        validTimestamps.add(Timestamp.valueOf("2017-06-03 15:48:05"));
        validTimestamps.add(Timestamp.valueOf("2017-06-04 15:48:05"));
        final String[] tableNames = {"SCHEDULE", "SCHEDULE_UPDATE", "JOURNEY"};
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateManagerRepository.deleteUpdatesWithInvalidTimestamp(tableNames, validTimestamps);

        Assert.assertEquals(3, queryCollector.queries.size());
        Assert.assertEquals(expectedQueries[0], queryCollector.queries.get(0));
        Assert.assertEquals(expectedQueries[1], queryCollector.queries.get(1));
        Assert.assertEquals(expectedQueries[2], queryCollector.queries.get(2));
        Assert.assertEquals(3, queryCollector.bindings.size());
        Assert.assertEquals(validTimestamps.get(0), queryCollector.bindings.get(0));
        Assert.assertEquals(validTimestamps.get(1), queryCollector.bindings.get(1));
        Assert.assertEquals(validTimestamps.get(2), queryCollector.bindings.get(2));
    }

    @Test
    public void deleteByUpdateTimestampTest() {
        final String expectedQuery = "DELETE FROM SCHEDULE WHERE UPDATE = CAST(? AS TIMESTAMP)";
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final String tableName = "SCHEDULE";
        final String timestampColumn = "UPDATE";

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateManagerRepository.deleteByUpdateTimestamp(tableName, timestamp, timestampColumn);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(timestamp, queryCollector.bindings.get(0));
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

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateManagerRepository.deleteByUpdateTimestamp(tableNames, timestamp);

        Assert.assertEquals(3, queryCollector.queries.size());
        Assert.assertEquals(expectedQueries[0], queryCollector.queries.get(0));
        Assert.assertEquals(expectedQueries[1], queryCollector.queries.get(1));
        Assert.assertEquals(expectedQueries[2], queryCollector.queries.get(2));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(timestamp, queryCollector.bindings.get(0));
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
