package com.hung.controller;

import com.hung.model.Order;
import com.hung.model.User;
import com.hung.service.OrderService;
import com.hung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantsOrder(id, order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@RequestHeader("Authorization") String jwt,
                                                   @PathVariable Long orderId,
                                                   @PathVariable String orderStatus) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
