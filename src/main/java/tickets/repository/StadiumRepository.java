package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Stadium;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, Integer>{
    @Query(value = "SELECT max(id) FROM stadium ", nativeQuery = true)
    public Integer getLastId();

    public Stadium findById(int stadiumId);

    @Query(value = "SELECT DISTINCT (id) FROM stadium", nativeQuery = true)
    public List<Integer> findAllIds();
}
