package com.project.Ecommerce.DaoImpl;


import com.project.Ecommerce.Dao.CartDao;
import com.project.Ecommerce.Dao.OrderDao;
import com.project.Ecommerce.Entities.Cart;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.ProductVariation;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.ExceptionHandling.ProductNotFoundException;
import com.project.Ecommerce.Repos.AddressRepository;
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
    @Autowired
    AddressRepository addressRepository;


    @Override
    public void addToCart(Long productVariationId, int quantity) {
        String username = getCurrentlyLoggedInUser.getCurrentUser();
        Customer user = customerRepository.findByUsername(username);
        Cart cart = new Cart();
        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        if(productVariation.isPresent()){
            cart.setProductVariation(productVariation.get());
            cart.setQuantity(quantity);
            cart.setCustomer(user);
            productVariation.get().addCarts(cart);
            cartRepository.save(cart);
        }
        else {
            throw new ProductNotFoundException("Product Variation is not present");
        }
    }


    @Override
    public void deleteFromCart(Long productVariationId) {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        if(productVariation.isPresent()){
            cartRepository.deleteByProductVariationId(productVariationId);
        }
        else {
            throw new ProductNotFoundException("Product Variation is not present");
        }
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
        if(cartOptional.isPresent()){
            if(addressRepository.findById(AddressId).isPresent()){
                Cart cart = cartOptional.get();
                ProductVariation productVariation = cart.getProductVariation();
                int quantity = cart.getQuantity();
                orderDao.placeNewOrder(productVariation.getId(), quantity, paymentMethod, AddressId);
                deleteFromCart(productVariation.getId());
            }
            else{
                throw new NullException("Address is not present");
            }
        }
        else {
            throw new NullException("Cart Id is wrong");
        }



    }

    @Override
    public void orderWholeCart(Long AddressId) {
        if(addressRepository.findById(AddressId).isPresent()){
            for (Cart cart : cartRepository.findAll()) {
                int quantity = cart.getQuantity();
                ProductVariation productVariation = cart.getProductVariation();
                orderDao.placeNewOrder(productVariation.getId(), quantity, "COD", AddressId);
                emptyCart();

            }
        }
        else{
            throw new NullException("Address is not present");
        }

    }
}
