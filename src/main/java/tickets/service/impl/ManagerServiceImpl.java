package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StatisticsBean;
import tickets.model.Account;
import tickets.model.Stadium;
import tickets.repository.AccountRepository;
import tickets.repository.MemberRepository;
import tickets.repository.StadiumRepository;
import tickets.service.ManagerService;
import tickets.service.MemberService;
import tickets.service.StadiumService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public ResultBean checkStadium(StadiumBean stadiumBean) {
        Stadium stadium = stadiumRepository.findById(stadiumBean.getId());
        stadium.setStatus("已审核");
        stadiumRepository.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public ResultBean pay(int stadiumId) {
        Account account = accountRepository.findByStadium_idAndStatus(stadiumId, "未结算");
        double stadium_income = account.getTotal_income() * 0.7;
        double web_income = account.getTotal_income() * 0.3;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        account.setPay_time(timestamp);
        account.setStadium_income(stadium_income);
        account.setWeb_income(web_income);
        account.setStatus("已结算");
        accountRepository.save(account);

        Stadium stadium = stadiumRepository.findById(stadiumId);
        stadium.setIncome(stadium.getIncome() + stadium_income);
        stadiumRepository.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public List<StatisticsBean> getStadiumsStatistics() {
        List<Integer> stadiumIds = stadiumRepository.findAllIds();
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
