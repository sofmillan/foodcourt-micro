package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;

public interface IRestEmployeeServicePort {

    void associateEmployee(RestaurantEmployeeModel restaurantEmployeeModel, String token);
}

