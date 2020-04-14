package com.project.Ecommerce.Repos;


import com.project.Ecommerce.Entities.OrderProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderProductRepository extends PagingAndSortingRepository<OrderProduct, Long> {
}
