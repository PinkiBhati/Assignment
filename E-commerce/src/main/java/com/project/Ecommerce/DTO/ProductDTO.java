package com.project.Ecommerce.DTO;

import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
public class ProductDTO {

    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String brand;
    private String description;
    Boolean isCancellable;
    Boolean isReturnable;
    Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCancellable() {
        return isCancellable;
    }

    public Boolean getIsCancellable(){return isCancellable;}

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean isReturnable() {
        return isReturnable;
    }

    public Boolean getIsReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
