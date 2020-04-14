package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Dao.SellerDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Role;
import com.project.Ecommerce.Entities.Seller;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.ExceptionHandling.PatternMismatchException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SellerDaoImpl implements SellerDao {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GetCurrentDetails getCurrentDetails;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Object[]> getSellerDetails() {
        String username = getCurrentDetails.getUser();
        List<Object[]> objects = sellerRepository.getDetails(username);
        return objects;
    }

    @Transactional
    @Override
    public String getAnCustomerAccount(Customer customer) {
        if (customer.getContact() != null) {
            if (customer.getContact().matches("(\\+91|0)[0-9]{10}")) {
                String username = getCurrentDetails.getUser();
                User seller = userRepository.findByUsername(username);
                customerRepository.insertContact(customer.getContact(), seller.getId());
                Set<Role> roles = seller.getRoles();
                Role role = new Role();
                role.setRoleName("ROLE_CUSTOMER");
                roles.add(role);
                seller.setRoles(roles);
                Set<User> users = new HashSet<>();
                role.setUsers(users);
                userRepository.save(seller);
                return "success";
            } else {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        } else {
            throw new NullException("Contact number is mandatory");
        }

    }

    @Override
    public String editSellerDetails(Seller seller) {
        String user = getCurrentDetails.getUser();
        Seller user1 = sellerRepository.findByUsername(user);
        if (seller.getCompanyName() != null) {
            user1.setCompanyName(seller.getCompanyName());
        }
        if (seller.getCompanyContact() != null) {
            if (seller.getCompanyContact().matches("(\\+91|0)[0-9]{10}")) {
                user1.setCompanyContact(seller.getCompanyContact());
            } else {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
        if (seller.getGst() != null) {

            if (seller.getGst().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")) {
                user1.setGst(seller.getGst());
            } else {
                throw new PatternMismatchException("gst number is not correct");
            }
        }
        user1.setModifiedBy(user);
        sellerRepository.save(user1);
        return "success";
    }


}