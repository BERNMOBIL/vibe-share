package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.mapping.CalendarDateMapping}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public final class CalendarDateMapperContract {
    public static final String TABLE_NAME = "calendar_date_mapper";
    public static final String GTFS_ID = "gtfs_id";
    public static final String ID = "id";
    public static final String UPDATE = "update";

    public static final String[] COLUMNS = { GTFS_ID, ID, UPDATE };

    private CalendarDateMapperContract(){}
}
