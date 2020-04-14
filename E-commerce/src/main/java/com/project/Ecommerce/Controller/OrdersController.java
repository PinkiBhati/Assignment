package com.project.Ecommerce.Controller;

import com.project.Ecommerce.Dao.OrderDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Repos.CustomerRepository;
import com.project.Ecommerce.Repos.OrdersRepository;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    OrderDao orderDao;

    @Autowired
    GetCurrentDetails getCurrentDetails;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @ApiOperation("This URI is for Customer to place a order")
    @PostMapping("/placeOrder/{productVariationId}/{quantity}/{paymentMethod}/{AddressId}")
    public void placeOrder(@PathVariable Long productVariationId, @PathVariable int quantity,
                           @PathVariable String paymentMethod, @PathVariable Long AddressId)
    {

        orderDao.placeNewOrder(productVariationId,quantity,paymentMethod,AddressId);
    }


    @ApiOperation("This URI is for Customer to view his order history")
    @GetMapping("/showOrderHistory")
    public List<Object[]> getOrderHistory()
    {

        String username = getCurrentDetails.getUser();
        Customer customer =customerRepository.findByUsername(username);
        List<Object[]> objects= ordersRepository.getAllOrders(customer.getId());
        return objects;
    }

}
