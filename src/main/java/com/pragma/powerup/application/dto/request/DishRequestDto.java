package com.pragma.powerup.application.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRequestDto {
    @NotNull(message = "Dish name must not be null")
    @NotBlank(message = "Dish name cannot be an empty string")
    private String name;
    @Min(value = 0, message = "Price must be greater than 0")
    @NotNull(message = "Price must not be null")
    private Integer price;
    @NotNull(message = "Dish description must not be null")
    @NotBlank(message = "Dish description cannot be an empty string")
    private String description;
    @NotNull(message = "Dish image must not be null")
    @NotBlank(message = "Dish image url cannot be an empty string")
    @URL(message = "Dish image url is not valid")
    private String imageUrl;
    @NotNull(message = "Category Id must not be null")
    private Long categoryId;
    @NotNull(message = "restaurant Id must not be null")
    private Long restaurantId;
}