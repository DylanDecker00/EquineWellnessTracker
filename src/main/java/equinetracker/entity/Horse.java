package equinetracker.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "horses")
public class Horse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "horse_id")
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "kind")
    private String kind;

    @Column(name = "color")
    private String color;

    @Column(name = "gender")
    private String gender;

    @Column(name = "work_specialty")
    private String workSpecialty;

    @Column(name = "age", nullable = true)
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    
    @OneToMany(mappedBy = "horse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HorseServiceManager> horseServiceManagers;
    
}
