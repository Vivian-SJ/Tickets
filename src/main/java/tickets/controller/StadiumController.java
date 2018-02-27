package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.*;
import tickets.service.StadiumService;

import java.util.List;

@RestController
@RequestMapping("/tickets/stadium")
public class StadiumController {
    @Autowired
    private StadiumService stadiumService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public StadiumRegisterBean register(@RequestBody StadiumBean stadiumBean) {
        return stadiumService.register(stadiumBean);
    }

    @RequestMapping(value = "/info/{stadiumId}", method = RequestMethod.GET)
    public StadiumBean getInfoById(@PathVariable(value = "stadiumId") int stadiumId) {
        return stadiumService.getInfoById(stadiumId);
    }

    @RequestMapping(value = "/info/modify", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean modifyInfo(@RequestBody StadiumBean stadiumBean) {
        return stadiumService.modifyInfo(stadiumBean);
    }

    @RequestMapping(value = "/releaseShow", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean releaseShow(@RequestBody ShowBean showBean) {
        return stadiumService.releaseShow(showBean);
    }

    @RequestMapping(value = "/shows/{stadiumId}", method = RequestMethod.GET)
    public List<ShowBean> displayShows(@PathVariable(value = "stadiumId") int stadiumId) {
        return stadiumService.displayShows(stadiumId);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean checkTicket(@RequestBody TicketCheckBean ticketCheckBean) {
        return stadiumService.checkTicket(ticketCheckBean);
    }

    @RequestMapping(value = "/statistics/{stadiumId}", method = RequestMethod.GET)
    public StatisticsBean displayStadiumStatistics(@PathVariable(value = "stadiumId") int stadiumId) {
        return stadiumService.displayMemberStatistics(stadiumId);
    }
}
