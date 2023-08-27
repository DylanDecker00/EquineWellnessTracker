package equinetracker.entity;

import equinetracker.service.HServiceId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "horse_service")
public class HorseServiceManager {

    @EmbeddedId
    private HServiceId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("horseId")
    @JoinColumn(name = "horse_id")
    private Horse horse;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private Service service;

    // Assuming you have a column for hours spent
    @Column(name = "hours_spent")
    private double hoursSpent;

    // Any additional columns if they exist
    // example:
    // @Column(name = "example_column")
    // private String exampleColumn;

    // Constructors, getters, setters, etc.

    // For brevity, I'll just show a couple of getters and setters, but you'd include all of them.
    
    public HServiceId getId() {
        return id;
    }

    public void setId(HServiceId id) {
        this.id = id;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    
    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    // ... (other methods as needed)
}
