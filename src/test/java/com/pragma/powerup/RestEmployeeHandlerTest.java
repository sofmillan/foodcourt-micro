package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.application.handler.IRestaurantEmployeeHandler;
import com.pragma.powerup.application.handler.impl.RestaurantEmployeeHandler;
import com.pragma.powerup.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.usecase.RestaurantEmployeeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestEmployeeHandlerTest {
    IRestaurantEmployeeHandler restaurantEmployeeHandler;
    IRestEmployeeServicePort restEmployeeService;
    IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;

    @BeforeEach
    void setUp(){
        restEmployeeService = mock(RestaurantEmployeeUseCase.class);
        restaurantEmployeeRequestMapper = mock(IRestaurantEmployeeRequestMapper.class);
        restaurantEmployeeHandler = new RestaurantEmployeeHandler(restEmployeeService, restaurantEmployeeRequestMapper);
    }

    @Test
    void Should_AssociateEmployee(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        RestaurantEmployeeRequestDto requestDto = new RestaurantEmployeeRequestDto();
        requestDto.setRestaurantId(1L);
        requestDto.setUserId(1L);


        RestaurantEmployeeModel modelRestEmployee = new RestaurantEmployeeModel();
        modelRestEmployee.setRestaurantId(1L);
        modelRestEmployee.setUserId(1L);
        when(restaurantEmployeeRequestMapper.toModel(requestDto)).thenReturn(modelRestEmployee);

        restaurantEmployeeHandler.associateEmployee(requestDto, token);

        verify(restEmployeeService).associateEmployee(modelRestEmployee, token);
    }
}
