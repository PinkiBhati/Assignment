package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.CategoryMetadataFieldDao;
import com.project.Ecommerce.Entities.CategoryMetadataField;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api
@RestController
public class CategoryMetadataFieldController {
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    CategoryMetadataFieldDao categoryMetadataFieldDao;

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to add a CategoryMetadata Field Name")
    @PostMapping("/addCategoryMetadataField")
    public String addCategoryMetadataField(@Valid @RequestBody CategoryMetadataField categoryMetadataField) {
        categoryMetadataFieldRepository.save(categoryMetadataField);
        return "CategoryMetadataField is successfully created";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to delete A categoryMetadata Field Name associated to the ID provided")
    @DeleteMapping("/deleteCategoryMetadataField/{id}")
    public String deleteCategoryMetadataField(@PathVariable(value = "id") Long id) {
        categoryMetadataFieldDao.deleteCategoryMetadataField(id);
        return "CategoryMetadataField is successfully deleted";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to View All the metadata Names with paging")
    @GetMapping("/viewCategoryMetadataField")
    public ResponseEntity<List<CategoryMetadataField>> viewCategoryMetadataField(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                                 @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        List<CategoryMetadataField> list = categoryMetadataFieldDao.viewCategoryMetadataField(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<CategoryMetadataField>>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
