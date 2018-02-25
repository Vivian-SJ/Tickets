package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ResultBean;
import tickets.bean.SeatBean;
import tickets.bean.StadiumBean;
import tickets.bean.StadiumRegisterBean;
import tickets.model.Seat;
import tickets.model.Stadium;
import tickets.repository.SeatRepository;
import tickets.repository.StadiumRepository;
import tickets.service.StadiumService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StadiumServiceImpl implements StadiumService{
    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public StadiumRegisterBean register(StadiumBean stadiumBean) {
        Stadium stadium = new Stadium(stadiumBean);
        System.out.println("id test" + stadiumBean.getId());
        Integer maxId = stadiumRepository.getLastId();
        if (maxId == null) {
            maxId = 1000000;
        } else {
            maxId = maxId+1;
        }
        stadium.setId(maxId);
        stadiumRepository.save(stadium);

        List<SeatBean> seatBeans = stadiumBean.getSeats();
        for (SeatBean seatBean : seatBeans) {
            seatBean.setStadiumId(maxId);
            Seat seat = new Seat(seatBean);
            seatRepository.insertSeat(seat.getStadium_id(), seat.getName(), seat.getAmount());
        }
        ResultBean resultBean = new ResultBean(true);
        return new StadiumRegisterBean(maxId, resultBean);
    }

    @Override
    public StadiumBean getInfoById(int stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId);
        List<Seat> seats = seatRepository.findSeatsById(stadiumId);
        List<SeatBean> seatBeans = new ArrayList<>();
        for (Seat seat : seats) {
            SeatBean seatBean = new SeatBean(seat);
            seatBeans.add(seatBean);
        }
        return new StadiumBean(stadium, seatBeans);
    }

    @Override
    public ResultBean modifyInfo(StadiumBean stadiumBean) {
        Stadium stadium = new Stadium(stadiumBean);
        stadiumRepository.save(stadium);
        List<SeatBean> seatBeans = stadiumBean.getSeats();
        for (SeatBean seatBean : seatBeans) {
            Seat seat = new Seat(seatBean);
            seatRepository.save(seat);
        }
        return new ResultBean(true);
    }
}
