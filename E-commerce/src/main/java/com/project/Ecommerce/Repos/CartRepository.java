package com.project.Ecommerce.Repos;

import com.project.Ecommerce.Entities.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CartRepository  extends PagingAndSortingRepository<Cart, Long> {


    @Modifying
    @Transactional
    @Query(value = "delete from cart where product_variation_id=:product_variation_id",nativeQuery = true)
    void deleteByProductVariationId(@Param("product_variation_id") long product_variation_id);

    @Modifying
    @Transactional
    @Query(value = "delete from cart where customer_id=:customer_id",nativeQuery = true)
    void emptyUserCart(@Param("customer_id") long customer_id);

    @Query(value = "select info_attributes,price,product_id,name from product_variation join " +
            "product on product_variation.product_id=product.id and product_variation.id in" +
            " (select product_variation_id from cart where customer_id=:customer_id)",nativeQuery = true)
    List<Object[]> viewCart(@Param("customer_id") long customer_id);
}
