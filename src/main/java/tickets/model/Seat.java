package tickets.model;

import tickets.bean.SeatBean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    private int id;
    private int stadium_id;
    private String name;
    //该场馆拥有该类座位的数量
    private int amount;

    public Seat() {
    }

    public Seat(SeatBean seatBean) {
        if (seatBean.getId() != 0) {
            this.id = seatBean.getId();
        }
        this.stadium_id = seatBean.getStadiumId();
        this.name = seatBean.getName();
        this.amount = seatBean.getAmount();
    }

    public Seat(int stadium_id, String name, int amount) {
        this.stadium_id = stadium_id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
