package tickets.model;

import tickets.bean.ShowBean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "show")
public class Show {
    @Id
    private int id;
    private String name;
    private Timestamp time;
    private int stadium_id;
    private String type;
    private String description;

    public Show() {
    }

    public Show(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public Show(ShowBean showBean) {
        if (showBean.getId()!=0) {
            this.id = showBean.getId();
        }
        this.name = showBean.getName();
        this.time = showBean.getTime();
        this.stadium_id = showBean.getStadiumId();
        this.type = showBean.getType().toString();
        this.description = showBean.getDescription();
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(int stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
