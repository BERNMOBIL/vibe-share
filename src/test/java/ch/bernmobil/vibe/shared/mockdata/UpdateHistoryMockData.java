package ch.bernmobil.vibe.shared.mockdata;

import ch.bernmobil.vibe.shared.entitiy.UpdateHistory;

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

    private static String[] status = {
        "FAILED",
        "IN_PROGRESS",
        "SUCCESS",
    };

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
        dataSourceWithValidUpdateCollision = getDataSource();
        Timestamp collision = new Timestamp(System.currentTimeMillis() - 5000);
        dataSourceWithValidUpdateCollision.add(new UpdateHistory(collision, "IN_PROGRESS"));
        return dataSourceWithValidUpdateCollision;
    }

    public static List<UpdateHistory> getDataSourceWithInvalidUpdateCollision() {
        dataSourceWithInvalidUpdateCollision = getDataSource();
        Timestamp invalidCollision = new Timestamp(System.currentTimeMillis() - 5000000);
        dataSourceWithInvalidUpdateCollision.add(new UpdateHistory(invalidCollision, "IN_PROGRESS"));
        return dataSourceWithInvalidUpdateCollision;
    }
}
