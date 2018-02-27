package tickets.bean;

import tickets.model.Show;
import tickets.util.ShowType;

import java.sql.Timestamp;
import java.util.Map;

public class ShowBean {
    private int id;
    private int stadiumId;
    private Timestamp time;
    private String name;
    private ShowType type;
    //不同的座位及其价格
    private Map<Integer, Double> seatAndPrice;
    private String description;

    public ShowBean() {
    }

    public ShowBean(Show show, Map<Integer, Double> map) {
        this.id = show.getId();
        this.stadiumId = show.getStadium_id();
        this.time = show.getTime();
        this.name = show.getName();
        this.type = ShowType.toEnumValue(show.getType());
        this.seatAndPrice = map;
        this.description = show.getDescription();
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShowType getType() {
        return type;
    }

    public void setType(ShowType type) {
        this.type = type;
    }

    public Map<Integer, Double> getSeatAndPrice() {
        return seatAndPrice;
    }

    public void setSeatAndPrice(Map<Integer, Double> seatAndPrice) {
        this.seatAndPrice = seatAndPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
