package com.jpa.Author.Associations.OneToMany.Entities;

import com.jpa.Author.Associations.OneToMany.Entities.Author2;

import javax.persistence.*;

//Que 7 with additional table

@Entity
public class Book2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String bookName;


    @ManyToOne
    @JoinColumn(name = "author2_id")
    private Author2 author2;


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

    public Author2 getAuthor2() {
        return author2;
    }

    public void setAuthor2(Author2 author2) {
        this.author2 = author2;
    }

    @Override
    public String toString() {
        return "Book2{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author2=" + author2 +
                '}';
    }
}
