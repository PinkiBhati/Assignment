package com.jpa2.Employee2.Entities;

import org.aspectj.weaver.ast.Test;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Tester extends Person {
    private int salary;

    public Tester(){}


    public Tester(int id, String name, int salary) {
        super(id, name);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Tester{" +
                "salary=" + salary +
                '}';
    }
}
