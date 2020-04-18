package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.DTO.SellerProfileDTO;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Seller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface SellerDao {

    public String getAnCustomerAccount(Customer customer);
    public SellerProfileDTO viewProfileOfSeller();
    public void updateProfileForSeller(SellerProfileDTO sellerProfileDTO);

}
