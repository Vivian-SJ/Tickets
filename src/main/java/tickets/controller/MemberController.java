package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tickets.bean.*;
import tickets.model.Coupon;
import tickets.service.MemberService;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value = "/member/login", method = RequestMethod.POST)
    public ResultBeanWithId login(@RequestParam(value = "email") String email,
                                  @RequestParam(value = "password") String password){
        System.out.println(email);
        System.out.println(password);
        return new ResultBeanWithId(memberService.checkUser(email, password), memberService.getMemberId(email));
    }

    @ResponseBody
    @RequestMapping(value = "/member/register", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean register(@RequestBody MemberBean memberBean) {
        return memberService.register(memberBean);
    }

    @RequestMapping(value = "/member/register/confirm", method = RequestMethod.GET)
    public String registerConfirm(@RequestParam(value = "email") String emailCode,
                                      @RequestParam(value = "code") String code) {
        System.out.println("here");
        ResultBean resultBean = memberService.registerConfirm(emailCode, code);
        if (resultBean.isResult()) {
            return "register-confirm-right";
        }
        return "register-confirm-wrong";
    }

//    @ResponseBody
//    @RequestMapping(value="/greeting",method = RequestMethod.GET)
//    public String Greeting() {
//        return "login";
//    }

    @ResponseBody
    @RequestMapping(value = "/member/info/{memberId}", method = RequestMethod.GET)
    public MemberBean findMemberById(@PathVariable(value = "memberId") int memberId) {
        return memberService.findMemberBeanById(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/info", method = RequestMethod.GET)
    public MemberBean findMemberByEmail(@RequestParam(value = "email") String email) {
        return memberService.findMemberBeanByEmail(email);
    }

    @ResponseBody
    @RequestMapping(value = "/member/cancel/{memberId}", method = RequestMethod.GET)
    public ResultBean cancelMember(@PathVariable(value = "memberId") int memberId) {
        return memberService.cancelMember(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/logout/{memberId}", method = RequestMethod.GET)
    public ResultBean logout(@PathVariable(value = "memberId") int memberId) {
        return new ResultBean(true);
    }

    @ResponseBody
    @RequestMapping(value = "/member/info/modify/{memberId}", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBean modifyInfo(@PathVariable(value = "memberId") int memberId, @RequestBody MemberBean memberBean) {
        return memberService.modifyInfo(memberId, memberBean);
    }

    @ResponseBody
    @RequestMapping(value = "/member/account/{memberId}", method = RequestMethod.GET)
    public MemberAccountBean getMemberAccountInfo(@PathVariable(value = "memberId") int memberId) {
        return memberService.getMemberAccountInfo(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/coupon/exchange", method = RequestMethod.POST)
    public ResultBean exchangeCoupon(@RequestParam(value = "memberId") int memberId,
                                     @RequestParam(value = "couponValue") double couponValue,
                                     @RequestParam(value = "amount") int amount) {
        return memberService.exchangeCoupon(memberId, couponValue, amount);
    }

    @ResponseBody
    @RequestMapping(value = "/member/credit/{memberId}", method = RequestMethod.GET)
    public double getCredit(@PathVariable(value = "memberId")int memberId) {
        return memberService.getCredit(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/coupons/{memberId}", method = RequestMethod.GET)
    public List<Coupon> getCoupons(@PathVariable(value = "memberId") int memberId) {
        return memberService.getCoupons(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/allshows", method = RequestMethod.GET)
    public ShowsBean getAllShows() {
        return memberService.getAllShows();
    }

    @ResponseBody
    @RequestMapping(value = "/member/ticket/buy", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResultBeanWithId buyTicket(@RequestBody OrderBean orderBean) {
        return memberService.buyTicket(orderBean);
    }

    @ResponseBody
    @RequestMapping(value = "/member/ticket/cancel/{orderId}", method = RequestMethod.GET)
    public ResultBean cancelOrder(@PathVariable(value = "orderId") int orderId) {
        return memberService.cancelOrder(orderId, false);
    }

    @ResponseBody
    @RequestMapping(value = "/member/ticket/pay", method = RequestMethod.POST)
    public ResultBean payOrder(@RequestParam(value = "orderId") int orderId) {
        return memberService.payOrder(orderId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/order/display/{memberId}", method = RequestMethod.GET)
    public List<OrderBean> displayOrder (@PathVariable(value = "memberId") int memberId) {
        return memberService.displayOrder(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/order/{orderId}", method = RequestMethod.GET)
    public OrderBean getOrder(@PathVariable(value = "orderId") int orderId) {
        return memberService.getOrder(orderId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/statistics/{memberId}", method = RequestMethod.GET)
    public StatisticsBeanForMemberAndStadium displayMemberStatistics(@PathVariable(value = "memberId") int memberId) {
        return memberService.displayMemberStatistics(memberId);
    }

    @ResponseBody
    @RequestMapping(value = "/member/typesAndShows/{memberId}", method = RequestMethod.GET)
    public ShowsBean getOrderShowsAndTypes(@PathVariable(value = "memberId") int memberId) {
        return memberService.getOrderShowsAndTypes(memberId);
    }
}
