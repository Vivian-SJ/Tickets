package tickets.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ShowSeatId implements Serializable{
    private int show_id;
    private int seat_id;

    public ShowSeatId() {
    }

    public ShowSeatId(int show_id, int seat_id) {
        this.show_id = show_id;
        this.seat_id = seat_id;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }
}
