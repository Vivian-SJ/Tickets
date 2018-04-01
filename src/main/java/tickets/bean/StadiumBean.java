package tickets.bean;

import tickets.model.Stadium;

import java.util.List;

public class StadiumBean {
    private int id;
    private String password;
    private String name;
    private String place;
    private String description;
    private List<SeatBean> seats;
    //是否审批
    private String status;
    private double income;
    //若未通过审批，未通过的原因存在这里
    private String status_info;

    public StadiumBean() {
    }

    public StadiumBean(Stadium stadium, List<SeatBean> seats) {
        this.id = stadium.getId();
        this.name = stadium.getName();
        this.password = stadium.getPassword();
        this.place = stadium.getPlace();
        this.description = stadium.getDescription();
        this.seats = seats;
        this.status = stadium.getStatus();
        this.income = stadium.getIncome();
        if (stadium.getStatus_info()!=null) {
            this.status_info = stadium.getStatus_info();
        }
    }

    public String getStatus_info() {
        return status_info;
    }

    public void setStatus_info(String status_info) {
        this.status_info = status_info;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SeatBean> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatBean> seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
