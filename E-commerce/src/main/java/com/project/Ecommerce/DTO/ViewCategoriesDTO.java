package com.project.Ecommerce.DTO;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;

@Component
public class ViewCategoriesDTO
{
    String name;
    List<String> fieldName;
    List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}