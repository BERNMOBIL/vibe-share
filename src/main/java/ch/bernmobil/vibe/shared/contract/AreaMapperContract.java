package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.mapping.AreaMapping}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public final class AreaMapperContract {
    public static final String TABLE_NAME = "area_mapper";
    public static final String GTFS_ID = "gtfs_id";
    public static final String ID = "id";
    public static final String UPDATE = "update";

    public static final String[] COLUMNS = { GTFS_ID, ID, UPDATE };
    private AreaMapperContract(){}
}
