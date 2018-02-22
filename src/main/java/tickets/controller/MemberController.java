package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.MemberBean;
import tickets.bean.ResultBean;
import tickets.service.MemberService;

@RestController
@RequestMapping("/tickets")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(@RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password){
        System.out.println(email);
        System.out.println(password);
        return memberService.checkUser(email, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean register(@RequestBody MemberBean memberBean) {
        return memberService.register(memberBean);
    }

    @RequestMapping(value = "/register/confirm", method = RequestMethod.GET)
    public ResultBean registerConfirm(@RequestParam(value = "email") String emailCode,
                                      @RequestParam(value = "code") String code) {
        return memberService.registerConfirm(emailCode, code);
    }

    @RequestMapping(value="/greeting",method = RequestMethod.GET)
    public String Greeting() {
        return "login";
    }

    @RequestMapping(value = "/member/info/{memberId}", method = RequestMethod.GET)
    public MemberBean findMemberById(@PathVariable(value = "memberId") int memberId) {
        return memberService.findMemberById(memberId);
    }
}
