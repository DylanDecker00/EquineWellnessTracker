package equinetracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import equinetracker.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
