package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    TokenDao tokenDao;

    @PostMapping("/activateUser/{token}")
    public void verifyUser(@PathVariable(name = "token") String token) {

        tokenDao.verifyToken(token);

    }
}
