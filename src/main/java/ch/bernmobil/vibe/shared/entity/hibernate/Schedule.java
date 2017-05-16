package ch.bernmobil.vibe.shared.entity.hibernate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Schedule {

    @Id
    private UUID id;
    private String platform;
    private LocalTime plannedArrival;
    private LocalTime plannedDeparture;
    @Column(name = "update")
    private LocalDateTime updateTimestamp;

    @OneToOne
    @JoinColumn(name = "stop")
    private Stop stop;

    @OneToOne
    @JoinColumn(name = "journey")
    private Journey journey;

    @OneToOne(targetEntity = ScheduleUpdate.class, mappedBy = "schedule")
    private ScheduleUpdate scheduleUpdate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public LocalTime getPlannedArrival() {
        return plannedArrival;
    }

    public void setPlannedArrival(LocalTime plannedArrival) {
        this.plannedArrival = plannedArrival;
    }

    public LocalTime getPlannedDeparture() {
        return plannedDeparture;
    }

    public void setPlannedDeparture(LocalTime plannedDeparture) {
        this.plannedDeparture = plannedDeparture;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public ScheduleUpdate getScheduleUpdate() {
        return scheduleUpdate;
    }

    public void setScheduleUpdate(
        ScheduleUpdate scheduleUpdate) {
        this.scheduleUpdate = scheduleUpdate;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    @Transient
    public int compareByDepartureTime(Schedule other) {
        return plannedDeparture.compareTo(other.getPlannedDeparture());
    }

    @Transient
    public boolean hasScheduleUpdate() {
        return scheduleUpdate != null;
    }

    @Transient
    public boolean hasPlatform() {
        return !platform.equals("0");
    }

}
