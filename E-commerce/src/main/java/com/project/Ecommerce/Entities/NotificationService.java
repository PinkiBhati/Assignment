package com.project.Ecommerce.Entities;

import com.project.Ecommerce.Dao.TokenDao;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    private JavaMailSender javaMailSender;


    @Autowired
    TokenDao tokenDao;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;


    @Async
    public void sendNotification(User user) throws MailException {

        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getUsername());
        mail.setFrom("bhatipinki056@gmail.com");
        mail.setSubject("Here is your token to verify your account");
        mail.setText("To confirm your account, please click here : "
                +"http://localhost:3000/activate/"+tokenDao.getToken(user));
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }


    @Async
    public void sendToAdmin(User user,String text) throws MailException
    {

        System.out.println("hii");
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getUsername());
        mail.setFrom("bhatipinki056@gmail.com");
        mail.setSubject("Please activate the product");
        mail.setText(text);
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }

    @Async
    public  void sendMailToUser(User seller, String subject, String text)throws MailException
    {
        System.out.println("Sending email...");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(seller.getUsername());
        mail.setFrom("bhatipinki056@gmail.com");
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);
        System.out.println("Email Sent!");
    }


}
