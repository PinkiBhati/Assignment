package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.DTO.SellerProfileDTO;
import com.project.Ecommerce.Dao.SellerDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.PatternMismatchException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class SellerDaoImpl implements SellerDao {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;
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
    ModelMapper modelMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;
    Long[] params={};



    @Override
    public SellerProfileDTO viewProfileOfSeller()
    {
        String userName = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller= sellerRepository.findByUsername(userName);
        SellerProfileDTO sellerProfileDTO= new SellerProfileDTO(seller.getId(),seller.getFirstName()
                ,seller.getMiddleName(),seller.getLastName(),seller.isActive(),
                seller.getCompanyContact(),seller.getCompanyName(),seller.getGst());
        Set<Address> addressSet= seller.getAddresses();
        for (Address address: addressSet)
        {
            sellerProfileDTO.setAddressId(address.getId());
            sellerProfileDTO.setCity(address.getCity());
            sellerProfileDTO.setCountry(address.getCountry());
            sellerProfileDTO.setAddressLine(address.getAddressLine());
            sellerProfileDTO.setState(address.getState());
            sellerProfileDTO.setZipCode(address.getZipCode());
        }
        return sellerProfileDTO;
    }



    @Override
    public void updateProfileForSeller( SellerProfileDTO sellerProfileDTO)
    {
        String userName= getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller= sellerRepository.findByUsername(userName);

        if(sellerProfileDTO.getFirstName()!=null)
        {
            seller.setFirstName(sellerProfileDTO.getFirstName());
        }
        if(sellerProfileDTO.getMiddleName()!=null)
        {
            seller.setMiddleName(sellerProfileDTO.getMiddleName());
        }
        if(sellerProfileDTO.getLastName()!=null)
        {
            seller.setLastName(sellerProfileDTO.getLastName());
        }
        if(sellerProfileDTO.getActive()!=null)
        {
            seller.setActive(sellerProfileDTO.getActive());
        }
        if(sellerProfileDTO.getCompanyContact()!=null)
        {
            if(sellerProfileDTO.getCompanyContact().matches("(\\+91|0)[0-9]{10}"))
            {
                seller.setCompanyContact(sellerProfileDTO.getCompanyContact());
            }
            else {
                throw new NullException(messageSource.getMessage("message28",params , LocaleContextHolder.getLocale()));
            }

        }
        if(sellerProfileDTO.getCompanyName()!=null)
        {
            seller.setCompanyName(sellerProfileDTO.getCompanyName());
        }
        if (sellerProfileDTO.getGst()!=null)
        {
            if(sellerProfileDTO.getGst().matches("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}"))
            {
                seller.setGst(sellerProfileDTO.getGst());
            }
        }
        seller.setModifiedBy(seller.getUsername());
        sellerRepository.save(seller);
    }


    //extra
    @Transactional
    @Override
    public String getAnCustomerAccount(Customer customer) {
        if (customer.getContact() != null) {
            if (customer.getContact().matches("(\\+91|0)[0-9]{10}")) {
                String username = getCurrentlyLoggedInUser.getCurrentUser();
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
                throw new PatternMismatchException(messageSource.getMessage("message28",params , LocaleContextHolder.getLocale()));
            }
        } else {
            throw new NullException(messageSource.getMessage("message47",params , LocaleContextHolder.getLocale()));
        }
    }
}