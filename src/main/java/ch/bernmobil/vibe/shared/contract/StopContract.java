package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.Stop}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class StopContract {
    public static final String TABLE_NAME = "stop";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AREA = "area";
    public static final String UPDATE = "update";
    public static final String[] COLUMNS = {ID, NAME, AREA, UPDATE};

    private StopContract() {}
}
