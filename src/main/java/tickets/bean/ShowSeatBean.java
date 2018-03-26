package tickets.bean;

import java.util.Map;

public class ShowSeatBean {
    private Map<String, Integer> seatNameAndId;
    private Map<String, Double> seatNameAndPrice;
    private Map<Integer, Integer> seatIdAndAmount;

    public ShowSeatBean() {
    }

    public ShowSeatBean(Map<String, Integer> seatNameAndId, Map<String, Double> seatNameAndPrice, Map<Integer, Integer> seatIdAndAmount) {
        this.seatNameAndId = seatNameAndId;
        this.seatNameAndPrice = seatNameAndPrice;
        this.seatIdAndAmount = seatIdAndAmount;
    }

    public Map<Integer, Integer> getSeatIdAndAmount() {
        return seatIdAndAmount;
    }

    public void setSeatIdAndAmount(Map<Integer, Integer> seatIdAndAmount) {
        this.seatIdAndAmount = seatIdAndAmount;
    }

    public Map<String, Integer> getSeatNameAndId() {
        return seatNameAndId;
    }

    public void setSeatNameAndId(Map<String, Integer> seatNameAndId) {
        this.seatNameAndId = seatNameAndId;
    }

    public Map<String, Double> getSeatNameAndPrice() {
        return seatNameAndPrice;
    }

    public void setSeatNameAndPrice(Map<String, Double> seatNameAndPrice) {
        this.seatNameAndPrice = seatNameAndPrice;
    }
}
