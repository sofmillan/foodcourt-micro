package com.pragma.powerup.domain.model;

import com.pragma.powerup.application.dto.request.OrderDishRequestDto;

import java.util.List;

public class OrderModel {

    private Long restaurantId;

    private List<OrderDishModel> orderDishes;


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderDishModel> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDishModel> orderDishes) {
        this.orderDishes = orderDishes;
    }
}
