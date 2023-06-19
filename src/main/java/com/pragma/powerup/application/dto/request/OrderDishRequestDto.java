package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDishRequestDto {
    private Long dishId;
    private Integer amount;
}
