package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ShowBean;
import tickets.bean.ShowSeatBean;
import tickets.model.Show;
import tickets.model.ShowSeat;
import tickets.model.ShowSeatId;
import tickets.repository.SeatRepository;
import tickets.repository.ShowRepository;
import tickets.repository.ShowSeatRepository;
import tickets.service.ShowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService{
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public ShowBean getShowBeanById(int showId) {
        Show show = showRepository.findById(showId);
        List<ShowSeat> showSeatList = showSeatRepository.findByShowId(showId);
        Map<String, Double> seatNameAndPrice = new HashMap<>();
        Map<String, Integer> seatNameAndId = new HashMap<>();
        Map<Integer, Integer> seatIdAndAmount = new HashMap<>();
        for (ShowSeat showSeat : showSeatList) {
            int seatId = showSeat.getShowSeatId().getSeat_id();
            String seatName = seatRepository.findNameById(seatId);
            seatNameAndId.put(seatName, seatId);
            seatNameAndPrice.put(seatName, showSeat.getPrice());
            seatIdAndAmount.put(seatId, showSeat.getAvailable_amount());
        }
        return new ShowBean(show, new ShowSeatBean(seatNameAndId, seatNameAndPrice, seatIdAndAmount));
    }

    @Override
    public Show getShowById(int showId) {
        return showRepository.findById(showId);
    }

    @Override
    public int getSeatId(String seatName, int stadiumId) {
        return seatRepository.findIdByNameAndStadium_id(seatName, stadiumId);
    }

    @Override
    public ShowSeat getShowSeatById(ShowSeatId showSeatId) {
        return showSeatRepository.findOne(showSeatId);
    }
}
