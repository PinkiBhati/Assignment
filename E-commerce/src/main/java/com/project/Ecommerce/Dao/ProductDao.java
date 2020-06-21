package com.project.Ecommerce.Dao;

import com.project.Ecommerce.DTO.ProductDTO;
import com.project.Ecommerce.DTO.ViewProductDTO;
import com.project.Ecommerce.DTO.ViewProductForCustomerDTO;
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
    public ViewProductForCustomerDTO viewSingleProductForAdmin(Long productId);
    public  ViewProductDTO viewSingleProduct(Long productId);
    public List<ViewProductForCustomerDTO> viewProducts(Long categoryId);
    public List<ViewProductForCustomerDTO> viewSimilarProducts(Long productId,Integer pageNo, Integer pageSize,String sortBy);

    /*public List<Object[]> getAllProductForCustomer(String categoryName, Integer pageNo, Integer pageSize, String sortBy);*/

    public List<ViewProductDTO> getProductDetails(Integer pageNo, Integer pageSize, String sortBy);
    public ViewProductForCustomerDTO viewProduct(Long id);
    public void deleteProduct( Long productId);
    public void updateProduct(ProductDTO product, Long productId);
    long getId(String productName);

    public  List<ViewProductForCustomerDTO> getAllProducts();
}
