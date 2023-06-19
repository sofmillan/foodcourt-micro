package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;

import java.util.List;
import java.util.Map;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto, String token);


    void updateDish(Long id, Map<String, Object> fields, String token);

    void updateActiveField(Long id, Map<String, Object> fields, String token);

    List<DishPageResponseDto> showMenu(Long restaurantId, Long categoryId, Integer number, String token);
}
