package com.project.Ecommerce.Entities;

import com.project.Ecommerce.Dao.TokenDao;
import com.project.Ecommerce.Repos.TokenRepository;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class ForgetPassword {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Lazy
    @Autowired
    NotificationService notificationService;

    @Autowired
    TokenDao tokenDao;

    private JavaMailSender javaMailSender;

    @Autowired
    public  ForgetPassword(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void forgetPassword(String emailId) {
        User user = userRepository.findByUsername(emailId);
        if (user == null) {
            System.out.println("no user found with this email id");
            throw new RuntimeException();
        }
        else {
            System.out.println("Sending email...");
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getUsername());
            mail.setFrom("bhatipinki056@gmail.com");
            mail.setSubject("Regarding forgot password");
            String uu = tokenDao.getToken(user);
            mail.setText(uu);
            javaMailSender.send(mail);
            System.out.println("Email Sent!");
        }
    }
    public void setPassword(String tokenOnMail, String password) {
        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(tokenOnMail)) {
                token1 = token;
            }
        }
        if (token1 == null) {
            System.out.println("invalid token");
        } else {
            if (token1.isExpired()) {
                notificationService.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
            } else {
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setPassword(new BCryptPasswordEncoder().encode(password));
                userRepository.save(user2);
                tokenRepository.delete(token1);
            }
        }
    }
}
