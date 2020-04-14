package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long> {

    @Query(value = "select amount_paid,payment_method from orders join order_product " +
            "on orders.id= order_product.order_id where orders.customer_user_id =:customer_user_id",nativeQuery = true)
    public List<Object[]> getAllOrders(@Param(value = "customer_user_id") long customer_user_id);
}
