package tickets.bean;

import tickets.model.Coupon;

import java.util.List;

public class MemberAccountBean {
    private int rank;
    private double credit;
    private List<Coupon> coupons;

    public MemberAccountBean() {
    }

    public MemberAccountBean(int rank, double credit, List<Coupon> coupons) {
        this.rank = rank;
        this.credit = credit;
        this.coupons = coupons;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
