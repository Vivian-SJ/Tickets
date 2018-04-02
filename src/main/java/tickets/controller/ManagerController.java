package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.*;
import tickets.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping(value = "/tickets/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(@RequestParam(value = "id") int id,
                            @RequestParam(value = "password") String password) {
        return managerService.login(id, password);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean checkStadium(@RequestBody StadiumBean stadiumBean) {
        return managerService.checkStadium(stadiumBean);
    }

    @RequestMapping(value = "/check/refuse", method = RequestMethod.POST)
    public ResultBean refuseCheck(@RequestParam(value = "stadiumId") int stadiumId, @RequestParam(value = "message") String message) {
        return managerService.refuseCheck(stadiumId, message);
    }

    @RequestMapping(value = "/toBePayedAccounts", method = RequestMethod.GET)
    public List<AccountBean> getToBePayedAccounts() {
        return managerService.getToBePayedAccounts();
    }

    @RequestMapping(value = "/pay" , method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean pay(@RequestBody AccountBean accountBean) {
        return managerService.pay(accountBean);
    }

    @RequestMapping(value = "/statistics/stadiums", method = RequestMethod.GET)
    public List<StatisticsBean> getStadiumsStatistics(){
        return managerService.getStadiumsStatistics();
    }

    @RequestMapping(value = "/statistics/members", method = RequestMethod.GET)
    public List<StatisticsBean> getMembersStatistics(){
        return managerService.getMembersStatistics();
    }

    @RequestMapping(value = "/allShows", method = RequestMethod.GET)
    public ShowsBean getAllShows() {
        return managerService.getAllShows();
    }

    @RequestMapping(value = "/web/statistics", method = RequestMethod.GET)
    public ManagerStatisticsBean getWebStatistics() {
        return managerService.getWebStatistics();
    }
}
