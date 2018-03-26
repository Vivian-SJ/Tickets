package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tickets.bean.SeatBean;
import tickets.service.SeatService;

@RestController
@RequestMapping(value = "/tickets/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @RequestMapping(value = "/{seatId}", method = RequestMethod.GET)
    public SeatBean getSeatInfo(@PathVariable(value = "seatId") int seatId) {
        return seatService.getSeatInfo(seatId);
    }
}
