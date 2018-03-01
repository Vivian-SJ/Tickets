package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Member findById (int Id);

    public Member findByEmail (String email);

    @Query(value = "SELECT DISTINCT (id) FROM member", nativeQuery = true)
    public List<Integer> findAllIds();
}
