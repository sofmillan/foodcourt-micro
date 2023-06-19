package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;

public interface IRestEmployeePersistencePort {

    void associateEmployee(RestaurantEmployeeModel restaurantEmployeeModel);

    boolean findRightOwner(Long ownerId, Long restaurantId);
}
