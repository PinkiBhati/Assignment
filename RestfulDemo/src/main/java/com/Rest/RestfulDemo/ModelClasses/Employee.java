package com.Rest.RestfulDemo.ModelClasses;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Employee {


    @NotNull
    @Size(min = 3, max = 20)
    String name;

    int id;
    @Positive
    int age;

    protected Employee() {
        System.out.println("hello");

    }

    public Employee(int id, String name, int age) {

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return null;
    }

    public int getId() {
        return id;
    }

    public Employee setId(int id) {
        this.id = id;
        return null;
    }

    public int getAge() {
        return age;
    }

    public Employee setAge(int age) {
        this.age = age;

        return null;
    }
}
