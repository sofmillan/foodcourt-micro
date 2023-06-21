package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;

import java.util.List;
import java.util.Map;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);
    void updateDish(Long id, Map<String, Object> fields);

    void updateActiveField(Long id, Map<String, Object> fields);

    List<DishModel> showMenu(Long restaurantId, Long categoryId, Integer elements);

    Long findDishRestaurant(Long idRestaurant);

    Long findOwnerByDish(Long dishId);
}
