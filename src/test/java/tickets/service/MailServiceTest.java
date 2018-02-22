package tickets.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("1030518209@qq.com","测试邮件"," 你好");
    }

//    @Test
//    public void sendTemplateMail() {
//        //创建邮件正文
//        Context context = new Context();
//        context.setVariable("id", "1");
//        String emailContent = templateEngine.process("mail", context);
//        mailService.sendRegisterMail("1030518209@qq.com","主题：这是模板邮件",emailContent);
//    }
}
