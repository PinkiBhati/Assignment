package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.UserAttempts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAttemptsRepository extends PagingAndSortingRepository<UserAttempts, Long> {

}
