package com.project.Ecommerce.Controller;


import com.project.Ecommerce.DTO.FilterDTO;
import com.project.Ecommerce.DTO.ViewCategoriesDTO;
import com.project.Ecommerce.Dao.CategoryDao;
import com.project.Ecommerce.Entities.Category;
import com.project.Ecommerce.Entities.CategoryMetadataField;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import com.project.Ecommerce.Repos.CategoryMetadataFieldValuesRepository;
import com.project.Ecommerce.Repos.CategoryRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api
@RestController
public class CategoryController {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;


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

    //leaf node k products ni dikhenge
    @ApiOperation("This URI is for Customer to traverse the categories ")
    @GetMapping("/getCategory/{categoryId}")
    public List<Object[]> getSubCategory(@PathVariable(name = "categoryId")Long categoryId) {

        return categoryDao.getSubCategory(categoryId);
    }


    //correct
    @ApiOperation("This URI is for updating a Category Name")
    @PutMapping("/updateCategory/{categoryId}")
    public String updateCategory(@Valid @RequestBody Category category, @PathVariable(name = "categoryId") Long categoryId) {
        categoryDao.updateCategory(category, categoryId);
        return "Category successfully updated";
    }


    @ApiOperation("This URI is for Admin to add a category at root level ")
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

    @ApiOperation("This URI is for Admin to viewing a category ")
    @GetMapping("/viewSingleCategory/{categoryId}")
    public List<ViewCategoriesDTO> viewSingleCategory(@PathVariable("categoryId") Long categoryId)
    {
        return categoryDao.viewSingleCategory(categoryId);

    }

    @ApiOperation("This URI is for Admin to get all the categories")
    @GetMapping("/viewAllCategories")
    public ResponseEntity<List<ViewCategoriesDTO>> viewAllCategories(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                     @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                     @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        List<ViewCategoriesDTO> list = categoryDao.viewAllCategoriesForAdmin(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ViewCategoriesDTO>>(list, new HttpHeaders(), HttpStatus.OK);


    }

    //looking nice :-)
    @ApiOperation("This URI is for Seller to view all the categories")
    @GetMapping("/viewAllCategory")
    public List<ViewCategoriesDTO> viewAllCategory()
    {
        return categoryDao.viewAllCategoriesForSeller();
    }

    //looking nice :-)
    @ApiOperation("This URI is for Filtering the data for a categoryId")
    @GetMapping("/filtering/{categoryId}")
    public FilterDTO filtering(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryDao.getFilteringDetails(categoryId);
    }
}
