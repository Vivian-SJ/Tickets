package tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tickets.model.ShowSeat;
import tickets.model.ShowSeatId;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SeatRepositoryTest {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Test
    public void test() {
        ShowSeatId showSeatId = new ShowSeatId(13, 36);
        ShowSeat showSeat = new ShowSeat(showSeatId, 250, 200);
        showSeatRepository.save(showSeat);
    }
}
