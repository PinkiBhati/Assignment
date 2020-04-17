package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.CategoryMetadataFieldValuesDao;
import com.project.Ecommerce.Entities.CategoryMetadataFieldValues;
import com.project.Ecommerce.Repos.CategoryMetadataFieldRepository;
import com.project.Ecommerce.Repos.CategoryMetadataFieldValuesRepository;
import com.project.Ecommerce.Repos.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController
public class CategoryMetadataFieldValuesController {

    @Autowired
    CategoryMetadataFieldValuesDao categoryMetadataFieldValuesDao;
    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to add a Metadata Field Value for a " +
            "Combination of Category and a Metadata Field")
    @PostMapping("/addMetadataValues/{categoryId}/{metadataId}")
    public void addMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                  @PathVariable(value = "categoryId") Long categoryId,
                                  @PathVariable(value = "metadataId") Long metadataId) {
        categoryMetadataFieldValuesDao.addMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);

    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to update Metadata Field Value for a " +
            "Combination of Category and a Metadata Field")
    @PutMapping("/updateMetadataValues/{categoryId}/{metadataId}")
    public void updateMetadataValues(@Valid @RequestBody CategoryMetadataFieldValues categoryMetadataFieldValues,
                                     @PathVariable(value = "categoryId") Long categoryId,
                                     @PathVariable(value = "metadataId") Long metadataId) {

        categoryMetadataFieldValuesDao.updateMetadataValues(categoryMetadataFieldValues, categoryId, metadataId);

    }



    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to view A single Metadata Field Values for a " +
            "Combination of Category and a Metadata Field")
    @GetMapping("/viewAMetadataValue/{categoryId}/{metadataId}")
    public List<Object[]> viewAMetadataValue(@PathVariable(value = "categoryId") Long categoryId,
                                             @PathVariable(value = "metadataId") Long metadataId) {
        return categoryMetadataFieldValuesDao.viewAMetadataValue(categoryId, metadataId);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to View All the Metadata Field Values")
    @GetMapping("/viewAllMetadataValues")
    public List<Object[]> viewAllMetadataValues() {
        return categoryMetadataFieldValuesRepository.getAllMetadataValues();
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for admin to Delete a particular entry from Metadata Field Values")
    @DeleteMapping("/deleteAMetadataValue/{categoryId}/{metadataId}")
    public void deleteAMetadataValue(@PathVariable(value = "categoryId") Long categoryId,
                                     @PathVariable(value = "metadataId") Long metadataId) {
        categoryMetadataFieldValuesDao.deleteAMetadataValue(categoryId, metadataId);
    }
}
