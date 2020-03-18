package com.jpa.Author.Associations.OneToOne.Dao;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;
import com.jpa.Author.Associations.OneToOne.Entities.Author;
import com.jpa.Author.Associations.OneToOne.Entities.Book;
import com.jpa.Author.Associations.OneToOne.Repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthorDao {

    public Author createAuthor(){

        Author author= new Author("Pinki");

        Address address= new Address(123,"sadar","delhi");

        Subject subject= new Subject("Maths");
        Subject subject1= new Subject("English");
        Subject subject2= new Subject("hindi");

        Book book= new Book("Learn Hibernate");


        author.addSubjects(subject);
        author.addSubjects(subject1);
        author.addSubjects(subject2);
        author.setAddress(address);
        author.setBook(book);

        return author;
    }

}
