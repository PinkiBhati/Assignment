package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.ProductReview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewDao {
    public void addReview(ProductReview productReview, Long productId);

    public List<Object[]> getReviews(Long productId);
}
