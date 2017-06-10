package ch.bernmobil.vibe.shared.mapping;

import java.util.UUID;

public class AreaMapping {

    private String gtfsId;
    private UUID id;

    public AreaMapping(String gtfsId, UUID id) {
        setGtfsId(gtfsId);
        setId(id);
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
}
