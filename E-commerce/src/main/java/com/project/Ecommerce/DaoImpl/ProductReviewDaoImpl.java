package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.Dao.ProductReviewDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Product;
import com.project.Ecommerce.Entities.ProductReview;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.CustomerRepository;
import com.project.Ecommerce.Repos.ProductRepository;
import com.project.Ecommerce.Repos.ProductReviewRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewDaoImpl implements ProductReviewDao {


    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    GetCurrentlyLoggedInUser getCurrentUser;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private MessageSource messageSource;
    Long[] params={};

    @Override
    public void addReview(ProductReview productReview, Long productId) {
        String username = getCurrentUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        productReview.setCustomer(customer);
        Optional<Product> product = productRepository.findById(productId);
        productReview.setProduct(product.get());
        productReviewRepository.save(productReview);
    }

    @Override
    public List<Object[]> getReviews(Long productId) {

        List<Object[]> list = productReviewRepository.getAllReviews(productId);
        if (list.isEmpty()) {
            throw new NullException(messageSource.getMessage("message33",params , LocaleContextHolder.getLocale()));
        }
        return list;
    }
}

