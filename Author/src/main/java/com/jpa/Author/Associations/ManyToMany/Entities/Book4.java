package com.jpa.Author.Associations.ManyToMany.Entities;


import javax.persistence.*;
import java.util.ArrayList;

import java.util.List;
//Que 8

@Entity
public class Book4{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String bookName;


    @ManyToMany(mappedBy = "book4")
    private List<Author4> author4;

    public Book4() {
    }

    public Book4(int id, String bookName) {
        this.id = id;
        this.bookName = bookName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }



    public void addAuthors(Author4 authors){
        if(author4!= null)
        {
            if(authors ==null)
            {
                author4 = new ArrayList<>();
            }

            author4.add(authors);
        }
    }


    @Override
    public String toString() {
        return "Book4{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author4=" + author4 +
                '}';
    }
}
