package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tickets.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{
//    @Query(value = "SELECT * FROM orders WHERE id = ?1", nativeQuery = true)
    public Order findById(int id);

    @Query(value = "select * from orders where member_id = ?1", nativeQuery = true)
    public List<Order> findByMember_id(int memberId);

    @Query(value = "select * from  orders where member_id = ?1 AND status = ?2", nativeQuery = true)
    public List<Order> findByMember_idAndType(int memberId, String type);

//    @Query(value = "select sum(actual_price) FROM orders WHERE member_id = ?1", nativeQuery = true)
//    public double getMemberTotalPrice(int memberId);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    public int getId();

    @Query(value = "SELECT COUNT(*) FROM orders WHERE member_id = ?1", nativeQuery = true)
    public int getAmountByMemberId(int memberId);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE stadium_id = ?1", nativeQuery = true)
    public int getAmountByStadiumId(int stadiumId);
//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO orders VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
//    public void save(int memberId, int stadiumId, int showId, int seatId, int amount, String status, Timestamp time, String ps, double price, String type);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE orders SET member_id=?2, stadium_id=?3, show_id=?4, seat_id=?5, amount=?6, status=?7, time=?8, ps=?9, price=?10, type=?11 WHERE id=?1", nativeQuery = true)
//    public void update(int id, int memberId, int stadiumId, int showId, int seatId, int amount, String status, Timestamp time, String ps, double price, String type);

    @Query(value = "select * from  orders where stadium_id = ?1 AND type = ?2", nativeQuery = true)
    public List<Order> findByStadium_idAndType(int memberId, String type);

    @Query(value = "select sum(actual_price) FROM orders WHERE stadium_id = ?1", nativeQuery = true)
    public double getStadiumTotalPrice(int stadiumId);

}
