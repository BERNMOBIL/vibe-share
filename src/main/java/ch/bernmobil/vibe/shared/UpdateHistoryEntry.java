package ch.bernmobil.vibe.shared;

import java.sql.Timestamp;

public class UpdateHistoryEntry {
        private Timestamp time;
    private UpdateManager.Status status;

    public UpdateHistoryEntry(Timestamp time, UpdateManager.Status status) {
        this.time = time;
        this.status = status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public UpdateManager.Status getStatus() {
        return status;
    }

    public void setStatus(UpdateManager.Status status) {
        this.status = status;
    }
}
