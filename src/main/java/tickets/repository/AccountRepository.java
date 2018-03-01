package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query(value = "SELECT * FROM account WHERE stadium_id = ?1 AND status=?2", nativeQuery = true)
    public Account findByStadium_idAndStatus(int stadiumId, String status);
}
