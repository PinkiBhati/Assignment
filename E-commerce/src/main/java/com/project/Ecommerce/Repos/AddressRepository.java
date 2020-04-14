package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long> {


    @Query(value = "select id,city,country,label,state,zip_code from address where user_id=:user_id",nativeQuery = true)
    public  List<Object[]> findAllByUser(@Param(value = "user_id")long user_id);

    @Modifying
    @Transactional
    @Query(value = "delete address from address where id=:id",nativeQuery = true)
    public void deleteAddress(@Param(value = "id") Long id);

}
