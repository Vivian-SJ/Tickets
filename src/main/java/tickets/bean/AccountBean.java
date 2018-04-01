package tickets.bean;

import tickets.model.Account;
import tickets.model.Show;
import tickets.model.Stadium;

import java.sql.Timestamp;

public class AccountBean {
    private int id;
    private int showId;
    private String showName;
    private Timestamp showTime;
    private int stadiumId;
    private String stadiumName;
    private double totalIncome;
    private double webIncome;
    private double stadiumIncome;
    //结算时间
    private Timestamp payTime;
    //结算状态：已结算/未结算
    private String status;

    public AccountBean() {
    }

    public AccountBean(Account account, Show show, Stadium stadium) {
        this.id = account.getId();
        this.showId = account.getShow_id();
        this.showName = show.getName();
        this.showTime = show.getTime();
        this.stadiumId = account.getStadium_id();
        this.stadiumName = stadium.getName();
        this.totalIncome = account.getTotal_income();
        this.webIncome = account.getWeb_income();
        this.stadiumIncome = account.getStadium_income();
        this.payTime = account.getPay_time();
        this.status = account.getStatus();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getWebIncome() {
        return webIncome;
    }

    public void setWebIncome(double webIncome) {
        this.webIncome = webIncome;
    }

    public double getStadiumIncome() {
        return stadiumIncome;
    }

    public void setStadiumIncome(double stadiumIncome) {
        this.stadiumIncome = stadiumIncome;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
