package com.project.Ecommerce.DTO;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Positive;
import java.util.List;

@Component
public class ProductVariationDTO {
    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String brand;
    private String description;
    Boolean isCancellable;
    Boolean isReturnable;
    Boolean isActiveOfProduct;
    Boolean isActiveOfProductVariation;

    private Integer quantityAvailable;
    private double price;
    List<String> fields;
    List<String> values;



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

    public Boolean getCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean getReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean getActiveOfProduct() {
        return isActiveOfProduct;
    }

    public void setActiveOfProduct(Boolean activeOfProduct) {
        isActiveOfProduct = activeOfProduct;
    }

    public Boolean getActiveOfProductVariation() {
        return isActiveOfProductVariation;
    }

    public void setActiveOfProductVariation(Boolean activeOfProductVariation) {
        isActiveOfProductVariation = activeOfProductVariation;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
