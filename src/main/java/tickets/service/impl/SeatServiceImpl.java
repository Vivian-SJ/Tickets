package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.SeatBean;
import tickets.repository.SeatRepository;
import tickets.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService{
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public SeatBean getSeatInfo(int seatId) {
        return new SeatBean(seatRepository.findById(seatId));
    }
}
