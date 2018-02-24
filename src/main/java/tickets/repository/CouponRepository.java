package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Coupon;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    @Query(value = "select * from coupon where member_id = ?1", nativeQuery = true)
    public List<Coupon> findBymember_id(int memberId);

    public Coupon findById(int id);

    @Query(value = "select * from coupon where member_id = ?1 and order_id = ?2", nativeQuery = true)
    public List<Coupon> findByMember_idAndOrder_id(int memberId, int orderId);
}
