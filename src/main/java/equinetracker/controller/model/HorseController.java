package equinetracker.controller.model;

import equinetracker.entity.Horse;
import equinetracker.entity.Owner;
import jakarta.validation.Valid;
import equinetracker.dao.HorseRepository;
import equinetracker.dao.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horses")
public class HorseController {

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    // ... other methods ...

    @PostMapping
    public ResponseEntity<Horse> createHorse(@Valid @RequestBody Horse horseDTO) {
        Horse savedHorse = null;
        if (horseDTO.getOwner() != null && horseDTO.getOwner().getId() != null) {
            Owner owner = ownerRepository.findById(horseDTO.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

            horseDTO.setOwner(owner);
            savedHorse = horseRepository.save(horseDTO); // Save the horse first to get its ID

            // Update owner's ownership column with new horse's ID
            if (owner.getOwnership() == null) {
                owner.setOwnership(savedHorse.getId().toString());
            } else {
                owner.setOwnership(owner.getOwnership() + ", " + savedHorse.getId());
            }
            ownerRepository.save(owner);
        } else {
            savedHorse = horseRepository.save(horseDTO);
        }

        if (savedHorse != null) {
            return ResponseEntity.ok(savedHorse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Horse> updateHorse(@PathVariable Long id, @Valid @RequestBody Horse updatedHorse) {
        return horseRepository.findById(id).map(horse -> {
            if (updatedHorse.getName() != null) {
                horse.setName(updatedHorse.getName());
            }
            if (updatedHorse.getAge() != null) {
                horse.setAge(updatedHorse.getAge());
            }
            if (updatedHorse.getKind() != null) {
                horse.setKind(updatedHorse.getKind());
            }
            if (updatedHorse.getWorkSpecialty() != null) {
                horse.setWorkSpecialty(updatedHorse.getWorkSpecialty());
            }
            if (updatedHorse.getGender() != null) {
                horse.setGender(updatedHorse.getGender());
        }
            if (updatedHorse.getOwner() != null && updatedHorse.getOwner().getId() != null) {
                Owner owner = ownerRepository.findById(updatedHorse.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
                horse.setOwner(owner);

                // Update owner's ownership column with new horse's ID
                if (owner.getOwnership() == null) {
                    owner.setOwnership(horse.getId().toString());
                } else {
                    owner.setOwnership(owner.getOwnership() + ", " + horse.getId());
                }
                ownerRepository.save(owner);
            }
            horseRepository.save(horse);
            return ResponseEntity.ok(horse);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHorse(@PathVariable Long id) {
        return horseRepository.findById(id).map(horse -> {
            // Remove the horse from the owner's list of owned horses
            if (horse.getOwner() != null) {
                horse.getOwner().getHorsesOwned().remove(horse);
                horse.getOwner().updateOwnership(); // Update the ownership column
                ownerRepository.save(horse.getOwner());
            }
        
            horseRepository.delete(horse);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Horse> getAllHorses() {
        List<Horse> horses = horseRepository.findAll();
        // Add a log statement to check the fetched data
        System.out.println("Fetched all horses: " + horses);
        return horses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horse> getHorseById(@PathVariable Long id) {
        return horseRepository.findById(id)
                .map(horse -> {
                    // Add a log statement to check the fetched data
                    System.out.println("Fetched horse by ID: " + horse);
                    return ResponseEntity.ok(horse);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
