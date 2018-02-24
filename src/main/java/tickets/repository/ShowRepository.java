package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.model.Show;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    public Show findById(int id);
}
