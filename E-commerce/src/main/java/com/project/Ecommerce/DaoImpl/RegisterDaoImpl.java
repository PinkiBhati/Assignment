package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Dao.RegisterDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.Repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RegisterDaoImpl implements RegisterDao {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    private JavaMailSender javaMailSender;


    @Autowired
    private TokenStore tokenStore;
    @Lazy
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepository userRepository;

    @Override
    public String registerCustomer(CustomerDTO customer) {
        if (customer.getPassword().equals(customer.getConfirmPassword())) {
            Customer customer1 = modelMapper.map(customer, Customer.class);
            String password = customer.getPassword();
            customer1.setPassword(passwordEncoder.encode(password));
            customer1.addRoles(new Role("ROLE_CUSTOMER"));
            customer1.setCreatedBy(customer1.getUsername());
            userRepository.save(customer1);
            if (userRepository.existsById(customer1.getId())) {
                notificationService.sendNotification(customer1);
            }
            return "success";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException("Password is not same as confirm password");
        }

    }

    @Async
    @Override
    public String registerSeller(SellerDTO seller) {
        if (seller.getPassword().equals(seller.getConfirmPassword())) {
            Seller seller1 = modelMapper.map(seller, Seller.class);
            String password = seller.getPassword();
            seller1.setPassword(passwordEncoder.encode(password));
            seller1.addRoles(new Role("ROLE_SELLER"));
            seller1.setCreatedBy(seller.getUsername());
            userRepository.save(seller1);

            if (userRepository.existsById(seller1.getId())) {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(seller.getUsername());
                mail.setFrom("bhatipinki056@gmail.com");
                mail.setSubject("Regarding account activation");
                mail.setText("you account has been created you can access it once admin verifies it");
                javaMailSender.send(mail);
            }
            return "success";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException("Both passwords are not same");
        }

    }

    @Override
    public String logout(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println("username is " + username);
        User user = userRepository.findByUsername(username);
        user.setCount(0);
        userRepository.save(user);
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }
}
