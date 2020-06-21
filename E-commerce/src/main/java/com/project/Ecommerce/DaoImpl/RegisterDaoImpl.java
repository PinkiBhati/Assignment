package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Dao.RegisterDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.ExceptionHandling.UserNotFoundException;
import com.project.Ecommerce.Repos.AddressRepository;
import com.project.Ecommerce.Repos.TokenRepository;
import com.project.Ecommerce.Repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class RegisterDaoImpl implements RegisterDao {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    private JavaMailSender javaMailSender;

    @Autowired
    RegisterDaoImpl(JavaMailSender javaMailSender)
    {
        this.javaMailSender=javaMailSender;

    }

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private TokenStore tokenStore;
    @Lazy
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private MessageSource messageSource;
    Long[] params={};



    @Override
    public String registerCustomer(CustomerDTO customer) {
        if (customer.getPassword().equals(customer.getConfirmPassword())) {
            Customer customer1 = modelMapper.map(customer, Customer.class);
            String password = customer.getPassword();
            customer1.setPassword(passwordEncoder.encode(password));
            customer1.addRoles(new Role("ROLE_CUSTOMER"));
            customer1.setCreatedBy(customer1.getUsername());
            customer1.setActive(false);
            userRepository.save(customer1);
            if (userRepository.existsById(customer1.getId())) {
                notificationService.sendNotification(customer1);
            }
            return "successfully registered";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException(messageSource.getMessage("message45",params , LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public String registerSeller(SellerDTO seller) {
        if (seller.getPassword().equals(seller.getConfirmPassword())) {
            Seller seller1 = modelMapper.map(seller, Seller.class);
            Address address= new Address();
            address.setAddressLine(seller.getAddressLine());
            address.setZipCode(seller.getZipCode());
            address.setCity(seller.getCity());
            address.setState(seller.getState());
            address.setCountry(seller.getCountry());
            address.setLabel("OFFICE");
            address.setUser(seller1);
            Set<Address> addressSet= new HashSet<>();
            addressSet.add(address);
            seller1.setAddresses(addressSet);
            String password = seller.getPassword();
            seller1.setPassword(passwordEncoder.encode(password));
            seller1.addRoles(new Role("ROLE_SELLER"));
            seller1.setCreatedBy(seller.getUsername());
            seller1.setActive(false);
            userRepository.save(seller1);

            if (userRepository.existsById(seller1.getId())) {

                String subject="Regarding account activation";
                String text="you account has been created you can access it once admin verifies it";
                notificationService.sendMailToUser(seller1,subject,text);

            }
            return "success";
        } else {
            throw new PasswordAndConfirmPasswordMismatchException(messageSource.getMessage("message45",params , LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @Override
    public String reSendActivationLink(String emailId)
    {
        User user= userRepository.findByUsername(emailId);
        if(user==null)
        {
            throw new UserNotFoundException(messageSource.getMessage("message46",params , LocaleContextHolder.getLocale()));
        }

        else {
            for (Token token: tokenRepository.findAll())
            {
                if(user.getUsername().equals(token.getName())&& user.getActive()==false&&user.getEnabled()==false)
                {
                    tokenRepository.deleteById(token.getId());

                }
            }

            notificationService.sendNotification(user);
        }
        return "successfully sent";
    }
}
