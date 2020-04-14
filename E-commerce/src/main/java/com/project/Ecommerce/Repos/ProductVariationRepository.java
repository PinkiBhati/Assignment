package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariationRepository  extends PagingAndSortingRepository<ProductVariation, Long> {

    @Query(value = "select price,quantity_available,info_json from " +
            "product_variation  where product_id=:product_id ", nativeQuery = true)
    public List<Object[]> getProductVariations(@Param(value = "product_id") long product_id);



    @Query(value = "select product_id from product_variation where id =:id",nativeQuery = true)
    public long getProductId(@Param(value = "id") long id);


    @Query(value = "select price from product_variation where id =:id",nativeQuery = true)
    public double getPrice(@Param(value = "id") long id);


    @Query(value = "select * from product_variation where product_id=:product_id",nativeQuery = true)
    List<Object[]> getProductVariation(@Param("product_id") long product_id);

    @Query(value = "select price,quantity_available,product.name,product.brand,product.description from product_variation join product " +
            "on product_variation.product_id=product.id where product_variation.id=?1 ",nativeQuery = true)
    List<Object[]> getSingleProductVariation(Long id);

}
