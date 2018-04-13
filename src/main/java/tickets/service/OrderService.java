package tickets.service;

import tickets.model.Order;

import java.util.List;

public interface OrderService {
    /**
     * 获得某场演出需要分配座位的订单
     * @param showId 演出编号
     * @return 订单列表
     */
    public List<Order> getOrderToBeAllocatedSeat(int showId);

    public List<Order> getOrdersByMemberId(int memberId);

    public List<Order> getOrdersByMemberIdAndStatus(int memberId, String status);

    public List<Order> getOrdersByStadiumIdAndStatus(int stadiumId, String status);

    public void save(Order order);

    public Order getOrderById(int orderId);

    public int getLastId();

    public int getAmountByMemberId(int memberId);

    public int getAmountByStadiumId(int stadiumId);
}
