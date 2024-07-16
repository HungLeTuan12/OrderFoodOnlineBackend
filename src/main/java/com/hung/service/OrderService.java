package com.hung.service;

import com.hung.model.Order;
import com.hung.model.User;
import com.hung.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest req, User user) throws Exception;
    public Order updateOrder(Long orderId, String orderStatus) throws Exception;
    public void cancelOrder(Long orderId) throws Exception;
    public List<Order> getUsersOrder(Long userId);
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus);
    public Order findOrderById(Long orderId) throws Exception;
}
