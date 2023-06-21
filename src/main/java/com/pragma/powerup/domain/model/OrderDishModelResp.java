package com.pragma.powerup.domain.model;


import lombok.Data;

@Data
public class OrderDishModelResp {
    private Long id;
    private DishModelResp dish;
    private Integer amount;
}
