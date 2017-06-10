package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.UpdateManager.Status;
import ch.bernmobil.vibe.shared.entity.UpdateHistory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@ActiveProfiles("testConfiguration")
public class UpdateHistoryRepositoryTest {
    private UpdateHistoryRepository updateHistoryRepository;
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
    public void findUpdateHistoryByTimestampTest() {
        final Timestamp timestamp = Timestamp.valueOf("2017-06-02 15:48:05");
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY WHERE TIME = CAST(? AS TIMESTAMP) LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {timestamp, 1}
        };
        final UpdateHistory expectedResult = new UpdateHistory(timestamp, UpdateManager.Status.FAILED);
        UpdateHistory actualResult = updateHistoryRepository.findByTimestamp(timestamp);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastSuccessUpdateHistoryTest() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {Status.SUCCESS.toString(), 1}
        };
        final UpdateHistory expectedResult = new UpdateHistory(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        UpdateHistory actualResult = updateHistoryRepository.findLastSuccessUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        Assert.assertEquals(expectedResult.getTime(), actualResult.getTime());
    }

    @Test
    public void findLastSuccessUpdateHistoryWithEmptyDatabaseTest() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {Status.SUCCESS.toString(), 1}
        };
        mockProvider.actLikeUpdateHistoryIsEmpty();
        UpdateHistory actualResult = updateHistoryRepository.findLastSuccessUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertNull(actualResult);
    }

    @Test
    public void findLastUpdate() {
        final String[] expectedQueries = {
            "SELECT * FROM UPDATE_HISTORY ORDER BY TIME DESC LIMIT ?"
        };
        final UpdateHistory expectedResult = new UpdateHistory(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS);
        final Object[][] expectedBindings = {
            {1}
        };
        UpdateHistory actualResult = updateHistoryRepository.findLastUpdate();

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
        mockProvider.actLikeUpdateHistoryIsEmpty();
        UpdateHistory actualResult = updateHistoryRepository.findLastUpdate();

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
        Assert.assertNull(actualResult);
    }

    @Test
    public void findLatestNSuccessfulUpdates() {
        final String[] expectedQueries = {
                "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {"SUCCESS", 1}
        };
        final int numUpdates = 2;
        final UpdateHistory[] expectedResult = {
                new UpdateHistory(Timestamp.valueOf("2017-06-04 15:48:05"), UpdateManager.Status.SUCCESS),
                new UpdateHistory(Timestamp.valueOf("2017-06-03 15:48:05"), UpdateManager.Status.IN_PROGRESS),
        };

        List<UpdateHistory> actualResult = updateHistoryRepository.findLatestNSuccessfulUpdates(numUpdates);

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
            "SELECT * FROM UPDATE_HISTORY WHERE STATUS = ? ORDER BY TIME DESC LIMIT ?"
        };
        final Object[][] expectedBindings = {
            {"SUCCESS", 1}
        };
        final int numUpdates = 2;

        mockProvider.actLikeUpdateHistoryIsEmpty();
        List<UpdateHistory> actualResult = updateHistoryRepository.findLatestNSuccessfulUpdates(numUpdates);

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
        final UpdateHistory updateHistory = new UpdateHistory(timestamp, UpdateManager.Status.SUCCESS);

        updateHistoryRepository.insert(updateHistory);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }
    @Test
    public void insertValidInProgressUpdateHistoryTest() {
        final Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistory updateHistory = new UpdateHistory(timestamp, UpdateManager.Status.IN_PROGRESS);
        final String[] expectedQueries = {
            "INSERT INTO UPDATE_HISTORY (TIME, STATUS) VALUES (CAST(? AS TIMESTAMP), ?)"
        };
        final Object[][] expectedBindings = {
            {timestamp, Status.SUCCESS.toString()}
        };

        updateHistoryRepository.insert(updateHistory);

        testHelper.assertQueries(expectedQueries);
        testHelper.assertBindings(expectedBindings);
    }

    @Test
    public void updateHistoryTest() {
        Timestamp timestamp = Timestamp.valueOf("2017-06-04 15:48:05");
        final UpdateHistory updateHistory = new UpdateHistory(timestamp, UpdateManager.Status.IN_PROGRESS);
        final String[] expectedQueries = {
            "UPDATE UPDATE_HISTORY SET STATUS = ? WHERE TIME = CAST(? AS TIMESTAMP)"
        };
        final Object[][] expectedBindings = {
            {Status.IN_PROGRESS.toString(), timestamp}
        };

        updateHistoryRepository.update(updateHistory);

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
