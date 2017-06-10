package ch.bernmobil.vibe.shared.contract;

/**
 * Database contract to define table name and column name for {@link ch.bernmobil.vibe.shared.entity.Schedule}.
 *
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
@SuppressWarnings("ALL")
public final class ScheduleContract {
    public static final String TABLE_NAME = "schedule";
    public static final String ID = "id";
    public static final String PLATFORM = "platform";
    public static final String PLANNED_ARRIVAL = "planned_arrival";
    public static final String PLANNED_DEPARTURE = "planned_departure";
    public static final String STOP = "stop";
    public static final String JOURNEY = "journey";
    public static final String UPDATE = "update";
    public static final String[] COLUMNS = { ID, PLATFORM, PLANNED_ARRIVAL, PLANNED_DEPARTURE, STOP, JOURNEY, UPDATE };

    private ScheduleContract(){}
}
