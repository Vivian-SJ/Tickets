package tickets.bean;

import tickets.model.Seat;

public class SeatBean {
    private int id;
    private int stadiumId;
    private String name;
    private int amount;

    public SeatBean() {
    }

    public SeatBean(Seat seat) {
        this.id = seat.getId();
        this.stadiumId = seat.getStadium_id();
        this.name = seat.getName();
        this.amount = seat.getAmount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
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
