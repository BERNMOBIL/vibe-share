/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.records;


import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.tables.JourneyDisruption;

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
public class JourneyDisruptionRecord extends UpdatableRecordImpl<JourneyDisruptionRecord> implements Record5<UUID, String, Timestamp, Timestamp, UUID> {

    private static final long serialVersionUID = 1465435471;

    /**
     * Setter for <code>public.journey_disruption.id</code>.
     */
    public JourneyDisruptionRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.journey_disruption.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.journey_disruption.message</code>.
     */
    public JourneyDisruptionRecord setMessage(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.journey_disruption.message</code>.
     */
    public String getMessage() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.journey_disruption.start</code>.
     */
    public JourneyDisruptionRecord setStart(Timestamp value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.journey_disruption.start</code>.
     */
    public Timestamp getStart() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.journey_disruption.planned_end</code>.
     */
    public JourneyDisruptionRecord setPlannedEnd(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.journey_disruption.planned_end</code>.
     */
    public Timestamp getPlannedEnd() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>public.journey_disruption.journey</code>.
     */
    public JourneyDisruptionRecord setJourney(UUID value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.journey_disruption.journey</code>.
     */
    public UUID getJourney() {
        return (UUID) get(4);
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
    public Row5<UUID, String, Timestamp, Timestamp, UUID> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UUID, String, Timestamp, Timestamp, UUID> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return JourneyDisruption.JOURNEY_DISRUPTION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JourneyDisruption.JOURNEY_DISRUPTION.MESSAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return JourneyDisruption.JOURNEY_DISRUPTION.START;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return JourneyDisruption.JOURNEY_DISRUPTION.PLANNED_END;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field5() {
        return JourneyDisruption.JOURNEY_DISRUPTION.JOURNEY;
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
        return getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getPlannedEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value5() {
        return getJourney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord value2(String value) {
        setMessage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord value3(Timestamp value) {
        setStart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord value4(Timestamp value) {
        setPlannedEnd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord value5(UUID value) {
        setJourney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JourneyDisruptionRecord values(UUID value1, String value2, Timestamp value3, Timestamp value4, UUID value5) {
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
     * Create a detached JourneyDisruptionRecord
     */
    public JourneyDisruptionRecord() {
        super(JourneyDisruption.JOURNEY_DISRUPTION);
    }

    /**
     * Create a detached, initialised JourneyDisruptionRecord
     */
    public JourneyDisruptionRecord(UUID id, String message, Timestamp start, Timestamp plannedEnd, UUID journey) {
        super(JourneyDisruption.JOURNEY_DISRUPTION);

        set(0, id);
        set(1, message);
        set(2, start);
        set(3, plannedEnd);
        set(4, journey);
    }
}
