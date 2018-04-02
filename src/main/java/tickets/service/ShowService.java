package tickets.service;

import tickets.bean.ShowBean;
import tickets.model.Show;
import tickets.model.ShowSeat;
import tickets.model.ShowSeatId;

public interface ShowService {
    public ShowBean getShowBeanById(int showId);

    public Show getShowById(int showId);

    public int getSeatId(String seatName, int stadiumId);

    public ShowSeat getShowSeatById(ShowSeatId showSeatId);
}
