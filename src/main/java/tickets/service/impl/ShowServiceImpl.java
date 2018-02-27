package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ShowBean;
import tickets.model.Show;
import tickets.model.ShowSeatPrice;
import tickets.repository.ShowRepository;
import tickets.repository.ShowSeatPriceRepository;
import tickets.service.ShowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService{
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatPriceRepository showSeatPriceRepository;

    @Override
    public ShowBean getShowInfoById(int showId) {
        Show show = showRepository.findById(showId);
        List<ShowSeatPrice> showSeatPriceList = showSeatPriceRepository.findByShowId(showId);
        Map<Integer, Double> map = new HashMap<>();
        for (ShowSeatPrice showSeatPrice : showSeatPriceList) {
            map.put(showSeatPrice.getShowSeatPriceId().getSeat_id(), showSeatPrice.getPrice());
        }
        return new ShowBean(show, map);
    }
}
