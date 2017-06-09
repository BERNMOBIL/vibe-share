package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.Area}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public final class AreaContract {

    public static final String TABLE_NAME = "area";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String UPDATE = "update";

    public static final String[] COLUMNS = { ID, NAME, UPDATE };
    private AreaContract(){}
}
