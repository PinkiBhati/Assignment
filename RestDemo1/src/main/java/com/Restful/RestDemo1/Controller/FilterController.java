package com.Restful.RestDemo1.Controller;

import com.Restful.RestDemo1.DaoServices.NewUserDao;
import com.Restful.RestDemo1.DaoServices.UserDao;
import com.Restful.RestDemo1.ModelClasses.NewUser;
import com.Restful.RestDemo1.ModelClasses.User;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FilterController {
    @Autowired
    private NewUserDao newUserDao;

    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "This api applies Dynamic Filtering to User Class..")
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveUsers(){

        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept("firstname");
        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("UserFilter",filter);
        MappingJacksonValue mapping= new MappingJacksonValue(newUserDao.findAllUsers());
        mapping.setFilters(filterProvider);
        return mapping;

    }

    @ApiOperation(value = "This api applies Static Filtering to New User class..")
    @GetMapping("/filterStatic")
    public List<User> retrieveUserApplyingStaticFilter()
    {
        return userDao.findAllUsers();
    }


}
