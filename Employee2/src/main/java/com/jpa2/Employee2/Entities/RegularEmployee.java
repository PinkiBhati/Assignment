package com.jpa2.Employee2.Entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("regular_emp")
//@PrimaryKeyJoinColumn(name = "id")
public class RegularEmployee extends Employee{
    private String project;

    public RegularEmployee(){
        super();
    }

    public RegularEmployee(String project) {
        this.project = project;
    }

    public RegularEmployee(int id, String firstName, String lastName, int salary, int age, String project) {
        super(id, firstName, lastName, salary, age);
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
