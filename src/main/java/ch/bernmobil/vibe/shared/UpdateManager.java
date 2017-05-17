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

    private final UpdateManagerRepository mapperRepository;
    private final UpdateManagerRepository staticRepository;

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

    public UpdateManager(UpdateManagerRepository mapperRepository,
                         UpdateManagerRepository staticRepository,
                         UpdateHistoryRepository updateHistoryRepository,
                         int updateHistoryLength,
                         Duration updateTimeout) {
        this.mapperRepository = mapperRepository;
        this.staticRepository = staticRepository;
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
        staticRepository.truncate(ScheduleUpdateContract.TABLE_NAME);
        staticRepository.deleteUpdatesWithInvalidTimestamp(TABLES_TO_DELETE, latestUpdatesTimestamps);
        mapperRepository.deleteUpdatesWithInvalidTimestamp(MAPPING_TABLES_TO_DELETE, latestUpdatesTimestamps);
    }

    public void repairFailedUpdate() {
        Timestamp failedUpdateTimestamp = UpdateManager.activeUpdateTimestamp;
        //TODO: discuss if history should contain failed update entries
        staticRepository.deleteByUpdateTimestamp(UpdateHistoryContract.TABLE_NAME, failedUpdateTimestamp, UpdateHistoryContract.TIME);
        staticRepository.deleteByUpdateTimestamp(TABLES_TO_DELETE, failedUpdateTimestamp);
        mapperRepository.deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, failedUpdateTimestamp);
    }


    public boolean hasUpdateCollision() {
        UpdateHistoryEntry lastUpdate = updateHistoryRepository.findLastUpdate();
        if(lastUpdate != null && lastUpdate.getStatus().equals(Status.IN_PROGRESS)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            long timeDiff = now.getTime() - lastUpdate.getTime().getTime();
            if(timeDiff > updateTimeoutMilliseconds) {
                lastUpdate.setStatus(Status.FAILED);
                updateHistoryRepository.update(lastUpdate);
                staticRepository.deleteByUpdateTimestamp(TABLES_TO_DELETE, lastUpdate.getTime());
                mapperRepository.deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, lastUpdate.getTime());
                return false;
            }
            return true;
        }
        return false;
    }
}