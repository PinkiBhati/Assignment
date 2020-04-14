package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.UserDTO;
import com.project.Ecommerce.Dao.UserDao;
import com.project.Ecommerce.Entities.Address;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.Repos.AddressRepository;
import com.project.Ecommerce.Repos.CustomerRepository;
import com.project.Ecommerce.Repos.UserRepository;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
public class UserController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GetCurrentDetails getCurrentDetails;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserDao userDao;


    @PutMapping("/updateAddress/{addressId}")
    public void update(@Valid @RequestBody Address address, @PathVariable int addressId)
    {
      userDao.update(address,addressId);

    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(){
       return userDao.deleteUser();
    }


    @PostMapping("/addNewAddress")
    public String addNewAddress(@Valid @RequestBody Address address)
    {
       return userDao.addNewAddress(address);
    }

    @DeleteMapping("/deleteAddress/{AddressId}")
    public String deleteAddress(@PathVariable(value = "AddressId") long AddressId)
    {
        addressRepository.deleteAddress(AddressId);
        return "Address deleted successfully";
    }


    @Lazy
    @PutMapping("/editUsername")
    public String editUsername(@RequestBody UserDTO user)
    {
        return  userDao.editUsername(user);
    }

    @Lazy
    @PutMapping("/editEmail")
    public String editEmail(@RequestBody UserDTO user)
    {
        return userDao.editEmail(user);
    }


    @Lazy
    @PutMapping("/editEmail/{token}")
    public String SetNewEmail(@RequestBody UserDTO user, @PathVariable String token)
    {
        return userDao.verifyNewEmail(token,user);
    }
    @PutMapping("/editPassword")
    public String editPassword( @RequestBody UserDTO user) {
        return userDao.editPassword(user);
    }



}

