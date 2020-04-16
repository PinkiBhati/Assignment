package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.UserDTO;
import com.project.Ecommerce.Dao.TokenDao;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.AlreadyExists;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.ExceptionHandling.TokenNotFoundException;
import com.project.Ecommerce.Repos.AddressRepository;
import com.project.Ecommerce.Repos.RoleRepository;
import com.project.Ecommerce.Repos.TokenRepository;
import com.project.Ecommerce.Repos.UserRepository;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    NotificationService notificationService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    private JavaMailSender javaMailSender;

    @Autowired
    public UserDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    TokenDao tokenDao;

    @Autowired
    GetCurrentDetails getCurrentDetails;

    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Set<Role> roleSet = user.getRoles();
        Iterator<Role> roleIterator = roleSet.iterator();
        List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
        while (roleIterator.hasNext()) {
            Role role3 = roleIterator.next();
            grantAuthorityList.add(new GrantAuthorityImpl(role3.getRoleName()));
        }
        System.out.println(user);

        if (username != null) {
            return new AppUser(user.getUsername(), user.getPassword(), grantAuthorityList, user.isEnabled(), user.isAccountNonLocked(), user.isAccountNonExpired());
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public String update(AddressDTO address, Long addressId) {
        String username = getCurrentDetails.getUser();
        User user = userRepository.findByUsername(username);
        Set<Address> addressSet= user.getAddresses();
        {
            for (Address address1: addressSet)
            {
                if(addressId.equals(address1.getId()))
                {
                    address1= modelMapper.map(address,Address.class);
                    if(address.getAddressLine()!=null)
                    {
                        address1.setAddressLine(address.getAddressLine());
                    }
                    if(address.getCity()!=null)
                    {
                        address1.setCity(address.getCity());
                    }
                    if(address.getState()!=null)
                    {
                        address1.setState(address.getState());
                    }

                    if(address.getZipCode()!=null)
                    {
                        address1.setZipCode(address.getZipCode());
                    }
                    if(address.getCountry()!=null)
                    {
                        address1.setCountry(address.getCountry());
                    }
                    user.setModifiedBy(username);
                    address1.setId(addressId);
                    address1.setUser(user);
                    addressRepository.save(address1);
                }
            }
        }
        return "Success";

    }


    @Override
    public String deleteUser() {
        String username = getCurrentDetails.getUser();
        User user = userRepository.findByUsername(username);
        user.setModifiedBy(username);
        userRepository.deleteUser(user.getId());
        return "Success";

    }


    @Override
    public String addNewAddress(Address address) {
        String username = getCurrentDetails.getUser();
        User user = userRepository.findByUsername(username);
        address.setLabel("HOME");
        address.setUser(user);
        user.addAddress(address);
        user.setModifiedBy(username);
        userRepository.save(user);
        return "success";
    }


    @Override
    public String editUsername(UserDTO user) {
        User user2 = userRepository.findByUsername(user.getUsername());
        if (user2 == null) {
            String username = getCurrentDetails.getUser();
            User user1 = userRepository.findByUsername(username);
            user1.setUsername(user.getUsername());
            user1.setModifiedBy(username);
            userRepository.save(user1);
            return "success";
        } else {
            throw new AlreadyExists("Username is not available");
        }

    }


    @Lazy
    public String editEmail(UserDTO user) {
        User user1 = modelMapper.map(user, User.class);
        String username = getCurrentDetails.getUser();
        user1.setUsername(username);
        System.out.println(user1.getUsername());
        notificationService.sendNotification(user1);
        return "success";
    }

    @Lazy
    public String verifyNewEmail(String token, UserDTO user) {
        Token token1 = null;
        for (Token token2 : tokenRepository.findAll()) {
            if (token2.getRandomToken().equals(token)) {
                token1 = token2;
            }
        }
        if (token1 == null) {
            throw new TokenNotFoundException("token is invalid");
        } else {
            if (token1.isExpired()) {
                User user1 = modelMapper.map(user, User.class);
                String username = getCurrentDetails.getUser();
                user1.setUsername(username);
                System.out.println(user1.getUsername());
                notificationService.sendNotification(user1);
                tokenRepository.delete(token1);
                throw new TokenNotFoundException("token is expired check mail for new token");
            } else {
                System.out.println("saving");
                User user2 = userRepository.findByUsername(token1.getName());
                user2.setUsername(user.getUsername());
                user2.setModifiedBy(user2.getUsername());
                userRepository.save(user2);
                tokenRepository.delete(token1);
                return "success";
            }
        }


    }


    @Override
    public String editPassword(UserDTO user) {
        String username = getCurrentDetails.getUser();
        User user1 = userRepository.findByUsername(username);
        if (user.getPassword() != null && user.getConfirmPassword() != null) {
            if (user.getPassword().equals(user.getConfirmPassword())) {
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
                user1.setModifiedBy(username);
                userRepository.save(user1);

                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getUsername());
                mail.setFrom("bhatipinki056@gmail.com");
                mail.setSubject("password changed status");
                mail.setText("your password has been successfully changed");
                javaMailSender.send(mail);

            } else {
                throw new PasswordAndConfirmPasswordMismatchException("password and confirm password does not match");
            }
        } else {
            throw new NullPointerException("password and confirm password both are mandatory");
        }
        return "success";

    }

}
