package com.jpa.Employee.Repos;

import com.jpa.Employee.Entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//que 2
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {

    List<Employee> findByName(String name);
    List<Employee> findByNameStartingWith(String prefix);
    List<Employee> findByAgeBetween(int age1,int age2);
}
