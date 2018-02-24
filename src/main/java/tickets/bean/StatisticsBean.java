package tickets.bean;

import java.util.List;
import java.util.Map;

public class StatisticsBean {
    //不同类型的订单
    private Map<String, List<OrderBean>> statusAndOrder;
    //总消费/总收入
    private double totalPrice;

    public StatisticsBean() {
    }

    public StatisticsBean(Map<String, List<OrderBean>> statusAndOrder, double totalPrice) {
        this.statusAndOrder = statusAndOrder;
        this.totalPrice = totalPrice;
    }

    public Map<String, List<OrderBean>> getStatusAndOrder() {
        return statusAndOrder;
    }

    public void setStatusAndOrder(Map<String, List<OrderBean>> statusAndOrder) {
        this.statusAndOrder = statusAndOrder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
