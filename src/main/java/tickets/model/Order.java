package tickets.model;

import tickets.bean.OrderBean;
import tickets.util.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
public class Order {
    @Id
    private int id;
    private int member_id;
    private int stadium_id;
    private int show_id;
    private int seat_id;
    private int amount;
    private String status;
    private java.sql.Timestamp time;
    //备注信息
    private String ps;
    //订单金额
    private double price;
    private String type;

    public Order() {
    }

    public Order(OrderBean orderBean) {
        this.member_id = orderBean.getMemberId();
        this.stadium_id = orderBean.getStadiumId();
        this.show_id = orderBean.getShowId();
        this.seat_id = orderBean.getSeatId();
        this.amount = orderBean.getTicketAmount();
        this.status = OrderStatus.TO_BE_PAID.toString();
        this.time = orderBean.getTime();
        this.price = orderBean.getActualPrice();
        String ps = "此单原价为"+ orderBean.getExpectedPrice()+ "元，您已享受" + orderBean.getDiscount() + "折会员优惠";
        if (orderBean.getCouponIds().size()>0) {
            ps = ps + ",并使用了" + orderBean.getCouponIds().size() + "张优惠券";
        }
        this.ps = ps;
        this.type = orderBean.getType().toString();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
