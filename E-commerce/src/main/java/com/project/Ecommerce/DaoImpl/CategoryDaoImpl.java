package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Entities.Category;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.Ecommerce.Entities.User;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    GetCurrentDetails getCurrentDetails;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;


    @Override
    public List<Object[]> getSubcategory() {
        List<Object[]> objects = categoryRepository.getSubcategory();
        return objects;
    }

    @Override
    public List<Object[]> getAllMainCategory() {

        List<Object[]> objects = categoryRepository.getMainCategory();
        if (objects.isEmpty()) {
            throw new NullException("No category Found");
        }
        return objects;
    }

    @Override
    public List<Object[]> getAllSubCategory(String mainCategory) {
        return categoryRepository.getSubCategory(mainCategory);
    }

    @Override
    public void addNewSubCategory(Long parentCategoryId,Category category)
    {
        int result = categoryRepository.checkIfLeaf(parentCategoryId);
        if (result==0)
        {
            Optional<Category> category1 = categoryRepository.findById(parentCategoryId);
            if (category1.isPresent()) {
                category.setCategory(category1.get());
                categoryRepository.save(category);
            }
            else
            {
                throw new NotFoundException("category with this id is not present");
            }
        }
        else
        {
            throw new NullPointerException("parent category you selected is already a leaf node");
        }
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {

        if (categoryRepository.findById(categoryId).isPresent()) {
            Category category1 = categoryRepository.findById(categoryId).get();
            category1.setName(category.getName());
            categoryRepository.save(category1);

        } else {
            throw new NotFoundException("This category ID is wrong as no entry is present for this ID");

        }
    }

    @Override
    public List<Object[]> getFilteringDetails(Long categoryId) {
        List<Long> listOfMetadataIdsForParticularCategory = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);

        List<Object[]> newListForCombiningMetadataFieldsWithValues = new ArrayList<Object[]>();
        for (Long idOfMetadata : listOfMetadataIdsForParticularCategory) {
            List<Object[]> listOfMetadataFields = categoryMetadataFieldRepository.getMetadataField(idOfMetadata);
            newListForCombiningMetadataFieldsWithValues.addAll(listOfMetadataFields);
        }
        List<Object[]> listOfMetadataValues = categoryMetadataFieldValuesRepository.getValues(categoryId);
        newListForCombiningMetadataFieldsWithValues.addAll(listOfMetadataValues);

        Category category = categoryRepository.findById(categoryId).get();
        categoryRepository.getSubCategory(category.getName());
        return newListForCombiningMetadataFieldsWithValues;


    }

    @Override
    public ResponseEntity addMainCategory(Category category)
    {
        categoryRepository.save(category);
        return ResponseEntity.ok().body("category added");
    }

}
