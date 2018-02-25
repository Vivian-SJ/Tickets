package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StadiumRegisterBean;
import tickets.service.StadiumService;

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
}
