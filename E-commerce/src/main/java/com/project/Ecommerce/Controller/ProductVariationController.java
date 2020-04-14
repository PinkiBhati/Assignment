package com.project.Ecommerce.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.Ecommerce.Dao.ProductVariationDao;
import com.project.Ecommerce.Entities.ProductVariation;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.ProductVariationRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
public class ProductVariationController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductVariationDao productVariationDao;

    //correct
    @ApiOperation("This URI is for seller to get all product variation of a product")
    @GetMapping("/getAllProductVariations/{productId}")
    public List<Object[]> getAllProductVariations(@PathVariable Long productId) {
       return productVariationDao.getAllProductVariations(productId);
    }

   //correct , perfect
    @ApiOperation("This URI is for seller to get a single product variation associated to a product which he owns")
    @GetMapping("/viewSingleProductVariation/{productVariationId}")
    public List<Object[]> getSingleProductVariation(@PathVariable Long productVariationId)
    {
        return productVariationDao.getSingleProductVariation(productVariationId);
    }

    //correctly done
    @ApiOperation("This URI is for seller to add a new product variation to a product he owns")
    @PostMapping("/addProductVariations/{productId}")
    public void addNewProductVariation(@Valid @RequestBody ProductVariation productVariation, @PathVariable Long productId) throws JsonProcessingException {

        productVariationDao.addNewProductVariation(productVariation, productId);

    }

    //jo jo bhejo vo vo edit ho
    @ApiOperation("This URI is for Seller to update a product variation associated to a product he owns")
    @PutMapping("/editProductVariations/{productVariationId}")
    public void updateProductVariation(@RequestBody ProductVariation productVariation, @PathVariable Long productVariationId) throws JsonProcessingException {

        productVariationDao.editProductVariation(productVariation,productVariationId);
    }

    //not required
    @DeleteMapping("/deleteProductVariation/{productVariationId}")
    public void deleteProductVariation(@PathVariable int productVariationId) {
        productVariationDao.removeProductVariation(productVariationId);
    }


    @PostMapping("/uploadVariationPic/{id}")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        return productVariationDao.uploadFile(file,id);
    }
}
