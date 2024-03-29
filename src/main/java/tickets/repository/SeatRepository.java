package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Seat;

import javax.transaction.Transactional;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    public Seat findById(int id);

    @Query(value = "select * from seat WHERE stadium_id = ?1", nativeQuery = true)
    public List<Seat> findSeatsById(int stadiumId);

    @Query(value = "SELECT name FROM seat WHERE id = ?1", nativeQuery = true)
    public String findNameById(int id);

    @Query(value = "SELECT id FROM seat WHERE name=?1 AND stadium_id=?2", nativeQuery = true)
    public int findIdByNameAndStadium_id(String name, int stadiumId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO seat(stadium_id, name, amount) VALUES (?1, ?2, ?3)", nativeQuery = true)
    public void insertSeat(int stadiumId, String name, int amount);
}
