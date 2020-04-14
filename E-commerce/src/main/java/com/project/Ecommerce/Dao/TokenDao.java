package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao {

    public void verifyToken(String  u);
    public String  getToken(User user);
    /*public void purgeExpired();*/
}
