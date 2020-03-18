package com.jpa.Author.Associations.OneToOne.Repos;

import com.jpa.Author.Associations.OneToOne.Entities.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Integer> {

}
