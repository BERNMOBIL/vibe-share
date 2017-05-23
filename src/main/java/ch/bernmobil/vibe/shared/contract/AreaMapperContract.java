package ch.bernmobil.vibe.shared.contract;

public final class AreaMapperContract {
    public AreaMapperContract(){}
    public final static String TABLE_NAME = "area_mapper";
    public final static String GTFS_ID = "gtfs_id";
    public final static String ID = "id";
    public final static String UPDATE = "update";

    public final static String[] COLUMNS = { GTFS_ID, ID, UPDATE };
}
