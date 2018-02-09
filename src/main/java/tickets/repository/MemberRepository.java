package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Member findById (int Id);
}
