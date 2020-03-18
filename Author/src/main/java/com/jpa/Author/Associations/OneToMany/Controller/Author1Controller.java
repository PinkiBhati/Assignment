package com.jpa.Author.Associations.OneToMany.Controller;

import com.jpa.Author.Associations.ManyToMany.Dao.Author4Dao;
import com.jpa.Author.Associations.ManyToMany.Entities.Author4;
import com.jpa.Author.Associations.ManyToMany.Repos.Author4Repository;
import com.jpa.Author.Associations.OneToMany.Dao.Author1Dao;
import com.jpa.Author.Associations.OneToMany.Entities.Author1;
import com.jpa.Author.Associations.OneToMany.Repos.Author1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Author1Controller {

    @Autowired
    private Author1Repository author1Repository;

    @Autowired
    private Author1Dao author1Dao;


    @GetMapping("/savingAuthor1Details")
    public Author1 savingAuthor1Details(){

        Author1 author1= author1Dao.createAuthor1();

        author1Repository.save(author1);
        System.out.println(author1);

        return author1;
    }
}
