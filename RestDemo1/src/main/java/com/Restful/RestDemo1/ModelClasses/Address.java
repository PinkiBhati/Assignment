package com.Restful.RestDemo1.ModelClasses;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "This is Address Class..")
public class Address {

    private String city;
    private String state;

    public Address() {
    }

    public Address(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
