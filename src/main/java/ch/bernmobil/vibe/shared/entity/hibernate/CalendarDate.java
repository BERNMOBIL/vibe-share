package ch.bernmobil.vibe.shared.entity.hibernate;


import ch.bernmobil.vibe.shared.converter.JsonObjectConverter;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CalendarDate {

    @Id
    private UUID id;
    @Convert(converter = JsonObjectConverter.class)
    private JsonObject days;
    private LocalDate validFrom;
    private LocalDate validUntil;
    @Column(name = "update")
    private LocalDateTime updateTimestamp;
    @OneToOne
    @JoinColumn(name = "journey")
    private Journey journey;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public JsonObject getDays() {
        return days;
    }

    public void setDays(JsonObject days) {
        this.days = days;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
