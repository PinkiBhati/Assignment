package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.DTO.ViewProductForCustomerDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.ProductDao;
import com.project.Ecommerce.Entities.Category;
import com.project.Ecommerce.Entities.CategoryMetadataField;
import com.project.Ecommerce.Entities.Product;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.Repos.CategoryRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api
@RestController
public class ProductController {

    @Autowired
    ProductDao productDao;

    @Autowired
    CustomerDao customerDao;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;


    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to add a new product to a category")
    @PostMapping("/addProduct/{categoryId}")
    public void addNewProduct(@Valid @RequestBody ProductDTO product, @PathVariable(name = "categoryId") Long categoryId) {
      productDao.addNewProduct(product,categoryId);

    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to view a single product he owns ")
    @GetMapping("/viewSingleProduct/{productId}")
    public ViewProductDTO viewSingleProduct(@PathVariable Long productId)
    {
        return productDao.viewSingleProduct(productId);
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI gets all the product of the particular seller")
    @GetMapping("/getProducts")
    public ResponseEntity<List<ViewProductDTO>> getProductDetails(@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        List<ViewProductDTO> list = productDao.getProductDetails(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ViewProductDTO>>(list, new HttpHeaders(), HttpStatus.OK);


    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to delete a particular product he owns")
    @DeleteMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productDao.deleteProduct(productId);
        return "Product and its variation deleted successfully";

    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to update a product he owns")
    @PutMapping("/updateProduct/{productId}")
    public String updateProduct( @RequestBody ProductDTO product, @PathVariable Long productId) {
       productDao.updateProduct(product,productId);
       return "product is successfully updated";

    }


    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
    @ApiOperation("This URI is for Customer to view a single Product and all its product variation")
    @GetMapping("/viewProduct/{productId}")
    public ViewProductForCustomerDTO viewProduct(@PathVariable Long productId)
    {
        return productDao.viewProduct(productId);
    }

    @Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
    @ApiOperation("This URI is for Customer to view all non-deleted active products along with there product variations  ")
    @GetMapping("/viewAllProductsForCustomer/{categoryId}")
    public ResponseEntity<List<ViewProductForCustomerDTO>> viewProductsForCustomer(@PathVariable Long categoryId)
    {
        List<ViewProductForCustomerDTO> list = productDao.viewProducts(categoryId);
        return new ResponseEntity<List<ViewProductForCustomerDTO>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to get all similar products based on the category and product's brand")
    @GetMapping("/viewSimilarProduct/{productId}")
    public ResponseEntity<List<ViewProductForCustomerDTO>> viewSimilarProducts(@PathVariable("productId") Long productId,@RequestParam(name = "pageNo", required = true, defaultValue = "0") Integer pageNo,
                                                                               @RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
                                                                               @RequestParam(name = "sortBy", defaultValue = "id") String sortBy)
    {
        List<ViewProductForCustomerDTO> list = productDao.viewSimilarProducts(productId,pageNo, pageSize, sortBy);
        return new ResponseEntity<List<ViewProductForCustomerDTO>>(list, new HttpHeaders(), HttpStatus.OK);

    }


    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to view a single product")
    @GetMapping("/viewSingleProductForAdmin/{productId}")
    public ViewProductForCustomerDTO viewSingleProductForAdmin(@PathVariable Long productId)
    {
        return productDao.viewSingleProductForAdmin(productId);
    }


    @Secured({"ROLE_ADMIN","ROLE_CUSTOMER"})
    @ApiOperation(value = "URI for Admin to get all products")
    @GetMapping("/getAllProductsForAdmin")
    public ResponseEntity<List<ViewProductForCustomerDTO>> getAllProducts() {
        List<ViewProductForCustomerDTO> list = productDao.getAllProducts();
        return new ResponseEntity<List<ViewProductForCustomerDTO>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to  deactivates a product and all its product variation")
    @PutMapping("/deactivateProduct/{productId}")
    public String deactivateProduct(@PathVariable Long productId) {

        productDao.deactivate(productId);
        return "Success";
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("This URI is for Admin to activates a product")
    @PutMapping("/activateProduct/{productId}")
    public String activateProduct(@PathVariable Long productId) {
        productDao.activateProduct(productId);
        return "Success";
    }
}
