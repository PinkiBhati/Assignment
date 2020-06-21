package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.AddressDTO;
import com.project.Ecommerce.DTO.ProfileDTO;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Seller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Repository
public interface CustomerDao {

    public String editContact(Customer customer);
    public List<AddressDTO> getAddresses();
    public Customer getCustomer();
    public ProfileDTO viewProfile(HttpServletRequest request) throws IOException;
    public String updateProfile(ProfileDTO customer);
    public String viewProfileImage(HttpServletRequest request) throws IOException;
    public String getAnSellerAccount(Seller seller);
    public ResponseEntity<Object> uploadFile(MultipartFile file) throws IOException;
    public String returnRequested(long orderStatusId);
    public String cancelOrder(long orderStatusId);
    public List<Object[]> getCustomerDetails();

}
