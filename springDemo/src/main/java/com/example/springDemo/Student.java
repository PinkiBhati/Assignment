package com.example.springDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {
    String name;
    int age;
    int score;

    public Student() {
        System.out.println("Student class Object created");
    }

    /*
    After using autowired it will search for the courses object created by spring because we have used @Component with Courses class,
    otherwise it will throw NullPointerException.
    */

    @Autowired
    private Courses courses;

//Constructor Injection Starts.....

    public Student(Courses courses)
    {
        this.courses=courses;
    }

// Constructor injection Ends...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public void displayCourses()
    {
        courses.showCourses();
    }
    public void  display()
    {

        System.out.println("Student class is running.....");
        System.out.println("Name of Student is : "+getName());
        System.out.println("Age of Student is :"+getAge());
        System.out.println("Score of Student is :"+getScore());


        courses.show();

    }
}
