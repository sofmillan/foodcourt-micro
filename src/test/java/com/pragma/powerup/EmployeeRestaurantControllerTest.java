package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.application.handler.IRestaurantEmployeeHandler;
import com.pragma.powerup.application.handler.impl.RestaurantEmployeeHandler;
import com.pragma.powerup.infrastructure.input.rest.EmployeeRestaurantController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class EmployeeRestaurantControllerTest {
    IRestaurantEmployeeHandler restaurantEmployeeHandler;
    EmployeeRestaurantController employeeRestaurantController;
    RestaurantEmployeeRequestDto restaurantEmployeeRequestDto;
    String token;

    @BeforeEach
    void setUp(){
        restaurantEmployeeHandler = mock(RestaurantEmployeeHandler.class);
        employeeRestaurantController = new EmployeeRestaurantController(restaurantEmployeeHandler);
        restaurantEmployeeRequestDto = new RestaurantEmployeeRequestDto();
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_EmployeeSuccessfullyAssociated(){
        restaurantEmployeeRequestDto.setRestaurantId(1L);
        restaurantEmployeeRequestDto.setUserId(1L);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        doNothing().when(restaurantEmployeeHandler).associateEmployee(restaurantEmployeeRequestDto, token);

        ResponseEntity<Void> responseEntity = employeeRestaurantController.associateEmployee(restaurantEmployeeRequestDto, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(restaurantEmployeeHandler).associateEmployee(restaurantEmployeeRequestDto, token);
    }
}
