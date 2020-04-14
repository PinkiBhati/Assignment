package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.RegisterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    RegisterDao registerDao;


    @Lazy
    @PostMapping("/customerRegistration")
    public String customerRegistration(@Valid @RequestBody CustomerDTO customer) {
        return registerDao.registerCustomer(customer);
    }

    @Lazy
    @PostMapping("/sellerRegistration")
    public String sellerRegistration(@Valid @RequestBody SellerDTO seller) {
        return registerDao.registerSeller(seller);
    }

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request) {
        return registerDao.logout(request);
    }
}
