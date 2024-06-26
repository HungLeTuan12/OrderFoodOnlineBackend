package com.hung.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class RestaurantDto {
    // Attribute
    private String title;
    @Column(length = 1000)
    private List<String> images;
    private String description;
    private Long id;
}
