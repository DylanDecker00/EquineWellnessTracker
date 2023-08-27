package equinetracker.controller.model;

import equinetracker.dao.HorseRepository;
import equinetracker.dao.OwnerRepository;
import equinetracker.dto.OwnerandHorse;
import equinetracker.entity.Horse;
import equinetracker.entity.Owner;
import equinetracker.service.OwnerService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private HorseRepository horseRepository;
    
    @GetMapping("/{id}/expenses")
    public Object getOwnerWithTotalExpenses(@PathVariable Long id) {
        return ownerService.findTotalExpenseForOwner(id);
    }

    @GetMapping("/expenses")
    public List<Object[]> getOwnersWithTotalExpenses() {
        return ownerService.getTotalExpensesForOwners();
    }

    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        return ownerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Owner createOwner(@Valid @RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @Valid @RequestBody Owner updatedOwner) {
        return ownerRepository.findById(id).map(owner -> {
            owner.setFullName(updatedOwner.getFullName());
            owner.setEmail(updatedOwner.getEmail());
            owner.setPhone(updatedOwner.getPhone());
            // ... other fields

            Set<Long> horseIds = owner.getHorsesOwned().stream()
                    .map(Horse::getId)
                    .collect(Collectors.toSet());
            owner.setOwnership(StringUtils.collectionToCommaDelimitedString(horseIds));

            ownerRepository.save(owner);
            return ResponseEntity.ok(owner);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        return ownerRepository.findById(id).map(owner -> {
            try {
                ownerRepository.delete(owner);
            } catch (DataIntegrityViolationException e) {
                if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    throw new RuntimeException("Must delete horses under current ownership before deleting owner.", e);
                }
                throw e;
            }
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/createWithOwner")
    public ResponseEntity<?> createOwnerAndHorse(@Valid @RequestBody OwnerandHorse dto) {
        // Persist the owner first
        Owner savedOwner = ownerRepository.save(dto.getOwner());

        // Set the saved owner to the horse
        dto.getHorse().setOwner(savedOwner);

        // Now persist the horse without storing the result in a variable
        Horse savedHorse = horseRepository.save(dto.getHorse());

        // Update owner's ownership column with new horse's ID
        if (savedOwner.getOwnership() == null) {
            savedOwner.setOwnership(savedHorse.getId().toString());
        } else {
            savedOwner.setOwnership(savedOwner.getOwnership() + ", " + savedHorse.getId());
        }
        ownerRepository.save(savedOwner);

        // Return a response (you can adjust as per your needs)
        return ResponseEntity.ok("Owner and Horse successfully created!");
    }

    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleIntegrityViolationException(RuntimeException e) {
        if (e.getMessage().contains("Must delete horses under current ownership before deleting owner.")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}