package ch.bernmobil.vibe.shared;

import java.sql.Timestamp;

/**
 * Provides the {@link Timestamp} which refers to the ongoing Update
 * This class should be instantiated only once (singleton).
 * @author Oliviero Chiodo
 * @author Matteo Patisso
 */
public class UpdateTimestampManager {
    private Timestamp activeUpdateTimestamp;

    public Timestamp getActiveUpdateTimestamp() {
        return activeUpdateTimestamp;
    }

    public void setActiveUpdateTimestamp(Timestamp activeUpdateTimestamp) {
        this.activeUpdateTimestamp = activeUpdateTimestamp;
    }
}
