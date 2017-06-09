/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.tables.Stop;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


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
public class StopRecord extends UpdatableRecordImpl<StopRecord> implements Record4<UUID, String, UUID, Timestamp> {

    private static final long serialVersionUID = -1773055074;

    /**
     * Setter for <code>public.stop.id</code>.
     */
    public StopRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.stop.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.stop.name</code>.
     */
    public StopRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.stop.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.stop.area</code>.
     */
    public StopRecord setArea(UUID value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.stop.area</code>.
     */
    public UUID getArea() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>public.stop.update</code>.
     */
    public StopRecord setUpdate(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.stop.update</code>.
     */
    public Timestamp getUpdate() {
        return (Timestamp) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, UUID, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UUID, String, UUID, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Stop.STOP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Stop.STOP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field3() {
        return Stop.STOP.AREA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return Stop.STOP.UPDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value3() {
        return getArea();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopRecord value3(UUID value) {
        setArea(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopRecord value4(Timestamp value) {
        setUpdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StopRecord values(UUID value1, String value2, UUID value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StopRecord
     */
    public StopRecord() {
        super(Stop.STOP);
    }

    /**
     * Create a detached, initialised StopRecord
     */
    public StopRecord(UUID id, String name, UUID area, Timestamp update) {
        super(Stop.STOP);

        set(0, id);
        set(1, name);
        set(2, area);
        set(3, update);
    }
}