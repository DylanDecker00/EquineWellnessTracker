package equinetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import equinetracker.dao.HorseRepository;
import equinetracker.dao.OwnerRepository;
import equinetracker.dao.ServiceRepository;
import equinetracker.entity.Horse;
import equinetracker.entity.HorseServiceManager;
import equinetracker.entity.Owner;

import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquineWellnessTrackerService {

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Retrieve an Owner along with their Horses
    public Optional<Owner> getOwnerWithHorses(Long ownerId) {
        return ownerRepository.findById(ownerId);
    }

    // Calculate Total Expense for an Owner
    public double calculateTotalExpenseForOwner(Long ownerId) {
        Optional<Owner> ownerOpt = ownerRepository.findById(ownerId);
        if (ownerOpt.isEmpty()) {
            return 0.0; // or throw an exception
        }

        Owner owner = ownerOpt.get();
        return owner.getHorsesOwned().stream()
            .flatMap(horse -> horse.getHorseServiceManagers().stream())
            .mapToDouble(horseService -> horseService.getService().getHourlyRate())
            .sum();
    }

    // Find all Services availed by a particular Horse
    public Set<equinetracker.entity.Service> getServicesForHorse(Long horseId) {
        Optional<Horse> horseOpt = horseRepository.findById(horseId);
        if (horseOpt.isPresent()) {
            return horseOpt.get().getHorseServiceManagers().stream()
                           .map(HorseServiceManager::getService)
                           .collect(Collectors.toSet());
        }
        return Set.of();
    }

    // Add a Service to a Horse
    public void addServiceToHorse(Long horseId, Long serviceId) {
        Optional<Horse> horseOpt = horseRepository.findById(horseId);
        Optional<equinetracker.entity.Service> serviceOpt = serviceRepository.findById(serviceId);

        if (horseOpt.isPresent() && serviceOpt.isPresent()) {
            Horse horse = horseOpt.get();
            HorseServiceManager horseServiceManager = new HorseServiceManager();
            horseServiceManager.setHorse(horse);
            horseServiceManager.setService(serviceOpt.get());
            // set other attributes for horseService if necessary
            
            horse.getHorseServiceManagers().add(horseServiceManager);
            horseRepository.save(horse);
        } else {
            // Handle the error case where either the horse or the service doesn't exist.
        }
    }
}
