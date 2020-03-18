package com.jpa.Author.Associations.OneToOne.Controller;

import com.jpa.Author.Associations.OneToOne.Dao.AuthorDao;
import com.jpa.Author.Associations.OneToOne.Entities.Author;
import com.jpa.Author.Associations.OneToOne.Repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorDao authorDao;

    @GetMapping("/savingDetails")
    public Author savingAuthorDetails()
    {


        Author author= authorDao.createAuthor();
        authorRepository.save(author);
        System.out.println(author);

        return author;
    }
}
