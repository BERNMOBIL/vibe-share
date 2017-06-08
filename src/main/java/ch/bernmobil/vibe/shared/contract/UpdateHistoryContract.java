package ch.bernmobil.vibe.shared.contract;

public final class UpdateHistoryContract {
    private UpdateHistoryContract(){};
    
    public final static String TABLE_NAME = "update_history";
    public final static String TIME = "time";
    public final static String STATUS = "status";

    public final static String[] COLUMNS = {TIME, STATUS};

}
