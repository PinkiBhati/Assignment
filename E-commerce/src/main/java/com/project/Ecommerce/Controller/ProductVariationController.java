package com.project.Ecommerce.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.Ecommerce.DTO.ProductVariationDTO;
import com.project.Ecommerce.Dao.ProductVariationDao;
import com.project.Ecommerce.Entities.ProductVariation;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.ProductVariationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Api
@RestController
public class ProductVariationController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductVariationDao productVariationDao;

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to get all product variation of a product")
    @GetMapping("/getAllProductVariations/{productId}")
    public List<ProductVariationDTO> getAllProductVariations(@PathVariable Long productId) throws IOException {
       return productVariationDao.getAllProductVariations(productId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to get a single product variation associated to a product which he owns")
    @GetMapping("/viewSingleProductVariation/{productVariationId}")
    public ProductVariationDTO getSingleProductVariation(@PathVariable Long productVariationId) throws IOException {
        return productVariationDao.getSingleProductVariation(productVariationId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to add a new product variation to a product he owns")
    @PostMapping("/addProductVariations/{productId}")
    public String addNewProductVariation(@Valid @RequestBody ProductVariation productVariation, @PathVariable Long productId) throws JsonProcessingException {

        productVariationDao.addNewProductVariation(productVariation, productId);
        return "successfully added";

    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to update a product variation associated to a product he owns")
    @PutMapping("/editProductVariations/{productVariationId}")
    public String updateProductVariation(@RequestBody ProductVariation productVariation, @PathVariable Long productVariationId) throws JsonProcessingException {

        productVariationDao.editProductVariation(productVariation,productVariationId);
        return "successfully updated";
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to delete a product Variation of a product which he owns")
    @DeleteMapping("/deleteProductVariation/{productVariationId}")
    public String deleteProductVariation(@PathVariable int productVariationId) {
        productVariationDao.removeProductVariation(productVariationId);
        return "successfully deleted";
    }


    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to upload the product variation image")
    @PostMapping("/uploadVariationPic/{id}")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        return productVariationDao.uploadFile(file,id);
    }

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SELLER"})
    @ApiOperation("This URI is for everyone to download the product Variation image")
    @GetMapping("/downloadProductVariationImage/{imageName}")
    public ResponseEntity<Object> downloadProductVariationImage(@PathVariable("imageName") String imageName, HttpServletRequest request) throws IOException {
        return productVariationDao.downloadProductVariationImage(imageName,request);
    }
}
