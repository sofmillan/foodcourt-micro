package com.pragma.powerup.domain.model;

import lombok.Data;

@Data
public class DishModelResp {

    private Long id;

    private String name;
    private String description;
    private String imageUrl;
}
