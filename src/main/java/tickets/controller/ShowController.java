package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tickets.bean.ShowBean;
import tickets.service.ShowService;

@RestController
@RequestMapping(value = "/tickets/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @RequestMapping(value = "/{showId}", method = RequestMethod.GET)
    public ShowBean getShowInfoById(@PathVariable(value = "showId") int showId) {
        return showService.getShowInfoById(showId);
    }
}
