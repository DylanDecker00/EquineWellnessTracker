package equinetracker.controller.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import equinetracker.dao.HorseRepository;
import equinetracker.dao.HorseServiceRepository;
import equinetracker.dao.ServiceRepository;
import equinetracker.entity.Horse;
import equinetracker.entity.HorseServiceManager;
import equinetracker.entity.Service;
import equinetracker.service.HServiceId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/horseServices")
public class HorseServiceController {

    private static final Logger logger = LoggerFactory.getLogger(HorseServiceController.class);

    @Autowired
    private HorseServiceRepository horseServiceRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private HorseRepository horseRepository;

    @GetMapping
    public List<HorseServiceManager> getAllHorseServices() {
        logger.info("Fetching all horse services");
        Iterable<HorseServiceManager> iterable = horseServiceRepository.findAll();
        List<HorseServiceManager> horseServiceManagers = StreamSupport
            .stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
        return horseServiceManagers;
    }

    @GetMapping("/{horseId}/{serviceId}")
    public ResponseEntity<HorseServiceManager> getHorseService(@PathVariable(name = "horseId") Long horseId, @PathVariable(name = "serviceId") Long serviceId) {
        logger.info("Fetching horse service for Horse ID: {} and Service ID: {}", horseId, serviceId);
        HServiceId id = new HServiceId(horseId, serviceId);
        return horseServiceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createHorseService(
            @RequestBody Map<String, Object> requestBody) {
        Long horseId = ((Integer) requestBody.get("horseId")).longValue();
        Long serviceId = ((Integer) requestBody.get("serviceId")).longValue();
        Double hoursSpent = (Double) requestBody.get("hoursSpent");

        // Fetch the Horse entity using horseId
        Horse horse = horseRepository.findById(horseId).orElse(null);

        if (horse == null) {
            return ResponseEntity.badRequest().body("Invalid horseId");
        }

        // Fetch the Service entity using serviceId
        Service service = serviceRepository.findById(serviceId).orElse(null);

        if (service == null) {
            return ResponseEntity.badRequest().body("Invalid serviceId");
        }

        // Create HServiceId
        HServiceId id = new HServiceId(horseId, serviceId);

        // Create HorseServiceManager
        HorseServiceManager horseServiceManager = new HorseServiceManager();
        horseServiceManager.setId(id);
        horseServiceManager.setHorse(horse); // Set the Horse entity
        horseServiceManager.setService(service); // Set the Service entity
        horseServiceManager.setHoursSpent(hoursSpent);

        // Save and return the created entity
        HorseServiceManager savedHorseService = horseServiceRepository.save(horseServiceManager);
        return ResponseEntity.ok(savedHorseService);
    }


    @PutMapping("/{horseId}/{serviceId}")
    public HorseServiceManager updateHorseService(@PathVariable(name = "horseId") Long horseId, @PathVariable(name = "serviceId") Long serviceId, @RequestBody HorseServiceManager updatedHorseService) {
        logger.info("Updating horse service for Horse ID: {} and Service ID: {}", horseId, serviceId);
        HServiceId id = new HServiceId(horseId, serviceId);
        return horseServiceRepository.findById(id).map(horseService -> {
            horseService.setHoursSpent(updatedHorseService.getHoursSpent());
            return horseServiceRepository.save(horseService);
        }).orElseGet(() -> {
            updatedHorseService.setId(id);
            return horseServiceRepository.save(updatedHorseService);
        });
    }

    @DeleteMapping("/{horseId}/{serviceId}")
    public ResponseEntity<?> deleteHorseService(@PathVariable(name = "horseId") Long horseId, @PathVariable(name = "serviceId") Long serviceId) {
        logger.info("Deleting horse service for Horse ID: {} and Service ID: {}", horseId, serviceId);
        HServiceId id = new HServiceId(horseId, serviceId);
        horseServiceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
