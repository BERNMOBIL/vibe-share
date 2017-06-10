package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.Journey}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class JourneyContract {
    public static final String TABLE_NAME = "journey";
    public static final String ID = "id";
    public static final String HEADSIGN = "headsign";
    public static final String ROUTE = "route";
    public static final String TERMINAL_STATION = "terminal_station";
    public static final String UPDATE = "update";
    public static final String[] COLUMNS = {ID, HEADSIGN, ROUTE, TERMINAL_STATION, UPDATE};

    private JourneyContract(){}

}
