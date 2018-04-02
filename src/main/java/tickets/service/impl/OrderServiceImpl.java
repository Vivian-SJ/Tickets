package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.model.Order;
import tickets.repository.OrderRepository;
import tickets.service.OrderService;

import java.util.List;
@Service("orderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrderToBeAllocatedSeat(int showId) {
        return orderRepository.getOrderToBeAllocatedSeat(showId);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }
}
