package com.example.springDemo;

import org.springframework.stereotype.Component;

@Component
public class Courses {
    String name;
    int id;

    @Override
    public String toString() {
        return "Courses{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void showCourses()
    {
        System.out.println("BBA ---BCA---- MCA");
    }
    public void show(){
        System.out.println("Courses class....");
    }
}
