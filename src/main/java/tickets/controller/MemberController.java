package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tickets.service.MemberService;
import tickets.bean.MemberBean;
import tickets.bean.ResultBean;

@Controller
@RequestMapping("/tickets")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(@RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password){
        System.out.println(email);
        System.out.println(password);
        boolean userIsValid = memberService.checkUser(email, password);
        if (userIsValid) {
            return new ResultBean(true);
        }
        return new ResultBean(false, "fail");
    }

    @RequestMapping(value="/greeting",method = RequestMethod.GET)
    public String Greeting() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/member/info/{memberId}", method = RequestMethod.GET)
    public MemberBean findMemberById(@PathVariable(value = "memberId") int memberId) {
        return memberService.findMemberById(memberId);
    }
}
