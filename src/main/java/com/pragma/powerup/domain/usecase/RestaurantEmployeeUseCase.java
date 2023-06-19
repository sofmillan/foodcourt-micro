package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.exception.DataNotValidException;

public class RestaurantEmployeeUseCase implements IRestEmployeeServicePort {

    private final IRestEmployeePersistencePort restEmployeePersistencePort;
    private final UserClientPort userClientPort;


    public RestaurantEmployeeUseCase(IRestEmployeePersistencePort restEmployeePersistencePort, UserClientPort userClientPort) {
        this.restEmployeePersistencePort = restEmployeePersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void associateEmployee(RestaurantEmployeeModel restaurantEmployeeModel, String token) {
        Long ownerId = userClientPort.findOwnerId(token);
        if(!restEmployeePersistencePort.findRightOwner(ownerId, restaurantEmployeeModel.getRestaurantId())){
            throw new DataNotValidException();
        }
        restEmployeePersistencePort.associateEmployee(restaurantEmployeeModel);
    }
}

