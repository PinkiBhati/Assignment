package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.OrderDao;
import com.project.Ecommerce.Dao.ProductVariationDao;
import com.project.Ecommerce.Entities.*;
import com.project.Ecommerce.Enums.FromStatus;
import com.project.Ecommerce.ExceptionHandling.NullException;
import com.project.Ecommerce.Repos.*;
import com.project.Ecommerce.Utilities.GetCurrentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDaoImpl implements OrderDao {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    GetCurrentDetails getCurrentDetails;

    @Autowired
    ProductVariationDao productVariationDao;

    @Override
    public void placeNewOrder(Long productVariationId, int quantity, String paymentMethod, Long AddressId) {
        String username = getCurrentDetails.getUser();
        Customer customer = customerRepository.findByUsername(username);
        productVariationDao.updateQuantity(productVariationId, quantity);
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
        ProductVariation productVariation = productVariationOptional.get();
        if (productVariation.isActive() == false) {
            throw new NullException("Item is not available");
        }
        Optional<Address> addressOptional = addressRepository.findById(AddressId);
        Address address = null;
        if (addressOptional.isPresent()) {
            address = addressOptional.get();
        } else {
            throw new NullException("Add an address to place a  order");
        }

        Orders orders = new Orders();
        orders.setAmountPaid((productVariationRepository.getPrice(productVariationId)) * quantity);
        orders.setCustomer(customer);
        orders.setPaymentMethod(paymentMethod);
        orders.setCustomerAddressCity(address.getCity());
        orders.setCustomerAddressCountry(address.getCountry());
        orders.setCustomerAddressLabel(address.getLabel());
        orders.setCustomerAddressState(address.getState());
        orders.setCustomerAddressZipCode(address.getZipCode());

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPrice((productVariationRepository.getPrice(productVariationId)) * quantity);
        orderProduct.setQuantity(quantity);
        orderProduct.setOrders(orders);
        orderProduct.setProductVariationMetaData(productVariation.getInfoJson());
        orderProduct.setProductVariation(productVariation);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setFromStatus(FromStatus.ORDER_PLACED);
        orderStatus.setOrderProduct(orderProduct);

        ordersRepository.save(orders);
        productVariationRepository.save(productVariation);
        orderProductRepository.save(orderProduct);
        addressRepository.save(address);
        orderStatusRepository.save(orderStatus);
    }
}
