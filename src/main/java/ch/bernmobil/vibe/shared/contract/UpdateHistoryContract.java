package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.UpdateHistory}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class UpdateHistoryContract {
    public static final String TABLE_NAME = "update_history";
    public static final String TIME = "time";
    public static final String STATUS = "status";
    public static final String[] COLUMNS = {TIME, STATUS};

    private UpdateHistoryContract(){}

}
