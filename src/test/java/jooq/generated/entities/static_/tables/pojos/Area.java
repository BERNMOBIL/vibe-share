/*
 * This file is generated by jOOQ.
*/
package jooq.generated.entities.static_.tables.pojos;


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
public class Area implements Serializable {

    private static final long serialVersionUID = -124421050;

    private final UUID      id;
    private final String    name;
    private final Timestamp update;

    public Area(Area value) {
        this.id = value.id;
        this.name = value.name;
        this.update = value.update;
    }

    public Area(
        UUID      id,
        String    name,
        Timestamp update
    ) {
        this.id = id;
        this.name = name;
        this.update = update;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Timestamp getUpdate() {
        return this.update;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Area (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(update);

        sb.append(")");
        return sb.toString();
    }
}
