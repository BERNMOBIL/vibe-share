package ch.bernmobil.vibe.shared.entity;

import ch.bernmobil.vibe.shared.UpdateManager.Status;

import java.sql.Timestamp;

public class UpdateHistory {
    private Timestamp time;
    private Status status;

    public UpdateHistory(Timestamp time, Status status) {
        this.time = time;
        this.status = status;
    }

    //Empty Constructor needed for because of the JOOQ-Conversion
    public UpdateHistory() {}

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
