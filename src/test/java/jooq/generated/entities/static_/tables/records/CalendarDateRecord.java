/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.records;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.tables.CalendarDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class CalendarDateRecord extends UpdatableRecordImpl<CalendarDateRecord> implements Record6<UUID, String, Date, Date, UUID, Timestamp> {

    private static final long serialVersionUID = 1079897748;

    /**
     * Setter for <code>public.calendar_date.id</code>.
     */
    public CalendarDateRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.calendar_date.days</code>.
     */
    public CalendarDateRecord setDays(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.days</code>.
     */
    public String getDays() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.calendar_date.valid_from</code>.
     */
    public CalendarDateRecord setValidFrom(Date value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.valid_from</code>.
     */
    public Date getValidFrom() {
        return (Date) get(2);
    }

    /**
     * Setter for <code>public.calendar_date.valid_until</code>.
     */
    public CalendarDateRecord setValidUntil(Date value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.valid_until</code>.
     */
    public Date getValidUntil() {
        return (Date) get(3);
    }

    /**
     * Setter for <code>public.calendar_date.journey</code>.
     */
    public CalendarDateRecord setJourney(UUID value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.journey</code>.
     */
    public UUID getJourney() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.calendar_date.update</code>.
     */
    public CalendarDateRecord setUpdate(Timestamp value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.calendar_date.update</code>.
     */
    public Timestamp getUpdate() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, String, Date, Date, UUID, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<UUID, String, Date, Date, UUID, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return CalendarDate.CALENDAR_DATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CalendarDate.CALENDAR_DATE.DAYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field3() {
        return CalendarDate.CALENDAR_DATE.VALID_FROM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field4() {
        return CalendarDate.CALENDAR_DATE.VALID_UNTIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field5() {
        return CalendarDate.CALENDAR_DATE.JOURNEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return CalendarDate.CALENDAR_DATE.UPDATE;
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
        return getDays();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value3() {
        return getValidFrom();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value4() {
        return getValidUntil();
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
    public Timestamp value6() {
        return getUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value2(String value) {
        setDays(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value3(Date value) {
        setValidFrom(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value4(Date value) {
        setValidUntil(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value5(UUID value) {
        setJourney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord value6(Timestamp value) {
        setUpdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarDateRecord values(UUID value1, String value2, Date value3, Date value4, UUID value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CalendarDateRecord
     */
    public CalendarDateRecord() {
        super(CalendarDate.CALENDAR_DATE);
    }

    /**
     * Create a detached, initialised CalendarDateRecord
     */
    public CalendarDateRecord(UUID id, String days, Date validFrom, Date validUntil, UUID journey, Timestamp update) {
        super(CalendarDate.CALENDAR_DATE);

        set(0, id);
        set(1, days);
        set(2, validFrom);
        set(3, validUntil);
        set(4, journey);
        set(5, update);
    }
}
