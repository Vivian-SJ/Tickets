package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.*;
import tickets.model.*;
import tickets.repository.*;
import tickets.service.SeatService;
import tickets.service.ShowService;
import tickets.service.StadiumService;
import tickets.util.CodeUtil;
import tickets.util.OrderStatus;

import java.sql.Timestamp;
import java.util.*;

@Service
public class StadiumServiceImpl implements StadiumService{
    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShowService showService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private SeatService seatService;

    @Override
    public ResultBeanWithId register(StadiumBean stadiumBean) {
        Stadium stadium = new Stadium(stadiumBean);
        System.out.println("id test" + stadiumBean.getId());
        Integer maxId = stadiumRepository.getLastId();
        if (maxId == null) {
            maxId = 1000000;
        } else {
            maxId = maxId+1;
        }
        stadium.setId(maxId);
        String passwordCode = "";
        try {
            passwordCode = CodeUtil.encrypt(stadiumBean.getPassword().getBytes());
        } catch (Exception e) {
            System.out.println("密码加密时出错了！");
            return new ResultBeanWithId(new ResultBean(false, "注册失败，请稍后再试"), -1);
        }
        stadium.setPassword(passwordCode);
        stadiumRepository.save(stadium);

        List<SeatBean> seatBeans = stadiumBean.getSeats();
        for (SeatBean seatBean : seatBeans) {
            seatBean.setStadiumId(maxId);
            Seat seat = new Seat(seatBean);
            seatRepository.insertSeat(seat.getStadium_id(), seat.getName(), seat.getAmount());
        }
        ResultBean resultBean = new ResultBean(true);
        return new ResultBeanWithId(resultBean, maxId);
    }

