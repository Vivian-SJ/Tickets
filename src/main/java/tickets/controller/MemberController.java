package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tickets.bean.*;
import tickets.service.MemberService;

import java.util.List;

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

    @RequestMapping(value = "/member/cancel/{memberId}", method = RequestMethod.GET)
    public ResultBean cancelMember(@PathVariable(value = "memberId") int memberId) {
        return memberService.cancelMember(memberId);
    }

    @RequestMapping(value = "/member/logout/{memberId}", method = RequestMethod.GET)
    public ResultBean logout(@PathVariable(value = "memberId") int memberId) {
        return new ResultBean(true);
    }

    @RequestMapping(value = "/member/info/modify/{memberId}", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean modifyInfo(@PathVariable(value = "memberId") int memberId, @RequestBody MemberBean memberBean) {
        return memberService.modifyInfo(memberId, memberBean);
    }

    @RequestMapping(value = "/member/account/{memberId}", method = RequestMethod.GET)
    public MemberAccountBean getMemberAccountInfo(@PathVariable(value = "memberId") int memberId) {
        return memberService.getMemberAccountInfo(memberId);
    }

    @RequestMapping(value = "/member/coupon/exchange", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean exchangeCoupon(@RequestParam(value = "memberId") int memberId,
                                     @RequestParam(value = "couponValue") double couponValue ) {
        return memberService.exchangeCoupon(memberId, couponValue);
    }
    @RequestMapping(value = "/member/ticket/buy", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean buyTicket(@RequestBody OrderBean orderBean) {
        return memberService.buyTicket(orderBean);
    }

    @RequestMapping(value = "/member/ticket/cancel/{orderId}", method = RequestMethod.GET)
    public ResultBean cancelOrder(@PathVariable(value = "orderId") int orderId) {
        return memberService.cancelOrder(orderId);
    }

    @RequestMapping(value = "/member/ticket/pay", method = RequestMethod.POST)
    public ResultBean payOrder(@RequestParam(value = "orderId") int orderId) {
        return memberService.payOrder(orderId);
    }

    @RequestMapping(value = "/member/order/display/{memberId}", method = RequestMethod.GET)
    public List<OrderBean> displayOrder (@PathVariable(value = "memberId") int memberId) {
        return memberService.displayOrder(memberId);
    }

    @RequestMapping(value = "/member/statics/{memberId}", method = RequestMethod.GET)
    public StatisticsBean displayMemberStatistics(@PathVariable(value = "memberId") int memberId) {
        return memberService.displayMemberStatistics(memberId);
    }

}
