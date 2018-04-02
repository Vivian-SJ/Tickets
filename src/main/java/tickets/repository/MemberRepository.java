package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Member findById (int Id);

    public Member findByEmail (String email);

    @Query(value = "SELECT DISTINCT (id) FROM member WHERE id!=-1", nativeQuery = true)
    public List<Integer> findAllIds();

    @Query(value = "SELECT DISTINCT (id) FROM member WHERE email = ?1", nativeQuery = true)
    public int findIdByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM member", nativeQuery = true)
    public int getMemberAmount();
}
