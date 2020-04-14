package com.project.Ecommerce.Dao;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao {
    public void placeNewOrder(Long productVariationId,int quantity, String paymentMethod,  Long AddressId);
}
