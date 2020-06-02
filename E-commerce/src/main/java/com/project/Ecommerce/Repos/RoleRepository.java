package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Role;
import com.project.Ecommerce.Entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    public Role findByRoleName(String rolename);
}
