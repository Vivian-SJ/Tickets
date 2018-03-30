package tickets.model;

import tickets.bean.StadiumBean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stadium")
public class Stadium {
    @Id
    private int id;
    private String name;
    private String password;
    private String place;
    private String description;
    //是否审批
    private String status;
    private double income;

    public Stadium() {
    }

    public Stadium(StadiumBean stadiumBean) {
        if (stadiumBean.getId() != 0) {
            this.id = stadiumBean.getId();
        }
        this.password = stadiumBean.getPassword();
        this.name = stadiumBean.getName();
        this.place = stadiumBean.getPlace();
        this.description = stadiumBean.getDescription();
        this.status = stadiumBean.getStatus();
        this.income = stadiumBean.getIncome();
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
