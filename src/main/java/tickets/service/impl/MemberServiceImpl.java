package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.model.Member;
import tickets.repository.MemberRepository;
import tickets.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member findMemberById(int memberId) {
        return memberRepository.findById(memberId);
    }
}
