package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Dao.CartDao;
import com.project.Ecommerce.Dao.OrderDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class CartController {
    @Autowired
    CartDao cartDao;

    @Autowired
    OrderDao orderDao;

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to add a product Variation to his Cart along with the quantity specified")
    @PostMapping("/addToCart/{productVariationId}/{quantity}")
    public String addToCart(@PathVariable("productVariationId") Long productVariationId, @PathVariable("quantity") int quantity) {
        cartDao.addToCart(productVariationId, quantity);
        return "product added to cart successfully";
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to delete a productVariation from his cart")
    @DeleteMapping("/deleteFromCart/{productVariationId}")
    public String deleteFromCart(@PathVariable("productVariationId") Long productVariationId) {
        cartDao.deleteFromCart(productVariationId);
        return "success";
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to view all the productVariations in his Cart")
    @GetMapping("/viewCart")
    public List<Object[]> viewCart() {
        return cartDao.viewCart();
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to delete all the productVariations from his Cart")
    @DeleteMapping("/emptyCart")
    public String emptyCart() {
        cartDao.emptyCart();
        return "success";
    }


    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to place a order for one of the productVariation from his" +
            " cart along with the address specified and the payment mode")
    @PostMapping("/OrderOneProductFromCart/{cartId}/{paymentMethod}/{AddressId}")
    public void placeOrderFromCart(@PathVariable("cartId") Long cartId, @PathVariable("paymentMethod") String paymentMethod,
                                   @PathVariable("AddressId") Long AddressId) {
        cartDao.placeOrderFromCart(cartId, paymentMethod, AddressId);

    }

    @Secured("ROLE_CUSTOMER")
    @ApiOperation("This URI is for Customer to place order for all the ProductVariation from his cart " +
            "along with the address specified")
    @PostMapping("/OrderWholeCart/{AddressId}")
    public void orderWholeCart(@PathVariable("AddressId") Long AddressId) {
        cartDao.orderWholeCart(AddressId);
    }
}
