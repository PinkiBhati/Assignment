package com.jpa.Author.Associations.OneToMany.Dao;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;
import com.jpa.Author.Associations.OneToMany.Entities.Author2;
import com.jpa.Author.Associations.OneToMany.Entities.Author3;
import com.jpa.Author.Associations.OneToMany.Entities.Book2;
import com.jpa.Author.Associations.OneToMany.Entities.Book3;
import org.springframework.stereotype.Component;

@Component
public class Author3Dao {

    public Author3 createAuthor3() {

        Author3 author3 = new Author3();
        author3.setName("Shivam");


        Address address = new Address(145, "Greater Noida", "UP");
        author3.setAddress(address);

        Subject subject = new Subject("C");
        Subject subject1 = new Subject("Data Science");
        Subject subject2 = new Subject("AWT");

        author3.addSubjects(subject);
        author3.addSubjects(subject1);
        author3.addSubjects(subject2);

        Book3 book3 = new Book3();
        book3.setBookName("Learn data science");


        Book3 book31 = new Book3();
        book31.setBookName("Learn AWT");



        author3.addBooks(book3);
        author3.addBooks(book31);


        return author3;
    }
}
