package com.project.Ecommerce.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CategoryMetadataFieldValues implements Serializable {

    @Column(nullable = false)
    String fieldValues;

    @EmbeddedId
    private CategoryMetadataFieldValuesId categoryMetadataFieldValuesId;


    @ManyToOne
    @JoinColumn(name = "category_metadata_id",insertable = false,updatable = false)
    private CategoryMetadataField categoryMetadataField;

    private CategoryMetadataField getCategoryMetadataField()
    {

        return categoryMetadataField;
    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    private Category category;
    private Category getCategory()
    {
        return category;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

    public CategoryMetadataFieldValuesId getCategoryMetadataFieldValuesId() {
        return categoryMetadataFieldValuesId;
    }

    public void setCategoryMetadataFieldValuesId(CategoryMetadataFieldValuesId categoryMetadataFieldValuesId) {
        this.categoryMetadataFieldValuesId = categoryMetadataFieldValuesId;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
