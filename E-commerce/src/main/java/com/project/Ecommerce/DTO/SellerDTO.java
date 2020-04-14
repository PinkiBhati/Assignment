package com.project.Ecommerce.DTO;

import com.project.Ecommerce.PasswordValidation.ValidPassword;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Component
public class SellerDTO {



        @Column(nullable=false,unique = true)
        @NotBlank(message = "Enter the UserName")
        @NotEmpty
        @Email(message="invalid_email")
        private String username;

        @Column(nullable=false)
        @NotEmpty(message = "Enter the First Name")
        private String firstName;

        @Column(nullable=true)
        private String middleName;

        @Column(nullable=false)
        @NotEmpty(message = "Enter the Last Name")
        private String lastName;

        @Column(nullable=false)
        @NotEmpty(message = "Password cant be null")
        @Size(min=8)
        @ValidPassword
        private String password;

        private String confirmPassword;


        @Column(unique = true)
        @Pattern(regexp = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}")
        private String Gst;
        @Column(unique = true)
        private String companyName;
    @Pattern(regexp = "(\\+91|0)[0-9]{10}")
        private String companyContact;

       public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }


    public String getGst() {
        return Gst;
    }

    public void setGst(String gst) {
        Gst = gst;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }
}

