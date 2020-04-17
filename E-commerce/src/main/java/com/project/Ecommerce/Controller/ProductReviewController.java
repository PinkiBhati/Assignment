package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Dao.ProductReviewDao;
import com.project.Ecommerce.Entities.ProductReview;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProductReviewController {


    @Autowired
    ProductReviewDao productReviewDao;

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to add his review foe a product")
    @PostMapping("/addReview/{product_id}")
    public String addProduct(@RequestBody ProductReview productReview, @PathVariable(name = "product_id")Long product_id)
    {
        productReviewDao.addReview(productReview,product_id);
        return "review added";
    }

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN","ROLE_SELLER"})
    @ApiOperation("This URI is for all users to get the reviews of the product")
    @GetMapping("/getReviews/{product_id}")
    public List<Object[]> getReviews(@PathVariable(name = "product_id")Long product_id)
    {
        return productReviewDao.getReviews(product_id);
    }

}
