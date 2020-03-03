package com.example.springDemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary

public class AdminUser implements User {

    public void typeOfUser()
    {
        System.out.println("This is Admin User...");
    }

}
