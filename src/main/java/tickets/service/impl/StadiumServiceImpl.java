package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.*;
import tickets.model.*;
import tickets.repository.*;
import tickets.service.StadiumService;
import tickets.util.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StadiumServiceImpl implements StadiumService{
    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatPriceRepository showSeatPriceRepository;

    @Autowired
    private OrderRepository orderRepository;

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

    @Override
    public ResultBean releaseShow(ShowBean showBean) {
        Show show = new Show(showBean);
        System.out.println("description:"+showBean.getDescription());
        showRepository.save(show.getName(), show.getTime(), show.getStadium_id(), show.getType(), show.getDescription());
        Map<Integer, Double> seatAndPrice = showBean.getSeatAndPrice();
        int showId = showRepository.getId();
        System.out.println(showId);
        for (Map.Entry<Integer, Double> entry : seatAndPrice.entrySet()) {
            ShowSeatPriceId showSeatPriceId = new ShowSeatPriceId(showId, entry.getKey());
            ShowSeatPrice showSeatPrice = new ShowSeatPrice(showSeatPriceId, entry.getValue());
            showSeatPriceRepository.save(showSeatPrice);
        }
        return new ResultBean(true);
    }

    @Override
    public List<ShowBean> displayShows(int stadiumId) {
        List<Show> shows = showRepository.findByStadium_id(stadiumId);
        List<ShowBean> showBeans = new ArrayList<>();
        for (Show show : shows) {
            List<ShowSeatPrice> showSeatPriceList = showSeatPriceRepository.findByShowId(show.getId());
            Map<Integer, Double> map = new HashMap<>();
            for (ShowSeatPrice showSeatPrice : showSeatPriceList) {
                map.put(showSeatPrice.getShowSeatPriceId().getSeat_id(), showSeatPrice.getPrice());
            }
            ShowBean showBean = new ShowBean(show, map);
            showBeans.add(showBean);
        }
        return showBeans;
    }

    @Override
    public ResultBean checkTicket(TicketCheckBean ticketCheckBean) {
        Order order = orderRepository.findById(ticketCheckBean.getOrderId());
        order.setStatus(ticketCheckBean.getStatus().toString());
        order.setPs(ticketCheckBean.getPs());
        orderRepository.save(order);
        return new ResultBean(true);
    }

    @Override
    public StatisticsBean displayStadiumStatistics(int stadiumId) {
        Map<String, List<OrderBean>> map = new HashMap<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            List<Order> orders = orderRepository.findByStadium_idAndType(stadiumId, orderStatus.toString());
            List<OrderBean> orderBeans = new ArrayList<>();
            for (Order order : orders) {
                OrderBean orderBean = new OrderBean(order);
                orderBeans.add(orderBean);
            }
            map.put(orderStatus.toString(), orderBeans);
        }
        double totalPrice = stadiumRepository.findById(stadiumId).getIncome();
        return new StatisticsBean(map, totalPrice);
    }
}
