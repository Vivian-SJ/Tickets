package tickets.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test() {
//        TicketBuyBean ticketBuyBean = new TicketBuyBean();
//        Order order = new Order();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("时间戳！！！！"+timestamp);
        Long l1 = timestamp.getTime();

        String t1 = "2018-02-20 16:25:02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        try {
            date1 = sdf.parse(t1);
        } catch (ParseException pe){
            System.out.println(pe.getMessage());
        }
        Timestamp timestamp1 = new Timestamp(date1.getTime());
        System.out.println(timestamp1);
        Long l2 = timestamp1.getTime();
        int diff = (int) (l1-l2)/(1000*60*60*24);
        System.out.println(diff);
    }
}
