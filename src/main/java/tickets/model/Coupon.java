package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    private int id;
    private int member_id;
    private double value;
    private String status;
    private int order_id;

    public Coupon() {
    }

    public Coupon(int member_id, double value, String status) {
        this.member_id = member_id;
        this.value = value;
        this.status = status;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public static double toCredit(double value) {
        switch ((int) value) {
            case 10:
                return 1000;
            case 25:
                return 2000;
            case 45:
                return 3000;
            case 70:
                return 4000;
            case 100:
                return 5000;
                default:
                    return 0;
        }
    }
}
