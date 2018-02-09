package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tickets.model.Member;
import tickets.service.MemberService;

@RestController
@RequestMapping("/tickets")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/info/{memberId}", method = RequestMethod.GET)
    public Member findMemberById(@PathVariable(value = "memberId") int memberId) {
        return memberService.findMemberById(memberId);
    }
}
