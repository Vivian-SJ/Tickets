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

    @Query(value = "SELECT * FROM stadium WHERE (status = '未审核' || status = '修改未审核')", nativeQuery = true)
    public List<Stadium> getUncheckStadiums();

    @Query(value = "SELECT COUNT(*) FROM stadium", nativeQuery = true)
    public int getStadiumAmount();
}
