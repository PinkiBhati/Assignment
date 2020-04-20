package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.PasswordDTO;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.Address;
import com.project.Ecommerce.Repos.AddressRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api
@RestController
public class UserController {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private UserDao userDao;

    @Secured({"ROLE_CUSTOMER","ROLE_SELLER","ROLE_ADMIN"})
    @ApiOperation("This URI is for all registered users to update his/her password")
    @PutMapping("/updatePassword")
    public String updatePassword(@Valid @RequestBody PasswordDTO password) {
        return userDao.editPassword(password);
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to add address")
    @PostMapping("/addNewAddress")
    public String addNewAddress(@Valid @RequestBody Address address) {
        return userDao.addNewAddress(address);
    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to delete his address")
    @DeleteMapping("/deleteAddress/{AddressId}")
    public String deleteAddress(@PathVariable(value = "AddressId") Long AddressId) {
        addressRepository.deleteAddress(AddressId);
        return "Address deleted successfully";
    }

    @Secured({"ROLE_CUSTOMER","ROLE_SELLER"})
    @ApiOperation("This URI is for Customer and Seller to update his address")
    @PutMapping("/updateAddress/{addressId}")
    public String updateAddress(@RequestBody AddressDTO address, @PathVariable(value = "addressId") Long addressId) {
         userDao.update(address, addressId);
         return "success";
    }

    @Secured({"ROLE_CUSTOMER","ROLE_SELLER"})
    @ApiOperation("This URI is for Customer and Seller to delete his account")
    @DeleteMapping("/deleteUser")
    public String deleteUser() {
        return userDao.deleteUser();
    }


    /*@Secured({"ROLE_CUSTOMER","ROLE_SELLER"})
    @ApiOperation("This URI is for Customer and Seller to update his E-mail ID")
    @Lazy
    @PutMapping("/editEmail")
    public String editEmail(@RequestBody UserDTO user) {
        return userDao.editEmail(user);
    }



    @Secured({"ROLE_CUSTOMER","ROLE_SELLER"})
    @ApiOperation("This URI is for Customer and Seller to verify his E-mail ID along with the token sent")
    @Lazy
    @PutMapping("/editEmail/{token}")
    public String SetNewEmail(@RequestBody UserDTO user, @PathVariable String token) {
        return userDao.verifyNewEmail(token, user);
    }*/



}

