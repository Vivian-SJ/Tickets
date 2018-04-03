package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Coupon;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    @Query(value = "select * from coupon where member_id = ?1", nativeQuery = true)
    public List<Coupon> findBymember_id(int memberId);

    public Coupon findById(int id);

    @Query(value = "select * from coupon where member_id = ?1 and order_id = ?2", nativeQuery = true)
    public List<Coupon> findByMember_idAndOrder_id(int memberId, int orderId);

    @Query(value = "SELECT * FROM coupon WHERE order_id = ?1", nativeQuery = true)
    public List<Coupon> findByOrder_id(int orderId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO coupon(member_id, value, status) VALUES (?1, ?2, ?3)", nativeQuery = true)
    public void insertCoupon(int memberId, double value, String status);
}
