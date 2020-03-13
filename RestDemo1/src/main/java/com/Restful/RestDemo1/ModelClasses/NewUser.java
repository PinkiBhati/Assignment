package com.Restful.RestDemo1.ModelClasses;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "This is new User class ..")
@JsonFilter("UserFilter")
public class NewUser {

    private String firstname;
    private String lastname;
    private int age;
    private Address address;



    public NewUser() {
    }

    public NewUser(String firstname, String lastname, int age,Address address ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
