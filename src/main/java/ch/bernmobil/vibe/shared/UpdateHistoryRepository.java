package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.contract.UpdateHistoryContract;
import ch.bernmobil.vibe.shared.entity.UpdateHistory;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ch.bernmobil.vibe.shared.UpdateManager.Status;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * Database repository for accessing {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} information.
 *
 * @author Oliverio Chiodo
 * @author Matteo Patisso
 */
@Repository
public class UpdateHistoryRepository {
    private final DSLContext dslContext;


    /**
     * Constructs an instance using a {@link DSLContext}
     * @param dslContext Object of the JOOQ Query Builder to access the database
     */
    public UpdateHistoryRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private UpdateHistory recordOrNull(Record record) {
        if(record != null) {
            return record.into(UpdateHistory.class);
        }
        return null;
    }

    /**
     * Fetch one row of the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory}-table with the given timestamp
     * @param timestamp search-criteria.
     * @return {@link UpdateHistory} entry with the given timestamp.
     */
    public UpdateHistory findByTimestamp(Timestamp timestamp) {
        Record record =  dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .where(field(UpdateHistoryContract.TIME).equal(timestamp))
                .limit(1)
                .fetchAny();

        return recordOrNull(record);
    }

    /**
     * Fetch the newest row of the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table with the status {@link UpdateManager.Status#SUCCESS}
     * @return Latest {@link UpdateHistory} entry which is marked as {@link Status#SUCCESS}.
     */
    public UpdateHistory findLastSuccessUpdate() {
        Record record = dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .where(field(UpdateHistoryContract.STATUS).eq(Status.SUCCESS.toString()))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(1)
                .fetchAny();

        return recordOrNull(record);
    }

    /**
     * Fetch the newest row of the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table
     * @return Latest {@link UpdateHistory} entry.
     */
    public UpdateHistory findLastUpdate() {
        Record record = dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(1)
                .fetchAny();

        return recordOrNull(record);
    }

    /**
     * Fetch a number of newest row of the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory}-table with the Status {@link Status#SUCCESS}
     * @param amount of rows which will be returned maximum.
     * @return List {@link UpdateHistory} which were {@link Status#SUCCESS}.
     */
    public List<UpdateHistory> findLatestNSuccessfulUpdates(int amount) {
        return dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .where(field(UpdateHistoryContract.STATUS).eq(Status.SUCCESS.toString()))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(amount)
                .fetch()
                .into(UpdateHistory.class);
    }

    /**
     * Saves an {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} entity in the database
     * @param updateHistoryEntry to be inserted.
     */
    public void insert(UpdateHistory updateHistoryEntry) {
        Collection<Field<?>> fields = Arrays.stream(UpdateHistoryContract.COLUMNS)
                .map(DSL::field)
                .collect(Collectors.toList());
        dslContext.insertInto(table(UpdateHistoryContract.TABLE_NAME), fields)
                .values(updateHistoryEntry.getTime(), updateHistoryEntry.getStatus().toString())
                .execute();
    }

    /**
     * Updates an {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} entity in the database
     * <p>Notice: The {@link ch.bernmobil.vibe.shared.entity.UpdateHistory#time} attribute acts as primary key and identifies the entity to change</p>
     * @param element to be updated.
     */
    public void update(UpdateHistory element) {
        dslContext.update(table(UpdateHistoryContract.TABLE_NAME))
                .set(field(UpdateHistoryContract.STATUS), element.getStatus().toString())
                .where(field(UpdateHistoryContract.TIME).equal(element.getTime()))
                .execute();
    }

    /**
     * Counts the number of rows in the {@link ch.bernmobil.vibe.shared.entity.UpdateHistory} table in the database.
     * <p>Notice: This number is useful for the two-phase-locking used to synchronize multiple importer-instances</p>
     * @return Number of rows.
     */
    public int count() {
        return dslContext.selectCount()
                .from(table(UpdateHistoryContract.TABLE_NAME))
                .fetchOne()
                .into(Integer.class);
    }
}
