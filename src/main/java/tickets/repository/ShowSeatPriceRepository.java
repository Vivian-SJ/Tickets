package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.ShowSeatPrice;
import tickets.model.ShowSeatPriceId;

import java.util.List;

public interface ShowSeatPriceRepository extends JpaRepository<ShowSeatPrice, ShowSeatPriceId>{
    @Query(value = "SELECT * FROM show_seat_price WHERE show_id = ?1", nativeQuery = true)
    public List<ShowSeatPrice> findByShowId(int showId);
}
