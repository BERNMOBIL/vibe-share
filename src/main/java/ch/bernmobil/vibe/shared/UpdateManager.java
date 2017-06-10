package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.contract.*;
import ch.bernmobil.vibe.shared.entity.UpdateHistory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Manages an Static-Update and its dependencies
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@Component
public class UpdateManager {
    private final UpdateManagerRepository mapperRepository;
    private final UpdateManagerRepository staticRepository;
    /**
     * Tables to delete in the static-database if something goes wrong with an update and a clean-up has to be done
     */
    private static final String[] TABLES_TO_DELETE = {ScheduleContract.TABLE_NAME,
            CalendarDateContract.TABLE_NAME,
            CalendarExceptionContract.TABLE_NAME,
            JourneyContract.TABLE_NAME,
            RouteContract.TABLE_NAME,
            StopContract.TABLE_NAME,
            AreaContract.TABLE_NAME};
    /**
     * Tables to delete in the mappings-database if something goes wrong with an update and a clean-up has to be done
     */
    private static final String[] MAPPING_TABLES_TO_DELETE = { AreaMapperContract.TABLE_NAME,
            CalendarDateMapperContract.TABLE_NAME,
            JourneyMapperContract.TABLE_NAME,
            RouteMapperContract.TABLE_NAME,
            StopMapperContract.TABLE_NAME};
    private static final String updateTimestampColumn = "update";
    private final int updateHistoryLength;
    private final long updateTimeoutThresholdInMilliseconds;
    private final UpdateHistoryRepository updateHistoryRepository;
    private final UpdateTimestampManager updateTimestampManager;

    public enum Status {IN_PROGRESS, SUCCESS, FAILED}

    /**
     * Constructs an UpdateManager with all its dependencies.
     * @param mapperRepository used to access mapping database.
     * @param staticRepository used to access schedule database.
     * @param updateHistoryRepository used to operate on "update_history" table.
     * @param updateHistoryLength number of successful updates to keep in database.
     * @param updateTimeoutThreshold threshold to identify a failed update marked as {@link UpdateManager.Status#IN_PROGRESS}
     * @param updateTimestampManager to get and set current timestamp
     */
    public UpdateManager(UpdateManagerRepository mapperRepository,
                         UpdateManagerRepository staticRepository,
                         UpdateHistoryRepository updateHistoryRepository,
                         int updateHistoryLength,
                         Duration updateTimeoutThreshold,
                         UpdateTimestampManager updateTimestampManager) {
        this.mapperRepository = mapperRepository;
        this.staticRepository = staticRepository;
        this.updateHistoryRepository = updateHistoryRepository;
        this.updateHistoryLength = updateHistoryLength;
        this.updateTimeoutThresholdInMilliseconds = updateTimeoutThreshold.toMillis();
        this.updateTimestampManager = updateTimestampManager;
    }

    /**
     * Adds a new entry to the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory}-table with the status "{@link Status#IN_PROGRESS}"
     * @return {@link Timestamp} of the new version of data.
     */
    public Timestamp prepareUpdate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        UpdateHistory newEntry = new UpdateHistory(now, Status.IN_PROGRESS);
        updateHistoryRepository.insert(newEntry);
        return now;
    }

    /**
     * Saves a timestamp using the {@link UpdateTimestampManager}
     * @param timestamp to be saved
     */
    public void startUpdate(Timestamp timestamp) {
        updateTimestampManager.setActiveUpdateTimestamp(timestamp);
    }

    /**
     * Deletes all entries with another update-{@link Timestamp} then the newest {@link #updateHistoryLength} entries in
     * the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table with the status {@link Status#SUCCESS}
     * <p>Notice: This can be used after a successful update to clean up old data</p>
     */
    public void cleanOldData() {
        List<UpdateHistory> latestUpdates = updateHistoryRepository.findLatestNSuccessfulUpdates(updateHistoryLength);
        List<Timestamp> latestUpdatesTimestamps = latestUpdates.stream().map(UpdateHistory::getTime).collect(toList());
        staticRepository.truncate(ScheduleUpdateContract.TABLE_NAME);
        staticRepository.deleteUpdatesWithInvalidTimestamp(TABLES_TO_DELETE, latestUpdatesTimestamps);
        mapperRepository.deleteUpdatesWithInvalidTimestamp(MAPPING_TABLES_TO_DELETE, latestUpdatesTimestamps);
    }

    /**
     * Deletes already inserted data from an update before it failed
     */
    public void repairFailedUpdate() {
        if(updateTimestampManager.getActiveUpdateTimestamp() != null) {
            Timestamp failedUpdateTimestamp = updateTimestampManager.getActiveUpdateTimestamp();
            staticRepository.deleteByUpdateTimestamp(TABLES_TO_DELETE, failedUpdateTimestamp, updateTimestampColumn);
            mapperRepository.deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, failedUpdateTimestamp, updateTimestampColumn);
        }
    }

    /**
     * Checks if another static-importer is running by checking the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table.
     * <p>Notice: If a collision is detected, it is further controlled if the collidating update-timestamp is below the defined {@link #updateTimeoutThresholdInMilliseconds}</p>
     * @return true if collision
     */
    public boolean hasUpdateCollision() {
        UpdateHistory lastUpdate = updateHistoryRepository.findLastUpdate();
        if(lastUpdate != null && lastUpdate.getStatus().equals(Status.IN_PROGRESS)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            long timeDiff = now.getTime() - lastUpdate.getTime().getTime();
            if(timeDiff > updateTimeoutThresholdInMilliseconds) {
                lastUpdate.setStatus(Status.FAILED);
                updateHistoryRepository.update(lastUpdate);
                staticRepository.deleteByUpdateTimestamp(TABLES_TO_DELETE, lastUpdate.getTime(), updateTimestampColumn);
                mapperRepository.deleteByUpdateTimestamp(MAPPING_TABLES_TO_DELETE, lastUpdate.getTime(), updateTimestampColumn);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Changes the status of the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory}-entry marked by the {@link UpdateTimestampManager}
     * @param status to be set
     */
    public void setStatus(Status status) {
        UpdateHistory element = updateHistoryRepository.findByTimestamp(updateTimestampManager.getActiveUpdateTimestamp());
        element.setStatus(status);
        updateHistoryRepository.update(element);
    }

    /**
     * Deletes an entry from the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table by a given timestamp
     * @param timestamp which will be removed
     */
    public void removeUpdateByTimestamp(Timestamp timestamp) {
        staticRepository.deleteByUpdateTimestamp(UpdateHistoryContract.TABLE_NAME, timestamp, UpdateHistoryContract.TIME);
    }

    /**
     * Get count of rows in the "update_history" table
     * @return Number of rows
     */
    public int getRowCount() {
        return updateHistoryRepository.count();
    }
}
