package tickets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tickets.bean.ResultBean;
import tickets.model.Member;
import tickets.repository.MemberRepository;
import tickets.service.MailService;
import tickets.service.MemberService;
import tickets.bean.MemberBean;
import tickets.util.CodeUtil;

import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;

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


}
