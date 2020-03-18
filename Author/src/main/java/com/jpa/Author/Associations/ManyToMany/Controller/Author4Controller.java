package com.jpa.Author.Associations.ManyToMany.Controller;

import com.jpa.Author.Associations.ManyToMany.Dao.Author4Dao;
import com.jpa.Author.Associations.ManyToMany.Entities.Author4;
import com.jpa.Author.Associations.ManyToMany.Repos.Author4Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Author4Controller {

    @Autowired
    private Author4Repository author4Repository;

    @Autowired
    private Author4Dao author4Dao;

    @GetMapping("/savingAuthor4Details")
    public Author4 savingAuthor4Details(){

        Author4 author4= author4Dao.createAuthor4();

        author4Repository.save(author4);
        System.out.println(author4);

        return author4;
    }




}
