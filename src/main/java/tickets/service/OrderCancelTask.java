package tickets.service;

import org.springframework.web.context.WebApplicationContext;
import tickets.bean.OrderBean;

import javax.annotation.Resource;
import java.util.TimerTask;

public class OrderCancelTask extends TimerTask {
    @Resource(name = "memberService")
    private MemberService memberService;
    private int orderId;

    public OrderCancelTask(WebApplicationContext webApplicationContext, int orderId) {
        this.memberService = (MemberService) webApplicationContext.getBean("memberService");
        this.orderId = orderId;
    }

    @Override
    public void run() {
        OrderBean order1 = memberService.getOrder(orderId);
        if (order1.getOrderStatus().equals("待支付")) {
            System.out.println(orderId);
            memberService.cancelOrder(orderId);
            System.out.println("cancel order success!");
        }
    }
}
