package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
