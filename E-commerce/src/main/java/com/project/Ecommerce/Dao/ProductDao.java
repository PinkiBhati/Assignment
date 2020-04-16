package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.Entities.Product;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProductDao {
    public void addProduct(Product product, Long categoryId);
    public void addNewProduct( ProductDTO product, Long categoryId);
    public void deactivate( Long productId);
    public void activateProduct( Long productId);
    public List<ViewProductDTO> viewSingleProductForAdmin(Long productId);
    public List<ViewProductDTO> viewSingleProduct(Long productId);
    public List<Object[]> viewProducts(Long categoryId);
    public List<Object[]> viewSimilarProducts(Long productId);
    public List<Object[]> getAllProducts();
    /*public List<Object[]> getAllProductForCustomer(String categoryName, Integer pageNo, Integer pageSize, String sortBy);*/

    public List<Object[]> getProductDetails();
    public List<Object[]> viewProduct(Long id);
    public void deleteProduct( Long productId);
    public void editProduct(ProductDTO product, Long productId);
    long getId(String productName);

}
