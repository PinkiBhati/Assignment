package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.TokenDao;
import com.project.Ecommerce.Entities.NotificationService;
import com.project.Ecommerce.Entities.Token;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.ExceptionHandling.TokenNotFoundException;
import com.project.Ecommerce.Repos.TokenRepository;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Transactional
@Service
public class TokenDaoImpl implements TokenDao {
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    NotificationService notificationService;


    public ResponseEntity verifyToken(String tokenValue) {

        Token token1 = null;
        for (Token token : tokenRepository.findAll()) {
            if (token.getRandomToken().equals(tokenValue)) {
                token1 = token;
            }
        }

        if (token1 == null) {
            throw new TokenNotFoundException("Token is invalid");
        } else {
            if (token1.isExpired()) {

                notificationService.sendNotification(userRepository.findByUsername(token1.getName()));
                tokenRepository.delete(token1);
                return ResponseEntity.ok().body("your token has been expired");
            } else {
                System.out.println("saving");
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setEnabled(true);
                user2.setActive(true);
                System.out.println(user2.getUsername() + " " + user2.isEnabled());
                userRepository.save(user2);
                tokenRepository.delete(token1);
                return  ResponseEntity.ok().body("Your account has been verified");
            }
        }
    }


    public String getToken(User user) {
        Token token = new Token();
        String uu = UUID.randomUUID().toString();
        token.setRandomToken(uu);
        token.setTimeInMilli(System.currentTimeMillis());
        token.setName(user.getUsername());
        tokenRepository.save(token);
        return uu;
    }

    @Lazy
    @Scheduled(cron = "0 0/2 * * * *")
    public void purgeExpired() {
        for (Token token : tokenRepository.findAll()) {
            Long result = System.currentTimeMillis() - token.getTimeInMilli();
            if (result >= 60000) {
                System.out.println("hello");
                token.setExpired(true);
                tokenRepository.save(token);
            }

        }
    }


}
