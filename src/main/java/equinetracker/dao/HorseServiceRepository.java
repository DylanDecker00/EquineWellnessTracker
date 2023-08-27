package equinetracker.dao;

import org.springframework.data.repository.CrudRepository;

import equinetracker.entity.HorseServiceManager;
import equinetracker.service.HServiceId;

public interface HorseServiceRepository extends CrudRepository<HorseServiceManager, HServiceId> {
}
