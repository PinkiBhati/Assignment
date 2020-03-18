package com.Restful.RestDemo1.ModelClasses;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "This is a User Class")
public class User {
    private String name;

    private int rollNo;
  
     @JsonIgnore
    private String password;
    private int age;

    protected User() {

    }

    public User(String name, int rollNo, int age,String password) {
        this.name = name;
        this.rollNo = rollNo;
        this.age = age;
        this.password= password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
    
      public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
