package com.pragma.powerup.domain.model;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderModelResp {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Set<OrderDishModelResp> orderDishes;
}
