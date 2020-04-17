package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.ProfileDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Seller;
import com.project.Ecommerce.Repos.CustomerRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api
@RestController
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    UploadDao uploadDao;


    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

    @Autowired
    CustomerRepository customerRepository;

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to view his profile ")
    @GetMapping("/viewProfile")
    public ProfileDTO viewProfile()
    {
        return customerDao.viewProfile();
    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to View his all Addresses that he has provided")
    @GetMapping("/getAddresses")
    public List<AddressDTO> getAddresses() {
        return customerDao.getAddresses();
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to update his profile ")
    @PutMapping("/updateProfile")
    public String updateProfile(@RequestBody ProfileDTO customer)
    {
        return customerDao.updateProfile(customer);
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to Update his Contact")
    @PutMapping("/editContact")
    public String editContact(@RequestBody Customer customer) {
        return customerDao.editContact(customer);

    }



    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer so that he can get A account as a seller also")
    @PutMapping("/getSellerAccount")
    public String getAnSellerAccount(@RequestBody Seller seller) {
        System.out.println(seller.getFirstName());
        return customerDao.getAnSellerAccount(seller);

    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to get his details ")
    @GetMapping("/detailsOfCustomer")
    public List<Object[]> getDetails() {
        List<Object[]> objects = customerDao.getCustomerDetails();
        return objects;
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to request return for a order")
    @PostMapping("/returnRequested/{orderStatusId}")
    public String returnRequested(@PathVariable int orderStatusId) {
        return customerDao.returnRequested(orderStatusId);

    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to request for cancelling the order")
    @PostMapping("/cancelOrder/{orderStatusId}")
    public String cancelOrder(@PathVariable int orderStatusId) {

        return customerDao.cancelOrder(orderStatusId);

    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to upload his profile picture")
    @PostMapping("/uploadProfilePic")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return customerDao.uploadFile(file);
    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to view his profile image")
    @GetMapping("/viewProfileImage")
    public ResponseEntity<Object> viewProfileImage(HttpServletRequest request) throws IOException {
        return customerDao.viewProfileImage(request);
    }

}
