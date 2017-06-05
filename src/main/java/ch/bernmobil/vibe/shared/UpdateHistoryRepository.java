package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.contract.UpdateHistoryContract;
import ch.bernmobil.vibe.shared.entitiy.UpdateHistory;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ch.bernmobil.vibe.shared.UpdateManager.Status;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class UpdateHistoryRepository {
    private final DSLContext dslContext;

    public UpdateHistoryRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public UpdateHistoryEntry findByTimestamp(Timestamp timestamp) {
        return dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .where(field(UpdateHistoryContract.TIME).equal(timestamp))
                .fetchOne()
                .into(UpdateHistoryEntry.class);
    }

    public UpdateHistoryEntry findLastSuccessUpdate() {
        return dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .where(field(UpdateHistoryContract.STATUS).eq(Status.SUCCESS.toString()))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(1)
                .fetchOne()
                .into(UpdateHistoryEntry.class);
    }

    public UpdateHistoryEntry findLastUpdate() {
        Record record = dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(1)
                .fetchAny();
        if(record != null) {
            return record.into(UpdateHistoryEntry.class);
        }
        return null;
    }

    public List<UpdateHistoryEntry> findLatestNUpdates(int num) {
        return dslContext.selectFrom(table(UpdateHistoryContract.TABLE_NAME))
                .orderBy(field(UpdateHistoryContract.TIME).desc())
                .limit(num)
                .fetch()
                .into(UpdateHistoryEntry.class);
    }

    public void insert(UpdateHistoryEntry updateHistoryEntry) {
        Collection<Field<?>> fields = Arrays.stream(UpdateHistoryContract.COLUMNS)
                .map(DSL::field)
                .collect(Collectors.toList());
        dslContext.insertInto(table(UpdateHistoryContract.TABLE_NAME), fields)
                .values(updateHistoryEntry.getTime(), updateHistoryEntry.getStatus().toString())
                .execute();
    }

    public void update(UpdateHistoryEntry element) {
        dslContext.update(table(UpdateHistoryContract.TABLE_NAME))
                .set(field(UpdateHistoryContract.STATUS), element.getStatus().toString())
                .where(field(UpdateHistoryContract.TIME).equal(element.getTime()))
                .execute();
    }

    public int count() {
        return dslContext.selectCount()
                .from(table(UpdateHistoryContract.TABLE_NAME))
                .fetchOne()
                .into(Integer.class);
    }
}
