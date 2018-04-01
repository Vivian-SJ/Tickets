package tickets.service;

import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StatisticsBean;

import java.util.List;

public interface ManagerService {
    public ResultBean checkStadium(StadiumBean stadiumBean);

    public ResultBean refuseCheck(int stadiumId, String message);

    public ResultBean pay(int stadiumId);

    public List<StatisticsBean> getStadiumsStatistics();

    public List<StatisticsBean> getMembersStatistics();
}
