package ch.bernmobil.vibe.shared.contract;

public final class StopMapperContract {
    public StopMapperContract(){}
    public static String TABLE_NAME = "stop_mapper";
    public static String GTFS_ID = "gtfs_id";
    public static String ID = "id";
    public static String UPDATE = "update";

    public final static String[] COLUMNS = { GTFS_ID, ID, UPDATE };
}
