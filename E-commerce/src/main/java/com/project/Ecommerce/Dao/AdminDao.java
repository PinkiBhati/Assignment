package com.project.Ecommerce.Dao;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {
    public void activateCustomerAndSeller(Long id);
    public void deActivateCustomerAndSeller(Long id);
    public String lockUser(Long id);
    public String unlockUser(Long id);
}
