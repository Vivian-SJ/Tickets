package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.model.Member;
import tickets.repository.MemberRepository;
import tickets.service.MemberService;
import tickets.bean.MemberBean;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberBean findMemberById(int memberId) {
        MemberBean memberBean = new MemberBean(memberRepository.findById(memberId));
        return memberBean;
    }

    @Override
    public boolean checkUser(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            return false;
        }
        if (!member.getPassword().equals(password)) {
            return false;
        }
        return true;
    }


}
