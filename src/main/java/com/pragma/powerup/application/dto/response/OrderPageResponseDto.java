package com.pragma.powerup.application.dto.response;


import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderPageResponseDto {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Set<OrderDishResponseDto> orderDishes;
}
