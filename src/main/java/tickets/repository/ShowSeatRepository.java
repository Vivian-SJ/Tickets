package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.ShowSeat;
import tickets.model.ShowSeatId;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, ShowSeatId>{
    @Query(value = "SELECT * FROM show_seat WHERE show_id = ?1", nativeQuery = true)
    public List<ShowSeat> findByShowId(int showId);
}
