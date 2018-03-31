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
    public ResultBeanWithId register(@RequestBody StadiumBean stadiumBean) {
        return stadiumService.register(stadiumBean);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBeanWithId login(@RequestParam(value = "id") int id,
                                  @RequestParam(value = "password") String password){
        return stadiumService.login(id, password);
    }

    @RequestMapping(value = "/info/{stadiumId}", method = RequestMethod.GET)
    public StadiumBean getInfoById(@PathVariable(value = "stadiumId") int stadiumId) {
        return stadiumService.getInfoById(stadiumId);
    }

    @RequestMapping(value = "/info/modify", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean modifyInfo(@RequestBody StadiumBean stadiumBean) {
        System.out.println(stadiumBean.getId());
        System.out.println("ps: "+stadiumBean.getPassword());
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

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResultBean checkTicket(@RequestParam(value = "orderId") int orderId) {
        return stadiumService.checkTicket(orderId);
    }

    @RequestMapping(value = "/search/{orderId}", method = RequestMethod.GET)
    public OrderBean findOrderById(@PathVariable(value = "orderId") int orderId) {
        return stadiumService.findOrderById(orderId);
    }

    @RequestMapping(value = "/statistics/{stadiumId}", method = RequestMethod.GET)
    public StatisticsBean displayStadiumStatistics(@PathVariable(value = "stadiumId") int stadiumId) {
        return stadiumService.displayStadiumStatistics(stadiumId);
    }
}
