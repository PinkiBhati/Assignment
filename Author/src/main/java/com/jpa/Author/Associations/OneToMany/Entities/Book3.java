package com.jpa.Author.Associations.OneToMany.Entities;

import javax.persistence.*;



@Entity
public class Book3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String bookName;

    public Book3() {
    }

    public Book3(int id, String bookName) {
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


    @Override
    public String toString() {
        return "Book3{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
