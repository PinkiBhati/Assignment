package com.project.Ecommerce.Dao;

import java.util.List;

public interface CartDao {

    public void addToCart(Long product_variation_id,int quantity);

    public void deleteFromCart(Long product_variation_id);

    public void emptyCart();

    public List<Object[]> viewCart();
    public void placeOrderFromCart(Long cartId,String paymentMethod,Long AddressId);
    public void orderWholeCart( Long AddressId);
}
