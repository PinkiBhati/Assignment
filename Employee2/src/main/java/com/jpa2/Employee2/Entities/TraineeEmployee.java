package com.jpa2.Employee2.Entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("trainee_emp")

//@PrimaryKeyJoinColumn(name = "id")
public class TraineeEmployee extends Employee {
    private int score;

public TraineeEmployee(){
    super();
}

    public TraineeEmployee(int score) {
        this.score = score;
    }

    public TraineeEmployee(int id, String firstName, String lastName, int salary, int age, int score) {
        super(id, firstName, lastName, salary, age);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
