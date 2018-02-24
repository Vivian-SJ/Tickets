package tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tickets.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    public Order findById(int id);
}
