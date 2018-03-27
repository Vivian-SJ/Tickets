package tickets.bean;

import tickets.model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderBean {
    private int id;
    private int memberId;
    private int stadiumId;
    private String stadiumName;
    private int showId;
    private String showName;
    //买票类型
    private String type;
    //如果是选座的话，选的座位类型
    private int seatId;
    private String seatName;
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
    private String orderStatus;
    //订单时间
    private Timestamp time;
    private String ps;
    private Timestamp showTime;

    public OrderBean() {
    }

    public OrderBean(Order order) {
        this.id = order.getId();
        this.memberId = order.getMember_id();
        this.stadiumId = order.getStadium_id();
        this.showId = order.getShow_id();
        this.type = order.getType();
        this.seatId = order.getSeat_id();
        this.ticketAmount = order.getAmount();
        this.actualPrice = order.getActual_price();
        this.expectedPrice = order.getExpected_price();
        this.discount = order.getDiscount();
        this.orderStatus = order.getStatus();
        this.time = order.getTime();
        this.ps = order.getPs();
    }

    public OrderBean(Order order, List<Integer> couponIds) {
        this(order);
        this.couponIds = couponIds;
    }

    public OrderBean(Order order, List<Integer> couponIds, String stadiumName, String showName, String seatName, Timestamp showTime) {
        this(order, couponIds);
        this.stadiumName = stadiumName;
        this.showName = showName;
        if (this.type.equals("立即购买")) {
            this.seatName = "未选座";
        } else {
            this.seatName = seatName;
        }
        this.showTime = showTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getShowTime() {
        return showTime;
    }

    public void setShowTime(Timestamp showTime) {
        this.showTime = showTime;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
}
