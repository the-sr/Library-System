package com.example.demo.utils;

import com.example.demo.repository.EmailConfigRepo;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class EmailService {

    private final EmailConfigRepo emailConfigRepo;

    private String message="Sent";
    private boolean status;
    private String sender;
    private String password;
    private String smtpHost;
    private String smtpPort;
    private String emailSignature;

    @PostConstruct
    public void config(){
        Optional<EmailConfig> optionalEmailConfig= emailConfigRepo.findById(1L);
        if(optionalEmailConfig.isPresent()) set(optionalEmailConfig.get());
        else use();
    }

    private void set(EmailConfig obj){
        sender=obj.getSender();
        password=obj.getPassword();
        smtpHost=obj.getSmtpHost();
        smtpPort=obj.getSmtpPort();
        emailSignature=obj.getEmailSignature();

    }

    private void use(){
        sender="99484d5e69fb31";
        password="43812bc9c1dd51";
        smtpHost="sandbox.smtp.mailtrap.io";
        smtpPort="2525";
        emailSignature="Regards,<br>Team Demo";
    }

    public boolean sendMail(String receiver, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",smtpHost);
        props.put("mail.smtp.port",smtpPort);

        try{
            Session session=Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(sender,false));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            emailSignature=emailSignature.replace("\n","<br>");
            message.setContent(body+"<br><br><br>"+emailSignature,"text/html;charset=UTF-8");
            message.setSentDate(new Date());
            Transport.send(message);
            status=true;
        }catch (Exception e){
            message=e.getMessage();
            status=false;
        }
        return status;
    }
}
