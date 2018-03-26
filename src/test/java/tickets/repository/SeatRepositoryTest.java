package tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tickets.model.Seat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SeatRepositoryTest {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Test
    public void test() {
//        Integer maxId = stadiumRepository.getLastId();
//        SeatBean seatBean = new SeatBean("1", 100);
//        SeatBean seatBean1 = new SeatBean("2", 200);
//        SeatBean seatBean2 = new SeatBean("3",200);
//        List<SeatBean> seatBeans = new ArrayList<>();
//        seatBeans.add(seatBean);
//        seatBeans.add(seatBean1);
//        seatBeans.add(seatBean2);
//        for (SeatBean seatBean3 : seatBeans) {
//            seatBean3.setStadiumId(1000000);
//            Seat seat = new Seat(seatBean3);
//            seatRepository.save(seat);
//        }
//        Seat seat = new Seat(1000000, "1", 100);
//        Seat seat1 = new Seat(1000000,"2", 200);
//        Seat seat2 = new Seat(1000000,"3",300);
//        seatRepository.save(seat);
//        seatRepository.save(seat1);
//        seatRepository.save(seat2);
        Seat seat = seatRepository.findById(35);
    }
}
