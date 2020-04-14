package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.UserDTO;
import com.project.Ecommerce.Entities.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public String update(Address address, long addressId);
    public String deleteUser();
    public String verifyNewEmail(String token, UserDTO user);
    public String addNewAddress( Address address);
    public String editUsername( UserDTO user);
    public String editEmail( UserDTO user);
    public String editPassword( UserDTO user);
}
