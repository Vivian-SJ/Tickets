package tickets.bean;

import tickets.util.OrderStatus;
import tickets.util.TicketBuyType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketBuyBean {
    private int memberId;
    private int stadiumId;
    private int showId;
    //买票类型
    private TicketBuyType type;
    //如果是选座的话，选的座位类型
    private int seatId;
    //买票数量
    private int ticketAmount;
    //使用的优惠券
    private List<Integer> couponIds = new ArrayList<>();
    //折扣
    private double discount;
    //预期总票价
    private double expectedPrice;
    //实际总票价
    private double actualPrice;
    //订单状态
    private OrderStatus orderStatus;
    //订单时间
    private Timestamp time;

    public TicketBuyBean() {
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public TicketBuyType getType() {
        return type;
    }

    public void setType(TicketBuyType type) {
        this.type = type;
    }

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Integer> couponIds) {
        this.couponIds = couponIds;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
