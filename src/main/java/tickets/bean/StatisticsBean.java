package tickets.bean;

import java.util.List;
import java.util.Map;

public class StatisticsBean {
    //总订单数
    private int orderSum;
    //不同类型的订单
    private Map<String, List<OrderBean>> statusAndOrder;
    //总消费/总收入
    private double totalPrice;

    public StatisticsBean() {
    }

    public StatisticsBean(int orderSum, Map<String, List<OrderBean>> statusAndOrder, double totalPrice) {
        this.orderSum = orderSum;
        this.statusAndOrder = statusAndOrder;
        this.totalPrice = totalPrice;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
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
