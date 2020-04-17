package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.OrderStatusDao;
import com.project.Ecommerce.Entities.OrderStatus;
import com.project.Ecommerce.Entities.Product;
import com.project.Ecommerce.Entities.Seller;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.OrderStatusRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.ProductVariationRepository;
import com.project.Ecommerce.Repos.SellerRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderStatusDaoImpl implements OrderStatusDao {

    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Override
    public void updateStatus(OrderStatus orderStatus, Long productVariationId, Long orderStatusId) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Seller seller = sellerRepository.findByUsername(username);
        Long productId = productVariationRepository.getProductId(productVariationId);
        Optional<Product> productOptional = productRepository.findById(productId);
        Product product1 = productOptional.get();
        if ((product1.getSellers().getUsername()).equals(seller.getUsername())) {
            Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusId);
            OrderStatus orderStatus1 = orderStatusOptional.get();
            orderStatus1.setFromStatus(orderStatus.getFromStatus());
            orderStatus1.setToStatus(orderStatus.getToStatus());
            orderStatus1.setTransitionNotesComments(orderStatus.getTransitionNotesComments());
            orderStatusRepository.save(orderStatus1);
        } else {
            throw new NullException("you can't change the to status of this product");
        }
    }

}
