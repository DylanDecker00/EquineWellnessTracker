package equinetracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import equinetracker.dao.OwnerRepository;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Object[]> getTotalExpensesForOwners() {
        return ownerRepository.findTotalExpensesForOwners();
    }

    public Object findTotalExpenseForOwner(Long ownerId) {
        return ownerRepository.findTotalExpenseForOwner(ownerId);
    }
}
