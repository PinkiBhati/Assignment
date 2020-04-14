package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Seller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerDao {

    public String getAnCustomerAccount(Customer customer);
    public List<Object[]> getSellerDetails();
    public String editSellerDetails( Seller seller);

}
