package tickets.service;

import tickets.bean.*;

import java.util.List;

public interface ManagerService {
    public ResultBean login(int id, String password);

    public ResultBean checkStadium(StadiumBean stadiumBean);

    public ResultBean refuseCheck(int stadiumId, String message);

    public List<AccountBean> getToBePayedAccounts();

    public ResultBean pay(AccountBean accountBean);

    public List<StatisticsBeanForMembersAndStadiums> getStadiumsStatistics();

    public List<StatisticsBeanForMembersAndStadiums> getMembersStatistics();

    public ShowsBean getAllShows();

    public StatisticsBeanForManager getWebStatistics();
}
