package com.project.Ecommerce.Dao;

import com.project.Ecommerce.Entities.OrderStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusDao {
    public void updateStatus(OrderStatus orderStatus , Long productVariationId, Long orderStatusId);

}
