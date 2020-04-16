package com.project.Ecommerce.Repos;

import com.project.Ecommerce.DTO.ListCustomerDTO;
import com.project.Ecommerce.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

   public User findByUsername(String username);

   @Query(value = "select id,first_name,middle_name,last_name,username from user where id in(" +
           "select user_id from user_role where role_id in(" +
           "select id from role where role_name='ROLE_CUSTOMER'))",nativeQuery = true)
   Page<User> findCustomers(Pageable pageable);


   @Query(value = "select id from user where id in(" +
           "select user_id from user_role where role_id in(select" +
           " id from role where role_name='ROLE_SELLER'))",nativeQuery = true)
   Page<User> findSellers(Pageable pageable);

  @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role_name='ROLE_ADMIN'))",nativeQuery = true)
  Long findAdmin();
    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role_name='ROLE_CUSTOMER'))",nativeQuery = true)
    List<Long> findCustomerIds();

    @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role_name='ROLE_SELLER'))",nativeQuery = true)
    List<Long> findSellerIds(Pageable pageable);


   @Transactional
   @Modifying
   @Query(value = "update User u set u.isDeleted = true, u.isAccountNonExpired = false , u.isEnabled= false where u.id =:id ")
   public void deleteUser(@Param("id") long id);



}
