package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    public Order findById(int id);

    @Query(value = "select * from order where member_id = ?1", nativeQuery = true)
    public List<Order> findByMember_id(int memberId);

    @Query(value = "select * from  order where member_id = ?1 AND type = ?2", nativeQuery = true)
    public List<Order> findByMember_idAndType(int memberId, String type);

    @Query(value = "select sum(price) FROM order WHERE member_id = ?1", nativeQuery = true)
    public double getTotalPrice(int memberId);
}
