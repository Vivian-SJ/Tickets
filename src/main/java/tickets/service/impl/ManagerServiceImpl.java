package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.*;
import tickets.model.*;
import tickets.repository.ManagerRepository;
import tickets.service.*;
import tickets.util.CodeUtil;
import tickets.util.ShowType;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    private AccountService accountService;

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ShowService showService;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public ResultBean login(int id, String password) {
        Manager manager = managerRepository.findOne(id);
        String rightPassword = "";
        try {
            rightPassword = new String(CodeUtil.decrypt(manager.getPassword()));
        } catch (Exception e) {
            return new ResultBean(false, "密码解密时出错");
        }
        if (password.equals(rightPassword)) {
            return new ResultBean(true);
        }
        return new ResultBean(false, "登录失败，请稍后再试");
    }

    @Override
    public ResultBean checkStadium(StadiumBean stadiumBean) {
        Stadium stadium = stadiumService.getStadiumById(stadiumBean.getId());
        stadium.setStatus("已审核");
        stadiumService.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public ResultBean refuseCheck(int stadiumId, String message) {
        Stadium stadium = stadiumService.getStadiumById(stadiumId);
        stadium.setStatus("审核未通过");
        stadium.setStatus_info(message);
        stadiumService.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public List<AccountBean> getToBePayedAccounts() {
        List<Account> accounts = accountService.getToBePaidAccounts();
        List<AccountBean> accountBeans = new ArrayList<>();
        for (Account account : accounts) {
            Show show = showService.getShowById(account.getShow_id());
            Timestamp now = new Timestamp(new Date().getTime());
            //若没有演出结束则先不结算
            if (show.getTime().compareTo(now) > 0) {
                continue;
            }
            Stadium stadium = stadiumService.getStadiumById(account.getStadium_id());
            AccountBean accountBean = new AccountBean(account, show, stadium);
            accountBeans.add(accountBean);
        }
        return accountBeans;
    }

    @Override
    public ResultBean pay(AccountBean accountBean) {
        Account account = accountService.findAccountById(accountBean.getId());
        double stadium_income = accountBean.getStadiumIncome();
        double web_income = accountBean.getWebIncome();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        account.setPay_time(timestamp);
        account.setStadium_income(stadium_income);
        account.setWeb_income(web_income);
        account.setStatus("已结算");
        accountService.save(account);

        Stadium stadium = stadiumService.getStadiumById(accountBean.getStadiumId());
        stadium.setIncome(stadium.getIncome() + stadium_income);
        stadiumService.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public List<StatisticsBeanForMembersAndStadiums> getStadiumsStatistics() {
        List<Integer> stadiumIds = stadiumService.findAllStaiumIds();
        List<StatisticsBeanForMembersAndStadiums> statisticsBeanForStadiums = new ArrayList<>();
        for (int id : stadiumIds) {
            Stadium stadium = stadiumService.getStadiumById(id);
            StatisticsBeanForMemberAndStadium statisticsBeanForMemberAndStadium = stadiumService.displayStadiumStatistics(id);
            statisticsBeanForStadiums.add(new StatisticsBeanForMembersAndStadiums(id, stadium.getName(), statisticsBeanForMemberAndStadium));
        }
        return statisticsBeanForStadiums;
    }

    @Override
    public List<StatisticsBeanForMembersAndStadiums> getMembersStatistics() {
        List<Integer> memberIds = memberService.findAllIds();
        List<StatisticsBeanForMembersAndStadiums> statisticsBeanForMembers = new ArrayList<>();
        for (int id : memberIds) {
            Member member = memberService.findMemberById(id);
            StatisticsBeanForMemberAndStadium statisticsBeanForMemberAndStadium = memberService.displayMemberStatistics(id);
            statisticsBeanForMembers.add(new StatisticsBeanForMembersAndStadiums(id, member.getName(), statisticsBeanForMemberAndStadium));
        }
        return statisticsBeanForMembers;
    }

    @Override
    public ShowsBean getAllShows() {
        Map<String, List<ShowBean>> map = new HashMap<>();
        for (int i = 0; i < ShowType.values().length; i++) {
            String type = ShowType.getName(i);
            List<Show> shows = showService.findByType(type);
            List<ShowBean> showBeans = new ArrayList<>();
            for (Show show : shows) {
                ShowBean showBean = showService.getShowBeanById(show.getId());
                showBeans.add(showBean);
            }
            map.put(type, showBeans);
        }
        return new ShowsBean(map);
    }

    @Override
    public StatisticsBeanForManager getWebStatistics() {
        int stadiumAmount = stadiumService.getStadiumAmount();
        int memberAmount = memberService.getMemberAmount();
        double income = accountService.getWebTotalIncome();
        return new StatisticsBeanForManager(income, stadiumAmount,memberAmount);
    }


}
