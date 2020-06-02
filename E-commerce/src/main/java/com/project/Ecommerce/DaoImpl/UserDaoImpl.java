package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.CurrentPasswordDTO;
import com.project.Ecommerce.DTO.PasswordDTO;
import com.project.Ecommerce.DTO.UserDTO;
import com.project.Ecommerce.Dao.TokenDao;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.Repos.AddressRepository;
import com.project.Ecommerce.Repos.RoleRepository;
import com.project.Ecommerce.Repos.TokenRepository;
import com.project.Ecommerce.Repos.UserRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private MessageSource messageSource;
    Long[] params={};

    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

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
    public void update(AddressDTO address, Long addressId) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        User user = userRepository.findByUsername(username);
        Set<Address> addresses = user.getAddresses();
        Optional<Address> address1 = addressRepository.findById(addressId);
        int count=0;
        if (address1.isPresent())
        {
            for (Address address2 : addresses) {
                if (address1.get().getId() == address2.getId()) {
                    if (address.getAddressLine() != null)
                        address2.setAddressLine(address.getAddressLine());
                    if (address.getCity() != null)
                        address2.setCity(address.getCity());
                    if (address.getCountry() != null)
                        address2.setCountry(address.getCountry());
                    if (address.getState() != null)
                        address2.setState(address.getState());
                    if (address.getZipCode() != null)
                        address2.setZipCode(address.getZipCode());
                    address2.setUser(user);
                    address2.setId(addressId);
                    address2.setModifiedBy(user.getUsername());
                    addressRepository.save(address2);
                    count++;
                }
            }
            if (count==0)
            {
                throw new NullException(messageSource.getMessage("message51",params , LocaleContextHolder.getLocale()));
            }
        }
        else
        {
            throw new NotFoundException(messageSource.getMessage("message52",params , LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public String deleteUser() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        User user = userRepository.findByUsername(username);
        user.setModifiedBy(username);
        userRepository.deleteUser(user.getId());
        return "Success";

    }


    @Override
    public String addNewAddress(Address address) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        User user = userRepository.findByUsername(username);
        address.setLabel("HOME");
        address.setUser(user);
        address.setCreatedBy(user.getUsername());
        user.addAddress(address);
        user.setModifiedBy(username);
        userRepository.save(user);
        return "success";
    }


   /* @Lazy
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
*/

    @Override
    public String editPassword(PasswordDTO passwordDTO) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        User user1 = userRepository.findByUsername(username);
            if (passwordDTO.getPassword() != null && passwordDTO.getConfirmPassword() != null) {
                if (passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
                    user1.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
                    user1.setModifiedBy(username);
                    userRepository.save(user1);
                    String subject="password changed status";
                    String text="your password has been successfully changed";
                    notificationService.sendMailToUser(user1,subject,text);

                } else {
                    throw new PasswordAndConfirmPasswordMismatchException(messageSource.getMessage("message45",params , LocaleContextHolder.getLocale()));
                }
            } else {
                throw new NullPointerException(messageSource.getMessage("message48",params , LocaleContextHolder.getLocale()));
            }

        return "success";

    }

}
