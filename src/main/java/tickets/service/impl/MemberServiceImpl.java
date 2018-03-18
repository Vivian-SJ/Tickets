package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tickets.bean.*;
import tickets.model.*;
import tickets.repository.*;
import tickets.service.MailService;
import tickets.service.MemberService;
import tickets.util.CodeUtil;
import tickets.util.CouponStatus;
import tickets.util.OrderStatus;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public MemberBean findMemberById(int memberId) {
        MemberBean memberBean = new MemberBean(memberRepository.findById(memberId));
        try {
            String password = new String(CodeUtil.decrypt(memberBean.getPassword()));
            memberBean.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            return new ResultBean(false, "注册失败，请稍后再试");
        }
        member.setActivate_code(activateCode);
        member.setActivate_state(false);
        member.setPassword(passwordCode);
        boolean result = mailService.sendRegisterMail(memberBean.getEmail(), emailCode, activateCode);
        if (result) {
            memberRepository.save(member);
            System.out.println("finish");
            return new ResultBean(true, "已成功发送验证邮件，请尽快验证");
        }
        return new ResultBean(false, "注册失败，请稍后再试");
    }

    @Override
    public ResultBean registerConfirm(String emailCode, String code) {
        try {
            String email = new String(CodeUtil.decrypt(emailCode));
            Member member = memberRepository.findByEmail(email);
            if (member != null && member.getActivate_code().equals(code)) {
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
        try {
            if (member == null) {
                return new ResultBean(false, "用户不存在，请检查邮箱是否正确");
            }
            if (!new String(CodeUtil.decrypt(member.getPassword())).equals(password)) {
                return new ResultBean(false, "密码与用户名不符");
            }
            return new ResultBean(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultBean(false, "登录失败");
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
        Member member = memberRepository.findById(memberId);
        double currentCredit = member.getCredit();
        if (currentCredit<Coupon.toCredit(value)) {
            return new ResultBean(false, "您的积分不足以兑换该种优惠券");
        }
        Coupon coupon = new Coupon(memberId, value, CouponStatus.UNUSED.toString());
        couponRepository.save(coupon);
        double newCredit = member.getCredit() - Coupon.toCredit(value);
        member.setCredit(newCredit);
        memberRepository.save(member);
        return new ResultBean(true);
    }

    @Override
    public List<Coupon> getCoupons(int memberId) {
        List<Coupon> coupons = new ArrayList<>();
        List<Coupon> allCoupons = couponRepository.findBymember_id(memberId);
        for (Coupon coupon : allCoupons) {
            if (CouponStatus.toEnumValue(coupon.getStatus()) == CouponStatus.UNUSED) {
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    @Override
    public double getCredit(int memberId) {
        return memberRepository.findById(memberId).getCredit();
    }

    @Override
    public ResultBean cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.CANCELED.toString());

        Member member = memberRepository.findById(order.getMember_id());

        //处理退款
        double price = order.getPrice();
        long cancelTimeLong = new Date().getTime();
        Show show = showRepository.findById(order.getShow_id());
        Timestamp showTime = show.getTime();
        long showTimeLong = showTime.getTime();
        //距离演出开始的天数
        int internalDays = (int) (showTimeLong - cancelTimeLong) / (1000 * 60 * 60 * 24);
        //退款
        double moneyAvailable = member.getMoney_available();
        if (internalDays >= 7) {
            member.setMoney_available(moneyAvailable + price);
            order.setPrice(0);
        } else if (internalDays >= 5) {
            member.setMoney_available(moneyAvailable + price * 0.5);
            order.setPrice(price * 0.5);
        } else if (internalDays >= 3) {
            member.setMoney_available(moneyAvailable + price * 0.3);
            order.setPrice(price * 0.7);
        }

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
        double newCredit = member.getCredit() + price;
        member.setCredit(newCredit);
        int rank = (int) newSumConsumption / 1000;
        member.setRank(rank);

        //删除已经使用的优惠券
        List<Coupon> coupons = couponRepository.findByMember_idAndOrder_id(member.getId(), orderId);
        for (Coupon coupon : coupons) {
            if (coupon.getStatus().equals(CouponStatus.SELECTED.toString())) {
                couponRepository.delete(coupon);
            }
        }

        //将这一笔交易记录下来，经理会和场馆结算
        Account account = new Account(order.getShow_id(), order.getStadium_id(), price);
        accountRepository.save(account);

        return new ResultBean(true);
    }

    @Override
    public List<OrderBean> displayOrder(int memberId) {
        List<Order> orders = orderRepository.findByMember_id(memberId);
        List<OrderBean> orderBeans = new ArrayList<>();
        for (Order order : orders) {
            OrderBean orderBean = new OrderBean(order);
            orderBeans.add(orderBean);
        }
        return orderBeans;
    }

    @Override
    public StatisticsBean displayMemberStatistics(int memberId) {
        Map<String, List<OrderBean>> map = new HashMap<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            List<Order> orders = orderRepository.findByMember_idAndType(memberId, orderStatus.toString());
            List<OrderBean> orderBeans = new ArrayList<>();
            for (Order order : orders) {
                OrderBean orderBean = new OrderBean(order);
                orderBeans.add(orderBean);
            }
            map.put(orderStatus.toString(), orderBeans);
        }
        double totalPrice = orderRepository.getMemberTotalPrice(memberId);
        return new StatisticsBean(map, totalPrice);
    }

    @Override
    public int getMemberId(String email) {
        return memberRepository.findIdByEmail(email);
    }

    @Override
    public ResultBean buyTicket(OrderBean orderBean) {
        Order order = new Order(orderBean);
        orderRepository.save(order);

        List<Integer> couponIds = orderBean.getCouponIds();
        if (couponIds.size() > 0) {
            for (int id : couponIds) {
                Coupon coupon = couponRepository.findById(id);
                coupon.setStatus(CouponStatus.SELECTED.toString());
                coupon.setOrder_id(order.getId());
                couponRepository.save(coupon);
            }
        }
        return new ResultBean(true);
    }


//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(s);
//        if (member == null) {
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        return member;
//    }
}
