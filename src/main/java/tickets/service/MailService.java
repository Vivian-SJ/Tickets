package tickets.service;

public interface MailService {
    public void sendSimpleMail(String to, String subject, String content);

    public boolean sendRegisterMail(String email, String emailCode, String activateCode);
}
