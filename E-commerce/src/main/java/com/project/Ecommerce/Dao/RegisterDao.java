package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.CustomerDTO;
import com.project.Ecommerce.DTO.SellerDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Repository
public interface RegisterDao {
    public String registerCustomer(CustomerDTO customer);

    public String registerSeller(SellerDTO seller);

    public String logout(HttpServletRequest request);
    public String reSendActivationLink(String emailId);
}
