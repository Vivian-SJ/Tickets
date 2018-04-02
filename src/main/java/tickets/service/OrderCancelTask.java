package tickets.service;

import org.springframework.web.context.WebApplicationContext;
import tickets.bean.OrderBean;
import tickets.model.Order;
import tickets.util.OrderStatus;

import javax.annotation.Resource;
import java.util.TimerTask;

public class OrderCancelTask extends TimerTask {
    @Resource(name = "memberService")
    private MemberService memberService;

    private int orderId;

    @Resource(name = "orderService")
    private OrderService orderService;

    public OrderCancelTask(WebApplicationContext webApplicationContext, int orderId) {
        this.memberService = (MemberService) webApplicationContext.getBean("memberService");
        this.orderId = orderId;
    }

    @Override
    public void run() {
        OrderBean order1 = memberService.getOrder(orderId);
        if (order1.getOrderStatus().equals("待支付")) {
            System.out.println(orderId);
            Order order = orderService.getOrderById(orderId);
            order.setStatus(OrderStatus.CANCELED.toString());
            orderService.save(order);
            System.out.println("cancel order success!");
        }
    }
}
