package tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShowRepositpryTest {
    @Autowired
    private ShowRepository showRepository;

    @Test
    public void test(){
//        Show show = new Show(1000000);
//        showRepository.save(show);
        showRepository.findById(2);
    }
}
