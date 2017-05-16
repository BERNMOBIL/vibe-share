package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.contract.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UpdateManager {

    private final JdbcTemplate jdbcMapperTemplate;
    private final JdbcTemplate jdbcVibeTemplate;

    private static final String[] TABLES_TO_DELETE = {ScheduleContract.TABLE_NAME,
            CalendarDateContract.TABLE_NAME,
            CalendarExceptionContract.TABLE_NAME,
            JourneyContract.TABLE_NAME,
            RouteContract.TABLE_NAME,
            StopContract.TABLE_NAME,
            AreaContract.TABLE_NAME};

    private static final String[] MAPPING_TABLES_TO_DELETE = { AreaMapperContract.TABLE_NAME,
            CalendarDateMapperContract.TABLE_NAME,
            JourneyMapperContract.TABLE_NAME,
            RouteMapperContract.TABLE_NAME,
            StopMapperContract.TABLE_NAME};

    private final int updateHistoryLength;
    private final long updateTimeoutMilliseconds;

    private final UpdateHistoryRepository updateHistoryRepository;

    //TODO: singleton object
    private static Timestamp activeUpdateTimestamp;
    //TODO
    public enum Status {IN_PROGRESS, SUCCESS, FAILED}

    public UpdateManager(DataSource mapperDataSource,
                         DataSource postgresDataSource,
                         UpdateHistoryRepository updateHistoryRepository,
                         int updateHistoryLength,
                         Duration updateTimeout) {
        jdbcMapperTemplate = new JdbcTemplate(mapperDataSource);
        jdbcVibeTemplate = new JdbcTemplate(postgresDataSource);
        this.updateHistoryRepository = updateHistoryRepository;
        this.updateHistoryLength = updateHistoryLength;
        this.updateTimeoutMilliseconds = updateTimeout.toMillis();
    }

    public void startUpdate() {
        activeUpdateTimestamp = new Timestamp(System.currentTimeMillis());
        UpdateHistoryEntry newEntry = new UpdateHistoryEntry(activeUpdateTimestamp, Status.IN_PROGRESS);
        updateHistoryRepository.insert(newEntry);
    }

    public void cleanOldData() {
        List<UpdateHistoryEntry> latestUpdates = updateHistoryRepository.findLatestNUpdates(updateHistoryLength);
        List<Timestamp> latestUpdatesTimestamps = latestUpdates.stream().map(UpdateHistoryEntry::getTime).collect(toList());
        jdbcVibeTemplate.update(new QueryBuilder().truncate(ScheduleUpdateContract.TABLE_NAME).getQuery());
        deleteUpdatesWithInvalidTimestamp(TABLES_TO_DELETE, latestUpdatesTimestamps, jdbcVibeTemplate);
        deleteUpdatesWithInvalidTimestamp(MAPPING_TABLES_TO_DELETE, latestUpdatesTimestamps, jdbcMapperTemplate);
    }

    public void repairFailedUpdate() {
        Timestamp failedUpdateTimestamp = UpdateManager.activeUpdateTimestamp;
        deleteByUpdateTimestamp(UpdateHistoryContract.TABLE_NAME, failedUpdateTimestamp, jdbcVibeTemplate, UpdateHistoryContract.TIME);
        deleteByUpdateTimestamp(TABLES_TO_DELETE, failedUpdateTimestamp, jdbcVibeTemplate);
        deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, failedUpdateTimestamp, jdbcMapperTemplate);
    }

    private void deleteUpdatesWithInvalidTimestamp(String table, List<Timestamp> lastUpdates, JdbcTemplate jdbcTemplate) {
        ArrayList<QueryBuilder.Predicate> predicates = new ArrayList<>();
        for (Timestamp timestamp : lastUpdates) {
            predicates.add(QueryBuilder.Predicate.notEquals("update", String.format("'%s'", timestamp)));
        }
        QueryBuilder.Predicate predicate = QueryBuilder.Predicate.joinAnd(predicates);
        jdbcTemplate.update(new QueryBuilder()
                        .delete(table)
                        .where(predicate)
                        .getQuery());
    }

    private void deleteUpdatesWithInvalidTimestamp(String[] tables, List<Timestamp> lastUpdates, JdbcTemplate jdbcTemplate) {
        for (String table : tables) {
            deleteUpdatesWithInvalidTimestamp(table, lastUpdates, jdbcTemplate);
        }
    }

    private void deleteByUpdateTimestamp(String table, Timestamp updateTimestamp,
        JdbcTemplate jdbcTemplate, String timestampColumn) {
        jdbcTemplate.update(new QueryBuilder()
                        .delete(table)
                        .where(QueryBuilder.Predicate.equals(timestampColumn, String.format("'%s'", updateTimestamp)))
                        .getQuery());
    }

    private void deleteByUpdateTimestamp(String[] tables, Timestamp updateTimestamp, JdbcTemplate jdbcTemplate) {
        for (String table : tables) {
            deleteByUpdateTimestamp(table, updateTimestamp, jdbcTemplate, "update");
        }
    }

    public boolean hasUpdateCollision() {
        UpdateHistoryEntry lastUpdate = updateHistoryRepository.findLastUpdate();
        if(lastUpdate != null && lastUpdate.getStatus().equals(Status.IN_PROGRESS)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            long timeDiff = now.getTime() - lastUpdate.getTime().getTime();
            if(timeDiff > updateTimeoutMilliseconds) {
                lastUpdate.setStatus(Status.FAILED);
                updateHistoryRepository.update(lastUpdate);
                deleteByUpdateTimestamp(TABLES_TO_DELETE, lastUpdate.getTime(), jdbcVibeTemplate);
                deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, lastUpdate.getTime(), jdbcMapperTemplate);
                return false;
            }
            return true;
        }
        return false;
    }
}
