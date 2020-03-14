package com.jpa2.Employee2.Entities;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Coder extends Person {
    private String projectName;

   public Coder(){

   }


    public Coder(int id, String name, String projectName) {
        super(id, name);
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Coder{" +
                "projectName='" + projectName + '\'' +
                '}';
    }
}
