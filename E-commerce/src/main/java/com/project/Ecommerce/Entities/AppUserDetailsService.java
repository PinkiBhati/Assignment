package com.project.Ecommerce.Entities;



import com.project.Ecommerce.DaoImpl.UserDaoImpl;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username);
        System.out.println("Trying to authenticate user ::" + username);
        if(user!=null)
        {
            UserDetails userDetails = userDaoImpl.loadUserByUsername(username);
            return userDetails;
        }
        else {
            throw new NotFoundException("USER NOT FOUND");
        }

    }

}