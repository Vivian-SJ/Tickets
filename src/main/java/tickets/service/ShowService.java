package tickets.service;

import tickets.bean.ShowBean;

public interface ShowService {
    public ShowBean getShowInfoById(int showId);

    public int getSeatId(String seatName, int stadiumId);
}
