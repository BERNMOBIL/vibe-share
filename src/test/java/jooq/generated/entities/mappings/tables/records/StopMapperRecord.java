/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.mappings.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.mappings.tables.StopMapper;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.TableRecordImpl;


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
public class StopMapperRecord extends TableRecordImpl<StopMapperRecord> implements Record3<String, UUID, Timestamp> {

    private static final long serialVersionUID = -391713557;

    /**
     * Setter for <code>public.stop_mapper.gtfs_id</code>.
     */
    public StopMapperRecord setGtfsId(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.stop_mapper.gtfs_id</code>.
     */
    public String getGtfsId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.stop_mapper.id</code>.
     */
    public StopMapperRecord setId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.stop_mapper.id</code>.
     */
    public UUID getId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.stop_mapper.update</code>.
     */
    public StopMapperRecord setUpdate(Timestamp value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.stop_mapper.update</code>.
     */
    public Timestamp getUpdate() {
        return (Timestamp) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, UUID, Timestamp> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, UUID, Timestamp> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return StopMapper.STOP_MAPPER.GTFS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field2() {
        return StopMapper.STOP_MAPPER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return StopMapper.STOP_MAPPER.UPDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getGtfsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value2() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopMapperRecord value1(String value) {
        setGtfsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopMapperRecord value2(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopMapperRecord value3(Timestamp value) {
        setUpdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopMapperRecord values(String value1, UUID value2, Timestamp value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StopMapperRecord
     */
    public StopMapperRecord() {
        super(StopMapper.STOP_MAPPER);
    }

    /**
     * Create a detached, initialised StopMapperRecord
     */
    public StopMapperRecord(String gtfsId, UUID id, Timestamp update) {
        super(StopMapper.STOP_MAPPER);

        set(0, gtfsId);
        set(1, id);
        set(2, update);
    }
}