package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.Dao.CustomerDao;
import com.project.Ecommerce.Dao.ProductDao;
import com.project.Ecommerce.Entities.Category;
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

   //finally done

    @ApiOperation("This URI is for Seller to add a new product to a category")
    @PostMapping("/addProduct/{categoryId}")
    public void addNewProduct(@Valid @RequestBody ProductDTO product, @PathVariable(name = "categoryId") Long categoryId) {
      productDao.addNewProduct(product,categoryId);

    }


    //correct
    @ApiOperation("This URI is for seller to delete a particular product he owns")
    @DeleteMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productDao.deleteProduct(productId);
        return "Product and its variation deleted successfully";

    }

    //check for if condition
    @ApiOperation("This URI is for seller to update a product he owns")
    @PostMapping("/editProduct/{productId}")
    public void editProduct( @RequestBody ProductDTO product, @PathVariable Long productId) {
       productDao.editProduct(product,productId);

    }


    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for seller to view a single product he owns ")
    @GetMapping("/viewSingleProduct/{productId}")
    public List<ViewProductDTO> viewSingleProduct(@PathVariable Long productId)
    {
        return productDao.viewSingleProduct(productId);
    }

    @ApiOperation("This URI is for Admin to view a single product")
    @GetMapping("/viewSingleProductForAdmin/{productId}")
    public List<ViewProductDTO> viewSingleProductForAdmin(@PathVariable Long productId)
    {
        return productDao.viewSingleProductForAdmin(productId);
    }


    //correct
    @ApiOperation("This URI gets all the product of the particular seller")
    @GetMapping("/getProducts")
    public List<Object[]> getProductDetails()
    {
        List<Object[]> objectsList = productDao.getProductDetails();
        return objectsList;
    }

    //correct
    @ApiOperation("This URI is for Admin to  deactivates a product and all its product variation")
    @PutMapping("/deactivateProduct/{productId}")
    public String deactivateProduct(@PathVariable Long productId) {

        productDao.deactivate(productId);
        return "Success";
    }

    //correct
    @ApiOperation("This URI is for Admin to activates a product")
    @PutMapping("/activateProduct/{productId}")
    public String activateProduct(@PathVariable Long productId) {
        productDao.activateProduct(productId);
        return "Success";
    }


    //paging is left
    @ApiOperation("This URI is for Customer to get All products associated with a category ")
    @GetMapping("/getAllProductForCustomerAndAdmin/{categoryName}")
    public  List<Object[]> getAllProductForCustomer(@PathVariable("categoryName") String categoryName,@RequestParam(name = "pageNo",defaultValue ="0") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    @RequestParam(name = "sortBy",defaultValue = "id") String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        return productRepository.getAllProductsForCustomer(categoryName);
    }

    @ApiOperation("This URI is for Customer to view a single Product and all its product variation")
    @GetMapping("/viewProduct/{productId}")
    public List<Object[]> viewProduct(@PathVariable Long productId)
    {
        return productDao.viewProduct(productId);
    }


    @ApiOperation("This URI is for Customer to view all non-deleted active products along with there product variations  ")
    @GetMapping("/viewAllProductsForCustomer/{categoryId}")
    public List<Object[]> viewProductsForCustomer(@PathVariable Long categoryId)
    {
        return productDao.viewProducts(categoryId);

    }

    @GetMapping("/viewSimilarProduct/{productId}")
    public List<Object[]> viewSimilarProducts(@PathVariable("productId") Long productId)
    {
       return productDao.viewSimilarProducts(productId);

    }

    @ApiOperation(value = "URI for Admin to get all products")
    @GetMapping("/getAllProducts")
    public List<Object[]> getAllProducts() {
        return productDao.getAllProducts();
    }


}
