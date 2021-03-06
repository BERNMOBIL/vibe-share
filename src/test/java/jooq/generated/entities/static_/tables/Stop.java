/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.Keys;
import jooq.generated.entities.static_.Public;
import jooq.generated.entities.static_.tables.records.StopRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Stop extends TableImpl<StopRecord> {

    private static final long serialVersionUID = 1428787277;

    /**
     * The reference instance of <code>public.stop</code>
     */
    public static final Stop STOP = new Stop();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StopRecord> getRecordType() {
        return StopRecord.class;
    }

    /**
     * The column <code>public.stop.id</code>.
     */
    public final TableField<StopRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.stop.name</code>.
     */
    public final TableField<StopRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.stop.area</code>.
     */
    public final TableField<StopRecord, UUID> AREA = createField("area", org.jooq.impl.SQLDataType.UUID, this, "");

    /**
     * The column <code>public.stop.update</code>.
     */
    public final TableField<StopRecord, Timestamp> UPDATE = createField("update", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>public.stop</code> table reference
     */
    public Stop() {
        this("stop", null);
    }

    /**
     * Create an aliased <code>public.stop</code> table reference
     */
    public Stop(String alias) {
        this(alias, STOP);
    }

    private Stop(String alias, Table<StopRecord> aliased) {
        this(alias, aliased, null);
    }

    private Stop(String alias, Table<StopRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<StopRecord> getPrimaryKey() {
        return Keys.STOP_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StopRecord>> getKeys() {
        return Arrays.<UniqueKey<StopRecord>>asList(Keys.STOP_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<StopRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StopRecord, ?>>asList(Keys.STOP__STOP_AREA_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stop as(String alias) {
        return new Stop(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Stop rename(String name) {
        return new Stop(name, null);
    }
}
