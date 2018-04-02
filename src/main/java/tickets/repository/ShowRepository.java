package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Show;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {
    @Query(value = "SELECT * FROM `show` WHERE id = ?1", nativeQuery = true)
    public Show findById(int id);

    @Query(value = "SELECT * FROM `show` WHERE stadium_id = ?1", nativeQuery = true)
    public List<Show> findByStadium_id(int stadiumId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `show`(name, time, stadium_id, type, description) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    public void save(String name, Timestamp time, int stadiumId, String type, String description);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `show` SET name = ?2, time = ?3, stadium_id = ?4, type = ?5, description = ?6 WHERE id = ?1", nativeQuery = true)
    public void update(int id, String name, Timestamp time, int stadiumId, String type, String description);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    public int getId();

    @Query(value = "SELECT * FROM `show` WHERE type = ?1", nativeQuery = true)
    public List<Show> findByType(String type);

    @Query(value = "SELECT * FROM `show` WHERE to_days(time)-to_days(now())=14", nativeQuery = true)
    public List<Show> getShowsToBeAllocatedSeat();
}
