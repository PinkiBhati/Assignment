package com.example.springDemo;

public class TightlyCoupled {
    Course1 course1 = new Course1();
    Course2 course2 = new Course2();

    public void setCourse() {
        course1.show();
    }

    public static void main(String[] args) {

        TightlyCoupled tightlyCoupled = new TightlyCoupled();

        tightlyCoupled.setCourse();
    }
}
class Course1 {

    public void show() {
        System.out.println("This is course 1..");
    }
}


class Course2 {
    public void show() {
        System.out.println("This is Course 2..");
    }
}