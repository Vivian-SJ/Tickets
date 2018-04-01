package tickets.service;

import tickets.bean.AccountBean;
import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StatisticsBean;

import java.util.List;

public interface ManagerService {
    public ResultBean login(int id, String password);

    public ResultBean checkStadium(StadiumBean stadiumBean);

    public ResultBean refuseCheck(int stadiumId, String message);

    public List<AccountBean> getToBePayedAccounts();

    public ResultBean pay(AccountBean accountBean);

    public List<StatisticsBean> getStadiumsStatistics();

    public List<StatisticsBean> getMembersStatistics();
}
