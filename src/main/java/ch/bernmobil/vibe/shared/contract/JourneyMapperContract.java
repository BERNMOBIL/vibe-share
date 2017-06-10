package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.mapping.JourneyMapping}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class JourneyMapperContract {
    public static final String TABLE_NAME = "journey_mapper";
    public static final String GTFS_TRIP_ID = "gtfs_trip_id";
    public static final String GTFS_SERVICE_ID = "gtfs_service_id";
    public static final String ID = "id";
    public static final String UPDATE = "update";
    public static final String[] COLUMNS = { GTFS_TRIP_ID, GTFS_SERVICE_ID, ID, UPDATE };

    private JourneyMapperContract(){}
}
