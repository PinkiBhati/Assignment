package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.RegisterDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api
@RestController
public class RegisterController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    RegisterDao registerDao;


    @ApiOperation("This URI is for everyone to register as CUSTOMER")
    @Lazy
    @PostMapping("/customerRegistration")
    public String customerRegistration(@Valid @RequestBody CustomerDTO customer) {
        return registerDao.registerCustomer(customer);
    }

    @ApiOperation("This URI is for everyone to register as SELLER")
    @Lazy
    @PostMapping("/sellerRegistration")
    public String sellerRegistration(@Valid @RequestBody SellerDTO seller) {
        return registerDao.registerSeller(seller);
    }


    @ApiOperation("This URI is for Customer to get activation link again")
    @PostMapping("/reSendActivationLink/{emailId}")
    public String reSendActivationLink(@PathVariable("emailId") String emailId)
    { return registerDao.reSendActivationLink(emailId); }


    @Secured({"ROLE_CUSTOMER","ROLE_SELLER","ROLE_ADMIN"})
    @ApiOperation("This URI is for all the users to logout from the account")
    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request) {
        return registerDao.logout(request);
    }
}
