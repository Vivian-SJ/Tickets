package tickets.bean;

import tickets.model.Account;

import java.sql.Timestamp;

public class AccountBean {
    private int id;
    private int show_id;
    private int stadium_id;
    private double total_income;
    private double web_income;
    private double stadium_income;
    //结算时间
    private Timestamp pay_time;
    //结算状态：已结算/未结算
    private String status;

    public AccountBean() {
    }

    public AccountBean(Account account) {
        this.id = account.getId();
        this.show_id = account.getShow_id();
        this.stadium_id = account.getStadium_id();
        this.total_income = account.getTotal_income();
        this.web_income = account.getWeb_income();
        this.stadium_income = account.getStadium_income();
        this.pay_time = account.getPay_time();
        this.status = account.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public double getTotal_income() {
        return total_income;
    }

    public void setTotal_income(double total_income) {
        this.total_income = total_income;
    }

    public double getWeb_income() {
        return web_income;
    }

    public void setWeb_income(double web_income) {
        this.web_income = web_income;
    }

    public double getStadium_income() {
        return stadium_income;
    }

    public void setStadium_income(double stadium_income) {
        this.stadium_income = stadium_income;
    }

    public Timestamp getPay_time() {
        return pay_time;
    }

    public void setPay_time(Timestamp pay_time) {
        this.pay_time = pay_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
