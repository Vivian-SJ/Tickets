package tickets.service;

import tickets.bean.*;

import java.util.List;

public interface StadiumService {
    public ResultBeanWithId register(StadiumBean stadiumBean);

    public ResultBeanWithId login (int id, String password);

    public StadiumBean getInfoById(int stadiumId);

    public ResultBean modifyInfo(StadiumBean stadiumBean);

    public ResultBean releaseShow(ShowBean showBean);

    public List<ShowBean> displayShows(int stadiumId);

    public ResultBean checkTicket(int orderId);

    public OrderBean findOrderById(int orderId);

    public StatisticsBean displayStadiumStatistics(int stadiumId);

    public List<StadiumBean> getUncheckStadiums();
}
