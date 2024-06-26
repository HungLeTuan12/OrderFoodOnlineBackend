package com.hung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.dto.RestaurantDto;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class Order {
    // Attribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User customer;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    private Long totalAmount;
    private String orderStatus;
    private Date createdAt;
    @ManyToOne
    private Address deliveryAddress;
    @OneToMany
    private List<OrderItem> items;
    private int totalItem;
    private int totalPrice;
//    private Payment payment;
}
