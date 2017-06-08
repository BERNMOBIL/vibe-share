package ch.bernmobil.vibe.shared.contract;

public final class JourneyContract {
    private JourneyContract(){};

    public final static String TABLE_NAME = "journey";
    public final static String ID = "id";
    public final static String HEADSIGN = "headsign";
    public final static String ROUTE = "route";
    public final static String TERMINAL_STATION = "terminal_station";
    public final static String UPDATE = "update";

    public final static String[] COLUMNS = {ID, HEADSIGN, ROUTE, TERMINAL_STATION, UPDATE};

}
