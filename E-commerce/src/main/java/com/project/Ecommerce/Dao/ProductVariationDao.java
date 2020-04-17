package com.project.Ecommerce.Dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.Ecommerce.DTO.ProductVariationDTO;
import com.project.Ecommerce.Entities.ProductVariation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Repository
public interface ProductVariationDao {

    public List<ProductVariationDTO> getAllProductVariations(Long productId) throws JsonProcessingException, IOException;
    public void makeProductVariationNotAvailable(ProductVariation productVariation);
    public ProductVariationDTO getSingleProductVariation( Long productVariationId) throws IOException;
    public void updateQuantity(long productVariationId, int quantity);
    public ResponseEntity<Object> downloadProductVariationImage(String imageName,HttpServletRequest request) throws IOException;
    public ResponseEntity<Object> uploadFile( MultipartFile file, Long id) throws IOException;
    public void editProductVariation(ProductVariation productVariation,Long productVariationId) throws JsonProcessingException;
    public void removeProductVariation( long productVariationId);
    public void addNewProductVariation(ProductVariation productVariation, Long productId) throws JsonProcessingException;
}
