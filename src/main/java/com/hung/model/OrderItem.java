package com.hung.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class OrderItem {
    // Attribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Food food;
    private int quantity;
    private Long totalPrice;
    private List<String> ingredients;
}
