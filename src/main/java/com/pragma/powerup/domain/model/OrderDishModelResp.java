package com.pragma.powerup.domain.model;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import lombok.Data;

@Data
public class OrderDishModelResp {
    private Long id;
    private DishModelResp dish;
    private Integer amount;
}
