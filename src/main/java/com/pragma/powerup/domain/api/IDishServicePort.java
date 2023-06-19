package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.DishModel;
import java.util.List;
import java.util.Map;

public interface IDishServicePort {
    void saveDish(DishModel dishModel, String token);

    void updateDish(Long id, Map<String, Object> fields, String token);

    void updateActiveField(Long id, Map<String, Object> fields, String token);

    List<DishModel> showMenu(Long restaurantId, Long categoryId, Integer elements, String token);

}
