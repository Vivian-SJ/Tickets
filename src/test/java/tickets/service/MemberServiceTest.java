package tickets.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tickets.bean.OrderBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void test() {
        memberService.buyTicket(new OrderBean());
//        memberService.displayOrder(18);
    }
}
