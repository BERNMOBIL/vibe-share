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
 * Repository class to operate on all tables of the schedule database. It is used to delete rows with an outdated
 * {@link Timestamp}.
 */
@Repository
public class UpdateManagerRepository {
    private final DSLContext dslContext;

    public UpdateManagerRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void truncate(String tableName) {
        dslContext.truncate(table(tableName)).execute();
    }

    public void deleteUpdatesWithInvalidTimestamp(String tableName, List<Timestamp> lastUpdates) {
        DeleteWhereStep<Record> deleteQuery = dslContext.delete(table(tableName));
        for (Timestamp timestamp : lastUpdates) {
            deleteQuery.where(field("update").notEqual(timestamp));
        }
        deleteQuery.execute();
    }

    public void deleteUpdatesWithInvalidTimestamp(String[] tableNames, List<Timestamp> lastUpdates) {
        for (String table : tableNames) {
            deleteUpdatesWithInvalidTimestamp(table, lastUpdates);
        }
    }

    public void deleteByUpdateTimestamp(String tableName, Timestamp updateTimestamp, String timestampColumn) {
        dslContext.delete(table(tableName))
                .where(field(timestampColumn).eq(updateTimestamp))
                .execute();
    }

    public void deleteByUpdateTimestamp(String[] tables, Timestamp updateTimestamp) {
        for (String table : tables) {
            deleteByUpdateTimestamp(table, updateTimestamp, "update");
        }
    }
}
