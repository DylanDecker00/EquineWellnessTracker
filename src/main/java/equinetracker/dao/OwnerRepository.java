package equinetracker.dao;

import equinetracker.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	@Query(value = "SELECT o.owner_id, o.full_name AS owner_name, SUM(s.hourly_rate * h_s.hours_spent) AS total_expense " + 
            "FROM owners o " +
            "JOIN horses h ON o.owner_id = h.owner_id " +
            "JOIN horse_service h_s ON h.horse_id = h_s.horse_id " +
            "JOIN services s ON h_s.service_id = s.service_id " +
            "GROUP BY o.owner_id, o.full_name", nativeQuery = true)
List<Object[]> findTotalExpensesForOwners();

@Query(value = "SELECT o.owner_id, o.full_name AS owner_name, SUM(s.hourly_rate * h_s.hours_spent) AS total_expense " + 
        "FROM owners o " +
        "JOIN horses h ON o.owner_id = h.owner_id " +
        "JOIN horse_service h_s ON h.horse_id = h_s.horse_id " +
        "JOIN services s ON h_s.service_id = s.service_id " +
        "WHERE o.owner_id = :ownerId " +
        "GROUP BY o.owner_id, o.full_name", nativeQuery = true)
Object findTotalExpenseForOwner(@Param("ownerId") Long ownerId);

}
