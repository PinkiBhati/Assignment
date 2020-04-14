package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.User;
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

   @Query(value = "select id,username from user where id in(" +
           "select user_id from user_role where role_id in(" +
           "select id from role where role_name='ROLE_CUSTOMER'))",nativeQuery = true)
   List<Object[]> findCustomers();


   @Query(value = "select id,username from user where id in(" +
           "select user_id from user_role where role_id in(select" +
           " id from role where role_name='ROLE_SELLER'))",nativeQuery = true)
   List<Object[]> findSellers();

  /* @Query(value = "select username from user join user_role on user.id= user_role.user_id join role on " +
           "user_role.role_id= role.id where role_name= 'ROLE_ADMIN'",nativeQuery = true)*/
  @Query(value = "select id from user where id in(select user_id from user_role where role_id in(select id from role where role_name='ROLE_ADMIN'))",nativeQuery = true)
  Long findAdmin();


   @Transactional
   @Modifying
   @Query(value = "update User u set u.isDeleted = true, u.isAccountNonExpired = false , u.isEnabled= false where u.id =:id ")
   public void deleteUser(@Param("id") long id);



}
