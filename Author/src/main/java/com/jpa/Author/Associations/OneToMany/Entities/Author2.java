package com.jpa.Author.Associations.OneToMany.Entities;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Que 7 with additional table

@Entity
public class Author2{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    public  List<Subject> subjects2;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Book2> book2;

    public Author2() {
    }

    public Author2(int id, String name, Address address, List<Subject> subjects2, Set<Book2> book2) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subjects2 = subjects2;
        this.book2 = book2;
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

    public List<Subject> getSubjects2() {
        return subjects2;
    }

    public void setSubjects2(List<Subject> subjects2) {
        this.subjects2 = subjects2;
    }

    public Set<Book2> getBook2() {
        return book2;
    }

    public void setBook2(Set<Book2> book2) {
        this.book2 = book2;
    }

    public void addSubjects(Subject subject) {
        if (subject != null) {
            if (subjects2 == null) {
                subjects2 = new ArrayList<>();
            }
            subjects2.add(subject);
        }
    }

    public void addBooks(Book2 books){
        if(books!= null)
        {
            if(book2 ==null)
            {
                book2 = new HashSet<>();
            }

            book2.add(books);
        }
    }
}

