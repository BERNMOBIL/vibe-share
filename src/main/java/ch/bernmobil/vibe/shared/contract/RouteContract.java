package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.Route}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public final class RouteContract {
    public static final String TABLE_NAME = "route";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String LINE = "line";
    public static final String UPDATE = "update";
    public static final String[] COLUMNS = {ID, TYPE, LINE, UPDATE};

    private RouteContract(){}
}
