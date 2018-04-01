package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.AccountBean;
import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StatisticsBean;
import tickets.model.Account;
import tickets.model.Manager;
import tickets.model.Stadium;
import tickets.repository.ManagerRepository;
import tickets.repository.MemberRepository;
import tickets.service.AccountService;
import tickets.service.ManagerService;
import tickets.service.MemberService;
import tickets.service.StadiumService;
import tickets.util.CodeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    private AccountService accountService;

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

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
            AccountBean accountBean = new AccountBean(account);
            accountBeans.add(accountBean);
        }
        return accountBeans;
    }

    @Override
    public ResultBean pay(AccountBean accountBean) {
        Account account = accountService.findAccountById(accountBean.getId());
        double stadium_income = accountBean.getStadium_income();
        double web_income = accountBean.getWeb_income();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        account.setPay_time(timestamp);
        account.setStadium_income(stadium_income);
        account.setWeb_income(web_income);
        account.setStatus("已结算");
        accountService.save(account);

        Stadium stadium = stadiumService.getStadiumById(accountBean.getStadium_id());
        stadium.setIncome(stadium.getIncome() + stadium_income);
        stadiumService.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public List<StatisticsBean> getStadiumsStatistics() {
        List<Integer> stadiumIds = stadiumService.findAllStaiumIds();
        List<StatisticsBean> statisticsBeans = new ArrayList<>();
        for (int id : stadiumIds) {
            StatisticsBean statisticsBean = stadiumService.displayStadiumStatistics(id);
            statisticsBeans.add(statisticsBean);
        }
        return statisticsBeans;
    }

    @Override
    public List<StatisticsBean> getMembersStatistics() {
        List<Integer> memberIds = memberRepository.findAllIds();
        List<StatisticsBean> statisticsBeans = new ArrayList<>();
        for (int id : memberIds) {
            StatisticsBean statisticsBean = memberService.displayMemberStatistics(id);
            statisticsBeans.add(statisticsBean);
        }
        return statisticsBeans;
    }


}
