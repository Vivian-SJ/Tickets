package tickets.service;

import tickets.bean.*;
import tickets.model.Stadium;

import java.util.List;

public interface StadiumService {
    public ResultBeanWithId register(StadiumBean stadiumBean);

    public ResultBeanWithId login (int id, String password);

    public Stadium getStadiumById(int stadiumId);

    public StadiumBean getStadiumBeanById(int stadiumId);

    public ResultBean modifyInfo(StadiumBean stadiumBean);

    public ResultBean releaseShow(ShowBean showBean);

    public List<ShowBean> displayShows(int stadiumId);

    public ResultBean checkTicket(int orderId);

    public OrderBean findOrderById(int orderId);

    public StatisticsBeanForMemberAndStadium displayStadiumStatistics(int stadiumId);

    public List<StadiumBean> getUncheckStadiums();

    public void save(Stadium stadium);

    public List<Integer> findAllStaiumIds();

    public int getStadiumAmount();
}
