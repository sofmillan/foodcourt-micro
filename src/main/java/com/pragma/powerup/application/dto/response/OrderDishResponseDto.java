package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.DishModelResp;
import lombok.Data;

@Data
public class OrderDishResponseDto {
    private Long id;
    private DishResponseDto dish;
    private Integer amount;
}
