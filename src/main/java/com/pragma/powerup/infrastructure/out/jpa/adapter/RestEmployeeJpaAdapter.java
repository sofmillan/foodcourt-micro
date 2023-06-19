package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantEmployeeRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestEmployeeJpaAdapter implements IRestEmployeePersistencePort {

    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestEmployeeEntityMapper restEmployeeEntityMapper;
    private final RestaurantRepository restaurantRepository;
    @Override
    public void associateEmployee(RestaurantEmployeeModel restaurantEmployeeModel) {
        restaurantEmployeeRepository.save(restEmployeeEntityMapper.toEntity(restaurantEmployeeModel));
    }

    @Override
    public boolean findRightOwner(Long ownerId, Long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(DataNotFoundException::new);
        return restaurant.getOwnerId().equals(ownerId);
    }


}

