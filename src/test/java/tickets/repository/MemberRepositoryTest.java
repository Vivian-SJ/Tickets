package tickets.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tickets.model.Member;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test() {
        Member member = new Member("1", "123", "1030518209@qq.com", "女");
        memberRepository.save(member);
        memberRepository.findById(4);
        System.out.println(member.getEmail());
        System.out.println(member.getGender());
    }

    @Test
    public void test1() {
        Member member = memberRepository.findByEmail("1");
        if (member == null) {
            System.out.println("null");
        }
    }
}
