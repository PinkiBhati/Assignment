package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Dao.TokenDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class TokenController {

    @Autowired
    TokenDao tokenDao;

    @ApiOperation("This URI is for everyone so that they can verify their account and can login")
    @PostMapping("/activateUser/{token}")
    public ResponseEntity verifyUser(@PathVariable(name = "token") String token) {

        return tokenDao.verifyToken(token);

    }
}
