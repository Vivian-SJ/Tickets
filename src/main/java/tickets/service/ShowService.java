package tickets.service;

import tickets.bean.ShowBean;
import tickets.model.Show;
import tickets.model.ShowSeat;
import tickets.model.ShowSeatId;

import java.util.List;

public interface ShowService {
    public ShowBean getShowBeanById(int showId);

    public Show getShowById(int showId);

    public int getSeatId(String seatName, int stadiumId);

    public ShowSeat getShowSeatById(ShowSeatId showSeatId);

    public List<ShowSeat> getShowSeatByShowId(int showId);

    /**
     * 查找每天需要分配座位的演出（即两周后开始的演出）
     * @return 演出列表
     */
    public List<Show> getShowsToBeAllocatedSeat();

    public void saveShowSeat(ShowSeat showSeat);

    public void save(Show show);

    public List<Show> findByType(String type);

    public List<Show> findByStadiumId(int stadiumId);

    public int getLastShowId();
}
