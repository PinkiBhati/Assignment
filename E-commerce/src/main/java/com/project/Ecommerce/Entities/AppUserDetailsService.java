package com.project.Ecommerce.Entities;



import com.project.Ecommerce.DaoImpl.UserDaoImpl;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    private UserRepository userRepository;

    int count;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username);

        user.setCount(user.getCount()+1);
        userRepository.save(user);

        if (user.getCount()>3)
        {
            System.out.println("setting false");
            user.setAccountNonLocked(false);
            userRepository.save(user);
            notificationService.sendToCustomer(user);
        }

        System.out.println("Trying to authenticate user ::" + username);
        UserDetails userDetails = userDaoImpl.loadUserByUsername(username);
        return userDetails;
    }



}