    @Override
    public ResultBeanWithId login(int id, String password) {
        Stadium stadium = stadiumRepository.findById(id);
        try {
            if (stadium == null) {
                return new ResultBeanWithId(new ResultBean(false, "用户不存在，请检查ID是否正确"),-1);
            }
            if (!new String(CodeUtil.decrypt(stadium.getPassword())).equals(password)) {
                return new ResultBeanWithId(new ResultBean(false, "密码与用户名不符"),-1);
            }
            return new ResultBeanWithId(new ResultBean(true),id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultBeanWithId(new ResultBean(false, "登录失败"),-1);
    }

    @Override
    public Stadium getStadiumById(int stadiumId) {
        return stadiumRepository.findById(stadiumId);
    }

    @Override
    public StadiumBean getStadiumBeanById(int stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId);
        List<Seat> seats = seatRepository.findSeatsById(stadiumId);
        List<SeatBean> seatBeans = new ArrayList<>();
        for (Seat seat : seats) {
            SeatBean seatBean = new SeatBean(seat);
            seatBeans.add(seatBean);
        }
        StadiumBean stadiumBean = new StadiumBean(stadium, seatBeans);
        try {
            String password = new String(CodeUtil.decrypt(stadiumBean.getPassword()));
            stadiumBean.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stadiumBean;
    }

    @Override
    public ResultBean modifyInfo(StadiumBean stadiumBean) {
        Stadium stadium = stadiumRepository.findById(stadiumBean.getId());
        stadium.setName(stadiumBean.getName());
        String passwordCode = "";
        try {
            passwordCode = CodeUtil.encrypt(stadiumBean.getPassword().getBytes());
        } catch (Exception e) {
            System.out.println("密码加密时出错了！");
            return new ResultBean(false, "修改失败，请稍后再试");
        }
        stadium.setPassword(passwordCode);
        stadium.setDescription(stadiumBean.getDescription());
        stadium.setPlace(stadiumBean.getPlace());
        stadium.setStatus("修改未审核");
        stadiumRepository.save(stadium);
        return new ResultBean(true);
    }

    @Override
    public ResultBean releaseShow(ShowBean showBean) {
//        showBean.setStatus("未审核");
        Show show = new Show(showBean);
        System.out.println("description:"+showBean.getDescription());
        showRepository.save(show.getName(), show.getTime(), show.getStadium_id(), show.getType(), show.getDescription());
        Map<String, Double> seatAndPrice = showBean.getShowSeatBean().getSeatNameAndPrice();
        System.out.println(seatAndPrice.size());
        int showId = showRepository.getId();
        System.out.println(showId);
        for (Map.Entry<String, Double> entry : seatAndPrice.entrySet()) {
            int seatId = seatRepository.findIdByNameAndStadium_id(entry.getKey(), showBean.getStadiumId());
            System.out.println("seatId:"+seatId);
            Seat seat = seatRepository.findById(seatId);
            ShowSeatId showSeatId = new ShowSeatId(showId, seatId);
            ShowSeat showSeat = new ShowSeat(showSeatId, entry.getValue(), seat.getAmount());
            showSeatRepository.save(showSeat);
        }
        return new ResultBean(true);
    }

    @Override
    public List<ShowBean> displayShows(int stadiumId) {
        List<Show> shows = showRepository.findByStadium_id(stadiumId);
        List<ShowBean> showBeans = new ArrayList<>();
        for (Show show : shows) {
            ShowBean showBean = showService.getShowBeanById(show.getId());
            showBeans.add(showBean);
        }
        return showBeans;
    }

    @Override
    public ResultBean checkTicket(int orderId) {
        Order order = orderRepository.findById(orderId);
        if (!order.getStatus().equals("已出票")){
            return null;
        }
        order.setStatus(OrderStatus.USED.toString());
        orderRepository.save(order);
        return new ResultBean(true);
    }

    @Override
    public OrderBean findOrderById(int orderId) {
        Order order = orderRepository.findById(orderId);
        Show show = showRepository.findById(order.getShow_id());
        String showName = show.getName();
        Timestamp showTime = show.getTime();
        String stadiumName = this.getStadiumBeanById(order.getStadium_id()).getName();
        String seatName = "";
        if (order.getSeat_id() != -1) {
            seatName = seatService.getSeatInfo(order.getSeat_id()).getName();
        }
        List<Coupon> coupons = couponRepository.findByOrder_id(orderId);
        List<Integer> couponIds = new ArrayList<>();
        for (Coupon coupon : coupons) {
            couponIds.add(coupon.getId());
        }
        return new OrderBean(order, couponIds, stadiumName, showName, seatName, showTime);
    }

    @Override
    public StatisticsBean displayStadiumStatistics(int stadiumId) {
        int orderSum = orderRepository.getAmountByStadiumId(stadiumId);
        Map<String, List<OrderBean>> map = new HashMap<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            List<Order> orders = orderRepository.findByStadium_idAndStatus(stadiumId, orderStatus.toString());
            List<OrderBean> orderBeans = new ArrayList<>();
            for (Order order : orders) {
                OrderBean orderBean = new OrderBean(order);
                orderBeans.add(orderBean);
            }
            map.put(orderStatus.toString(), orderBeans);
        }
        double totalPrice = stadiumRepository.findById(stadiumId).getIncome();
        return new StatisticsBean(orderSum, map, totalPrice);
    }

    @Override
    public List<StadiumBean> getUncheckStadiums() {
        List<Stadium> stadiums = stadiumRepository.getUncheckStadiums();
        List<StadiumBean> stadiumBeans = new ArrayList<>();
        for (Stadium stadium : stadiums) {
            List<Seat> seats = seatRepository.findSeatsById(stadium.getId());
            List<SeatBean> seatBeans = new ArrayList<>();
            for (Seat seat : seats) {
                SeatBean seatBean = new SeatBean(seat);
                seatBeans.add(seatBean);
            }
            StadiumBean stadiumBean = new StadiumBean(stadium, seatBeans);
            try {
                String password = new String(CodeUtil.decrypt(stadiumBean.getPassword()));
                stadiumBean.setPassword(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stadiumBeans.add(stadiumBean);
        }
        return stadiumBeans;
    }

    @Override
    public void save(Stadium stadium) {
        stadiumRepository.save(stadium);
    }

    @Override
    public List<Integer> findAllStaiumIds() {
        return stadiumRepository.findAllIds();
    }

    @Override
    public int getStadiumAmount() {
        return stadiumRepository.getStadiumAmount();
    }
}
