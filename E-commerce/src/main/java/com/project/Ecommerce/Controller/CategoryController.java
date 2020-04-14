package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Dao.SellerDao;
import com.project.Ecommerce.Entities.Category;
import com.project.Ecommerce.Repos.CategoryRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @ApiOperation("This URI is for getting all the leaf categories")
    @GetMapping("/getSubcategories")
    public List<Object[]> getSubcategories() {
        List<Object[]> objects = categoryDao.getSubcategory();
        return objects;
    }

    @ApiOperation("This URI is for getting all the Main Categories")
    @GetMapping("/getCategory")
    public List<Object[]> getCategories() {

        return categoryDao.getAllMainCategory();
    }

    //check bcz still not up to the mark
    @ApiOperation("This URI is for Filtering the data for a categoryId")
    @GetMapping("/filtering/{categoryId}")
    public List<Object[]> filtering(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryDao.getFilteringDetails(categoryId);
    }


    //check it properly , findByName () is not working fine
    @ApiOperation("This URI is for traversing the whole category to the products")
    @GetMapping("/getCategory/{categoryName}")
    public List<Object[]> getSubCategory(@PathVariable(name = "categoryName") String categoryName) {
        List<Object[]> list = categoryDao.getAllSubCategory(categoryName);
        if (list.isEmpty()) {
            list = productRepository.getProductss(categoryName);
            if (list.isEmpty()) {
                list = productRepository.findByName(categoryName);

            }
        }
        return list;
    }

    //check



    @ApiOperation("This URI is for updating a Category Name")
    @PutMapping("/updateCategory/{categoryId}")
    public String updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "categoryId") Long categoryId) {
        categoryDao.updateCategory(category, categoryId);
        return "Category successfully updated";
    }


    @PostMapping("/addNewCategory")
    public ResponseEntity addMainCategory(@Valid @RequestBody Category category)
    {
        return categoryDao.addMainCategory(category);
    }

    @ApiOperation("This URI is for adding a new SubCategory")
    @PostMapping("/addNewCategory/{parentCategoryId}")
    public String addNewSubCategory(@PathVariable(name = "parentCategoryId") Long parentCategoryId, @Valid @RequestBody Category category) {
        categoryDao.addNewSubCategory(parentCategoryId, category);
        return "subcategory added successfully";
    }
}
