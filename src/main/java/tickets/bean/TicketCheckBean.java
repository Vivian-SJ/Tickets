package tickets.bean;

import tickets.util.OrderStatus;

public class TicketCheckBean {
//    private int stadiumId;
    private int orderId;
    private OrderStatus status;
    private String ps;

    public TicketCheckBean() {
    }

//    public int getStadiumId() {
//        return stadiumId;
//    }
//
//    public void setStadiumId(int stadiumId) {
//        this.stadiumId = stadiumId;
//    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
}
