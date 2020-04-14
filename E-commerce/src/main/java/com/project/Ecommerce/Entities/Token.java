package com.project.Ecommerce.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Token {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String randomToken;
    Long timeInMilli;
    boolean isExpired;

    public boolean isExpired(){
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

    public void setTimeInMilli(Long timeInMilli) {
        this.timeInMilli = timeInMilli;
    }

    public Long getTimeInMilli() {
        return timeInMilli;
    }




}
