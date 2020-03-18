package com.jpa.Author.Associations.OneToMany.Entities;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Que 7 without additional table

@Entity
public class Author1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    public  List<Subject> subjects1;

    @OneToMany(mappedBy = "author1",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Book1> book1;

    public Author1() {
    }

    public Author1(int id, String name, Address address, List<Subject> subjects1, Set<Book1> book1) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subjects1 = subjects1;
        this.book1 = book1;
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

    public List<Subject> getSubjects1() {
        return subjects1;
    }

    public void setSubjects1(List<Subject> subjects1) {
        this.subjects1 = subjects1;
    }

    public Set<Book1> getBook1() {
        return book1;
    }

    public void setBook1(Set<Book1> book1) {
        this.book1 = book1;
    }


    public void addBooks(Book1 books){
        if(books!= null)
        {
           if(book1 ==null)
           {
               book1 = new HashSet<>();
           }

           book1.add(books);
        }
    }

    public void addSubjects(Subject subject)
    {
        if (subject!= null)
        {
            if (subjects1 == null)
            {
                subjects1 =new ArrayList<>();
            }
            subjects1.add(subject);
        }
    }

    @Override
    public String toString() {
        return "Author1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", subjects1=" + subjects1 +
                ", book1=" + book1 +
                '}';
    }
}
