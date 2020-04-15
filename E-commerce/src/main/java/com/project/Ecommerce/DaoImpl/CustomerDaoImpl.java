package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.ProfileDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.Enums.FromStatus;
import com.project.Ecommerce.Enums.ToStatus;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.PasswordAndConfirmPasswordMismatchException;
import com.project.Ecommerce.ExceptionHandling.PatternMismatchException;
import com.project.Ecommerce.ExceptionHandling.UserNotFoundException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    UploadDao uploadDao;


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserDao userDao;


    @Autowired
    GetCurrentDetails getCurrentDetails;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String editContact(Customer customer) {
        if (customer.getContact().matches("(\\+91|0)[0-9]{10}")) {
            String user = getCurrentDetails.getUser();
            Customer user1 = customerRepository.findByUsername(user);
            user1.setContact(customer.getContact());
            user1.setModifiedBy(user);
            customerRepository.save(user1);
            return "Success";
        } else {
            throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
        }
    }

    @Override
    public List<Object[]> getAddresses() {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Object[]> list = addressRepository.findAllByUser(customer.getId());
        if (list.isEmpty()) {
            throw new UserNotFoundException("No address found for this user");
        }
        return list;

    }

    @Override
    public List<Object[]> getCustomerDetails() {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Object[]> objects = customerRepository.getDetails(customer.getUsername());
        return objects;
    }


    @Override
    public Customer getCustomer() {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }

    @Transactional
    @Override
    public String getAnSellerAccount(Seller seller) {

        if (seller.getCompanyName() != null && seller.getCompanyContact() != null && seller.getGst() != null) {
            String username = getCurrentDetails.getUser();
            User customer = userRepository.findByUsername(username);
            if (seller.getCompanyContact().matches("(\\+91|0)[0-9]{10}")) {
                if (seller.getGst().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")) {
                    seller.setId(customer.getId());
                    sellerRepository.insertIntoSeller(seller.getGst(), seller.getCompanyContact(), seller.getCompanyName(), seller.getId());
                    Set<Role> roles = customer.getRoles();
                    Role role = new Role();
                    role.setRoleName("ROLE_SELLER");
                    roles.add(role);
                    customer.setRoles(roles);
                    Set<User> users = new HashSet<>();
                    role.setUsers(users);
                    userRepository.save(customer);
                    return "success";
                } else {
                    throw new PatternMismatchException("gst number is not correct");
                }
            } else {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");

            }
        } else {
            throw new NullException("all the fields are mandatory");
        }


    }


    @Override
    public String returnRequested(long orderStatusId) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();

        if (orderStatus.getToStatus() == ToStatus.CLOSED) {
            throw new NullException("You Can't request for return ");

        } else if (orderStatus.getToStatus() == ToStatus.DELIVERED) {
            orderStatus.setFromStatus(FromStatus.RETURN_REQUESTED);
            orderStatusRepository.save(orderStatus);

        } else {
            throw new NullException("You can't request for return");
        }

        return "success";
    }

    @Override
    public String cancelOrder(long orderStatusId) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();

        if (orderStatus.getToStatus() == ToStatus.DELIVERED) {
            throw new NullException("You can't cancel the order.");
        } else {
            orderStatus.setFromStatus(FromStatus.CANCELLED);
            orderStatusRepository.save(orderStatus);
        }
        return "success";
    }


    @Override
    public ProfileDTO viewProfile()
    {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(customer.getFirstName());
        profileDTO.setLastName(customer.getLastName());
        profileDTO.setContactNo(customer.getContact());
        profileDTO.setMiddleName(customer.getMiddleName());
        return profileDTO;
    }

    @Override
    public String updateProfile(ProfileDTO customer)
    {
        String username = getCurrentDetails.getUser();
        Customer customer1 = customerRepository.findByUsername(username);
        if (customer.getFirstName()!=null)
            customer1.setFirstName(customer.getFirstName());
        if (customer.getMiddleName()!=null)
            customer1.setMiddleName(customer.getMiddleName());
        if (customer.getLastName()!=null)
            customer1.setLastName(customer.getLastName());
        if (customer.getContactNo()!=null)
        {
            if (customer.getContactNo().matches("(\\+91|0)[0-9]{10}"))
            {
                customer1.setContact(customer.getContactNo());
            }
            else
            {
                throw new PatternMismatchException("Contact number should start with +91 or 0 and length should be 10");
            }
        }
       /* if (customer.isActive()==false)
        {
            customer1.setActive(false);

        }*/
        customer1.setEnabled(true);
        customerRepository.save(customer1);
        return "success";
    }

    @Override
    public ResponseEntity<Object> viewProfileImage(HttpServletRequest request) throws IOException
    {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        String filename = customer.getId().toString();
        System.out.println(filename);
        return uploadDao.downloadImage(filename, request);

    }

    @Override
    public ResponseEntity<Object> uploadFile(MultipartFile file) throws IOException
    {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        return uploadDao.uploadSingleImage(file, customer);
    }
}
