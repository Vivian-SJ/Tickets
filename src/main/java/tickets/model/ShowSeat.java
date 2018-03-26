package tickets.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "show_seat")
public class ShowSeat {
    @EmbeddedId
    private ShowSeatId showSeatId;

    private double price;

    private int available_amount;

    public ShowSeat() {
    }

    public ShowSeat(ShowSeatId showSeatId, double price, int available_amount) {
        this.showSeatId = showSeatId;
        this.price = price;
        this.available_amount = available_amount;
    }

    public int getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(int available_amount) {
        this.available_amount = available_amount;
    }

    public ShowSeatId getShowSeatId() {
        return showSeatId;
    }

    public void setShowSeatId(ShowSeatId showSeatId) {
        this.showSeatId = showSeatId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
