package ch.bernmobil.vibe.shared.mapping;

import java.util.UUID;

public class StopMapping {
    private String gtfsId;
    private String name;
    private UUID id;

    public StopMapping(String gtfsId, String name, UUID id) {
        this.gtfsId = gtfsId;
        this.name = name;
        this.id = id;
    }

    public StopMapping(String gtfsId, UUID id) {
        this(gtfsId, null, id);
    }

    public String getGtfsId() {
        return gtfsId;
    }

    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
