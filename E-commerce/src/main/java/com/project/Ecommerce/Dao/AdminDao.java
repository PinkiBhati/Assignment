package com.project.Ecommerce.Dao;


import com.project.Ecommerce.DTO.ListCustomerDTO;
import com.project.Ecommerce.DTO.ListSellerDTO;
import com.project.Ecommerce.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {
    public void activateCustomerAndSeller(Long id);
    public void deActivateCustomerAndSeller(Long id);
    public List<ListCustomerDTO> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy);
    public List<ListSellerDTO> getAllSellers(Integer pageNo, Integer pageSize, String sortBy);
    public String lockUser(Long id);
    public String unlockUser(Long id);
}
