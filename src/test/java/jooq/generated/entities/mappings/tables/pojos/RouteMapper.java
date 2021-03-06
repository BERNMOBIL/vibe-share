/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.mappings.tables.pojos;


import java.io.Serializable;
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
public class RouteMapper implements Serializable {

    private static final long serialVersionUID = -139897047;

    private final String    gtfsId;
    private final UUID      id;
    private final Timestamp update;

    public RouteMapper(RouteMapper value) {
        this.gtfsId = value.gtfsId;
        this.id = value.id;
        this.update = value.update;
    }

    public RouteMapper(
        String    gtfsId,
        UUID      id,
        Timestamp update
    ) {
        this.gtfsId = gtfsId;
        this.id = id;
        this.update = update;
    }

    public String getGtfsId() {
        return this.gtfsId;
    }

    public UUID getId() {
        return this.id;
    }

    public Timestamp getUpdate() {
        return this.update;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RouteMapper (");

        sb.append(gtfsId);
        sb.append(", ").append(id);
        sb.append(", ").append(update);

        sb.append(")");
        return sb.toString();
    }
}
