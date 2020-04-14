package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Seller;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface SellerRepository extends PagingAndSortingRepository<Seller, Long> {

    Seller findByUsername(String username);

    @Query(value =  "select username,email,first_name,last_name," +
            "company_name,gst,company_contact" +
            " from user join seller on user.id=seller.id where username=:username",nativeQuery = true)
    List<Object[]> getDetails(@Param(value = "username") String username);

    @Modifying
    @Transactional
    @Query(value = "insert into seller values(?1,?2,?3,?4)",nativeQuery = true)
    void insertIntoSeller(String gst,String companyContact,String companyName,Long id);


    /*@Query(value = "select roleName from role join user_role on seller.id= role.")
    public String getRoleOfSeller(@Param("id") int id);*/


}
