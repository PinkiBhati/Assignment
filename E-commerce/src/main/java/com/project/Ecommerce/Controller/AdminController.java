package com.project.Ecommerce.Controller;


import com.project.Ecommerce.DTO.ListCustomerDTO;
import com.project.Ecommerce.DTO.ListSellerDTO;
import com.project.Ecommerce.Dao.AdminDao;
import com.project.Ecommerce.Entities.CategoryMetadataField;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.ExceptionHandling.UserNotFoundException;
import com.project.Ecommerce.Repos.CategoryRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AdminDao adminDao;

    @Autowired
    CategoryRepository categoryRepository;


    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can view all the registered Customers")
    @GetMapping("/viewAllCustomers")
    public ResponseEntity<List<ListCustomerDTO>> viewCustomer(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                              @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        List<ListCustomerDTO> list = adminDao.getAllCustomers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ListCustomerDTO>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can view all the registered Sellers")
    @GetMapping("/viewAllSellers")
    public ResponseEntity<List<ListSellerDTO>> getAllSellers(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                             @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        List<ListSellerDTO> list = adminDao.getAllSellers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ListSellerDTO>>(list, new HttpHeaders(), HttpStatus.OK);

    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can activate a particular user with given ID")
    @PatchMapping("/activateAccount/{userId}")
    public String activateUser(@PathVariable(name = "userId") Long userId) {
        adminDao.activateCustomerAndSeller(userId);
        return "successfully activated";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can deactivate a particular user with given ID")
    @PatchMapping("/deactivateAccount/{userId}")
    public String deactivateUser(@PathVariable(name = "userId") Long userId) {
        adminDao.deActivateCustomerAndSeller(userId);
        return "Successfully deactivated";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can lock an user's  account")
    @PutMapping("/lockAccount/{userId}")
    public String lockUser(@PathVariable(name = "userId") Long userId) {
        return adminDao.lockUser(userId);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "URI in which Admin can unlock an user's account")
    @PutMapping("/unLockAccount/{userId}")
    public String unlockUser(@PathVariable(name = "userId") Long userId) {
        return adminDao.unlockUser(userId);
    }


}
