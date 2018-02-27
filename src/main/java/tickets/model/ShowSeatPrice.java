package tickets.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "show_seat_price")
public class ShowSeatPrice {
    @EmbeddedId
    private ShowSeatPriceId showSeatPriceId;

    private double price;

    public ShowSeatPrice() {
    }

    public ShowSeatPrice(ShowSeatPriceId showSeatPriceId, double price) {
        this.showSeatPriceId = showSeatPriceId;
        this.price = price;
    }

    public ShowSeatPriceId getShowSeatPriceId() {
        return showSeatPriceId;
    }

    public void setShowSeatPriceId(ShowSeatPriceId showSeatPriceId) {
        this.showSeatPriceId = showSeatPriceId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
