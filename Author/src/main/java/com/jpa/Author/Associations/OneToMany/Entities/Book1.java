package com.jpa.Author.Associations.OneToMany.Entities;

import javax.persistence.*;

//Que 7 without additional table

@Entity
public class Book1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String bookName;

    @ManyToOne
    @JoinColumn(name = "author1_id")
    private Author1 author1;

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

    public Author1 getAuthor1() {
        return author1;
    }

    public void setAuthor1(Author1 author1) {
        this.author1 = author1;
    }

    @Override
    public String toString() {
        return "Book1{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author1=" + author1 +
                '}';
    }
}
