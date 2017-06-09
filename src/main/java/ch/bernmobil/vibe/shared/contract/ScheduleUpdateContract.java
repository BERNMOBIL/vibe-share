package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.ScheduleUpdate}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public final class ScheduleUpdateContract {
    public static final String TABLE_NAME = "schedule_update";
    public static final String ID = "id";
    public static final String SCHEDULE = "schedule";
    public static final String ACTUAL_ARRIVAL = "actual_arrival";
    public static final String ACTUAL_DEPARTURE = "actual_departure";
    public static final String[] COLUMNS = { ID, SCHEDULE, ACTUAL_ARRIVAL, ACTUAL_DEPARTURE };

    private ScheduleUpdateContract() {}
}
