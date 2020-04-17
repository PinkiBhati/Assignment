package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.Dao.CartDao;
import com.project.Ecommerce.Dao.OrderDao;
import com.project.Ecommerce.Entities.Cart;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.ProductVariation;
import com.project.Ecommerce.Repos.CartRepository;
import com.project.Ecommerce.Repos.CustomerRepository;
import com.project.Ecommerce.Repos.ProductVariationRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDaoImpl implements CartDao {

    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public void addToCart(Long productVariationId, int quantity) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer user = customerRepository.findByUsername(username);
        Cart cart = new Cart();
        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        cart.setProductVariation(productVariation.get());
        cart.setQuantity(quantity);
        cart.setCustomer(user);
        productVariation.get().addCarts(cart);
        cartRepository.save(cart);
    }


    @Override
    public void deleteFromCart(Long productVariationId) {
        cartRepository.deleteByProductVariationId(productVariationId);
    }

    @Override
    public void emptyCart() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        cartRepository.emptyUserCart(customer.getId());
    }

    @Override
    public List<Object[]> viewCart() {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer customer = customerRepository.findByUsername(username);
        List<Object[]> list = cartRepository.viewCart(customer.getId());
        if (list.isEmpty()) {
            System.out.println("cart is empty");
        }
        return list;

    }

    @Override
    public void placeOrderFromCart(Long cartId, String paymentMethod, Long AddressId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Cart cart = cartOptional.get();
        ProductVariation productVariation = cart.getProductVariation();
        int quantity = cart.getQuantity();
        orderDao.placeNewOrder(productVariation.getId(), quantity, paymentMethod, AddressId);
        deleteFromCart(productVariation.getId());


    }

    @Override
    public void orderWholeCart(Long AddressId) {
        for (Cart cart : cartRepository.findAll()) {
            int quantity = cart.getQuantity();
            ProductVariation productVariation = cart.getProductVariation();
            orderDao.placeNewOrder(productVariation.getId(), quantity, "COD", AddressId);
            emptyCart();

        }
    }
}
