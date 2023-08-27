package equinetracker.service;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class HServiceId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "horse_id")
    private Long horseId;

    @Column(name = "service_id")
    private Long serviceId;

    public HServiceId() {}

    public HServiceId(Long horseId, Long serviceId) {
        this.horseId = horseId;
        this.serviceId = serviceId;
    }

    // Getters and Setters

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    // hashCode() and equals() methods

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((horseId == null) ? 0 : horseId.hashCode());
        result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HServiceId other = (HServiceId) obj;
        if (horseId == null) {
            if (other.horseId != null)
                return false;
        } else if (!horseId.equals(other.horseId))
            return false;
        if (serviceId == null) {
            if (other.serviceId != null)
                return false;
        } else if (!serviceId.equals(other.serviceId))
            return false;
        return true;
    }
}
