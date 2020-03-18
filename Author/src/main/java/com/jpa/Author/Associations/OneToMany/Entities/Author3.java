package com.jpa.Author.Associations.OneToMany.Entities;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Que7 unidirectional

@Entity
public class Author3{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    public  List<Subject> subjects3;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Book3> book3;

    public Author3() {
    }

    public Author3(int id, String name, Address address, List<Subject> subjects3, Set<Book3> book3) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subjects3 = subjects3;
        this.book3 = book3;
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

    public List<Subject> getSubjects3() {
        return subjects3;
    }

    public void setSubjects3(List<Subject> subjects3) {
        this.subjects3 = subjects3;
    }

    public Set<Book3> getBook3() {
        return book3;
    }

    public void setBook3(Set<Book3> book3) {
        this.book3 = book3;
    }

    public void addSubjects(Subject subject) {
        if (subject != null) {
            if (subjects3 == null) {
                subjects3 = new ArrayList<>();
            }
            subjects3.add(subject);
        }
    }

    public void addBooks(Book3 books){
        if(books!= null)
        {
            if(book3 ==null)
            {
                book3 = new HashSet<>();
            }

            book3.add(books);
        }
    }

    @Override
    public String toString() {
        return "Author3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", subjects3=" + subjects3 +
                ", book3=" + book3 +
                '}';
    }
}

