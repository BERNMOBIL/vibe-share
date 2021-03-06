/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.tables.Journey;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class JourneyRecord extends UpdatableRecordImpl<JourneyRecord> implements Record5<UUID, String, UUID, UUID, Timestamp> {

    private static final long serialVersionUID = -21514542;

    /**
     * Setter for <code>public.journey.id</code>.
     */
    public JourneyRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.journey.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.journey.headsign</code>.
     */
    public JourneyRecord setHeadsign(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.journey.headsign</code>.
     */
    public String getHeadsign() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.journey.route</code>.
     */
    public JourneyRecord setRoute(UUID value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.journey.route</code>.
     */
    public UUID getRoute() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>public.journey.terminal_station</code>.
     */
    public JourneyRecord setTerminalStation(UUID value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.journey.terminal_station</code>.
     */
    public UUID getTerminalStation() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.journey.update</code>.
     */
    public JourneyRecord setUpdate(Timestamp value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.journey.update</code>.
     */
    public Timestamp getUpdate() {
        return (Timestamp) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UUID, String, UUID, UUID, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UUID, String, UUID, UUID, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Journey.JOURNEY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Journey.JOURNEY.HEADSIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field3() {
        return Journey.JOURNEY.ROUTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field4() {
        return Journey.JOURNEY.TERMINAL_STATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return Journey.JOURNEY.UPDATE;
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
        return getHeadsign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value3() {
        return getRoute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value4() {
        return getTerminalStation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord value2(String value) {
        setHeadsign(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord value3(UUID value) {
        setRoute(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord value4(UUID value) {
        setTerminalStation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord value5(Timestamp value) {
        setUpdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyRecord values(UUID value1, String value2, UUID value3, UUID value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JourneyRecord
     */
    public JourneyRecord() {
        super(Journey.JOURNEY);
    }

    /**
     * Create a detached, initialised JourneyRecord
     */
    public JourneyRecord(UUID id, String headsign, UUID route, UUID terminalStation, Timestamp update) {
        super(Journey.JOURNEY);

        set(0, id);
        set(1, headsign);
        set(2, route);
        set(3, terminalStation);
        set(4, update);
    }
}
