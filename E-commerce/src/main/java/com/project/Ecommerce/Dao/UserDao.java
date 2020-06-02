package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.CurrentPasswordDTO;
import com.project.Ecommerce.DTO.PasswordDTO;
import com.project.Ecommerce.DTO.UserDTO;
import com.project.Ecommerce.Entities.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public void update(AddressDTO address, Long addressId);
    public String deleteUser();
    public String addNewAddress( Address address);
    /*public String verifyNewEmail(String token, UserDTO user);
    public String editEmail( UserDTO user);*/
    public String editPassword(PasswordDTO passwordDTO);
}
