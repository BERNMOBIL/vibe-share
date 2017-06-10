package ch.bernmobil.vibe.shared;

import org.jooq.DSLContext;
import org.jooq.DeleteWhereStep;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * Repository class to operate on all tables of the schedule database. It is used to delete rows with an outdated {@link Timestamp}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@Repository
public class UpdateManagerRepository {
    private final DSLContext dslContext;

    /**
     * Constructs an instance using a {@link DSLContext}
     * @param dslContext Object of the JOOQ Query Builder to access the database
     */
    public UpdateManagerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    /**
     * Truncates a database-table using the JOOQ {@link DSLContext}
     * @param tableName table to truncate
     */
    public void truncate(String tableName) {
        dslContext.truncate(table(tableName)).execute();
    }

    /**
     * Deletes all entries from a table which doesn't have one of the passed timestamp as update-timestamp
     * @param tableName table in which entries should be deleted
     * @param validTimestamps entries with this timestamp as update-timestamp will <u>not</u> be deleted
     */
    public void deleteUpdatesWithInvalidTimestamp(String tableName, List<Timestamp> validTimestamps) {
        DeleteWhereStep<Record> deleteQuery = dslContext.delete(table(tableName));
        for (Timestamp timestamp : validTimestamps) {
            deleteQuery.where(field("update").notEqual(timestamp));
        }
        deleteQuery.execute();
    }

    /**
     * Deletes all entries from multiple tables which doesn't have one of the passed timestamp as update-timestamp
     * @param tableNames tables in which entries should be deleted
     * @param validTimestamps entries with this timestamp as update-timestamp will <u>not</u> be deleted
     */
    public void deleteUpdatesWithInvalidTimestamp(String[] tableNames, List<Timestamp> validTimestamps) {
        for (String table : tableNames) {
            deleteUpdatesWithInvalidTimestamp(table, validTimestamps);
        }
    }

    /**
     * Deletes all entries from a table which have the passed timestamp as update-timestamp
     * @param tableName table in which entries should be deleted
     * @param timestamp entries with this timestamp as update-timestamp will be deleted
     * @param timestampColumn column-name of the update-timestamp
     */
    public void deleteByUpdateTimestamp(String tableName, Timestamp timestamp, String timestampColumn) {
        dslContext.delete(table(tableName))
                .where(field(timestampColumn).eq(timestamp))
                .execute();
    }

    /**
     * Deletes all entries from multiple tables which have the passed timestamp as update-timestamp

     * @param tables tables in which entries should be deleted
     * @param timestamp entries with this timestamp as update-timestamp will be deleted
     * @param timestampColumn column-name of the update-timestamp
     */
    public void deleteByUpdateTimestamp(String[] tables, Timestamp timestamp, String timestampColumn) {
        for (String table : tables) {
            deleteByUpdateTimestamp(table, timestamp, timestampColumn);
        }
    }
}
