package com.Restful.RestDemo1.Controller;


import com.Restful.RestDemo1.DaoServices.NewUserDao;
import com.Restful.RestDemo1.DaoServices.UserDao;
import com.Restful.RestDemo1.ModelClasses.NewUser;
import com.Restful.RestDemo1.ModelClasses.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserVersoningController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private NewUserDao newUserDao;

    @ApiOperation(value = "uri versioning control: version 1")
    @GetMapping("/v1/Users")
    public List<User> user1(){
        return userDao.findAllUsers();
    }

    @ApiOperation(value = "uri versioning control: version 2")
    @GetMapping("/v2/Users")
    public  List<NewUser> user2(){
        return newUserDao.findAllUsers();
    }


    @ApiOperation(value = "Request Parameter Versioning: version 1")
    @GetMapping(value = "/param/Users",params = "version=1")
    public List<User> paramUser1(){
        return userDao.findAllUsers();
    }


    @ApiOperation(value = "Request Parameter Versioning: version 2")
    @GetMapping(value = "/param/Users",params = "version=2")
    public List<NewUser> paramUser2(){
        return newUserDao.findAllUsers();
    }



    @ApiOperation(value = " header versioning: version 1")
    @GetMapping(value = "/header/Users",headers = "X-version=1")
    public List<User> headerUser1(){
        return userDao.findAllUsers();
    }

    @ApiOperation(value = "header versioning : version 2")
    @GetMapping(value = "/header/Users",headers = "X-version=2")
    public  List<NewUser> headerUser2(){
        return newUserDao.findAllUsers();
    }

    @ApiOperation(value = " MimeType versioning: version 1")
    @GetMapping(value = "/produces/Users",produces = "application/x-version1+json")
    public List<User> producerUser1(){
        return userDao.findAllUsers();
    }

    @ApiOperation(value = "MimeType versioning: version 2")
    @GetMapping(value = "/produces/Users",produces = "application/x-version2+json")
    public  List<NewUser> producerUser2(){
        return newUserDao.findAllUsers();
    }

}
