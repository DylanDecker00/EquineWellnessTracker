package equinetracker.entity;

import equinetracker.service.HServiceId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "horse_service")
public class HorseServiceEntity {

    @EmbeddedId
    private HServiceId id;

    @Column(name = "hours_spent")
    @NotNull
    private Double hoursSpent;

    public HorseServiceEntity() {}

    public HorseServiceEntity(HServiceId id, @NotNull Double hoursSpent) {
        this.id = id;
        this.hoursSpent = hoursSpent;
    }

    // Getters and setters

    public HServiceId getId() {
        return id;
    }

    public void setId(HServiceId id) {
        this.id = id;
    }

    public Double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }
}
