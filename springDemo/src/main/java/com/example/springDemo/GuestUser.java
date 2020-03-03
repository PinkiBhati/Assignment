package com.example.springDemo;

import org.springframework.stereotype.Component;

@Component
public class GuestUser implements User {
    public void typeOfUser()
    {
        System.out.println("This is Guest User....");
    }
}
