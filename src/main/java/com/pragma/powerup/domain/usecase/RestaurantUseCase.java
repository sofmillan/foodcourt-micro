package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.DataAlreadyExistsException;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;


import java.util.List;


public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final UserClientPort userClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, UserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel, String token) {
        if(!userClientPort.validateAdministrator(token)){
            throw new ForbiddenException();
        }
        if(restaurantPersistencePort.findRestaurantByNit(restaurantModel.getNit())){
            throw new DataAlreadyExistsException();
        }

        if(!userClientPort.validateOwnerById(restaurantModel.getOwnerId())){
            throw new DataNotValidException();
        }
        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }


    @Override
    public List<RestaurantModel> findRestaurants(Integer number, String token) {
        if(!userClientPort.validateClientByToken(token)){
            throw new ForbiddenException();
        }
        if (number<0){
            throw new DataNotValidException();
        }

        return restaurantPersistencePort.findRestaurants(number);
    }
}
