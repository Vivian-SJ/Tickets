package tickets.bean;

import tickets.model.Show;
import tickets.util.ShowType;

import java.sql.Timestamp;

public class ShowBean {
    private int id;
    private int stadiumId;
    private Timestamp time;
    private String name;
    private ShowType type;
    //不同的座位及其价格
    private ShowSeatBean showSeatBean;
//    private Map<String, Double> seatAndPrice;
    private String description;

    public ShowBean() {
    }

    public ShowBean(Show show, ShowSeatBean showSeatBean) {
        this.id = show.getId();
        this.stadiumId = show.getStadium_id();
        this.time = show.getTime();
        this.name = show.getName();
        this.type = ShowType.toEnumValue(show.getType());
        this.showSeatBean = showSeatBean;
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

    public ShowSeatBean getShowSeatBean() {
        return showSeatBean;
    }

    public void setShowSeatBean(ShowSeatBean showSeatBean) {
        this.showSeatBean = showSeatBean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
