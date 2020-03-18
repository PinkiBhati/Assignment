package com.jpa.Author.Associations.OneToMany.Controller;

import com.jpa.Author.Associations.OneToMany.Dao.Author3Dao;
import com.jpa.Author.Associations.OneToMany.Entities.Author2;

import com.jpa.Author.Associations.OneToMany.Entities.Author3;
import com.jpa.Author.Associations.OneToMany.Repos.Author3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Author3Controller {

    @Autowired
    private Author3Repository author3Repository;

    @Autowired
    private Author3Dao author3Dao;

    @GetMapping("/savingAuthor3Details")
    public Author3 savingAuthor3Details(){
        Author3 author3= author3Dao.createAuthor3();
        author3Repository.save(author3);
        System.out.println(author3);
        return author3;
    }
}
