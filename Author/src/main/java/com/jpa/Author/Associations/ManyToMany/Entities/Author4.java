package com.jpa.Author.Associations.ManyToMany.Entities;

import com.jpa.Author.Associations.CommonToAll.Address;
import com.jpa.Author.Associations.CommonToAll.Subject;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//Que 8

@Entity
public class Author4{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    public  List<Subject> subjects4;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "author4_book4",joinColumns = @JoinColumn(name = "author4_id",referencedColumnName = "id")
                                ,inverseJoinColumns=@JoinColumn(name = "book4_id",referencedColumnName = "id"))
    private Set<Book4> book4;

    public Author4() {
    }


    public Author4(int id, String name, Address address, List<Subject> subjects4, Set<Book4> book4) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.subjects4 = subjects4;
        this.book4 = book4;
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

    public List<Subject> getSubjects4() {
        return subjects4;
    }

    public void setSubjects4(List<Subject> subjects4) {
        this.subjects4 = subjects4;
    }

    public Set<Book4> getBook4() {
        return book4;
    }

    public void setBook4(Set<Book4> book4) {
        this.book4 = book4;
    }


    public void addSubjects(Subject subject) {
        if (subject != null) {
            if (subjects4 == null) {
                subjects4 = new ArrayList<>();
            }
            subjects4.add(subject);
        }
    }

    public void addBooks(Book4 books){
        if(books!= null)
        {
            if(book4 ==null)
            {
                book4 = new HashSet<>();
            }

            book4.add(books);
        }
    }


    @Override
    public String toString() {
        return "Author4{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", subjects4=" + subjects4 +
                ", book4=" + book4 +
                '}';
    }
}
