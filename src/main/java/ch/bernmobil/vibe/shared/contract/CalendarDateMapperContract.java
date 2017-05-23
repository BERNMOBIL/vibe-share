package ch.bernmobil.vibe.shared.contract;

public final class CalendarDateMapperContract {
    public CalendarDateMapperContract(){}
    public final static String TABLE_NAME = "calendar_date_mapper";
    public final static String GTFS_ID = "gtfs_id";
    public final static String ID = "id";
    public final static String UPDATE = "update";

    public final static String[] COLUMNS = { GTFS_ID, ID, UPDATE };
}
