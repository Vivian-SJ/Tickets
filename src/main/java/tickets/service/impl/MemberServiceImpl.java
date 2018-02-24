package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.MemberAccountBean;
import tickets.bean.ResultBean;
import tickets.bean.TicketBuyBean;
import tickets.model.Coupon;
import tickets.model.Member;
import tickets.model.Order;
import tickets.model.Show;
import tickets.repository.CouponRepository;
import tickets.repository.MemberRepository;
import tickets.repository.OrderRepositoryTest;
import tickets.repository.ShowRepository;
import tickets.service.MailService;
import tickets.service.MemberService;
import tickets.bean.MemberBean;
import tickets.util.CodeUtil;
import tickets.util.CouponStatus;
import tickets.util.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrderRepositoryTest orderRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MailService mailService;

    @Override
    public MemberBean findMemberById(int memberId) {
        MemberBean memberBean = new MemberBean(memberRepository.findById(memberId));
        return memberBean;
    }

    @Override
    public ResultBean register(MemberBean memberBean) {
        if (!memberBean.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return new ResultBean(false, "邮箱不合法");
        }
        if (memberRepository.findByEmail(memberBean.getEmail()) != null && memberRepository.findByEmail(memberBean.getEmail()).isActivate_state()) {
            return new ResultBean(false, "邮箱已被注册");
        }
        Member member = new Member(memberBean);
        String activateCode = UUID.randomUUID().toString();
        String passwordCode = "";
        String emailCode = "";
        try {
            passwordCode = CodeUtil.encrypt(memberBean.getPassword().getBytes());
            emailCode = CodeUtil.encrypt(memberBean.getEmail().getBytes());
        } catch (Exception e) {
            System.out.println("邮箱和密码加密时出错了！");
        }
        member.setActivate_code(activateCode);
        member.setActivate_state(false);
        member.setPassword(passwordCode);
        boolean result = mailService.sendRegisterMail(memberBean.getEmail(), emailCode, activateCode);
        if (result) {
            memberRepository.save(member);
            return new ResultBean(true, "已成功发送验证邮件，请尽快验证");
        }
        return new ResultBean(false, "注册失败，请稍后再试");
    }

    @Override
    public ResultBean registerConfirm(String emailCode, String code){
        try {
            String email = new String(CodeUtil.decrypt(emailCode));
            Member member = memberRepository.findByEmail(email);
            if (member!=null && member.getActivate_code().equals(code)) {
                member.setActivate_state(true);
                memberRepository.save(member);
                return new ResultBean(true, "验证成功！欢迎来到Tickets！");
            }
        } catch (Exception e) {
            return new ResultBean(false, "邮箱解码失败");
        }
        return new ResultBean(false, "验证失败");
    }

    @Override
    public ResultBean checkUser(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            return new ResultBean(false, "用户不存在，请检查邮箱是否正确");
        }
        if (!member.getPassword().equals(password)) {
            return new ResultBean(false, "密码与用户名不符");
        }
        return new ResultBean(true);
    }

    @Override
    public ResultBean cancelMember(int memberId) {
        Member member = memberRepository.findById(memberId);
        member.setValid(false);
        memberRepository.save(member);
        return new ResultBean(true);
    }

    @Override
    public ResultBean modifyInfo(int memberId, MemberBean memberBean) {
        Member member = memberRepository.findById(memberId);
        member.setName(memberBean.getName());
        member.setPassword(memberBean.getPassword());
        member.setImage(memberBean.getImage());
        member.setGender(memberBean.getGender());
        memberRepository.save(member);
        return new ResultBean(true);
    }

    @Override
    public MemberAccountBean getMemberAccountInfo(int memberId) {
        Member member = memberRepository.findById(memberId);
        List<Coupon> coupons = couponRepository.findBymember_id(memberId);
        return new MemberAccountBean(member.getRank(), member.getCredit(), coupons);
    }

    @Override
    public ResultBean exchangeCoupon(int memberId, double value) {
        Coupon coupon = new Coupon(memberId, value, CouponStatus.UNUSED.toString());
        couponRepository.save(coupon);
        Member member = memberRepository.findById(memberId);
        double newCredit = member.getCredit() - Coupon.toCredit(value);
        member.setCredit(newCredit);
        return new ResultBean(true);
    }

    @Override
    public ResultBean cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.CANCELED.toString());
        Member member = memberRepository.findById(order.getMember_id());

        //处理退款
        double price = order.getPrice();
        Timestamp time = order.getTime();
        Show show = showRepository.findById(order.getShow_id());
        Timestamp showTime = show.getTime();


        //把这个订单使用的优惠券恢复正常
        List<Coupon> coupons = couponRepository.findByMember_idAndOrder_id(member.getId(), orderId);
        for (Coupon coupon : coupons) {
            if (coupon.getStatus().equals(CouponStatus.SELECTED.toString())) {
                coupon.setStatus(CouponStatus.UNUSED.toString());
                coupon.setOrder_id(-1);
                couponRepository.save(coupon);
            }
        }
        return new ResultBean(true);
    }

    @Override
    public ResultBean payOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.WAIT_TICKET.toString());

        //关于用户余额、积分等的操作
        Member member = memberRepository.findById(order.getMember_id());
        double price = order.getPrice();
        double newMoneyAvailable = member.getMoney_available() - price;
        member.setMoney_available(newMoneyAvailable);
        double newSumConsumption = member.getSum_consumption() + price;
        member.setSum_consumption(newSumConsumption);
        double newCredit = member.getCredit()+price;
        member.setCredit(newCredit);
        int rank = (int)newSumConsumption/1000;
        member.setRank(rank);

        //删除已经使用的优惠券
        List<Coupon> coupons = couponRepository.findByMember_idAndOrder_id(member.getId(), orderId);
        for (Coupon coupon : coupons) {
            if (coupon.getStatus().equals(CouponStatus.SELECTED.toString())) {
                couponRepository.delete(coupon);
            }
        }
        return new ResultBean(true);
    }

    @Override
    public ResultBean buyTicket(TicketBuyBean ticketBuyBean) {
        Order order = new Order(ticketBuyBean);
        orderRepository.save(order);

        List<Integer> couponIds = ticketBuyBean.getCouponIds();
        if (couponIds.size()>0) {
            for (int id : couponIds) {
                Coupon coupon = couponRepository.findById(id);
                coupon.setStatus(CouponStatus.SELECTED.toString());
                coupon.setOrder_id(order.getId());
                couponRepository.save(coupon);
            }
        }
        return new ResultBean(true);
    }




}
