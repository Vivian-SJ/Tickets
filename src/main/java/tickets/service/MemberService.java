package tickets.service;

import tickets.bean.MemberBean;

public interface MemberService {
    public MemberBean findMemberById(int memberId);

    /**
     * 检查用户合法性
     * @param email 用户邮箱
     * @param password 用户输入的密码
     * @return 用户标识和密码是否配对
     */
    public boolean checkUser (String email, String password);
}
