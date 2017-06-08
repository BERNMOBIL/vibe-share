package ch.bernmobil.vibe.shared.entitiy;

import java.sql.Timestamp;

public class UpdateHistory {
    private Timestamp time;
    private String status;

    public UpdateHistory(Timestamp time, String status) {
        this.time = time;
        this.status = status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
