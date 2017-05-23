package ch.bernmobil.vibe.shared.contract;

public final class JourneyMapperContract {
    public JourneyMapperContract(){}
    public final static String TABLE_NAME = "journey_mapper";
    public final static String GTFS_TRIP_ID = "gtfs_trip_id";
    public final static String GTFS_SERVICE_ID = "gtfs_service_id";
    public final static String ID = "id";
    public final static String UPDATE = "update";

    public final static String[] COLUMNS = { GTFS_TRIP_ID, GTFS_SERVICE_ID, ID, UPDATE };
}
