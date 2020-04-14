package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>
{


    public Customer findByUsername( String username);

    @Query(value = "select username,first_name,last_name,contact from user join customer on user.id=customer.id where username=:username",nativeQuery = true)
    List<Object[]> getDetails(@Param(value = "username") String username);


    @Query("select firstName,middleName,lastName,isActive,contact from Customer where id=:id")
    List<Object[]> viewProfile(@Param(value = "id") Long id);


    @Modifying
    @Transactional
    @Query(value = "insert into customer values(?1,?2)",nativeQuery = true)
    void insertContact(String contactNo,long id);
}
