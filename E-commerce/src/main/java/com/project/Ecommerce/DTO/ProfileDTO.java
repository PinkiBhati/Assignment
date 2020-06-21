package com.project.Ecommerce.DTO;


import org.springframework.stereotype.Component;

@Component
public class ProfileDTO
{
    private String firstName;
    private String middleName;
    private String lastName;
    private String contactNo;
    private boolean isActive;
    private String fileName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public boolean getIsActive()
    {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}