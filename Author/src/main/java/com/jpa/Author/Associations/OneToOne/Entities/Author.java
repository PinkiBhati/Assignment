package com.jpa.Author.Associations.OneToOne.Entities;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;


import javax.persistence.*;

import java.util.ArrayList;

import java.util.List;
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    public  List<Subject> subjects;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;

    public Author() {
    }

    public Author( String name) {

        this.name = name;
    }


    public Author(String name, Address address, List<Subject> subjects, Book book) {
        this.name = name;
        this.address = address;
        this.subjects = subjects;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }



    public void addSubjects(Subject subject) {
        if (subject != null) {
            if (subjects == null) {
                subjects = new ArrayList<>();
            }
            subjects.add(subject);
        }
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", subjects=" + subjects +
                ", book=" + book +
                '}';
    }
}
