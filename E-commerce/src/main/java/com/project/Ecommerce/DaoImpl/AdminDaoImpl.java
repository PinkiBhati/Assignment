package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.DTO.ListCustomerDTO;
import com.project.Ecommerce.DTO.ListSellerDTO;
import com.project.Ecommerce.Dao.AdminDao;
import com.project.Ecommerce.Entities.Address;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.ExceptionHandling.UserNotFoundException;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminDaoImpl implements AdminDao {
    private JavaMailSender javaMailSender;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    public AdminDaoImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    UserRepository userRepository;

    @Async
    public void activateCustomerAndSeller(Long userId) throws MailException {
        User user1 = null;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isEnabled() == true) {
                System.out.println("user account is already activated");
            } else {
                user1.setEnabled(true);
                System.out.println("Sending email for account activation");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getUsername());
                mail.setFrom("bhatipinki056@gmail.com");
                mail.setSubject("Regarding account activation");
                mail.setText("your account has been activated by admin you can now login");
                System.out.println("now starting");
                javaMailSender.send(mail);
                userRepository.save(user1);
                System.out.println("Email Sent!");

            }
        } else {
            throw new UserNotFoundException("user with this id is not present");
        }

    }


    @Async
    public void deActivateCustomerAndSeller(Long userId) {

        User user1 = null;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isEnabled() == false) {
                System.out.println("user account is already deactivated");
            } else {
                user1.setEnabled(false);
                userRepository.save(user1);
                System.out.println("Sending email...");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getUsername());
                mail.setFrom("pinkibhati056@gmail.com");
                mail.setSubject("Regarding account deactivation");
                mail.setText("your account has been deactivated by admin you cant  login now");
                javaMailSender.send(mail);
                System.out.println("Email Sent!");
            }
        } else {
            throw new UserNotFoundException("user with this id is not present");
        }

    }


    @Async
    public String lockUser(Long userId) {
        User user1 = null;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isAccountNonLocked() == false) {
                return "user account is already locked";
            } else {
                user1.setAccountNonLocked(false);
                userRepository.save(user1);
                System.out.println("Sending email...");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getUsername());
                mail.setFrom("bhatipinki056@gmail.com");
                mail.setSubject("Regarding account status");
                mail.setText("your account has been locked by admin you can not login now");
                javaMailSender.send(mail);
                System.out.println("Email Sent!");
                return "account has been locked";
            }
        } else {
            System.out.println("user with this id is not present");
            throw new RuntimeException();
        }

    }

    @Async
    public String unlockUser(Long userId) {
        User user1 = null;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user1 = user.get();
            if (user1.isAccountNonLocked() == true) {
                return "user account is already unlocked";
            } else {
                user1.setAccountNonLocked(true);
                userRepository.save(user1);
                System.out.println("Sending email...");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(user1.getUsername());
                mail.setFrom("bhatipinki056@gmail.com");
                mail.setSubject("Regarding account status");
                mail.setText("your account has been unlocked by admin you can login now");
                javaMailSender.send(mail);
                System.out.println("Email Sent!");
                return "account has been locked";
            }
        } else {
            System.out.println("user with this id is not present");
            throw new RuntimeException();
        }

    }


    @Override
    public List<ListCustomerDTO> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        List<ListCustomerDTO> userList= new ArrayList<>();
        List<Long> longList=  userRepository.findCustomerIds();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));

            for(User user: userRepository.findAll(paging))
            {
                for (Long l : longList)
                {
                    if(user.getId()==l)
                    {
                        ListCustomerDTO listCustomerDTO=new ListCustomerDTO();
                        listCustomerDTO.setFirstName(user.getFirstName());
                        listCustomerDTO.setMiddleName(user.getMiddleName());
                        listCustomerDTO.setLastName(user.getLastName());
                        listCustomerDTO.setActive(user.getActive());
                        listCustomerDTO.setEmail(user.getUsername());
                        listCustomerDTO.setId(user.getId());
                        userList.add(listCustomerDTO);
                    }
                }


            }
               return userList;
    }


    @Override
    public List<ListSellerDTO> getAllSellers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        List<ListSellerDTO> userList= new ArrayList<>();
        List<Long> longList=  userRepository.findSellerIds(paging);


            System.out.println("1");
            for (Long l : longList)
            {
                User user= userRepository.findById(l).get();
                    ListSellerDTO listSellerDTO=new ListSellerDTO();
                    Set<Address> addressSet= user.getAddresses();
                    for (Address address: addressSet)
                    {
                        listSellerDTO.setCity(address.getCity());
                        listSellerDTO.setCountry(address.getCountry());
                        listSellerDTO.setAddressLine(address.getAddressLine());
                        listSellerDTO.setZipCode(address.getZipCode());
                        listSellerDTO.setLabel(address.getLabel());
                        listSellerDTO.setState(address.getState());

                    }

                    listSellerDTO.setFirstName(user.getFirstName());
                    listSellerDTO.setMiddleName(user.getMiddleName());
                    listSellerDTO.setLastName(user.getLastName());
                    listSellerDTO.setActive(user.getActive());
                    listSellerDTO.setEmail(user.getUsername());
                    listSellerDTO.setId(user.getId());
                    userList.add(listSellerDTO);
                }
              return userList;




    }




}

