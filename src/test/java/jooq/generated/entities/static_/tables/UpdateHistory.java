/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.generated.entities.static_.Keys;
import jooq.generated.entities.static_.Public;
import jooq.generated.entities.static_.tables.records.UpdateHistoryRecord;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UpdateHistory extends TableImpl<UpdateHistoryRecord> {

    private static final long serialVersionUID = -1614584778;

    /**
     * The reference instance of <code>public.update_history</code>
     */
    public static final UpdateHistory UPDATE_HISTORY = new UpdateHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UpdateHistoryRecord> getRecordType() {
        return UpdateHistoryRecord.class;
    }

    /**
     * The column <code>public.update_history.time</code>.
     */
    public final TableField<UpdateHistoryRecord, Timestamp> TIME = createField("time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.update_history.status</code>.
     */
    public final TableField<UpdateHistoryRecord, String> STATUS = createField("status", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.update_history</code> table reference
     */
    public UpdateHistory() {
        this("update_history", null);
    }

    /**
     * Create an aliased <code>public.update_history</code> table reference
     */
    public UpdateHistory(String alias) {
        this(alias, UPDATE_HISTORY);
    }

    private UpdateHistory(String alias, Table<UpdateHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private UpdateHistory(String alias, Table<UpdateHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UpdateHistoryRecord> getPrimaryKey() {
        return Keys.UPDATE_HISTORY_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UpdateHistoryRecord>> getKeys() {
        return Arrays.<UniqueKey<UpdateHistoryRecord>>asList(Keys.UPDATE_HISTORY_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateHistory as(String alias) {
        return new UpdateHistory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UpdateHistory rename(String name) {
        return new UpdateHistory(name, null);
    }
}
