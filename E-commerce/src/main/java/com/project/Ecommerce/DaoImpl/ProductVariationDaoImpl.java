package com.project.Ecommerce.DaoImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ecommerce.DTO.ProductVariationDTO;
import com.project.Ecommerce.Dao.ProductVariationDao;
import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Entities.Product;
import com.project.Ecommerce.Entities.ProductVariation;
import com.project.Ecommerce.Entities.Seller;
import com.project.Ecommerce.ExceptionHandling.NotFoundException;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ProductVariationDaoImpl implements ProductVariationDao {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UploadDao uploadDao;
    @Autowired
    GetCurrentDetails getCurrentDetails;
    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    CategoryMetadataFieldRepository categoryMetadataFieldRepository;


    @Override
    public void makeProductVariationNotAvailable(ProductVariation productVariation) {
        if (productVariation.getQuantityAvailable() == 0) {
            productVariation.setActive(false);
        }
    }

    @Override
    public void updateQuantity(long productVariationId, int quantity) {
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        ProductVariation productVariation = productVariationOptional.get();
        int quantity_Available = productVariation.getQuantityAvailable();
        int quantityAvailable = quantity_Available - quantity;
        if (quantityAvailable < 0) {
            throw new NullException("only " + quantity_Available + "is available ");
        }
        productVariation.setQuantityAvailable(quantityAvailable);
        productVariationRepository.save(productVariation);

    }




    @Override
    public void addNewProductVariation(ProductVariation productVariation, Long productId) throws JsonProcessingException {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (!productOptional.isPresent()) {
            throw new NotFoundException("Either the product name is wrong or the product is unavailable");
        } else {
            Product product1 = productOptional.get();
            if (product1.getIsActive() == true) {
                Map<String, String> stringMap = new HashMap<String, String>();
                String seller = getCurrentDetails.getUser();
                Seller seller1 = sellerRepository.findByUsername(seller);
                Map<String, Object> map = productVariation.getInfoAttributes();

                if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
                    productVariation.setProduct(product1);
                    Long categoryId = productRepository.getCategoryId(product1.getId());
                    List<Long> metadataIds = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);

                    for (long l : metadataIds) {

                        String metadata = categoryMetadataFieldRepository.getNameOfMetadata(l);
                        String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l);
                        stringMap.put(metadata, metadataValues);

                    }

                    int count = 0;

                    for (String key : map.keySet()) {
                        if (stringMap.containsKey(key)) {
                            String str = stringMap.get(key);
                            Object obj = map.get(key);
                            String[] strings = str.split(",");
                            List<String> list = Arrays.asList(strings);
                            if (list.contains(obj)) {
                                count++;
                            }

                        }
                    }

                    if (count == map.size()) {
                        String info = objectMapper.writeValueAsString(productVariation.getInfoAttributes());
                        productVariation.setMetadata(info);
                        productVariation.setActive(true);
                        productVariationRepository.save(productVariation);
                    } else {
                        throw new NotFoundException("Field values are not provided correctly");
                    }

                } else {
                    throw new NullException("you can't add any product variation to this product");
                }

            } else {
                throw new NullException("This product is not active so you cannot add product variation");
            }

        }

    }

    @Override
    public void removeProductVariation(long productVariationId) {
        String username = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        long id = productVariationRepository.getProductId(productVariationId);
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();
        if ((product.getSellers().getUsername()).equals(seller.getUsername())) {
            productVariationRepository.deleteById(productVariationId);
        } else {
            throw new NullException("You can't delete this product");
        }
    }

    @Override
    public void editProductVariation(ProductVariation productVariation, Long productVariationId) throws JsonProcessingException {
        String username = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        Long productId = productVariationRepository.getProductId(productVariationId);
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        if (productVariationOptional.isPresent() && productOptional.isPresent()) {
            Product product = productOptional.get();

            if (product.getIsActive() == true) {
                if ((product.getSellers().getUsername()).equals(seller.getUsername())) {
                    Map<String, String> stringMap = new HashMap<String, String>();
                    Map<String, Object> map = productVariation.getInfoAttributes();
                    Long categoryId = productRepository.getCategoryId(product.getId());
                    List<Long> metadataIds = categoryMetadataFieldValuesRepository.getMetadataId(categoryId);

                    for (long l : metadataIds) {

                        String metadata = categoryMetadataFieldRepository.getNameOfMetadata(l);
                        String metadataValues = categoryMetadataFieldValuesRepository.getFieldValuesForCompositeKey(categoryId, l);
                        stringMap.put(metadata, metadataValues);

                    }

                    int count = 0;

                    for (String key : map.keySet()) {
                        if (stringMap.containsKey(key)) {
                            String str = stringMap.get(key);
                            Object obj = map.get(key);
                            String[] strings = str.split(",");
                            List<String> list = Arrays.asList(strings);
                            if (list.contains(obj)) {
                                count++;
                            }

                        }
                    }

                    //check
                    if (count == map.size()) {
                        Optional<ProductVariation> productVariation1 = productVariationRepository.findById(productVariationId);
                        ProductVariation productVariation2 = productVariation1.get();
                        productVariation2.setActive(productVariation.getisActive());
                        productVariation2.setQuantityAvailable(productVariation.getQuantityAvailable());
                        productVariation2.setPrice(productVariation.getPrice());
                        String info = objectMapper.writeValueAsString(productVariation.getInfoAttributes());
                        productVariation2.setMetadata(info);
                        productVariationRepository.save(productVariation2);
                    } else {
                        throw new NotFoundException("Field values are not provided correctly");
                    }

                } else {
                    throw new NullException("You don't have this product to sell");
                }
            } else {
                throw new NullException("This product is not active");
            }
        } else {
            throw new NullException("This product variation ID is wrong");
        }

    }

    @Override
    public ProductVariationDTO getSingleProductVariation(Long productVariationId) throws IOException {
        String username = getCurrentDetails.getUser();
        Seller seller = sellerRepository.findByUsername(username);
        Long id = productVariationRepository.getProductId(productVariationId);
        ProductVariationDTO productVariationDTO= new ProductVariationDTO();
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<ProductVariation> productVariationOptional= productVariationRepository.findById(productVariationId);

        if (productOptional.isPresent()&&productVariationOptional.isPresent()) {


            Product product= productOptional.get();
            ProductVariation productVariation= productVariationOptional.get();
            if ((product.getSellers().getUsername()).equals(seller.getUsername()))
            {
                productVariationDTO.setName(product.getName());
                productVariationDTO.setBrand(product.getBrand());
                productVariationDTO.setDescription(product.getDescription());
                productVariationDTO.setCancellable(product.getIsCancellable());
                productVariationDTO.setActiveOfProduct(product.getIsActive());
                productVariationDTO.setActiveOfProductVariation(productVariation.getisActive());
                productVariationDTO.setPrice(productVariation.getPrice());
                productVariationDTO.setQuantityAvailable(productVariation.getQuantityAvailable());
                productVariationDTO.setReturnable(product.getIsReturnable());
                HashMap<String, Object> hashMap = objectMapper.readValue(productVariation.getMetadata(), HashMap.class);

                for (Map.Entry m : hashMap.entrySet()) {
                    fields.add(m.getKey().toString());
                    values.add(m.getValue().toString());
                }

                productVariationDTO.setFields(fields);
                productVariationDTO.setValues(values);


            }

            else {
                throw new NotFoundException("You cannot view this product variation");
            }

        }
        else
        {
            throw new NotFoundException("This product is deleted or product variation is not present");
        }

        return productVariationDTO;

    }

    @Override
    public ResponseEntity<Object> uploadFile(MultipartFile file, Long id) throws IOException {
        String seller = getCurrentDetails.getUser();
        Seller seller1 = sellerRepository.findByUsername(seller);

        Long idOfProduct = productVariationRepository.getProductId(id);
        Product product1 = productRepository.findById(idOfProduct).get();

        if ((product1.getSellers().getUsername()).equals(seller1.getUsername())) {
            Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
            if (productVariation.isPresent()) {
                return uploadDao.uploadSingleImageForProductVariation(file, productVariation.get());
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new NotFoundException("You cannot upload pic of this product variation");
        }

    }

    @Override
    public List<ProductVariationDTO> getAllProductVariations(Long productId) throws IOException {
        String seller = getCurrentDetails.getUser();
        Seller seller1 = sellerRepository.findByUsername(seller);

        List<ProductVariationDTO> productVariationDTOList = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if ((product.getSellers().getUsername()).equals(seller1.getUsername())) {
                ProductVariationDTO productVariationDTO = new ProductVariationDTO();
                for (ProductVariation productVariation : product.getProductVariations()) {

                    productVariationDTOList.add(getSingleProductVariation(productVariation.getId())) ;
                }
            } else {
                throw new NotFoundException("You can't see this product");
            }
        } else {
            throw new NotFoundException("This product is not present");
        }
        return productVariationDTOList;
    }


    @Override
    public ResponseEntity<Object> downloadProductVariationImage(String imageName, HttpServletRequest request) throws IOException {

       return uploadDao.downloadImageForVariation(imageName,request);
    }
}
