package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.ViewCategoriesDTO;
import com.project.Ecommerce.Entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CategoryDao {

    public List<Object[]> getSubcategory();
    public List<Object[]> getFilteringDetails(Long category_id);

    List<Object[]> getAllMainCategory();
    public ResponseEntity addMainCategory(Category category);
    public List<Object[]> viewSingleCategory( Long categoryId);
    public List<Object[]> viewAllCategories();
    public List<ViewCategoriesDTO> viewAllCategoriesForSeller();

    List<Object[]> getAllSubCategory(String mainCategory);

    void addNewSubCategory(Long parentCategoryId, Category category);
    public void updateCategory( Category category,Long categoryId);
}
