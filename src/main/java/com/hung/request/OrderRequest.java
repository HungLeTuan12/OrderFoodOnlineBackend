package com.hung.request;

import com.hung.model.Address;

public class OrderRequest {
    // Attribute
    private Long restaurantId;
    private Address deliveryAddress;
    // Data

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
