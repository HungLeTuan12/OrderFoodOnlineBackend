package com.hung.request;

public class IngredientCategoryRequest {
    // Attribute
    private String name;
    private Long restaurantId;
    // Data

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
