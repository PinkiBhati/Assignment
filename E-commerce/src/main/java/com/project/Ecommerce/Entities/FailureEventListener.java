package com.project.Ecommerce.Entities;

import com.project.Ecommerce.Repos.UserAttemptsRepository;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class FailureEventListener {

    @Autowired
    UserAttemptsRepository userAttemptsRepository;

    @Autowired
    UserRepository userRepository;

    @EventListener
    public void AuthenticationFailEvent(AuthenticationFailureBadCredentialsEvent event)
    {
        String username = event.getAuthentication().getPrincipal().toString();
        Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();
        int count=0;
        for (UserAttempts userAttempts1 : userAttempts)
        {
            if (userAttempts1.getEmail().equals(username))
            {

                if (userAttempts1.getAttempts()>=2)
                {
                    User user = userRepository.findByUsername(username);
                    count++;
                    user.setAccountNonLocked(false);
                    userRepository.save(user);

                }
                else {
                    count++;
                    userAttempts1.setAttempts(userAttempts1.getAttempts() + 1);
                    userAttemptsRepository.save(userAttempts1);
                }
            }
        }
        if (count==0)
        {
            UserAttempts userAttempts1 = new UserAttempts();
            User user = userRepository.findByUsername(username);
            userAttempts1.setEmail(user.getUsername());
            userAttempts1.setAttempts(1);
            userRepository.save(user);
            userAttemptsRepository.save(userAttempts1);
        }
    }

    @EventListener
    public void AuthenticationPass(AuthenticationSuccessEvent event)
    {
        try {
            LinkedHashMap<String ,String > hashMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
            Iterable<UserAttempts> userAttempts = userAttemptsRepository.findAll();


            for (UserAttempts userAttempts1 : userAttempts)
            {
                if (userAttempts1.getEmail().equals(hashMap.get("username")))
                {
                    userAttempts1.setAttempts(0);
                    userAttemptsRepository.save(userAttempts1);
                }
            }
        }
        catch (Exception e)
        {

        }
    }



}
