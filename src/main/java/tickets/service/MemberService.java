package tickets.service;

import tickets.bean.*;
import tickets.model.Coupon;
import tickets.model.Member;

import java.util.List;

public interface MemberService {
    public MemberBean findMemberBeanById(int memberId);

    public MemberBean findMemberBeanByEmail(String email);

    public Member findMemberById(int memberId);

    /**
     * 注册
     * @param memberBean 注册信息
     * @return 注册结果
     */
    public ResultBean register(MemberBean memberBean);

    /**
     * 注册验证
     * @param emailCode 加密后的邮箱账号
     * @param code 验证码
     * @return 验证结果
     */
    public ResultBean registerConfirm(String emailCode, String code);

    /**
     * 检查用户合法性
     * @param email 用户邮箱
     * @param password 用户输入的密码
     * @return 用户标识和密码是否配对
     */
    public ResultBean checkUser (String email, String password);

    /**
     * 取消会员资格
     * @param memberId 会员Id
     * @return 修改结果
     */
    public ResultBean cancelMember(int memberId);

    /**
     * 修改会员信息
     * @param memberId 会员Id
     * @param memberBean 会员信息
     * @return 修改结果
     */
    public ResultBean modifyInfo(int memberId, MemberBean memberBean);

    public ResultBeanWithId buyTicket(OrderBean orderBean);

    public MemberAccountBean getMemberAccountInfo(int memberId);

    public ResultBean exchangeCoupon(int memberId, double value, int amount);

    public List<Coupon> getCoupons(int memberId);

    public double getCredit(int memberId);

    public ShowsBean getAllShows();

    /**
     * 开票不成功的退款
     * @param orderId 退款订单号
     * @param refund 若refund为true，则是开票不成功退的，退全款
     * @return 退票结果
     */
    public ResultBean cancelOrder(int orderId, boolean refund);

    public ResultBean payOrder(int orderId);

    public List<OrderBean> displayOrder(int memberId);

    public OrderBean getOrder(int orderId);

    public StatisticsBeanForMemberAndStadium displayMemberStatistics(int memberId);

    public int getMemberId(String email);

    public int getMemberAmount();

    /**
     * 获得某用户预定过的不同类型的演出
     * @param memberId 用户Id
     * @return 不同类型即该类型的演出组成的Map
     */
    public ShowsBean getOrderShowsAndTypes(int memberId);

    public List<Integer> findAllIds();
}
