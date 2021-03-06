/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;


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
public class CalendarDate implements Serializable {

    private static final long serialVersionUID = -851399214;

    private final UUID      id;
    private final String    days;
    private final Date      validFrom;
    private final Date      validUntil;
    private final UUID      journey;
    private final Timestamp update;

    public CalendarDate(CalendarDate value) {
        this.id = value.id;
        this.days = value.days;
        this.validFrom = value.validFrom;
        this.validUntil = value.validUntil;
        this.journey = value.journey;
        this.update = value.update;
    }

    public CalendarDate(
        UUID      id,
        String    days,
        Date      validFrom,
        Date      validUntil,
        UUID      journey,
        Timestamp update
    ) {
        this.id = id;
        this.days = days;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.journey = journey;
        this.update = update;
    }

    public UUID getId() {
        return this.id;
    }

    public String getDays() {
        return this.days;
    }

    public Date getValidFrom() {
        return this.validFrom;
    }

    public Date getValidUntil() {
        return this.validUntil;
    }

    public UUID getJourney() {
        return this.journey;
    }

    public Timestamp getUpdate() {
        return this.update;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CalendarDate (");

        sb.append(id);
        sb.append(", ").append(days);
        sb.append(", ").append(validFrom);
        sb.append(", ").append(validUntil);
        sb.append(", ").append(journey);
        sb.append(", ").append(update);

        sb.append(")");
        return sb.toString();
    }
}
