package tickets.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tickets.service.MailService;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        try {
            mailSender.send(simpleMailMessage);
            logger.info("简单邮件已发送");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

    @Override
    public boolean sendRegisterMail(String email, String emailCode, String activateCode) {
        MimeMessage message = mailSender.createMimeMessage();

        String registerLink = "http://localhost:8080/tickets/register/confirm?email=" + emailCode + "&code=" +activateCode;
        Context context = new Context();
        context.setVariable("registerLink", registerLink);
        String emailContent = templateEngine.process("mail", context);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Tickets注册验证");
            helper.setText(emailContent, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
            return true;
        } catch (Exception e) {
            logger.error("发送html邮件时发生异常！", e);
        }
        return false;
    }
}
