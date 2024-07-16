package com.hung.service;

import com.hung.model.*;
import com.hung.repository.AddressRepository;
import com.hung.repository.OrderItemRepository;
import com.hung.repository.OrderRepository;
import com.hung.repository.UserRepository;
import com.hung.request.OrderRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {
        Address shippingAddress = req.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());

        Order order = new Order();

        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setCreatedAt((java.sql.Date) new Date());
        order.setOrderStatus("PENDING");
        order.setDeliveryAddress(savedAddress);

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        restaurant.getOrders().add(savedOrder);

        return order;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        if(orderStatus.equals("OUT_fOR_DELIVERY")
                    || orderStatus.equals("DELIVERED")
                    || orderStatus.equals("COMPLETED")
                    || orderStatus.equals("PENDING")
        )    {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please enter the valid status...");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orders != null) {
            orders = orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if(orderOptional.isEmpty()) {
            throw new Exception("Order is not found...");
        }
        return orderOptional.get();
    }
}
