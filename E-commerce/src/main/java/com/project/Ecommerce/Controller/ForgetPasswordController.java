package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Entities.ForgetPassword;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ForgetPasswordController {

    @Autowired
    ForgetPassword forgotPassword;

    @ApiOperation("This URI is for User to get a token on his mail when he hits forget password")
    @PostMapping("/forgotPassword/{email}")
    public void setForgetPasswordHandler(@PathVariable(name = "email") String email) {
        forgotPassword.forgetPassword(email);
    }

    @ApiOperation("This URI is for User to set a new password which he puts in header along with the token")
    @PutMapping("/setPassword/{token}/{password}")
    public void setForgetPassword(@Valid @PathVariable(name = "token") String token, @PathVariable(name = "password") String password) {
        forgotPassword.setPassword(token, password);
    }
}
