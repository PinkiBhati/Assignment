package com.jpa2.Employee2.Repos;

import com.jpa2.Employee2.Entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
