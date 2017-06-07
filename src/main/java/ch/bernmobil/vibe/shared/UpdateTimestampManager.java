package ch.bernmobil.vibe.shared;

import java.sql.Timestamp;

public class UpdateTimestampManager {
    private Timestamp activeUpdateTimestamp;

    public Timestamp getActiveUpdateTimestamp() {
        return activeUpdateTimestamp;
    }

    public void setActiveUpdateTimestamp(Timestamp activeUpdateTimestamp) {
        this.activeUpdateTimestamp = activeUpdateTimestamp;
    }
}
