package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.List;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);

    boolean findRestaurantByNit(String nit);

    List<RestaurantModel> findRestaurants(Integer number);

}
