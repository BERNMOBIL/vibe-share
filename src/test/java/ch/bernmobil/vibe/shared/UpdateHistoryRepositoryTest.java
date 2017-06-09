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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("testConfiguration")
public class UpdateHistoryRepositoryTest {
    private UpdateHistoryRepository updateHistoryRepository;
    private MockProvider mockProvider;

    @Before
    public void beforeTest() {
        mockProvider.actLikeUpdateHistoryIsEmpty = false;
    }

    @Test
    public void findUpdateHistoryEntryByTimestampTest() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP) LIMIT ?";
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(timestamp, UpdateManager.Status.FAILED);
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        UpdateHistoryEntry actualResult = updateHistoryRepository.findByTimestamp(timestamp);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(2, queryCollector.bindings.size());
        Assert.assertEquals(timestamp, queryCollector.bindings.get(0));
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastSuccessUpdateHistoryEntryTest() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?";
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastSuccessUpdate();

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(2, queryCollector.bindings.size());
        Assert.assertEquals(UpdateManager.Status.SUCCESS.toString(), queryCollector.bindings.get(0));
        Assert.assertEquals(1, queryCollector.bindings.get(1));
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastUpdate() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?";
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastUpdate();

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(1, queryCollector.bindings.get(0));
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }
    @Test
    public void findLastWithEmptyDatabaseUpdate() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?";
        final UpdateHistoryEntry expectedResult = null;
        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        mockProvider.actLikeUpdateHistoryIsEmpty = true;
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastUpdate();

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(1, queryCollector.bindings.get(0));
        Assert.assertNull(actualResult);
    }

    @Test
    public void findLatestNUpdates() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?";
        final int numUpdates = 2;
        final UpdateHistoryEntry[] expectedResult = {
                new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS),
                new UpdateHistoryEntry(Timestamp.valueOf("2017-06-03 15:48:05"), UpdateManager.Status.IN_PROGRESS),
        };

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        List<UpdateHistoryEntry> actualResult = updateHistoryRepository.findLatestNUpdates(numUpdates);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(numUpdates, queryCollector.bindings.get(0));
        Assert.assertEquals(expectedResult[0].getTime(), actualResult.get(0).getTime());
        Assert.assertEquals(expectedResult[0].getStatus(), actualResult.get(0).getStatus());
        Assert.assertEquals(expectedResult[1].getTime(), actualResult.get(1).getTime());
        Assert.assertEquals(expectedResult[1].getStatus(), actualResult.get(1).getStatus());
    }
    @Test
    public void findLatestNUpdatesWithEmtpyDatabase() {
        final String expectedQuery = "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?";
        final int numUpdates = 2;

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        mockProvider.actLikeUpdateHistoryIsEmpty = true;
        List<UpdateHistoryEntry> actualResult = updateHistoryRepository.findLatestNUpdates(numUpdates);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(1, queryCollector.bindings.size());
        Assert.assertEquals(numUpdates, queryCollector.bindings.get(0));
        Assert.assertTrue(actualResult.isEmpty());
    }

    @Test
    public void insertValidSuccessUpdateHistoryTest() {
        final String expectedQuery = "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)";
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.SUCCESS);

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateHistoryRepository.insert(updateHistoryEntry);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(2, queryCollector.bindings.size());
        Assert.assertEquals(timestamp, queryCollector.bindings.get(0));
        Assert.assertEquals(UpdateManager.Status.SUCCESS.toString(), queryCollector.bindings.get(1));
    }
    @Test
    public void insertValidInProgressUpdateHistoryTest() {
        final String expectedQuery = "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)";
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.IN_PROGRESS);

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateHistoryRepository.insert(updateHistoryEntry);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(2, queryCollector.bindings.size());
        Assert.assertEquals(timestamp, queryCollector.bindings.get(0));
        Assert.assertEquals(UpdateManager.Status.IN_PROGRESS.toString(), queryCollector.bindings.get(1));
    }

    @Test
    public void updateHistoryEntryTest() {
        final String expectedQuery = "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)";
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.IN_PROGRESS);

        QueryCollector queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        updateHistoryRepository.update(updateHistoryEntry);

        Assert.assertEquals(1, queryCollector.queries.size());
        Assert.assertEquals(expectedQuery, queryCollector.queries.get(0));
        Assert.assertEquals(2, queryCollector.bindings.size());
        Assert.assertEquals(UpdateManager.Status.IN_PROGRESS.toString(), queryCollector.bindings.get(0));
        Assert.assertEquals(timestamp, queryCollector.bindings.get(1));
    }


    @Autowired
    private void setUpdateHistoryRepository(UpdateHistoryRepository updateHistoryRepository) {
        this.updateHistoryRepository = updateHistoryRepository;
    }

    @Autowired
    private void setMockProvider(MockProvider mockProvider) {
        this.mockProvider = mockProvider;
    }

}
