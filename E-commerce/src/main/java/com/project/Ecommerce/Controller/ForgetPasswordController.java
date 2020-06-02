package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.PasswordDTO;
import com.project.Ecommerce.Entities.ForgetPassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api
@RestController
public class ForgetPasswordController {

    @Autowired
    ForgetPassword forgotPassword;

    @ApiOperation("This URI is for User to get a token on his mail when he hits forget password")
    @PostMapping("/forgotPassword/{email}")
    public void setForgetPasswordHandler(@PathVariable(name = "email") String email) {
        forgotPassword.forgetPassword(email);
    }

    @ApiOperation("uri for setting new password on entering token")
    @PutMapping("/setPassword/{token}")
    public ResponseEntity setForgotPassword(@PathVariable(name = "token") String token, @Valid @RequestBody PasswordDTO password)
    {
        return forgotPassword.setPassword(token,password);
    }
}
