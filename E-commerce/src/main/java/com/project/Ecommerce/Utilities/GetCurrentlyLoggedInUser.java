package com.project.Ecommerce.Utilities;

import com.project.Ecommerce.Entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetCurrentlyLoggedInUser {
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User user = null;
        if (principal instanceof UserDetails)
        {
            username = ((UserDetails) principal).getUsername();
            System.out.println("username is " + username);
        } else {
            username = principal.toString();
            System.out.println("username is " + username);
        }
        return username;
    }
}
