package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.ShowBean;
import tickets.service.ShowService;

@RestController
@RequestMapping(value = "/tickets/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @RequestMapping(value = "/{showId}", method = RequestMethod.GET)
    public ShowBean getShowInfoById(@PathVariable(value = "showId") int showId) {
        return showService.getShowBeanById(showId);
    }

//    @RequestMapping(value = "/seatId", method = RequestMethod.GET)
//    public int getSeatId(@RequestParam(value = "seatName") String seatName, @RequestParam(value = "stadiumId") int stadiumId){
//        return showService.getSeatId(seatName, stadiumId);
//    }
}
