package ch.bernmobil.vibe.shared.contract;

public final class CalendarDateContract {
    private CalendarDateContract(){}

    public final static String TABLE_NAME = "calendar_date";
    public final static String ID = "id";
    public final static String VALID_FROM = "valid_from";
    public final static String VALID_UNTIL = "valid_until";
    public final static String JOURNEY = "journey";
    public final static String DAYS = "days";
    public final static String UPDATE = "update";

    public final static String[] COLUMNS = {ID, VALID_FROM, VALID_UNTIL, JOURNEY, DAYS, UPDATE};
}
