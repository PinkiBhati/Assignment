package com.Restful.RestDemo1.Controller;

import com.Restful.RestDemo1.DaoServices.UserDao;
import com.Restful.RestDemo1.ModelClasses.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "All users")
    @GetMapping("/Users")
    public List<User> getAllUsers()
    {
        return userDao.findAllUsers();
    }

    @ApiOperation(value = "Fetching particular user")
    @GetMapping("/Users/{rollNo}")
    public EntityModel<User> getparticularUser(@PathVariable int rollNo){
        User user= userDao.findOneUser(rollNo);

        EntityModel<User> resource = new EntityModel<User>(user);

        WebMvcLinkBuilder linkTo =
                WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @ApiOperation(value = "Creating user")
    @PostMapping("/Users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User user1= userDao.createUser(user);

        URI location= ServletUriComponentsBuilder
                      .fromCurrentRequest()
                      .path("/{rollNo}")
                      .buildAndExpand(user1.getRollNo())
                      .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Internationalization for printing hello with username..")
    @GetMapping("/internationalization/{rollNo}")
    public String Inter(@PathVariable int rollNo)
    {
        User user=userDao.findOneUser(rollNo);
        String[] params= {user.getName()};
        return messageSource.getMessage("hello.message",params, LocaleContextHolder.getLocale());
    }

    @ApiOperation(value = "Deleting user with given roll number")
    @DeleteMapping("/Users/{rollNo}")

    public User deleteEmp(@PathVariable int rollNo)
    {
        return userDao.deleteUser(rollNo);

    }

}
