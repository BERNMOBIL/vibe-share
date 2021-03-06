/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import jooq.generated.entities.static_.Keys;
import jooq.generated.entities.static_.Public;
import jooq.generated.entities.static_.tables.records.CalendarExceptionRecord;

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
public class CalendarException extends TableImpl<CalendarExceptionRecord> {

    private static final long serialVersionUID = -44733206;

    /**
     * The reference instance of <code>public.calendar_exception</code>
     */
    public static final CalendarException CALENDAR_EXCEPTION = new CalendarException();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CalendarExceptionRecord> getRecordType() {
        return CalendarExceptionRecord.class;
    }

    /**
     * The column <code>public.calendar_exception.id</code>.
     */
    public final TableField<CalendarExceptionRecord, UUID> ID = createField("id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.calendar_exception.date</code>.
     */
    public final TableField<CalendarExceptionRecord, Date> DATE = createField("date", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>public.calendar_exception.type</code>.
     */
    public final TableField<CalendarExceptionRecord, BigDecimal> TYPE = createField("type", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.calendar_exception.calendar_date</code>.
     */
    public final TableField<CalendarExceptionRecord, UUID> CALENDAR_DATE = createField("calendar_date", org.jooq.impl.SQLDataType.UUID, this, "");

    /**
     * The column <code>public.calendar_exception.update</code>.
     */
    public final TableField<CalendarExceptionRecord, Timestamp> UPDATE = createField("update", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>public.calendar_exception</code> table reference
     */
    public CalendarException() {
        this("calendar_exception", null);
    }

    /**
     * Create an aliased <code>public.calendar_exception</code> table reference
     */
    public CalendarException(String alias) {
        this(alias, CALENDAR_EXCEPTION);
    }

    private CalendarException(String alias, Table<CalendarExceptionRecord> aliased) {
        this(alias, aliased, null);
    }

    private CalendarException(String alias, Table<CalendarExceptionRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<CalendarExceptionRecord> getPrimaryKey() {
        return Keys.CALENDAR_EXCEPTION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CalendarExceptionRecord>> getKeys() {
        return Arrays.<UniqueKey<CalendarExceptionRecord>>asList(Keys.CALENDAR_EXCEPTION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CalendarExceptionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CalendarExceptionRecord, ?>>asList(Keys.CALENDAR_EXCEPTION__CALENDAR_EXCEPTION_CALENDAR_DATE_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CalendarException as(String alias) {
        return new CalendarException(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CalendarException rename(String name) {
        return new CalendarException(name, null);
    }
}
