package tickets.service;

import tickets.bean.MemberBean;
import tickets.bean.ResultBean;

public interface MemberService {
    public MemberBean findMemberById(int memberId);

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
}
