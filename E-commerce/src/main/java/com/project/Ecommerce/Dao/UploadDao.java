package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.ProductVariation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UploadDao {

    public ResponseEntity<Object> uploadSingleImageForProductVariation(MultipartFile multipartFile, ProductVariation productVariation) throws IOException;

    public ResponseEntity<Object> uploadSingleImage(MultipartFile multipartFile, Customer customer) throws IOException;

    public ResponseEntity<Object> uploadMultipleFiles(MultipartFile[] multipartFiles) throws IOException;

    public ResponseEntity downloadImage(String filename, HttpServletRequest request) throws IOException;

    public ResponseEntity<Object> downloadImageForVariation(String imageName, HttpServletRequest request) throws IOException;
}
