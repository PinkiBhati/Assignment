package com.jpa.Author.Associations.OneToMany.Controller;

import com.jpa.Author.Associations.OneToMany.Dao.Author2Dao;
import com.jpa.Author.Associations.OneToMany.Entities.Author2;
import com.jpa.Author.Associations.OneToMany.Repos.Author2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class author2Controller {


    @Autowired
    private Author2Repository author2Repository;

    @Autowired
    private Author2Dao author2Dao;

    @GetMapping("/savingAuthor2Details")
    public Author2 savingAuthor2Details(){
        Author2 author2= author2Dao.createAuthor2();
        author2Repository.save(author2);
        System.out.println(author2);
        return author2;
    }
}
