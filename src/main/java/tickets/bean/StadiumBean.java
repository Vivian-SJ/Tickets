package tickets.bean;

import tickets.model.Stadium;

import java.util.List;

public class StadiumBean {
    private int id;
    private String name;
    private String place;
    private String description;
    private List<SeatBean> seats;

    public StadiumBean() {
    }

    public StadiumBean(Stadium stadium, List<SeatBean> seats) {
        this.id = stadium.getId();
        this.name = stadium.getName();
        this.place = stadium.getPlace();
        this.description = stadium.getDescription();
        this.seats = seats;
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
}
