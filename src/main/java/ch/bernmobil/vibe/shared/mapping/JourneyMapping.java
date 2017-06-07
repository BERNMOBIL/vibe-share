package ch.bernmobil.vibe.shared.mapping;

import java.util.UUID;
import javax.persistence.Column;

public class JourneyMapping {
    @Column(name = "gtfs_trip_id")
    private String gtfsTripId;
    @Column(name = "gtfs_service_id")
    private String gtfsServiceId;
    @Column(name = "id")
    private UUID id;

    public JourneyMapping(String gtfsTripId, String gtfsServiceId, UUID id) {
        this.gtfsTripId = gtfsTripId;
        this.gtfsServiceId = gtfsServiceId;
        this.id = id;
    }

    public String getGtfsTripId() {
        return gtfsTripId;
    }

    public void setGtfsTripId(String gtfsTripId) {
        this.gtfsTripId = gtfsTripId;
    }

    public String getGtfsServiceId() {
        return gtfsServiceId;
    }

    public void setGtfsServiceId(String gtfsServiceId) {
        this.gtfsServiceId = gtfsServiceId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
