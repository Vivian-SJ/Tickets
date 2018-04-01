package tickets.service;

import tickets.bean.ShowBean;
import tickets.model.Show;

public interface ShowService {
    public ShowBean getShowBeanById(int showId);

    public Show getShowById(int showId);

    public int getSeatId(String seatName, int stadiumId);
}
