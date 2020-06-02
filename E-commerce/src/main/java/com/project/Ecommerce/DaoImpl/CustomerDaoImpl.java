package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.ProfileDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.Enums.FromStatus;
import com.project.Ecommerce.Enums.ToStatus;
import com.project.Ecommerce.ExceptionHandling.*;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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
    ModelMapper modelMapper;


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserDao userDao;


    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;
    Long[] params={};


    @Override
    public String editContact(Customer customer) {
        if (customer.getContact().matches("(\\+91|0)[0-9]{10}")) {
            String user = getCurrentlyLoggedInUser.getCurrentUser();
            Customer user1 = customerRepository.findByUsername(user);
            user1.setContact(customer.getContact());
            user1.setModifiedBy(user);
            customerRepository.save(user1);
            return "Success";
        } else {
            throw new PatternMismatchException(messageSource.getMessage("message28",params, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public List<AddressDTO> getAddresses() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        Set<Address> addressSet= customer.getAddresses();
        List<AddressDTO> addressDTOList= new ArrayList<>();
        if (addressSet.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("message29",params, LocaleContextHolder.getLocale()));
        }
        for (Address address: addressSet)
        {   if(address.isDeleted()== false){
            AddressDTO addressDTO= modelMapper.map(address,AddressDTO.class);
            addressDTOList.add(addressDTO);}
        }

        return addressDTOList;

    }

    @Override
    public List<Object[]> getCustomerDetails() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Object[]> objects = customerRepository.getDetails(customer.getUsername());
        return objects;
    }


    @Override
    public Customer getCustomer() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }


    @Override
    public ProfileDTO viewProfile()
    {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
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
        String username = getCurrentlyLoggedInUser.getCurrentUser();
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
                throw new PatternMismatchException(messageSource.getMessage("message28",params, LocaleContextHolder.getLocale()));
            }
        }
        customer1.setEnabled(true);
        customerRepository.save(customer1);
        return "success";
    }


    @Override
    public ResponseEntity<Object> viewProfileImage(HttpServletRequest request) throws IOException
    {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        String filename = customer.getId().toString();
        System.out.println(filename);
        return uploadDao.downloadImage(filename, request);

    }

    @Override
    public ResponseEntity<Object> uploadFile(MultipartFile file) throws IOException
    {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        return uploadDao.uploadSingleImage(file, customer);
    }


    @Transactional
    @Override
    public String getAnSellerAccount(Seller seller) {

        if (seller.getCompanyName() != null && seller.getCompanyContact() != null && seller.getGst() != null) {
            String username = getCurrentlyLoggedInUser.getCurrentUser();
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
                    throw new PatternMismatchException(messageSource.getMessage("message30",params, LocaleContextHolder.getLocale()));
                }
            } else {
                throw new PatternMismatchException(messageSource.getMessage("message28",params, LocaleContextHolder.getLocale()));

            }
        } else {
            throw new NullException("All the fields are mandatory");
        }


    }


    @Override
    public String returnRequested(long orderStatusId) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();

        if (orderStatus.getToStatus() == ToStatus.CLOSED) {
            throw new NullException(messageSource.getMessage("message31",params, LocaleContextHolder.getLocale()));

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
            throw new NullException(messageSource.getMessage("message32",params, LocaleContextHolder.getLocale()));
        } else {
            orderStatus.setFromStatus(FromStatus.CANCELLED);
            orderStatusRepository.save(orderStatus);
        }
        return "success";
    }


}
