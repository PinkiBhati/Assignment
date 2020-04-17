package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.OrderStatusDao;
import com.project.Ecommerce.Entities.NotificationService;
import com.project.Ecommerce.Entities.OrderStatus;
import com.project.Ecommerce.Repos.OrderStatusRepository;
import com.project.Ecommerce.Repos.UserRepository;
import com.project.Ecommerce.Utilities.GetCurrentlyLoggedInUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class OrderStatusController {

    @Autowired
    OrderStatusDao orderStatusDao;

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    GetCurrentlyLoggedInUser getCurrentlyLoggedInUser;

    @Secured("ROLE_SELLER")
    @ApiOperation("This URI is for Seller to update the status of the product ")
    @PostMapping("/setStatus/{productVariationId}/{orderStatusId}")
    public void setStatus(@RequestBody OrderStatus orderStatus ,
                          @PathVariable Long productVariationId, @PathVariable Long orderStatusId) {

        orderStatusDao.updateStatus(orderStatus,productVariationId,orderStatusId);
    }


  /*  @Scheduled(cron = "0 0 * * *")
    public void schedule()
    {
        for(OrderStatus orderStatus:orderStatusRepository.findAll())
        if(orderStatus.getToStatus()== ORDER_REJECTED)
        {

            notificationService.sendNotification(user);
        }
    }*/


}
