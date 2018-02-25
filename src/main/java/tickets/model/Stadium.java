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
    private String place;
    private String description;

    public Stadium() {
    }

    public Stadium(StadiumBean stadiumBean) {
        if (stadiumBean.getId() != 0) {
            this.id = stadiumBean.getId();
        }
        this.name = stadiumBean.getName();
        this.place = stadiumBean.getPlace();
        this.description = stadiumBean.getDescription();
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
}
