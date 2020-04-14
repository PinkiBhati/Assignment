package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.CategoryMetadataField;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryMetadataFieldDao {
    public void deleteCategoryMetadataField(Long id);
    public List<CategoryMetadataField> viewCategoryMetadataField(Integer pageNo, Integer pageSize, String sortBy);
}
