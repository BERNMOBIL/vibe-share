package ch.bernmobil.vibe.shared.mockdata;

import ch.bernmobil.vibe.shared.UpdateManager.Status;
import ch.bernmobil.vibe.shared.entity.UpdateHistory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UpdateHistoryMockData {
    private static List<UpdateHistory> dataSource;
    private static List<UpdateHistory> dataSourceWithValidUpdateCollision;
    private static List<UpdateHistory> dataSourceWithInvalidUpdateCollision;

    private static Timestamp[] times = {
            Timestamp.valueOf("2017-06-02 15:48:05"),
            Timestamp.valueOf("2017-06-03 15:48:05"),
            Timestamp.valueOf("2017-06-04 15:48:05"),
    };

    private static Status[] status = {
        Status.FAILED,
        Status.IN_PROGRESS,
        Status.SUCCESS,
    };

    private UpdateHistoryMockData() {}

    public static List<UpdateHistory> getDataSource() {
        if(dataSource == null) {
            dataSource = new ArrayList<>();
            for(int i = 0; i < times.length; i++) {
                dataSource.add(new UpdateHistory(times[i], status[i]));
            }
        }

        return dataSource;
    }

    public static List<UpdateHistory> getDataSourceWithValidUpdateCollision() {
        dataSourceWithValidUpdateCollision = new ArrayList<>(getDataSource());
        Timestamp collision = new Timestamp(System.currentTimeMillis() - 2 * 60 * 1000);
        dataSourceWithValidUpdateCollision.add(new UpdateHistory(collision, Status.IN_PROGRESS));
        return dataSourceWithValidUpdateCollision;
    }

    public static List<UpdateHistory> getDataSourceWithInvalidUpdateCollision() {
        dataSourceWithInvalidUpdateCollision = new ArrayList<>(getDataSource());
        Timestamp invalidCollision = new Timestamp(System.currentTimeMillis() - 5 * 60 * 60 * 1000);
        dataSourceWithInvalidUpdateCollision.add(new UpdateHistory(invalidCollision, Status.IN_PROGRESS));
        return dataSourceWithInvalidUpdateCollision;
    }
}
