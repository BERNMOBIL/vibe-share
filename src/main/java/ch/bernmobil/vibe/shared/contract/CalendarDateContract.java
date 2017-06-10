package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.CalendarDate}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class CalendarDateContract {
    public static final String TABLE_NAME = "calendar_date";
    public static final String ID = "id";
    public static final String VALID_FROM = "valid_from";
    public static final String VALID_UNTIL = "valid_until";
    public static final String JOURNEY = "journey";
    public static final String DAYS = "days";
    public static final String UPDATE = "update";

    public static final String[] COLUMNS = {ID, VALID_FROM, VALID_UNTIL, JOURNEY, DAYS, UPDATE};
    private CalendarDateContract(){}
}
