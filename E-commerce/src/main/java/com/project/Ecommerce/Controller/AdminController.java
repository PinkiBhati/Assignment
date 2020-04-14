package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.AdminDao;
import com.project.Ecommerce.ExceptionHandling.UserNotFoundException;
import com.project.Ecommerce.Repos.CategoryRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


    @ApiOperation(value = "URI in which Admin can activate a particular user with given ID")
    @PutMapping("/activate/{userId}")
    public void activateUser(@PathVariable(name = "userId") Long userId) {
        adminDao.activateCustomerAndSeller(userId);
    }


    @ApiOperation(value = "URI in which Admin can deactivate a particular user with given ID")
    @PutMapping("/deactivate/{userId}")
    public void deactivateUser(@PathVariable(name = "userId") Long userId) {
        adminDao.deActivateCustomerAndSeller(userId);
    }

    @ApiOperation(value = "URI in which Admin can view all the registered Customers")
    @GetMapping("/allCustomers")
    public List<Object[]> getAllCustomers() {

        List<Object[]> objects = userRepository.findCustomers();
        if (objects.isEmpty()) {
            throw new UserNotFoundException("There are no active Customers right now.");
        }
        return objects;
    }

    @ApiOperation(value = "URI in which Admin can view all the registered Sellers")
    @GetMapping("/allSellers")
    public List<Object[]> getAllSellers() {
        List<Object[]> objects = userRepository.findSellers();
        if (objects.isEmpty()) {
            throw new UserNotFoundException("There are no active Sellers right now.");
        }
        return objects;
    }


    @ApiOperation(value = "URI in which Admin can lock an user's  account")
    @PutMapping("/lockAccount/{userId}")
    public String lockUser(@PathVariable(name = "userId") Long userId) {
        return adminDao.lockUser(userId);
    }

    @ApiOperation(value = "URI in which Admin can unlock an user's account")
    @PutMapping("/unLockAccount/{userId}")
    public String unlockUser(@PathVariable(name = "userId") Long userId) {
        return adminDao.unlockUser(userId);
    }


}
