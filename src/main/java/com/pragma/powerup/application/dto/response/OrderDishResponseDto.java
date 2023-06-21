package com.pragma.powerup.application.dto.response;


import lombok.Data;

@Data
public class OrderDishResponseDto {
    private Long id;
    private DishResponseDto dish;
    private Integer amount;
}
