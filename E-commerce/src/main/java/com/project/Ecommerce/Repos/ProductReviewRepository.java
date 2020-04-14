package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.ProductReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends PagingAndSortingRepository<ProductReview, Long> {

    @Query(value = "select review,rating from product_review where product_id=:product_id",nativeQuery = true)
    List<Object[]> getAllReviews(@Param("product_id") long product_id);
}
