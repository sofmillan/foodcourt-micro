package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;


import java.util.List;
import java.util.Map;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final UserClientPort userClientPort;


    public DishUseCase(IDishPersistencePort dishPersistencePort, UserClientPort userClientPort) {
        this.dishPersistencePort = dishPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void saveDish(DishModel dishModel, String token) {
        Long ownerId = userClientPort.findOwnerId(token);
        Long ownerRestaurant = dishPersistencePort.findDishRestaurant(dishModel.getRestaurantId());
        if(ownerId == null || !ownerId.equals(ownerRestaurant) ){
            throw new ForbiddenException();
        }
        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public void updateDish(Long id, Map<String, Object> fields, String token) {
        Long ownerId = userClientPort.findOwnerId(token);
        Long ownerRestaurant = dishPersistencePort.findOwnerByDish(id);
        if(!ownerId.equals(ownerRestaurant)){
            throw new ForbiddenException();
        }
        dishPersistencePort.updateDish(id, fields);

    }

    @Override
    public void updateActiveField(Long id, Map<String, Object> fields, String token) {
        Long ownerId = userClientPort.findOwnerId(token);
        Long ownerRestaurant = dishPersistencePort.findOwnerByDish(id);
        if(!ownerId.equals(ownerRestaurant)){
            throw new ForbiddenException();
        }
        dishPersistencePort.updateActiveField(id, fields);
    }

    @Override
    public List<DishModel> showMenu(Long restaurantId, Long categoryId, Integer elements, String token) {
        if(!userClientPort.validateClientByToken(token)){
            throw new ForbiddenException();
        }
        return dishPersistencePort.showMenu(restaurantId, categoryId, elements);
    }
}
