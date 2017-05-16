package ch.bernmobil.vibe.shared;

import java.sql.Timestamp;

public class UpdateHistoryEntry {
    private int id;
    private Timestamp time;
    private UpdateManager.Status status;

    public UpdateHistoryEntry(int id, Timestamp time, UpdateManager.Status status) {
        this.id = id;
        this.time = time;
        this.status = status;
    }
    public UpdateHistoryEntry(Timestamp time, UpdateManager.Status status) {
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
