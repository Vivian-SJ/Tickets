package tickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tickets.model.Order;
import tickets.model.Show;
import tickets.model.ShowSeat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TicketsAllocationTask {
    @Autowired
    private ShowService showService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    //每天0点执行
    @Scheduled(cron = "0 35 14 * * ?")
//    @Scheduled(fixedDelay = 5000)
    public void allocateTickets() {
        System.out.println("run!!!");
        List<Show> shows = showService.getShowsToBeAllocatedSeat();
        System.out.println("getshow:" + shows.size());
        for (Show show : shows) {
            System.out.println("showId: " + show.getId());
            List<Order> orders = orderService.getOrderToBeAllocatedSeat(show.getId());
            System.out.println("getOrder" + orders.size());
            if (orders.size()==0) {
                continue;
            }
            List<ShowSeat> showSeats = showService.getShowSeatByShowId(show.getId());
            List<Double> prices = new ArrayList<>();
            for (ShowSeat showSeat : showSeats) {
                double price = showSeat.getPrice();
                prices.add(price);
            }
            Collections.sort(prices);
            int orderPointer = 0;
            for (int i=0;i<prices.size();i++) {
                if (orderPointer==orders.size()) {
                    break;
                }
                for (ShowSeat showSeat:showSeats) {
                    if (showSeat.getPrice()-prices.get(i)<0.0001) {
                        int availableAmount = showSeat.getAvailable_amount();
                        int needAmount = orders.get(orderPointer).getAmount();
                        if (availableAmount>=needAmount) {
                            Order order = orders.get(orderPointer);
                            order.setSeat_id(showSeat.getShowSeatId().getSeat_id());
                            order.setStatus("已出票");
                            orderService.save(order);
                            orderPointer++;
                        }
                        break;
                    }
                }
            }
            //遍历完成之后还没有配完票，剩下的就退款
            if (orderPointer<orders.size()) {
                for (int i=orderPointer;i<orders.size();i++) {
                    Order order = orders.get(i);
                    memberService.cancelOrder(order.getId(), true);
                }
            }
        }
    }
}
