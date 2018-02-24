package tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tickets.model.Coupon;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CouponRepositoryTest {
    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void testAdd() {
        Coupon coupon = new Coupon(5, 5, "未使用");
        couponRepository.save(coupon);
    }
    @Test
    public void test() {
        List<Coupon> coupons = couponRepository.findBymember_id(5);
        System.out.println(coupons.size());
        Coupon coupon = coupons.get(0);
        coupon.setStatus("selected");
        couponRepository.save(coupon);
    }
}
