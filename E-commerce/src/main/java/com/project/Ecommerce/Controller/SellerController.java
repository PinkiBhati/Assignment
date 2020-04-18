package com.project.Ecommerce.Controller;

import com.project.Ecommerce.DTO.SellerDTO;
import com.project.Ecommerce.DTO.SellerProfileDTO;
import com.project.Ecommerce.Dao.SellerDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.Seller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api
@RestController
public class SellerController {

    @Autowired
    private SellerDao sellerDao;

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to view his profile")
    @GetMapping("/viewProfileForSeller")
    public SellerProfileDTO viewProfileForSeller()
    {
        return sellerDao.viewProfileOfSeller();
    }

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to update his profile")
    @PutMapping("/updateProfileForSeller")
    public String  updateProfileForSeller(@RequestBody SellerProfileDTO sellerProfileDTO)
    {
        sellerDao.updateProfileForSeller(sellerProfileDTO);
        return "Your profile is successfully updated";
    }


    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to get a Customer Account")
    @PutMapping("/getNewAccountAsCustomer")
    public String getNewAccountAsCustomer(@RequestBody Customer customer)
    {
        return  sellerDao.getAnCustomerAccount(customer);
    }



    /* @PostMapping("/refundOrder/{orderStatusId}")
    public void refundOrder(@PathVariable int orderStatusId)
    {
        Optional<OrderStatus> orderStatusOptional =orderStatusRepository.findById(orderStatusId);
        OrderStatus orderStatus = orderStatusOptional.get();


        if(orderStatus.getToStatus()==ToStatus.CANCELLED)
        {

            orderStatus.setFromStatus(FromStatus.CANCELLED);
            orderStatus.setToStatus(ToStatus.REFUND_INITIATED);

            orderStatusRepository.save(orderStatus);
        }

        else{
            System.out.println("You cant request for refund");
        }

    }*/





}