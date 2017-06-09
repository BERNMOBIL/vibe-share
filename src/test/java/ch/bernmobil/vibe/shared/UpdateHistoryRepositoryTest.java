package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.UpdateManager.Status;
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
    private QueryCollector queryCollector;
    private TestHelper testHelper;

    @Before
    public void beforeTest() {
        queryCollector = new QueryCollector();
        mockProvider.useQueryCollector(queryCollector);
        mockProvider.cleanFlags();
        testHelper = new TestHelper(queryCollector);
    }

    @Test
    public void findUpdateHistoryEntryByTimestampTest() {
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP) LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {timestamp, 1}
        };
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(timestamp, UpdateManager.Status.FAILED);
        UpdateHistoryEntry actualResult = updateHistoryRepository.findByTimestamp(timestamp);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastSuccessUpdateHistoryEntryTest() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {Status.SUCCESS.toString(), 1}
        };
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastSuccessUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastUpdate() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final UpdateHistoryEntry expectedResult = new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        final Object[][] expectedBindings = {
            {1}
        };
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }
    @Test
    public void findLastWithEmptyDatabaseUpdate() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {1}
        };
        mockProvider.actLikeUpdateHistoryIsEmpty = true;
        UpdateHistoryEntry actualResult = updateHistoryRepository.findLastUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertNull(actualResult);
    }

    @Test
    public void findLatestNUpdates() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {1}
        };
        final int numUpdates = 2;
        final UpdateHistoryEntry[] expectedResult = {
                new UpdateHistoryEntry(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS),
                new UpdateHistoryEntry(Timestamp.valueOf("2017-06-03 15:48:05"), UpdateManager.Status.IN_PROGRESS),
        };

        List<UpdateHistoryEntry> actualResult = updateHistoryRepository.findLatestNUpdates(numUpdates);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult[0].getTime(), actualResult.get(0).getTime());
        Assert.assertEquals(expectedResult[0].getStatus(), actualResult.get(0).getStatus());
        Assert.assertEquals(expectedResult[1].getTime(), actualResult.get(1).getTime());
        Assert.assertEquals(expectedResult[1].getStatus(), actualResult.get(1).getStatus());
    }
    @Test
    public void findLatestNUpdatesWithEmtpyDatabase() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {1}
        };
        final int numUpdates = 2;

        mockProvider.actLikeUpdateHistoryIsEmpty = true;
        List<UpdateHistoryEntry> actualResult = updateHistoryRepository.findLatestNUpdates(numUpdates);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertTrue(actualResult.isEmpty());
    }

    @Test
    public void insertValidSuccessUpdateHistoryTest() {
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final String[] expectedQueries = {
            "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)"
        };
        final Object[][] expectedBindings = {
            {timestamp, Status.SUCCESS.toString()}
        };
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.SUCCESS);

        updateHistoryRepository.insert(updateHistoryEntry);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }
    @Test
    public void insertValidInProgressUpdateHistoryTest() {
        final Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.IN_PROGRESS);
        final String[] expectedQueries = {
            "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)"
        };
        final Object[][] expectedBindings = {
            {timestamp, Status.SUCCESS.toString()}
        };

        updateHistoryRepository.insert(updateHistoryEntry);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void updateHistoryEntryTest() {
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistoryEntry updateHistoryEntry = new UpdateHistoryEntry(timestamp, UpdateManager.Status.IN_PROGRESS);
        final String[] expectedQueries = {
            "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)"
        };
        final Object[][] expectedBindings = {
            {Status.IN_PROGRESS.toString(), timestamp}
        };

        updateHistoryRepository.update(updateHistoryEntry);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
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
