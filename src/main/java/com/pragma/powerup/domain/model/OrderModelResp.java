package com.pragma.powerup.domain.model;


import lombok.Data;

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
