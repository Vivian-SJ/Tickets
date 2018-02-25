package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Seat;

import javax.transaction.Transactional;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query(value = "select * from seat WHERE stadium_id = ?1", nativeQuery = true)
    public List<Seat> findSeatsById(int stadiumId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO seat(stadium_id, name, amount) VALUES (?1, ?2, ?3)", nativeQuery = true)
    public void insertSeat(int stadiumId, String name, int amount);
}
