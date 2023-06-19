package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.application.handler.IRestaurantEmployeeHandler;
import com.pragma.powerup.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantEmployeeHandler implements IRestaurantEmployeeHandler {

    private final IRestEmployeeServicePort iRestEmployeeServicePort;
    private final IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    @Override
    public void associateEmployee(RestaurantEmployeeRequestDto employeeRequestDto, String token) {
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmployeeRequestMapper.toModel(employeeRequestDto);
        iRestEmployeeServicePort.associateEmployee(restaurantEmployeeModel, token);
    }
}
